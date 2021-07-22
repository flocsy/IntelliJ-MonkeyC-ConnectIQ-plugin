package com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo;

import com.intellij.util.xml.DomFileDescription;

public class ProjectInfoDomFileDescription extends DomFileDescription<ProjectInfo> {
  public ProjectInfoDomFileDescription() {
    super(ProjectInfo.class, "monkeybrains");
  }
}