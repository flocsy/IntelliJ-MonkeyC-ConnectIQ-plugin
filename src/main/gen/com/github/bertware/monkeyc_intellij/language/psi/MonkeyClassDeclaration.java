// This is a generated file. Not intended for manual editing.
package com.github.bertware.monkeyc_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MonkeyClassDeclaration extends MonkeyClass {

  @Nullable
  MonkeyClassBody getClassBody();

  @Nullable
  MonkeyComponentName getComponentName();

  @NotNull
  List<MonkeyExpression> getExpressionList();

  @NotNull
  MonkeyModifiers getModifiers();

}
