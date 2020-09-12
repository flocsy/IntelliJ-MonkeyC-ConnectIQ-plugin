package com.github.bertware.monkeyc_intellij.project.runconfig.running;

import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyRunConfigurationModule;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class MonkeyRunConfigurationModule extends AbstractMonkeyRunConfigurationModule {
  public MonkeyRunConfigurationModule(@NotNull Project project) {
    super(project);
  }
}
