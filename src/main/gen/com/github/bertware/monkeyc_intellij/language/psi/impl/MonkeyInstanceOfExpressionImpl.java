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

public class MonkeyInstanceOfExpressionImpl extends MonkeyExpressionImpl implements MonkeyInstanceOfExpression {

  public MonkeyInstanceOfExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MonkeyVisitor visitor) {
    visitor.visitInstanceOfExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MonkeyVisitor) accept((MonkeyVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public MonkeyExpression getExpression() {
    return findNotNullChildByClass(MonkeyExpression.class);
  }

  @Override
  @Nullable
  public MonkeyQualifiedName getQualifiedName() {
    return findChildByClass(MonkeyQualifiedName.class);
  }

}
