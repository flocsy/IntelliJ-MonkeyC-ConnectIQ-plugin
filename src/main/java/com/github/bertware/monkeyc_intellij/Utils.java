package com.github.bertware.monkeyc_intellij;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.util.SystemInfo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
  public static GeneralCommandLine createGeneralCommandLine(String workDirectory, String exePath) {
    if (SystemInfo.isMac) {
      String macExePath = exePath;
      List<String> parameters = new ArrayList<>();

      // .app files are opened with open command?
      if (exePath.endsWith(".app")) {
        macExePath = "open";
        parameters.add(exePath);
        parameters.add("--args");
      }

      return new GeneralCommandLine()
        .withWorkDirectory(workDirectory)
        .withExePath(macExePath)
        .withParameters(parameters)
        .withCharset(StandardCharsets.UTF_8);
    }

    return new GeneralCommandLine()
      .withWorkDirectory(workDirectory)
      .withExePath(exePath)
      .withCharset(StandardCharsets.UTF_8);
  }
}
