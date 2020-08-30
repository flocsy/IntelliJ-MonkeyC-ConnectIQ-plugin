package com.github.bertware.monkeyc_intellij.ide.actions.appsettings.ui;

import com.github.bertware.monkeyc_intellij.MonkeyCIcons;
import com.github.bertware.monkeyc_intellij.ide.actions.appsettings.AppSettingsForm;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.ui.JBUI;



import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AppSettingsToolWindowPanel extends SimpleToolWindowPanel {
  private final Project project;
  private final AppSettingsForm appSettingsForm;

  public AppSettingsToolWindowPanel(Project project) {
    super(true, true);
    this.project = project;

    setToolbar(createToolbarPanel());
    appSettingsForm = new AppSettingsForm(project);
    JPanel panel = appSettingsForm.getPanel();
    panel.setBorder(new EmptyBorder(0, 10, 0, 0));
    setContent(ScrollPaneFactory.createScrollPane(panel));
  }

  private JPanel createToolbarPanel() {
    final DefaultActionGroup group = new DefaultActionGroup();
    group.add(new SendAction());
    group.add(new ReceiveAction());

    final ActionToolbar actionToolBar = ActionManager.getInstance().createActionToolbar("AppSettingsToolWindowPanel", group, true);
    return JBUI.Panels.simplePanel(actionToolBar.getComponent());
  }

  private boolean isModuleSelected() {
    return appSettingsForm != null && appSettingsForm.isModuleSelected();
  }

  private final class SendAction extends AnAction {
    public SendAction() {
      super("Send to simulator", "Send to simulator", MonkeyCIcons.SAVE16);
    }

    @Override
    public void update(AnActionEvent e) {
      e.getPresentation().setEnabled(isModuleSelected());
    }

    public void actionPerformed(AnActionEvent e) {
      appSettingsForm.sendSettingsToSimulator();
    }
  }

  private final class ReceiveAction extends AnAction {
    public ReceiveAction() {
      super("Receive from simulator", "Receive from simulator", MonkeyCIcons.REFRESH16);
    }

    @Override
    public void update(AnActionEvent e) {
      e.getPresentation().setEnabled(isModuleSelected());
    }

    public void actionPerformed(AnActionEvent e) {
      appSettingsForm.receiveSettings();
    }
  }
}
