package com.github.bertware.monkeyc_intellij.ide.actions;

import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.github.bertware.monkeyc_intellij.project.sdk.SdkHelper;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;


public class StartSimulatorAction extends AnAction implements DumbAware {
  public StartSimulatorAction() {
    super("Start Simulator");
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
    // TODO: proper warning to user in IDE that the project SDK is not set
    if (projectSdk == null || !(projectSdk.getSdkType() instanceof MonkeySdkType)) {
      return;
    }
    try {
      GeneralCommandLine runSimulatorCmd = SdkHelper.createRunSimulatorCmd(projectSdk);
      runSimulatorCmd.createProcess();
    } catch (ExecutionException executionException) {
      executionException.printStackTrace();
    }
  }
}
