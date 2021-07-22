package com.github.bertware.monkeyc_intellij.project.ui.module;

import com.github.bertware.monkeyc_intellij.project.module.MonkeyModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;


import javax.swing.*;

public class MonkeyModuleWizardStep extends ModuleWizardStep {
  private final MonkeyTemplateChooser myAppPropertiesEditor;

  private JPanel myPanel;

  public MonkeyModuleWizardStep(MonkeyModuleBuilder moduleBuilder, WizardContext context) {
    myAppPropertiesEditor = new MonkeyTemplateChooser(moduleBuilder, context);

    myPanel = new JPanel();
    myPanel.setLayout(new OverlayLayout(myPanel));
    myPanel.add(myAppPropertiesEditor.getContentPanel());
  }

  @Override
  public JComponent getComponent() {
    return myPanel;

  }

  @Override
  public void updateDataModel() {

  }
}
