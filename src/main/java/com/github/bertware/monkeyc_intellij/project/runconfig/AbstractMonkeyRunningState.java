package com.github.bertware.monkeyc_intellij.project.runconfig;

import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.sdk.SdkHelper;
import com.github.bertware.monkeyc_intellij.project.sdk.tools.SimulatorHelper;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.execution.util.ProgramParametersUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VirtualDirectoryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.github.bertware.monkeyc_intellij.Utils.createGeneralCommandLine;
import static java.util.Objects.requireNonNull;

public abstract class AbstractMonkeyRunningState extends CommandLineState {
  private MonkeyParameters monkeyParameters;
  private final boolean forTests;

  protected AbstractMonkeyRunningState(ExecutionEnvironment environment, boolean forTests) {
    super(environment);
    this.forTests = forTests;
  }

  public boolean isForTests() {
    return forTests;
  }

  @Override
  @NotNull
  public abstract ExecutionResult execute(@NotNull final Executor executor, @NotNull final ProgramRunner runner)
    throws ExecutionException;

  @NotNull
  @Override
  protected ProcessHandler startProcess() throws ExecutionException {
    throw new UnsupportedOperationException("overridden execute() does not use startProcess()");
  }

  protected MonkeyParameters getMonkeyParameters() throws ExecutionException {
    if (monkeyParameters == null) {
      monkeyParameters = createMonkeyParameters();
    }
    return monkeyParameters;
  }

  //TODO: tests only
  protected MonkeyParameters createMonkeyParameters() throws ExecutionException {
    final MonkeyParameters params = new MonkeyParameters();
    final AbstractMonkeyRunConfiguration runConfig = getConfiguration();
    final AbstractMonkeyRunConfigurationModule configurationModule = runConfig.getConfigurationModule();
    final int classPathType = MonkeyParameters.SDK_AND_CLASSES;
    params.configureByModule(configurationModule.getModule(), classPathType, isForTests());
    ProgramParametersUtil.configureConfiguration(params, runConfig);
    return params;
  }

  protected AbstractMonkeyRunConfiguration getConfiguration() {
    if (getEnvironment().getRunnerAndConfigurationSettings() == null) {
      throw new RuntimeException("runnerAndConfigurationSettings is null");
    }
    final RunConfiguration configuration = getEnvironment().getRunnerAndConfigurationSettings().getConfiguration();
    if (configuration == null || !(configuration instanceof AbstractMonkeyRunConfiguration)) {
      throw new RuntimeException("runnerAndConfigurationSettings.getConfiguration() is null or wrong type");
    }
    return (AbstractMonkeyRunConfiguration) configuration;
  }

  protected ExecutionResult runOnSimulator(ConsoleView console, Executor executor) throws ExecutionException {
    ProcessHandler runInSimHandler = startInSimulator();
    if (console != null) {
      console.attachToProcess(runInSimHandler);
    }

    AnAction[] actions = createActions(console, runInSimHandler, executor);
    return new DefaultExecutionResult(console, runInSimHandler, actions);
  }

  protected ProcessHandler startInSimulator() throws ExecutionException {
    GeneralCommandLine runInSimulatorCmd = createRunInSimulatorCmd();
    return new KillableColoredProcessHandler(runInSimulatorCmd);
  }

  protected void runSimulator(ConsoleView console) throws ExecutionException {
    MonkeyParameters monkeyParameters = getMonkeyParameters();
    Sdk sdk = monkeyParameters.getSdk();
    String outputDir = monkeyParameters.getOutputPath().getPath() + File.separator;
    SimulatorHelper simulatorHelper = new SimulatorHelper(console, sdk, outputDir);

    GeneralCommandLine runSimulatorCmd = createRunSimulatorCmd();
    runSimulatorCmd.createProcess();

    if (!simulatorHelper.findSimulatorPortNTimes(5, 1000).isPresent()) {
      throw new ExecutionException("Could not connect to simulatorHelper");
    }
  }

  // doesn't really run, but just copies prg on the device
  protected ExecutionResult runOnRealDevice(ConsoleView console) throws ExecutionException {
    VirtualFile copiedPrg = copyBuiltPrgToDevice();

    String message = String.format("Generated %s\nYou can now start the app on your device.", copiedPrg);
    console.print(message, ConsoleViewContentType.NORMAL_OUTPUT);
    ProcessHandler processHandler = new DummyProcessHandler();
    processHandler.destroyProcess();
    return new DefaultExecutionResult(console, processHandler);
  }

