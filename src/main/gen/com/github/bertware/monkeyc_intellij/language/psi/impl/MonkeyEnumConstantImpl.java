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

public class MonkeyEnumConstantImpl extends AbstractMonkeyComponentImpl implements MonkeyEnumConstant {

  public MonkeyEnumConstantImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MonkeyVisitor visitor) {
    visitor.visitEnumConstant(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MonkeyVisitor) accept((MonkeyVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public MonkeyComponentName getComponentName() {
    return findNotNullChildByClass(MonkeyComponentName.class);
  }

  @Override
  @Nullable
  public PsiElement getIntliteral() {
    return findChildByType(INTLITERAL);
  }

}
