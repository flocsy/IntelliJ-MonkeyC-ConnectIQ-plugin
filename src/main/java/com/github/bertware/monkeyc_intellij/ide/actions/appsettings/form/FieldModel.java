package com.github.bertware.monkeyc_intellij.ide.actions.appsettings.form;

import com.github.bertware.monkeyc_intellij.ide.actions.appsettings.json.Setting;

import javax.swing.*;
import java.util.Map;
import java.util.Optional;

/*
valueType configType
--------------------
string	  alphaNumeric, phone, email, url, password
number	  numeric, list, date
float	    numeric
boolean	  boolean
*/
public abstract class FieldModel<V> {
  protected final Setting setting;
  protected final Map<String, String> translations;

  public static FieldModel create(Setting setting, Map<String, String> translations) {
    switch (setting.getValueType()) {
      case BOOLEAN:
        return new BooleanFieldModel(setting, translations);
      case FLOAT:
        return new FloatFieldModel(setting, translations);
      case NUMBER:
        return new IntegerFieldModel(setting, translations);
      case STRING:
        return new StringFieldModel(setting, translations);
    }
    throw new IllegalArgumentException("unknown setting value type: " + setting.getValueType());
  }

  public FieldModel(Setting setting, Map<String, String> translations) {
    this.setting = setting;
    this.translations = translations;
  }

  public abstract JComponent getComponent();

  public abstract V getValue();

  protected void applyGenericProperties(JComponent component) {
    component.setEnabled(!setting.isConfigReadonly());
    component.setToolTipText(getConfigPrompt());
  }

  protected String getTranslated(String stringId) {
    if (stringId == null) {
      return null;
    }
    return Optional.ofNullable(translations.get(stringId)).orElse(stringId);
  }

  protected String getConfigPrompt() {
    return getTranslated(setting.getConfigPrompt());
  }
}
