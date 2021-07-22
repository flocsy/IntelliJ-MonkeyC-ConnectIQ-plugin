package com.github.bertware.monkeyc_intellij.ide.structure;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClass;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponent;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyFieldDeclaration;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyModuleDeclaration;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonkeyStructureViewModel extends StructureViewModelBase implements StructureViewModelBase.ElementInfoProvider {
  public MonkeyStructureViewModel(@NotNull PsiFile psiFile, @Nullable Editor editor) {
    super(psiFile, editor, new MonkeyStructureViewElement(psiFile));
    // order matters, first elements are compared first when walking up parents in AST:
    withSuitableClasses(MonkeyFieldDeclaration.class, MonkeyClass.class, MonkeyModuleDeclaration.class);
  }

  @Override
  public boolean shouldEnterElement(Object element) {
    return element instanceof MonkeyClass || element instanceof MonkeyModuleDeclaration;
  }

  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    final Object value = element.getValue();
    return value instanceof MonkeyComponent &&
      !(value instanceof MonkeyClass || value instanceof MonkeyModuleDeclaration);
  }
}
