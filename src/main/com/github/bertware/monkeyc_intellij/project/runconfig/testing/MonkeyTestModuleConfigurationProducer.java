package com.github.bertware.monkeyc_intellij.project.runconfig.testing;


import com.github.bertware.monkeyc_intellij.project.runconfig.AbstractMonkeyModuleConfigurationProducer;

public class MonkeyTestModuleConfigurationProducer extends AbstractMonkeyModuleConfigurationProducer {
  protected MonkeyTestModuleConfigurationProducer() {
    super(MonkeyTestConfigurationType.getInstance());
  }
}
