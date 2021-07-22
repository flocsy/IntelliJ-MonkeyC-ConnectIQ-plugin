package com.github.bertware.monkeyc_intellij.language.psi.impl;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyId;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyNamedElement;
import com.github.bertware.monkeyc_intellij.language.psi.util.MonkeyElementGenerator;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MonkeyNamedElementImpl extends MonkeyPsiCompositeElementImpl implements MonkeyNamedElement {

  public MonkeyNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public String getName() {
    return getId().getText();
  }

  // if reference/declaration is renamed, then this is called on all references and declaration
  @Override
  public PsiElement setName(@NotNull String newElementName) throws IncorrectOperationException {
    final MonkeyId identifier = getId();
    final MonkeyId identifierNew = MonkeyElementGenerator.createIdentifierFromText(getProject(), newElementName);
    if (identifierNew != null) {
      getNode().replaceChild(identifier.getNode(), identifierNew.getNode());
    }
    return this;
  }

  @Nullable
  public ItemPresentation getPresentation() {
    final PsiElement parent = getParent();
    if (parent instanceof NavigationItem) {
      return ((NavigationItem) parent).getPresentation();
    }
    return null;
  }

  @Override
  public PsiElement getNameIdentifier() {
    return this;
  }

  @NotNull
  @Override
  public MonkeyId getId() {
    return PsiTreeUtil.getRequiredChildOfType(this, MonkeyId.class);
  }

  @Override
  public String toString() {
    return "MonkeyComponentName:" + getName();
  }

}
