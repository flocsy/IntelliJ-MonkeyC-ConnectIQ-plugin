package com.github.bertware.monkeyc_intellij.project.module;

import com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.configuration.TargetDeviceModuleExtension;
import com.github.bertware.monkeyc_intellij.project.dom.manifest.Manifest;
import com.github.bertware.monkeyc_intellij.project.dom.manifest.Products;
import com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo.NewProjectFileMap;
import com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo.ProjectInfo;
import com.github.bertware.monkeyc_intellij.project.module.util.ExternalTemplateUtil;
import com.github.bertware.monkeyc_intellij.project.module.util.MonkeyModuleUtil;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetDevice;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetSdkVersion;
import com.github.bertware.monkeyc_intellij.project.runconfig.running.MonkeyConfigurationType;
import com.github.bertware.monkeyc_intellij.project.runconfig.running.MonkeyRunConfiguration;
import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.ui.module.MonkeyModuleWizardStep;
import com.github.bertware.monkeyc_intellij.project.ui.module.newProject.MonkeyApplicationModifiedSettingsStep;
import com.intellij.compiler.CompilerWorkspaceConfiguration;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.ReadonlyStatusHandler;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.DocumentUtil;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModuleType.MONKEY_RESOURCE_ROOT_TYPE;
import static com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModuleType.MONKEY_SOURCE_ROOT_TYPE;
import static com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeySdkType.hasMinSdkVersionSupport;
import static com.github.bertware.monkeyc_intellij.project.module.util.MonkeyModuleUtil.MANIFEST_XML;
import static java.util.Objects.requireNonNull;

public class MonkeyModuleBuilder extends ModuleBuilder implements ModuleBuilderListener {
    private static final Logger LOG = Logger.getInstance("#io.github.liias.monkey.project.module.MonkeyModuleBuilder");
    public static final String PROJECT_INFO_XML = "projectInfo.xml";
    public static final String FILE_TYPE_SOURCE = "source";
    public static final TargetDevice DEFAULT_TARGET_DEVICE = TargetDevice.DEFAULT_DEVICE;
    public static final TargetSdkVersion DEFAULT_TARGET_SDK_VERSION = TargetSdkVersion.VERSION_1_3_X;
    public static final String DEFAULT_MIN_SDK_VERSION = "1.3.1";

    // Not null only if new project where we need to generate content based on this type
    @Nullable
    private final String appType;

    public MonkeyModuleBuilder(@Nullable String appType) {
        this.appType = appType;
    }

    // Not null only if new project where we need to generate content based on this type
    @Nullable
    public String getAppType() {
        return appType;
    }

    @Override
    @Nullable
    public String getBuilderId() {
        ModuleType moduleType = getModuleType();
        String builderId = moduleType.getId();
        // getBuilderId() is (besides other places) called in
        // com.intellij.ide.util.newProjectWizard.StepSequence.addStepsForBuilder() so it has to be unique per template
        if (getAppType() != null) {
            builderId += "_" + getAppType();
        }
        return builderId;
    }

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        addListener(this);

        final Module module = rootModel.getModule();
        final MonkeySdkType sdkType = MonkeySdkType.getInstance();

        Sdk sdk = findAndSetSdk(module, sdkType);

        CompilerModuleExtension compilerModuleExtension = rootModel.getModuleExtension(CompilerModuleExtension.class);
        compilerModuleExtension.setExcludeOutput(true);
        compilerModuleExtension.inheritCompilerOutputPath(true);

        if (getModuleJdk() != null) {
            rootModel.setSdk(getModuleJdk());
        } else {
            rootModel.inheritSdk();
        }

