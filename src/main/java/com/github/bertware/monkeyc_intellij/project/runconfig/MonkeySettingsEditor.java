package com.github.bertware.monkeyc_intellij.project.runconfig;

import com.github.bertware.monkeyc_intellij.project.module.MonkeyModuleType;
import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.sdk.devices.DevicesReader;
import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.ui.CommonProgramParametersPanel;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.ListCellRendererWrapper;
import com.intellij.ui.PanelWithAnchor;
import com.intellij.util.ui.UIUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonkeySettingsEditor extends SettingsEditor<AbstractMonkeyRunConfiguration> implements PanelWithAnchor {
  private final Project project;
  private final Module module;
  private LabeledComponent<JComboBox<TargetDevice>> targetDevice;
  private LabeledComponent<JComboBox<DeploymentTarget>> deploymentTarget;

  private LabeledComponent<TextFieldWithBrowseButton> deviceDirectory;

  private JComponent anchor;
  private CommonProgramParametersPanel commonProgramParameters;
  private LabeledComponent<ModulesComboBox> moduleComponent;
  private JComponent wholePanel;

  public MonkeySettingsEditor(final Project project, Module module) {
    this.project = project;
    this.anchor = UIUtil.mergeComponentsWithAnchor(commonProgramParameters);
    this.module = module;
  }

  public TextFieldWithBrowseButton getDeviceDirectoryField() {
    return deviceDirectory.getComponent();
  }

  public void initComponents(Module module) {
    moduleComponent = new LabeledComponent<>();
    moduleComponent.setComponent(new ModulesComboBox());
    ModulesComboBox modulesComboBox = moduleComponent.getComponent();
    modulesComboBox.fillModules(project, MonkeyModuleType.getInstance());

    fillTargetDevices(module);
    fillDeploymentTargets();

    FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
    fileChooserDescriptor.setTitle("Select Device Path");
    getDeviceDirectoryField().addBrowseFolderListener(new TextBrowseFolderListener(fileChooserDescriptor, this.project));
  }

  public List<TargetDevice> getAllDevices(Module module) {
    Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
    if (sdk == null) {
      return new ArrayList<>();
    }
    String sdkBinPath = MonkeySdkType.getBinPath(sdk);

    DevicesReader devicesReader = new DevicesReader(sdkBinPath);
    return devicesReader.parseDevicesXml();
  }

  private static class TargetDeviceListRenderer extends ListCellRendererWrapper<TargetDevice> {
    public TargetDeviceListRenderer() {
      super();
    }

    @Override
    public void customize(JList list, TargetDevice device, int index, boolean selected, boolean hasFocus) {
      if (device != null) {
        //setIcon(ModuleType.get(value).getIcon());
        setText(device.getName());
      } else {
        setText("<unspecified>");
        setIcon(null);
      }
    }
  }

  private void fillDeploymentTargets() {
    final JComboBox<DeploymentTarget> comboBox = deploymentTarget.getComponent();
    comboBox.removeAllItems();
    comboBox.setRenderer(new DeploymentTargetListRenderer());

    comboBox.addItem(DeploymentTarget.SIMULATOR);
    comboBox.addItem(DeploymentTarget.DEVICE);
  }

  private static class DeploymentTargetListRenderer extends ListCellRendererWrapper<DeploymentTarget> {
    public DeploymentTargetListRenderer() {
      super();
    }

    @Override
    public void customize(JList list, DeploymentTarget deploymentTarget, int index, boolean selected, boolean hasFocus) {
      if (deploymentTarget != null) {
        //setIcon(ModuleType.get(value).getIcon());
        setText(deploymentTarget.getName());
      } else {
        setText("<unspecified>");
        setIcon(null);
      }
    }
  }

  private void fillTargetDevices(Module module) {
    final JComboBox<TargetDevice> comboBox = targetDevice.getComponent();
    comboBox.removeAllItems();
    //noinspection unchecked
    comboBox.setRenderer(new TargetDeviceListRenderer());

    getAllDevices(module).forEach(comboBox::addItem);

    comboBox.addActionListener(e -> {
      final TargetDevice selectedItem = (TargetDevice) comboBox.getSelectedItem();
      // TODO: needs rebuilding module or something else?
    });
  }

  @Override
  public JComponent getAnchor() {
    return this.anchor;
  }

  @Override
  public void setAnchor(@Nullable JComponent anchor) {
    this.anchor = anchor;
    commonProgramParameters.setAnchor(anchor);
    targetDevice.setAnchor(anchor);
  }

  @Override
  protected void resetEditorFrom(AbstractMonkeyRunConfiguration configuration) {
    initComponents(module);

    commonProgramParameters.reset(configuration);
    moduleComponent.getComponent().setSelectedModule(configuration.getConfigurationModule().getModule());

    String targetDeviceId = configuration.getTargetDeviceId();
    if (targetDeviceId != null) {
      final TargetDevice selectedTargetDevice = new TargetDevice();
      selectedTargetDevice.setId(targetDeviceId);
      this.targetDevice.getComponent().setSelectedItem(selectedTargetDevice);
    } else {
      this.targetDevice.getComponent().setSelectedIndex(0);
    }

    String deploymentTargetId = configuration.getDeploymentTargetId();
    if (!StringUtils.isEmpty(deploymentTargetId)) {
      final DeploymentTarget selectedDeploymentTarget = new DeploymentTarget(deploymentTargetId, null);
      this.deploymentTarget.getComponent().setSelectedItem(selectedDeploymentTarget);
    } else {
      this.deploymentTarget.getComponent().setSelectedIndex(0);
    }

    String deviceDirectoryPath = configuration.getDeviceDirectory();
    if (!StringUtils.isEmpty(deviceDirectoryPath)) {
      getDeviceDirectoryField().setText(deviceDirectoryPath);
    }
  }

  @Override
  protected void applyEditorTo(AbstractMonkeyRunConfiguration configuration) {
    initComponents(module);

    commonProgramParameters.applyTo(configuration);
    configuration.setModule(moduleComponent.getComponent().getSelectedModule());

    final TargetDevice selectedTargetDevice = (TargetDevice) this.targetDevice.getComponent().getSelectedItem();
    configuration.setTargetDeviceId(Optional.ofNullable(selectedTargetDevice).map(TargetDevice::getId).orElse(null));

    DeploymentTarget selectedDeploymentTarget = (DeploymentTarget) this.deploymentTarget.getComponent().getSelectedItem();
    configuration.setDeploymentTargetId(selectedDeploymentTarget.getId());

    configuration.setDeviceDirectory(getDeviceDirectoryField().getText());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return wholePanel;
  }
}
