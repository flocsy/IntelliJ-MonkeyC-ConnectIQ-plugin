package com.github.bertware.monkeyc_intellij.project.sdk.tools;

import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.sdk.SdkHelper;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.projectRoots.Sdk;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import static com.github.bertware.monkeyc_intellij.Utils.createGeneralCommandLine;


public class SimulatorHelper {
  public static final int SIMULATOR_PORT_MIN = 1234;
  public static final int SIMULATOR_PORT_MAX = 1238;
  public static final String SHELL_SUCCESS_CONNECTED_TO_SIMULATOR = "Connection Finished";

  @Nullable
  private ConsoleView console;

  @NotNull
  private final Sdk sdk;

  @NotNull
  private final String outputDir;

  public SimulatorHelper(@Nullable ConsoleView console, @NotNull Sdk sdk, @NotNull String outputDir) throws ExecutionException {
    this.console = console;
    this.sdk = sdk;
    this.outputDir = outputDir;
  }

  public Optional<Integer> findSimulatorPortNTimes(int maxAttempts, int delay) throws ExecutionException {
    for (int i = 0; i < maxAttempts; i++) {
      Optional<Integer> simulatorPort = findSimulatorPort(i + 1);
      if (simulatorPort.isPresent()) {
        return simulatorPort;
      }
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        printToConsole(e.getMessage(), ConsoleViewContentType.ERROR_OUTPUT);
      }
    }
    return Optional.empty();
  }

  private Optional<Integer> findSimulatorPort(int i) {
    for (int port = SIMULATOR_PORT_MIN; port < SIMULATOR_PORT_MAX; port++) {
      printToConsole("Attempt " + i + " to connect to simulator (port " + port + ")...\n", ConsoleViewContentType.NORMAL_OUTPUT);
      try {
        Process process = createShellCmd(port).createProcess();
        if (hasProcessOutputLineContaining(process, SHELL_SUCCESS_CONNECTED_TO_SIMULATOR)) {
          return Optional.of(port);
        }
      } catch (ExecutionException e) {
        printToConsole(e.getMessage(), ConsoleViewContentType.ERROR_OUTPUT);
      }
    }
    return Optional.empty();
  }

  // with shell we can poll if simulator is running and ready
  private GeneralCommandLine createShellCmd(int port) throws ExecutionException {
    String shellExecutableName = SdkHelper.get(SdkHelper.SHELL_CMD);
    String sdkBinPath = MonkeySdkType.getBinPath(sdk);
    String exePath = sdkBinPath + shellExecutableName;

    return createGeneralCommandLine(outputDir, exePath)
      .withParameters("--transport=tcp", "--transport_args=127.0.0.1:" + port, "tvm", "help");
  }

  private static boolean hasProcessOutputLineContaining(Process process, String successLine) {
    try (InputStream inputStream = process.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
    ) {
      return br.lines().anyMatch(l -> l.contains(successLine));
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private void printToConsole(String message, ConsoleViewContentType messageType) {
    if (console != null) {
      console.print(message, messageType);
    }
  }
}