        // Existing if importing project/module from existing sources
        Optional<ContentEntry> existingContentEntry = Arrays.stream(rootModel.getContentEntries()).findFirst();
        if (existingContentEntry.isPresent()) {
            // assume sources are already set
            ContentEntry contentEntry = existingContentEntry.get();
            List<VirtualFile> resourcePaths = findResourcePaths(contentEntry.getFile());
            for (VirtualFile resourcePath : resourcePaths) {
                contentEntry.addSourceFolder(resourcePath,  MONKEY_RESOURCE_ROOT_TYPE);
            }
        } else {
            ContentEntry contentEntry = doAddContentEntry(rootModel);
            if (contentEntry != null) {
                VirtualFile sourcePath = createSourcePath("source");
                contentEntry.addSourceFolder(sourcePath, MONKEY_SOURCE_ROOT_TYPE);

                // TODO: there can be many resource folders, e.g based on language or device that come from SDK's example project definitions
                VirtualFile resourcePath = createSourcePath("resources");
                contentEntry.addSourceFolder(resourcePath, MONKEY_RESOURCE_ROOT_TYPE);
            }
        }

        final TargetDeviceModuleExtension targetDeviceModuleExtension = rootModel.getModuleExtension(TargetDeviceModuleExtension.class);
        targetDeviceModuleExtension.setTargetDevice(DEFAULT_TARGET_DEVICE);
        targetDeviceModuleExtension.setTargetSdkVersion(DEFAULT_TARGET_SDK_VERSION);

        final Project project = rootModel.getProject();

        VirtualFile[] files = rootModel.getContentRoots();

