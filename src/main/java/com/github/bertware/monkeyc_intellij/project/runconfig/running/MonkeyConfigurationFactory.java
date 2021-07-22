package com.github.bertware.monkeyc_intellij.project.runconfig.running;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class MonkeyConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "MonkeyC configuration factory";

    protected MonkeyConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new MonkeyRunConfiguration("Monkey C Run configuration", new MonkeyRunConfigurationModule(project), this);
    }

    @NotNull
    @Override
    public String getName() {
        return FACTORY_NAME;
    }
}
