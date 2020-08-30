package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

public interface MonkeyComponent extends MonkeyPsiCompositeElement, PsiNameIdentifierOwner {
  @Nullable
  MonkeyComponentName getComponentName();
}