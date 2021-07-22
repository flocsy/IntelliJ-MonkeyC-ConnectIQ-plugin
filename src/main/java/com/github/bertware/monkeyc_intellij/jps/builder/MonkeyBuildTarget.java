package com.github.bertware.monkeyc_intellij.jps.builder;

import com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyGlobalProperties;
import com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModelSerializerExtension;
import com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModuleType;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.builders.*;
import org.jetbrains.jps.builders.storage.BuildDataPaths;
import org.jetbrains.jps.incremental.CompileContext;
import org.jetbrains.jps.indices.IgnoredFileIndex;
import org.jetbrains.jps.indices.ModuleExcludeIndex;
import org.jetbrains.jps.model.JpsGlobal;
import org.jetbrains.jps.model.JpsModel;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.java.JpsJavaClasspathKind;
import org.jetbrains.jps.model.java.JpsJavaExtensionService;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.module.JpsTypedModuleSourceRoot;

import java.io.File;
import java.util.*;

import static com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModuleType.MONKEY_SOURCE_ROOT_TYPE;
import static com.github.bertware.monkeyc_intellij.jps.model.JpsMonkeyModuleType.MONKEY_TEST_SOURCE_ROOT_TYPE;


public class MonkeyBuildTarget extends ModuleBasedTarget<MonkeySourceRootDescriptor> {
    public MonkeyBuildTarget(MonkeyBuildTargetType targetType, @NotNull JpsModule module) {
        super(targetType, module);
    }

    @Override
    public boolean isTests() {
        return getMCTargetType().isTests();
    }

    @NotNull
    @Override
    public String getId() {
        return getModule().getName();
    }

    @NotNull
    @Override
    public Collection<BuildTarget<?>> computeDependencies(BuildTargetRegistry targetRegistry, TargetOutputIndex outputIndex) {
        List<BuildTarget<?>> dependencies = new ArrayList<>();
        Set<JpsModule> modules = JpsJavaExtensionService.dependencies(myModule).includedIn(JpsJavaClasspathKind.compile(isTests())).getModules();
        for (JpsModule module : modules) {
            if (module.getModuleType() == JpsMonkeyModuleType.INSTANCE) {
                dependencies.add(new MonkeyBuildTarget(getMCTargetType(), module));
            }
        }
        if (isTests()) {
            dependencies.add(new MonkeyBuildTarget(MonkeyBuildTargetType.PRODUCTION, getModule()));
        }
        return dependencies;
    }

    @NotNull
    @Override
    public List<MonkeySourceRootDescriptor> computeRootDescriptors(JpsModel model, ModuleExcludeIndex index, IgnoredFileIndex ignoredFileIndex, BuildDataPaths dataPaths) {
        List<MonkeySourceRootDescriptor> result = new ArrayList<>();
        JavaSourceRootType type = isTests() ? MONKEY_TEST_SOURCE_ROOT_TYPE : MONKEY_SOURCE_ROOT_TYPE;
        for (JpsTypedModuleSourceRoot<JavaSourceRootProperties> root : myModule.getSourceRoots(type)) {
            result.add(new MonkeySourceRootDescriptor(root.getFile(), this));
        }
        return result;
    }

    @Nullable
    @Override
    public MonkeySourceRootDescriptor findRootDescriptor(String rootId, BuildRootIndex rootIndex) {
        return ContainerUtil.getFirstItem(rootIndex.getRootDescriptors(new File(rootId), Collections.singletonList(getMCTargetType()), null));

    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "Module '" + getModule().getName() + "' " + (isTests() ? "tests" : "production");
    }

    @NotNull
    @Override
    public Collection<File> getOutputRoots(CompileContext context) {
        return ContainerUtil.createMaybeSingletonList(getOutputDir());
    }

    @NotNull
    public MonkeyBuildTargetType getMCTargetType() {
        return (MonkeyBuildTargetType) getTargetType();
    }

    @Nullable
    public File getOutputDir() {
        return JpsJavaExtensionService.getInstance().getOutputDirectory(getModule(), isTests());
    }

    public JpsMonkeyGlobalProperties getMonkeyGlobalProperties() {
        JpsModel jpsModel = getModule().getProject().getModel();
        JpsGlobal jpsGlobal = jpsModel.getGlobal();
        JpsMonkeyGlobalProperties jpsMonkeyGlobalProperties = jpsGlobal.getContainer().getChild(JpsMonkeyModelSerializerExtension.MONKEY_GLOBAL_CONFIG_ROLE);
        return jpsMonkeyGlobalProperties != null ? jpsMonkeyGlobalProperties : new JpsMonkeyGlobalProperties(null);
    }
}
