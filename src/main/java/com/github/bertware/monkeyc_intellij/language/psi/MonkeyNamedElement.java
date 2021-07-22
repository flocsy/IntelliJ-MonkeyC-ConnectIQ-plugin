package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyId;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * Every element which can be renamed or referenced needs to implement com.intellij.psi.PsiNamedElement interface.
 */
public interface MonkeyNamedElement extends MonkeyPsiCompositeElement, PsiNamedElement, NavigationItem, PsiNameIdentifierOwner {
    @NotNull
    MonkeyId getId();
}