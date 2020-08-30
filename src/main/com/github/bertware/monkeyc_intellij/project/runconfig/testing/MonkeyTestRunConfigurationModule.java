package com.github.bertware.monkeyc_intellij.project.runconfig.testing;

import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyRunConfigurationModule;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class MonkeyTestRunConfigurationModule extends AbstractMonkeyRunConfigurationModule {
  public MonkeyTestRunConfigurationModule(@NotNull Project project) {
    super(project);
  }
}