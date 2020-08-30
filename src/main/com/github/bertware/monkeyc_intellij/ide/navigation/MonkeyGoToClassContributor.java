package com.github.bertware.monkeyc_intellij.ide.navigation;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClass;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyModuleDeclaration;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class MonkeyGoToClassContributor implements ChooseByNameContributor {
  @NotNull
  @Override
  public String[] getNames(Project project, boolean includeNonProjectItems) {
    List<String> collect = getClassVariants(project, includeNonProjectItems).stream()
      .map(MonkeyComponentName::getName)
      .collect(Collectors.toList());
    return collect.stream().toArray(String[]::new);
  }

  @NotNull
  @Override
  public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
    return getClassVariants(project, includeNonProjectItems).stream()
      .filter(i -> i.getName().equals(name))
      .toArray(NavigationItem[]::new);
  }

  private static List<MonkeyComponentName> getClassVariants(Project project, boolean includeNonProjectItems) {
    return ContributorHelper.getVariants(project, includeNonProjectItems).stream()
      .filter(i -> {
        PsiElement parent = i.getParent();
        return parent != null && (parent instanceof MonkeyClass || parent instanceof MonkeyModuleDeclaration);
      })
      .collect(Collectors.toList());
  }
}