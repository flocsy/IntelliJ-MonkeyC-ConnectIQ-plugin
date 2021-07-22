package com.github.bertware.monkeyc_intellij.language.psi.impl;


import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClassBody;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClassBodyMembers;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClassDeclaration;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClass;
import com.intellij.lang.ASTNode;




import org.jetbrains.annotations.NotNull;

abstract public class AbstractMonkeyPsiClass extends AbstractMonkeyComponentImpl implements MonkeyClass {
  public AbstractMonkeyPsiClass(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public MonkeyClassBodyMembers getBodyMembers() {
    if (this instanceof MonkeyClassDeclaration) {
      MonkeyClassDeclaration monkeyClassDeclaration = (MonkeyClassDeclaration) this;
      MonkeyClassBody classBody = monkeyClassDeclaration.getClassBody();
      if (classBody != null) {
        return classBody.getClassBodyMembers();
      }
    }
    return null;
  }


  @Override
  public String toString() {
    return "MonkeyClassDeclaration:" + getName();
  }
}
