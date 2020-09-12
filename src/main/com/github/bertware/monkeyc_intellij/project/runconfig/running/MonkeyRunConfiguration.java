package com.github.bertware.monkeyc_intellij.project.runconfig.running;

import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyRunConfiguration;
import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyRunConfigurationModule;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonkeyRunConfiguration extends AbstractMonkeyRunConfiguration {


    public MonkeyRunConfiguration(String name, @NotNull AbstractMonkeyRunConfigurationModule configurationModule, @NotNull ConfigurationFactory factory) {
        super(name, configurationModule, factory);
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new MonkeyRunningState(executionEnvironment);
    }
}
