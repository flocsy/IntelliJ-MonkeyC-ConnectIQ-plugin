package com.github.bertware.monkeyc_intellij.ide.actions.appsettings.form;

import com.github.bertware.monkeyc_intellij.ide.actions.appsettings.json.Setting;
import com.intellij.ui.components.OnOffButton;


import javax.swing.*;
import java.util.Map;

// config type: boolean
public class BooleanFieldModel extends FieldModel<Boolean> {
  private final OnOffButton component;

  public BooleanFieldModel(Setting setting, Map<String, String> translations) {
    super(setting, translations);
    this.component = createComponent();
  }

  public OnOffButton createComponent() {
    Boolean value = setting.getValueAsBoolean();
    OnOffButton onOffButton = new OnOffButton();
    onOffButton.setSelected(value);
    applyGenericProperties(onOffButton);
    return onOffButton;
  }

  @Override
  public JComponent getComponent() {
    return component;
  }

  @Override
  public Boolean getValue() {
    return component.isSelected();
  }
}
