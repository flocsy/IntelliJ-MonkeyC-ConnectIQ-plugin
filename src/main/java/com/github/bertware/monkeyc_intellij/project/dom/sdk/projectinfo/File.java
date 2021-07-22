package com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo;

import com.intellij.util.xml.GenericAttributeValue;

public interface File extends ProjectInfoDomElement {
  GenericAttributeValue<String> getType();

  String getValue();
}
