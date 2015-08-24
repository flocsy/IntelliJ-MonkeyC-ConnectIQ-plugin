package ee.edio.garmin.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.Key;
import ee.edio.garmin.runconfig.TargetDevice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetDeviceConfigurable implements UnnamedConfigurable {
  public static final Key<String> TARGET_DEVICE = new Key<>("TARGET_DEVICE");
  //private final ModuleConfigurationState moduleConfigurationState;
  private final Project myProject;
  private final ModifiableRootModel myModifiableRootModel;
  private ComboBox myComboBox;
  private JPanel myPanel = new JPanel(new GridBagLayout());
  private String targetDevice;

  public TargetDeviceConfigurable(Project project, ModifiableRootModel model) {
    myProject = project;
    myModifiableRootModel = model;
    myComboBox = new ComboBox();
    myComboBox.addItem("vivoactive");

    myComboBox.addItem("fenix3");

    myComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        final Object selectedItem = myComboBox.getSelectedItem();
        final TargetDevice targetDevice = selectedItem instanceof TargetDevice ? (TargetDevice) selectedItem : null;
        getTargetDeviceModuleExtension().setTargetDevice(targetDevice);
      }
    });

    JLabel label = new JLabel("Target Device");
    label.setLabelFor(myComboBox);
    myPanel.add(label,
        new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(12, 6, 12, 0), 0, 0));
    myPanel.add(myComboBox,
        new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(6, 6, 12, 0), 0, 0));
  }


  @Nullable
  @Override
  public JComponent createComponent() {
    return myPanel;
  }

  @Override
  public boolean isModified() {
    return getTargetDeviceModuleExtension().isChanged();
  }

  @Override
  public void apply() throws ConfigurationException {
    getTargetDeviceModuleExtension().commit();
  }

  @Override
  public void reset() {
    myComboBox.setSelectedItem(getTargetDeviceModuleExtension().getTargetDevice());
  }

  @Override
  public void disposeUIResources() {
    myPanel = null;
    myComboBox = null;
  }

  @NotNull
  public TargetDeviceModuleExtension getTargetDeviceModuleExtension() {
    return myModifiableRootModel.getModuleExtension(TargetDeviceModuleExtension.class);
  }
}