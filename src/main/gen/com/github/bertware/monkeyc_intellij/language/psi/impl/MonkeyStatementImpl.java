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

public class MonkeyStatementImpl extends MonkeyPsiCompositeElementImpl implements MonkeyStatement {

  public MonkeyStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MonkeyVisitor visitor) {
    visitor.visitStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MonkeyVisitor) accept((MonkeyVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MonkeyBlock getBlock() {
    return findChildByClass(MonkeyBlock.class);
  }

  @Override
  @Nullable
  public MonkeyExpression getExpression() {
    return findChildByClass(MonkeyExpression.class);
  }

  @Override
  @Nullable
  public MonkeyForStatement getForStatement() {
    return findChildByClass(MonkeyForStatement.class);
  }

  @Override
  @NotNull
  public List<MonkeyStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyStatement.class);
  }

  @Override
  @Nullable
  public MonkeySwitchBlockStatementGroups getSwitchBlockStatementGroups() {
    return findChildByClass(MonkeySwitchBlockStatementGroups.class);
  }

  @Override
  @Nullable
  public MonkeyTryStatement getTryStatement() {
    return findChildByClass(MonkeyTryStatement.class);
  }

  @Override
  @Nullable
  public PsiElement getIdentifier() {
    return findChildByType(IDENTIFIER);
  }

}
