package com.github.bertware.monkeyc_intellij.language.psi.util;

import com.github.bertware.monkeyc_intellij.MonkeyCFileType;
import com.github.bertware.monkeyc_intellij.MonkeyCLanguage;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyId;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.Nullable;

public class MonkeyElementGenerator {

  // Creates MonkeyId by creating a random element (i.e class) which has name
  @Nullable
  public static MonkeyId createIdentifierFromText(Project myProject, String name) {
    final PsiFile dummyFile = createDummyFile(myProject, "class " + name + " {}");

    final MonkeyComponent monkeyComponent = PsiTreeUtil.getChildOfType(dummyFile, MonkeyComponent.class);
    final MonkeyComponentName componentName = monkeyComponent == null ? null : monkeyComponent.getComponentName();
    return componentName == null ? null : componentName.getId();
  }

  private static PsiFile createDummyFile(Project myProject, String text) {
    final PsiFileFactory factory = PsiFileFactory.getInstance(myProject);
    final String name = "dummy." + MonkeyCFileType.INSTANCE.getDefaultExtension();
    final LightVirtualFile virtualFile = new LightVirtualFile(name, MonkeyCFileType.INSTANCE, text);
    final PsiFile psiFile = ((PsiFileFactoryImpl) factory).trySetupPsiForFile(virtualFile, MonkeyCLanguage.INSTANCE, false, true);
    assert psiFile != null;
    return psiFile;
  }
}
