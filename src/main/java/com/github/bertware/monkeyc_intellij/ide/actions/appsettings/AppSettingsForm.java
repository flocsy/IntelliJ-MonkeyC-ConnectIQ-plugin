package com.github.bertware.monkeyc_intellij.ide.actions.appsettings;

import com.github.bertware.monkeyc_intellij.ide.actions.appsettings.form.FieldModel;
import com.github.bertware.monkeyc_intellij.ide.actions.appsettings.json.Setting;
import com.github.bertware.monkeyc_intellij.project.module.MonkeyModuleType;
import com.intellij.application.options.ModulesComboBox;
import com.intellij.openapi.compiler.CompilerPaths;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;



import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



public class AppSettingsForm {
  private JPanel panel;
  private JPanel settingsPanel;
  private ModulesComboBox modulesComboBox;

  @Nullable
  private final Project project;

  @Nullable
  private AppSettingsManager appSettingsManager;

  private Map<String, FieldModel> fieldsBySettingKey;

  @Nullable
  private DialogWrapper appSettingsDialog;

  public AppSettingsForm(@Nullable Project project, @Nullable DialogWrapper appSettingsDialog) {
    this(project);
    this.appSettingsDialog = appSettingsDialog;
  }

  public AppSettingsForm(@Nullable Project project) {
    this.project = project;
    this.fieldsBySettingKey = new HashMap<>();
  }

  // TODO: place custom component creation code here
  private void createUIComponents() {
    modulesComboBox = new ModulesComboBox();
    if (project != null) {
      modulesComboBox.fillModules(project, MonkeyModuleType.getInstance());
    }

    modulesComboBox.addActionListener(e -> {
      selectModule(modulesComboBox.getSelectedModule());
    });
  }

  private void selectModule(@Nullable Module selectedModule) {
    if (selectedModule != null) {
      receiveSettings();
    } else {
      removeSettings();
    }
  }

  @Nullable
  private static VirtualFile findSettingsJsonFile(Module module) {
    VirtualFile moduleOutputDir = CompilerPaths.getModuleOutputDirectory(module, false);
    // moduleOutputDir is null if the output directories don't exist yet (e.g module has not been built)
    if (moduleOutputDir == null) {
      return null;
    }
    String projectName = module.getProject().getName();
    String settingsFilename = projectName + "-settings.json";
    return moduleOutputDir.findChild(settingsFilename);
  }

  public boolean isModuleSelected() {
    return modulesComboBox.getSelectedModule() != null;
  }

  public void receiveSettings() {
    final Module selectedModule = modulesComboBox.getSelectedModule();
    if (selectedModule != null) {
      VirtualFile settingsJsonFile = findSettingsJsonFile(selectedModule);
      if (settingsJsonFile == null) {
        removeSettings();
      } else {
        if (appSettingsManager == null) {
          appSettingsManager = new AppSettingsManager(selectedModule, settingsJsonFile);
        } else {
          appSettingsManager.initSettingsAndLanguages(settingsJsonFile);
        }
        fillSettings(appSettingsManager.getSettings(), this.appSettingsManager.getLanguages());
      }
    } else {
      removeSettings();
    }
  }

  private void removeSettings() {
    fieldsBySettingKey = new HashMap<>();
    settingsPanel.removeAll();
    settingsPanel.revalidate();

    resizeParentWindow();
  }

  private void resizeParentWindow() {
    if (appSettingsDialog != null) {
      appSettingsDialog.pack();
    }
  }

  private void fillSettings(List<Setting> settings, Map<String, Map<String, String>> languages) {
    settingsPanel.removeAll();

    GridLayoutManager layout = new GridLayoutManager(settings.size() + 1, 2);
    settingsPanel.setLayout(layout);
    GridConstraints gc = new GridConstraints();
    gc.setAnchor(GridConstraints.ANCHOR_WEST);
    gc.setVSizePolicy(GridConstraints.SIZEPOLICY_FIXED);

    Map<String, String> translations = languages.get("valyrian");

    for (Setting setting : settings) {
      gc.setColumn(0);
      gc.setFill(GridConstraints.FILL_NONE);
      gc.setHSizePolicy(GridConstraints.SIZEPOLICY_FIXED);
      String displayName = getTranslated(translations, setting.getConfigTitle());
      JBLabel label = new JBLabel(displayName);
      settingsPanel.add(label, gc);

      gc.setColumn(1);
      int fill = setting.getConfigType() == Setting.ConfigType.BOOLEAN ? GridConstraints.FILL_NONE : GridConstraints.FILL_HORIZONTAL;
      gc.setFill(fill);
      gc.setHSizePolicy(GridConstraints.SIZEPOLICY_WANT_GROW);

      FieldModel fieldModel = FieldModel.create(setting, translations);
      JComponent settingValueComponent = fieldModel.getComponent();
      settingsPanel.add(settingValueComponent, gc);
      label.setLabelFor(settingValueComponent);
      fieldsBySettingKey.put(setting.getKey(), fieldModel);

      gc.setRow(gc.getRow() + 1);
    }
    gc.setColumn(0);
    gc.setVSizePolicy(GridConstraints.FILL_VERTICAL);
    gc.setHSizePolicy(GridConstraints.SIZEPOLICY_FIXED);
    settingsPanel.add(new JBLabel(""), gc);

    settingsPanel.revalidate();

    resizeParentWindow();
  }

  private static String getTranslated(Map<String, String> translations, String stringId) {
    return Optional.ofNullable(translations.get(stringId)).orElse(stringId);
  }

  public JPanel getPanel() {
    return panel;
  }

  public void sendSettingsToSimulator() {
    if (appSettingsManager != null) {
      appSettingsManager.sendToSimulator(fieldsBySettingKey);
    }
  }
}
