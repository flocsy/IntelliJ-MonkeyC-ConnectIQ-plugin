package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.MonkeyCFileType;
import com.github.bertware.monkeyc_intellij.MonkeyCLanguage;
import com.github.bertware.monkeyc_intellij.language.psi.impl.MonkeyPsiCompositeElementImpl;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;

public class MonkeyFile extends PsiFileBase {
  public MonkeyFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, MonkeyCLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return MonkeyCFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Monkey C File: " + getName();
  }

  @Override
  public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
    return MonkeyPsiCompositeElementImpl.processDeclarationsImpl(this, processor, state)
      && super.processDeclarations(processor, state, lastParent, place);
  }

}
