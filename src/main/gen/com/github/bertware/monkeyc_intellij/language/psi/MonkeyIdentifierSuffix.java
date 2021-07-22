// This is a generated file. Not intended for manual editing.
package com.github.bertware.monkeyc_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MonkeyIdentifierSuffix extends MonkeyPsiCompositeElement {

  @Nullable
  MonkeyArguments getArguments();

  @NotNull
  List<MonkeyExpression> getExpressionList();

  @Nullable
  PsiElement getIdentifier();

}
