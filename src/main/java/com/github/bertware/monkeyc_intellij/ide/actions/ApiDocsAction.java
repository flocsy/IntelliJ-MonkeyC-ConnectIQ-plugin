package com.github.bertware.monkeyc_intellij.ide.actions;

import com.github.bertware.monkeyc_intellij.project.sdk.MonkeySdkType;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;

import java.nio.file.Path;
import java.nio.file.Paths;


public class ApiDocsAction extends AnAction implements DumbAware {
  public ApiDocsAction() {
    super("Browse API Documentation");
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
    // TODO: proper warning to user in IDE that the project SDK is not set
    if (projectSdk == null || !(projectSdk.getSdkType() instanceof MonkeySdkType)) {
      return;
    }
    String apiDocPath = MonkeySdkType.getApiDocPath(projectSdk);
    String framesHtmlPath = apiDocPath + "index.html";
    Path path = Paths.get(framesHtmlPath);
    BrowserUtil.browse(path);
  }
}
