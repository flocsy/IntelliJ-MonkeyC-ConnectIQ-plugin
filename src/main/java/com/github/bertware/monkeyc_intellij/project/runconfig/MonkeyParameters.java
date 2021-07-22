package com.github.bertware.monkeyc_intellij.project.runconfig;

import com.intellij.execution.CantRunException;
import com.intellij.execution.configurations.SimpleProgramParameters;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.NotNullFunction;
import com.intellij.util.PathsList;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Nullable;

public class MonkeyParameters extends SimpleProgramParameters {
  public static final int SDK_ONLY = 0x1;
  public static final int CLASSES_ONLY = 0x2;
  private static final int TESTS_ONLY = 0x4;
  public static final int SDK_AND_CLASSES = SDK_ONLY | CLASSES_ONLY;

  private Sdk sdk;

  private VirtualFile outputPath;

  private final PathsList classPath = new PathsList();

  public Sdk getSdk() {
    return sdk;
  }

  public void setSdk(Sdk sdk) {
    this.sdk = sdk;
  }

  public VirtualFile getOutputPath() {
    return outputPath;
  }

  public static Sdk getModuleSdk(final Module module) throws CantRunException {
    final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
    if (sdk == null) {
      throw new CantRunException("No SDK for module " + module.getName());
    }
    final VirtualFile homeDirectory = sdk.getHomeDirectory();
    if (homeDirectory == null || !homeDirectory.isValid()) {
      throw CantRunException.jdkMisconfigured(sdk, module);
    }
    return sdk;
  }

  public void configureByModule(final Module module,
                                @MagicConstant(valuesFromClass = MonkeyParameters.class) final int classPathType,
                                boolean forTests) throws CantRunException {

    CompilerModuleExtension extension = CompilerModuleExtension.getInstance(module);
    if (extension != null) {
      outputPath = forTests ? extension.getCompilerOutputPathForTests() : extension.getCompilerOutputPath();
    }

    final Sdk moduleSdk = getModuleSdk(module);

    if ((classPathType & SDK_ONLY) != 0) {
      if (moduleSdk == null) {
        throw CantRunException.noJdkConfigured();
      }
      setSdk(moduleSdk);
    }
  }

  private static OrderRootsEnumerator configureEnumerator(OrderEnumerator enumerator, @MagicConstant(valuesFromClass = MonkeyParameters.class) int classPathType, Sdk jdk) {
    if ((classPathType & SDK_ONLY) == 0) {
      enumerator = enumerator.withoutSdk();
    }
    if ((classPathType & TESTS_ONLY) == 0) {
      enumerator = enumerator.productionOnly();
    }
    OrderRootsEnumerator rootsEnumerator = enumerator.classes();
    final NotNullFunction<OrderEntry, VirtualFile[]> provider = computeRootProvider(classPathType, jdk);
    if (provider != null) {
      rootsEnumerator = rootsEnumerator.usingCustomRootProvider(provider);
    }
    return rootsEnumerator;
  }

  @Nullable
  private static NotNullFunction<OrderEntry, VirtualFile[]> computeRootProvider(@MagicConstant(valuesFromClass = MonkeyParameters.class) int classPathType, final Sdk sdk) {
    return (classPathType & SDK_ONLY) == 0 ? null : (NotNullFunction<OrderEntry, VirtualFile[]>) orderEntry -> {
      if (orderEntry instanceof JdkOrderEntry) {
        return sdk.getRootProvider().getFiles(OrderRootType.CLASSES);
      }
      return orderEntry.getFiles(OrderRootType.CLASSES);
    };
  }

}
