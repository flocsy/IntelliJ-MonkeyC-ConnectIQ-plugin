package com.github.bertware.monkeyc_intellij.project.runconfig.running;


import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyModuleConfigurationProducer;

public class MonkeyModuleConfigurationProducer extends AbstractMonkeyModuleConfigurationProducer {
  public MonkeyModuleConfigurationProducer() {
    super(MonkeyConfigurationType.getInstance());
  }
}
