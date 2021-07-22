package com.github.bertware.monkeyc_intellij.ide.structure;

import com.github.bertware.monkeyc_intellij.language.psi.*;
import com.github.bertware.monkeyc_intellij.language.psi.impl.MonkeyPsiCompositeElementImpl;
import com.github.bertware.monkeyc_intellij.language.resolve.ComponentNameScopeProcessor;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonkeyStructureViewElement extends PsiTreeElementBase<NavigatablePsiElement> {
  protected MonkeyStructureViewElement(@NotNull NavigatablePsiElement psiElement) {
    super(psiElement);
  }

  @NotNull
  @Override
  public Collection<StructureViewTreeElement> getChildrenBase() {
    NavigatablePsiElement element = getElement();
    List<StructureViewTreeElement> result = new ArrayList<>();

    if (element instanceof MonkeyFile) {
      THashSet<MonkeyComponentName> componentNames = new THashSet<>();
      MonkeyPsiCompositeElementImpl.processDeclarationsImpl(element, new ComponentNameScopeProcessor(componentNames), ResolveState.initial());
      for (MonkeyComponentName componentName : componentNames) {
        PsiElement parent = componentName.getParent();
        if (parent instanceof MonkeyComponent) {
          result.add(new MonkeyStructureViewElement((MonkeyComponent) parent));
        }
      }
    } else if (element instanceof MonkeyClass) {
      MonkeyClass monkeyClass = (MonkeyClass) element;
      for (MonkeyComponent subNamedComponent : getNamedSubComponents(monkeyClass)) {
        result.add(new MonkeyStructureViewElement(subNamedComponent));
      }
    } else if (element instanceof MonkeyModuleDeclaration) {
      MonkeyModuleDeclaration moduleDeclaration = (MonkeyModuleDeclaration) element;
      for (MonkeyComponent monkeyComponent : getNamedSubComponents(moduleDeclaration)) {
        result.add(new MonkeyStructureViewElement(monkeyComponent));
      }
    }

    return result;
  }

  @NotNull
  private static List<MonkeyComponent> getNamedSubComponents(MonkeyModuleDeclaration monkeyModuleDeclaration) {
    MonkeyModuleBodyMembers moduleBodyMembers = monkeyModuleDeclaration.getModuleBody().getModuleBodyMembers();
    return getModuleOrClassChildren(moduleBodyMembers);
  }

  @NotNull
  private static List<MonkeyComponent> getNamedSubComponents(MonkeyClass monkeyClass) {
    PsiElement classBodyMembers = monkeyClass.getBodyMembers();

    return getModuleOrClassChildren(classBodyMembers);
  }

  @NotNull
  private static List<MonkeyComponent> getModuleOrClassChildren(PsiElement classBodyMembers) {
    List<MonkeyComponent> result = new ArrayList<>();
    if (classBodyMembers == null) {
      return result;
    }
    MonkeyComponent[] namedComponents = PsiTreeUtil.getChildrenOfType(classBodyMembers, MonkeyComponent.class);
    if (namedComponents != null) {
      ContainerUtil.addAll(result, namedComponents);
    }
    return result;
  }

  @Nullable
  private static MonkeyClassBodyMembers getBody(@Nullable final MonkeyClass monkeyClass) {
    final MonkeyClassBody body = monkeyClass instanceof MonkeyClassDeclaration ? ((MonkeyClassDeclaration) monkeyClass).getClassBody() : null;
    return body == null ? null : body.getClassBodyMembers();
  }

  @Nullable
  @Override
  public String getPresentableText() {
    NavigatablePsiElement element = getElement();
    ItemPresentation presentation = element == null ? null : element.getPresentation();
    return presentation == null ? null : presentation.getPresentableText();
  }
}
