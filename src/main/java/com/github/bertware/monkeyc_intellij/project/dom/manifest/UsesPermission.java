package com.github.bertware.monkeyc_intellij.project.dom.manifest;

import com.intellij.spellchecker.xml.NoSpellchecking;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;

public interface UsesPermission extends ManifestDomElement {
  @NameValue
  @NoSpellchecking
  @Required
  NoNamespaceAttributeValue<String> getId();
}