  protected VirtualFile copyBuiltPrgToDevice() throws ExecutionException {
    try {
      MonkeyParameters monkeyParameters = getMonkeyParameters();
      String prgPath = monkeyParameters.getOutputPath().findChild(getPrgName()).getPath();
      String deviceDirectory = getConfiguration().getDeviceDirectory();

      return copyPrgTo(prgPath, deviceDirectory);
    } catch (IOException e) {
      throw new ExecutionException(e);
    }
  }

  @NotNull
  protected VirtualFile copyPrgTo(String prgOutputPath, String outputPath) throws IOException {
    LocalFileSystem lfs = LocalFileSystem.getInstance();
    VirtualFile fromOutputPrg = lfs.refreshAndFindFileByPath(prgOutputPath);
    requireNonNull(fromOutputPrg);

    VirtualDirectoryImpl toOutputDirectory = (VirtualDirectoryImpl) lfs.refreshAndFindFileByPath(outputPath);
    requireNonNull(toOutputDirectory);

    String fileName = fromOutputPrg.getName();

    VirtualFile existingPrg = toOutputDirectory.refreshAndFindChild(fileName);

    return ApplicationManager.getApplication()
      .runWriteAction((ThrowableComputable<VirtualFile, IOException>) () -> {
          if (existingPrg != null) {
            if (existingPrg.isDirectory()) {
              // just in case to catch mistakes
              throw new IllegalStateException("prg path should be file not directory: " + existingPrg);
            }
            existingPrg.delete(this);
          }
          return fromOutputPrg.copy(this, toOutputDirectory, fileName);
        }
      );
  }

  protected GeneralCommandLine createRunSimulatorCmd() throws ExecutionException {
    final Sdk sdk = getMonkeyParameters().getSdk();
    String sdkBinPath = MonkeySdkType.getBinPath(sdk);

    String simulatorExecutableName = SdkHelper.get(SdkHelper.SIMULATOR_CMD);
    String exePath = sdkBinPath + simulatorExecutableName;
    return createGeneralCommandLine(sdkBinPath, exePath).withRedirectErrorStream(true);
  }

  protected GeneralCommandLine createRunInSimulatorCmd() throws ExecutionException {
    MonkeyParameters monkeyParameters = getMonkeyParameters();
    VirtualFile child = monkeyParameters.getOutputPath().findChild(getPrgName());
    if (child == null) {
      throw new ExecutionException("No .prg file was built, so nothing to run, searched path was " +
        monkeyParameters.getOutputPath() + " and prg name " + getPrgName());
    }
    String prgPath = child.getPath();

    Sdk sdk = monkeyParameters.getSdk();

    String sdkBinPath = MonkeySdkType.getBinPath(sdk);
    String monkeydoExecutableName = SdkHelper.get(SdkHelper.MONKEYDO_CMD);
    String exePath = sdkBinPath + monkeydoExecutableName;
    GeneralCommandLine generalCommandLine = createGeneralCommandLine(sdkBinPath, exePath)
      .withParameters(prgPath, getConfiguration().getTargetDeviceId());

    if (isForTests()) {
      generalCommandLine.addParameter(SdkHelper.get(SdkHelper.MONKEYDO_TEST_PARAM));
      //if (testId != null) {
      //  generalCommandLine.addParameter(testId);
      //}
    }

    if (getConfiguration().getProgramParameters() != null) {
      ParametersList parametersList = new ParametersList();
      parametersList.addParametersString(getConfiguration().getProgramParameters());
      generalCommandLine.addParameters(parametersList.getParameters());
    }

    return generalCommandLine;
  }

  protected String getPrgName() {
    return getEnvironment().getProject().getName() + ".prg";
  }

  protected static class DummyProcessHandler extends ProcessHandler {
    @Override
    protected void destroyProcessImpl() {
      notifyProcessTerminated(0);
    }

    @Override
    protected void detachProcessImpl() {
      notifyProcessDetached();
    }

    @Override
    public boolean detachIsDefault() {
      return false;
    }

    @Nullable
    @Override
    public OutputStream getProcessInput() {
      return null;
    }
  }
}
