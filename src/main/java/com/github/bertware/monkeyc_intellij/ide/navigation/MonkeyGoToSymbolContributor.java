package com.github.bertware.monkeyc_intellij.ide.navigation;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class MonkeyGoToSymbolContributor implements ChooseByNameContributor {
  @NotNull
  @Override
  public String[] getNames(Project project, boolean includeNonProjectItems) {
    List<String> collect = ContributorHelper.getVariants(project, includeNonProjectItems).stream()
      .map(MonkeyComponentName::getName)
      .collect(Collectors.toList());
    return collect.stream().toArray(String[]::new);
  }

  @NotNull
  @Override
  public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
    return ContributorHelper.getVariants(project, includeNonProjectItems).stream()
      .filter(i -> i.getName().equals(name))
      .toArray(NavigationItem[]::new);
  }
}
