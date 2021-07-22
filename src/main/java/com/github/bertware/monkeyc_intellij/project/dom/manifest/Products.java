package com.github.bertware.monkeyc_intellij.project.dom.manifest;

import java.util.List;

public interface Products extends ManifestDomElement {
  List<Product> getProducts();
}