        if (files.length > 0) {
            final VirtualFile contentRoot = files[0];
            StartupManager.getInstance(project).runWhenProjectIsInitialized(() ->
                    ApplicationManager.getApplication().invokeLater(() ->
                            DocumentUtil.writeInRunUndoTransparentAction(() ->
                                    createModule(module, contentRoot)
                            )
                    )
            );
        }
    }

    private static List<VirtualFile> findResourcePaths(VirtualFile contentRoot) {
        VirtualFile[] children = contentRoot.getChildren();
        if (children == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(children)
                .filter(VirtualFile::isDirectory)
                .filter(child -> child.getName().startsWith("resources"))
                .collect(Collectors.toList());
    }

    @Override
    public int getWeight() {
        return 100;
    }

    @Override
    public boolean isTemplateBased() {
        return true;
    }

    private VirtualFile createSourcePath(String dirname) {
        String path = getContentEntryPath() + File.separator + dirname;
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(path));
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    private void createModule(Module module, VirtualFile contentRoot) {
        final MonkeySdkType sdkType = MonkeySdkType.getInstance();
        Sdk sdk = findAndSetSdk(module, sdkType);
        if (sdk != null) {
            VirtualFile sdkBinDir = sdkType.getBinDir(sdk);
            // add new files only if manifest file does not exist already
            if (contentRoot.findChild(MANIFEST_XML) == null) {
                createFromTemplate(module, contentRoot, sdkBinDir);
                fillManifestXml(sdk, module, contentRoot);
            }
            setupRunConfiguration(module);
        }
    }

    private Sdk findAndSetSdk(Module module, MonkeySdkType sdkType) {
        Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
        if (sdk == null) {
            sdk = ProjectRootManager.getInstance(module.getProject()).getProjectSdk();
        }
        if (sdk == null) {
            Comparator<Sdk> preferredSdkComparator = (o1, o2) -> {
                if (o1.getSdkType() instanceof MonkeySdkType) {
                    return 1;
                } else if (o2.getSdkType() instanceof MonkeySdkType) {
                    return -1;
                }
                return 0;
            };
            SdkConfigurationUtil.configureDirectoryProjectSdk(module.getProject(), preferredSdkComparator, sdkType);
            sdk = ProjectRootManager.getInstance(module.getProject()).getProjectSdk();
        }
        return sdk;
    }

    private void createFromTemplate(final Module module, final VirtualFile rootDir, VirtualFile sdkBinDir) {
        final Project project = module.getProject();
        ProjectInfo sdkProjectInfo = getSdkProjectInfo(project, sdkBinDir);
        List<NewProjectFileMap> newProjectFileMaps = sdkProjectInfo.getNewProjectFilesMaps().getNewProjectFileMaps();

        // TODO: allow user to choose apptype's app (i.e Simple or Complex), instead of selecting random first
        final NewProjectFileMap newProjectFileMap = newProjectFileMaps.stream()
                .filter(fileMap -> fileMap != null && appType.equals(fileMap.getAppType().getStringValue())
                )
                .findFirst()
                .get();

        writeTemplateFiles(module, sdkBinDir, rootDir, newProjectFileMap);
    }

    private static void writeTemplateFiles(Module module, VirtualFile sdkBinDir, VirtualFile rootDir, NewProjectFileMap newProjectFileMap) {
        final GenericAttributeValue<String> baseDir = newProjectFileMap.getBaseDir(); // e.g templates/watch-app/simple

        String appName = module.getName(); // used in resources.xml to set freeform app name
        final Project project = module.getProject();

        VelocityContext templateSubstitutionContext = getTemplateSubstitutionContext(appName);
        String sanitizedName = WordUtils.capitalize(appName);

        try {
            for (com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo.File file : newProjectFileMap.getFiles()) {
                final String relativeFilePath = file.getValue(); // e.g resources/images/launcher_icon.png
                String toRelativeFilePath = relativeFilePath;
                // source files are prefixed with app name
                if (FILE_TYPE_SOURCE.equals(file.getType().getStringValue())) {
                    final Path relativeFilePathPath = Paths.get(relativeFilePath);
                    final Path fileName = relativeFilePathPath.getFileName(); // e.g App.mc
                    final Path parent = relativeFilePathPath.getParent(); // e.g source
                    toRelativeFilePath = parent + "/" + sanitizedName + fileName;
                }
                final VirtualFile templateFile = sdkBinDir.findFileByRelativePath(baseDir + "/" + relativeFilePath);
                // this handles creating child directories as well
                // TODO verify correct working, used to be copyFileRelative
                VirtualFile newFile = VfsUtil.copyFile(project, templateFile, rootDir, toRelativeFilePath);

                if (!newFile.getFileType().isBinary()) {
                    String content = getParsedFileContent(templateSubstitutionContext, newFile);
                    VfsUtil.saveText(newFile, content);
                }
            }
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    private static VelocityContext getTemplateSubstitutionContext(String appName) {
        String sanitizedName = WordUtils.capitalize(appName);
        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("appName", appName);
        substitutions.put("appClassName", sanitizedName + "App");
        substitutions.put("viewClassName", sanitizedName + "View");
        substitutions.put("delegateClassName", sanitizedName + "Delegate");
        substitutions.put("menuDelegateClassName", sanitizedName + "MenuDelegate");
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, String> substitution : substitutions.entrySet()) {
            context.put(substitution.getKey(), substitution.getValue());
        }
        return context;
    }

    private static String getParsedFileContent(Context context, VirtualFile file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(VfsUtil.virtualToIoFile(file));
        final VelocityEngine velocityEngine = ExternalTemplateUtil.getEngine();
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "Monkey", fileReader);
        return writer.toString();
    }

    private void fillManifestXml(Sdk sdk, final Module module, final VirtualFile contentRoot) {
        final Project project = module.getProject();
        CommandProcessor.getInstance().executeCommand(project, () -> {
            Runnable action = () -> {
                final Manifest manifest = MonkeyModuleUtil.getManifest(project, contentRoot);
                if (manifest != null) {
                    StartupManager.getInstance(project).runWhenProjectIsInitialized(() -> FileDocumentManager.getInstance().saveAllDocuments());
                    ManifestData manifestData = new ManifestData(appType, sdk, DEFAULT_MIN_SDK_VERSION, DEFAULT_TARGET_DEVICE.getId());
                    configureManifest(manifest, module, manifestData);
                }
            };

            ApplicationManager.getApplication().runWriteAction(action);
        }, "Create project", null);

    }

    private static class ManifestData {
        private final Sdk sdk;

        @NotNull
        String appType;

        @NotNull
        String minSdkVersion;

        @NotNull
        String targetDeviceId;

        public ManifestData(@NotNull String appType, Sdk sdk, @NotNull String minSdkVersion, @NotNull String targetDeviceId) {
            this.sdk = sdk;
            this.appType = appType;
            this.minSdkVersion = minSdkVersion;
            this.targetDeviceId = targetDeviceId;
        }
    }

    private static void configureManifest(Manifest manifest, Module module, ManifestData manifestData) {
        requireNonNull(manifestData, "manifestData is null");
        requireNonNull(manifestData.appType, "appType is null");
        requireNonNull(manifestData.minSdkVersion, "minSdkVersion is null");
        requireNonNull(manifestData.targetDeviceId, "targetDeviceId is null");

        final PsiFile manifestFile = getValidatedPsiFile(manifest);
        if (manifestFile == null) {
            return;
        }

        String applicationId = MonkeyModuleUtil.generateProjectId();
        String entryClassName = WordUtils.capitalize(module.getName()) + "App";

        manifest.getApplication().getId().setValue(applicationId);
        manifest.getApplication().getType().setValue(manifestData.appType);
        manifest.getApplication().getName().setValue("@Strings.AppName");
        // entry is a class which extends Toybox.Application.AppBase
        manifest.getApplication().getEntry().setValue(entryClassName);
        manifest.getApplication().getLauncherIcon().setValue("@Drawables.LauncherIcon");

        JpsMonkeySdkType.SdkVersion sdkVersion = JpsMonkeySdkType.SdkVersion
                .fromVersionString(manifestData.sdk.getVersionString());
        if (hasMinSdkVersionSupport(sdkVersion)) {
            manifest.getApplication().getMinSdkVersion().setValue(manifestData.minSdkVersion);
        }

        Products products = manifest.getApplication().getProducts();
        XmlTag productsRootTag = products.getXmlTag();
        XmlTag productTag = productsRootTag.createChildTag("product", productsRootTag.getNamespace(), "", false);
        productTag = productsRootTag.addSubTag(productTag, true);
        productTag.setAttribute("id", null, manifestData.targetDeviceId);
        productTag.collapseIfEmpty();

        CodeStyleManager.getInstance(manifestFile.getProject()).reformat(manifestFile);
    }

    private static PsiFile getValidatedPsiFile(DomElement domElement) {
        final XmlTag rootTag = domElement.getXmlTag();
        if (rootTag == null) {
            return null;
        }

        final PsiFile psiFile = rootTag.getContainingFile();
        if (psiFile == null) {
            return null;
        }

        final VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null ||
                !ReadonlyStatusHandler.ensureFilesWritable(psiFile.getProject(), virtualFile)) {
            return null;
        }

        return psiFile;
    }

    public static ProjectInfo getSdkProjectInfo(Project project, VirtualFile sdkBinDir) {
        final VirtualFile projectInfoFile = sdkBinDir.findChild(PROJECT_INFO_XML);
        return projectInfoFile != null ? MonkeyModuleUtil.loadDomElement(project, projectInfoFile, ProjectInfo.class) : null;
    }

    private void setupRunConfiguration(Module module) {
        final Project project = module.getProject();
        final RunManager runManager = RunManager.getInstance(project);
        final ConfigurationFactory configurationFactory = MonkeyConfigurationType.getInstance().getFactory();
        final RunnerAndConfigurationSettings settings = runManager.createRunConfiguration(module.getName(), configurationFactory);
        final MonkeyRunConfiguration configuration = (MonkeyRunConfiguration) settings.getConfiguration();
        configuration.setModule(module);
        configuration.setTargetDeviceId(DEFAULT_TARGET_DEVICE.getId());
        runManager.addConfiguration(settings, false);
        runManager.setSelectedConfiguration(settings);
    }

    @NotNull
    @Override
    public ModuleType getModuleType() {
        return MonkeyModuleType.getInstance();
    }

    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType == MonkeySdkType.getInstance();
    }

    @Override
    public void moduleCreated(@NotNull Module module) {
        CompilerWorkspaceConfiguration.getInstance(module.getProject()).CLEAR_OUTPUT_DIRECTORY = false;
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext context, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{new MonkeyModuleWizardStep(this, context)};
    }

    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        if (appType == null) {
            return null;
        }
        return new MonkeyApplicationModifiedSettingsStep(this, settingsStep);
    }
}
