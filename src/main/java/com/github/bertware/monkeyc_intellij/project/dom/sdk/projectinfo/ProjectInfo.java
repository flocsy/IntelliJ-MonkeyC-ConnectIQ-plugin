package com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo;

public interface ProjectInfo extends ProjectInfoDomElement {
  AppPermissions getAppPermissions();

  AppTypes getAppTypes();

  PermissionMaps getPermissionMaps();

  NewProjectFilesMaps getNewProjectFilesMaps();
}
