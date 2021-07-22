package com.github.bertware.monkeyc_intellij.project.dom.manifest;

public interface Manifest extends ManifestDomElement {
  NoNamespaceAttributeValue<Integer> getVersion();

  Application getApplication();
}