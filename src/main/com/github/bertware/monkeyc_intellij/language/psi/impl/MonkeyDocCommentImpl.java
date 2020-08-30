package com.github.bertware.monkeyc_intellij.language.psi.impl;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyDocComment;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonkeyDocCommentImpl extends ASTWrapperPsiElement implements MonkeyDocComment {

  public MonkeyDocCommentImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Nullable
  @Override
  public PsiElement getOwner() {
    final PsiElement parent = getParent();

    // TODO: implement
    return null;
  }

  @Override
  public IElementType getTokenType() {
    return getNode().getElementType();
  }
}
