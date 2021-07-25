package com.github.bertware.monkeyc_intellij.ide.actions;

import com.github.bertware.monkeyc_intellij.project.sdk.SdkHelper;
import com.intellij.execution.ExecutionException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

import java.io.IOException;

import static com.github.bertware.monkeyc_intellij.Utils.createGeneralCommandLine;


public class OpenSdkManagerAction extends AnAction implements DumbAware {
  public OpenSdkManagerAction() {
    super("Open SDK Manager");
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    try {
      String homeDir = System.getProperty("user.home");
      String sdkManagerPath = SdkHelper.getSdkManagerPath();
      createGeneralCommandLine(homeDir, sdkManagerPath).withRedirectErrorStream(true);
    } catch (ExecutionException | IOException executionException) {
      executionException.printStackTrace();
    }
  }
}
