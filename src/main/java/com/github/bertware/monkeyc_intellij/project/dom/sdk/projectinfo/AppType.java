package com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo;

import com.intellij.util.xml.GenericAttributeValue;

public interface AppType extends ProjectInfoDomElement {
  GenericAttributeValue<String> getId();

  GenericAttributeValue<String> getValue();

  GenericAttributeValue<String> getName();

  GenericAttributeValue<String> getDescription();
}