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

public class MonkeyClassBodyMembersImpl extends MonkeyPsiCompositeElementImpl implements MonkeyClassBodyMembers {

  public MonkeyClassBodyMembersImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MonkeyVisitor visitor) {
    visitor.visitClassBodyMembers(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MonkeyVisitor) accept((MonkeyVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MonkeyClassDeclaration> getClassDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyClassDeclaration.class);
  }

  @Override
  @NotNull
  public List<MonkeyConstDeclaration> getConstDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyConstDeclaration.class);
  }

  @Override
  @NotNull
  public List<MonkeyEnumDeclaration> getEnumDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyEnumDeclaration.class);
  }

  @Override
  @NotNull
  public List<MonkeyFieldDeclarationList> getFieldDeclarationListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyFieldDeclarationList.class);
  }

  @Override
  @NotNull
  public List<MonkeyFunctionDeclaration> getFunctionDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MonkeyFunctionDeclaration.class);
  }

}
