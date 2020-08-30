package com.github.bertware.monkeyc_intellij.project.dom.sdk.projectinfo;

import com.intellij.util.xml.DefinesXml;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.JavaNameStrategy;
import com.intellij.util.xml.NameStrategy;

@DefinesXml
@NameStrategy(JavaNameStrategy.class)
public interface ProjectInfoDomElement extends DomElement {
}