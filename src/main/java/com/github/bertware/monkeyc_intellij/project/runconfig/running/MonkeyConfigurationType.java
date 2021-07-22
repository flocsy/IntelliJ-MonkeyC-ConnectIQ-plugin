package com.github.bertware.monkeyc_intellij.project.runconfig.running;

import com.github.bertware.monkeyc_intellij.MonkeyCIcons;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MonkeyConfigurationType implements ConfigurationType {

    private final ConfigurationFactory factory;

    public MonkeyConfigurationType() {
        factory = new MonkeyConfigurationFactory(this);
    }

    @Override
    public String getDisplayName() {
        return "Monkey C Application";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Configuration to run a Connect IQ app with the simulator";
    }

    @Override
    public Icon getIcon() {
        return MonkeyCIcons.MODULE16;
    }

    @NotNull
    @Override
    public String getId() {
        return "MONKEY_C_APPLICATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{factory};
    }

    public ConfigurationFactory getFactory() {
        return factory;
    }

    @NotNull
    public static MonkeyConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(MonkeyConfigurationType.class);
    }
}
