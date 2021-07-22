// This is a generated file. Not intended for manual editing.
package com.github.bertware.monkeyc_intellij.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.bertware.monkeyc_intellij.language.psi.MonkeyTypes.*;
import com.github.bertware.monkeyc_intellij.language.psi.*;

public class MonkeyUnaryExpressionImpl extends MonkeyExpressionImpl implements MonkeyUnaryExpression {

  public MonkeyUnaryExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MonkeyVisitor visitor) {
    visitor.visitUnaryExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MonkeyVisitor) accept((MonkeyVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MonkeyCreator getCreator() {
    return findChildByClass(MonkeyCreator.class);
  }

  @Override
  @Nullable
  public MonkeyExpression getExpression() {
    return findChildByClass(MonkeyExpression.class);
  }

  @Override
  @Nullable
  public MonkeyIdentifierSuffix getIdentifierSuffix() {
    return findChildByClass(MonkeyIdentifierSuffix.class);
  }

  @Override
  @Nullable
  public MonkeyLiteral getLiteral() {
    return findChildByClass(MonkeyLiteral.class);
  }

  @Override
  @NotNull
  public List<MonkeySelector> getSelectorList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeySelector.class);
  }

  @Override
  @Nullable
  public MonkeySymbol getSymbol() {
    return findChildByClass(MonkeySymbol.class);
  }

}
