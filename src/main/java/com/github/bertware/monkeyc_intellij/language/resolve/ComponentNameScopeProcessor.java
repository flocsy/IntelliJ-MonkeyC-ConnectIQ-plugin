package com.github.bertware.monkeyc_intellij.language.resolve;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ComponentNameScopeProcessor extends BaseScopeProcessor implements PsiScopeProcessor {
  @NotNull
  private final Set<MonkeyComponentName> myResult;

  public ComponentNameScopeProcessor(final @NotNull Set<MonkeyComponentName> result) {
    this.myResult = result;
  }

  @Override
  public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
    MonkeyComponentName componentName = (MonkeyComponentName) element;
    myResult.add(componentName);
    return true;
  }

}
