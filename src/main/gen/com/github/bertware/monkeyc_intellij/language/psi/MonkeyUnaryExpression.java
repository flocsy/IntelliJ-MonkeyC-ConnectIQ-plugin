// This is a generated file. Not intended for manual editing.
package com.github.bertware.monkeyc_intellij.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MonkeyUnaryExpression extends MonkeyExpression {

  @Nullable
  MonkeyCreator getCreator();

  @Nullable
  MonkeyExpression getExpression();

  @Nullable
  MonkeyIdentifierSuffix getIdentifierSuffix();

  @Nullable
  MonkeyLiteral getLiteral();

  @NotNull
  List<MonkeySelector> getSelectorList();

  @Nullable
  MonkeySymbol getSymbol();

}
