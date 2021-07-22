package com.github.bertware.monkeyc_intellij.language.lexer;

import com.intellij.lexer.FlexAdapter;

public class MonkeyLexer extends FlexAdapter {
  public MonkeyLexer() {
    super(new _MonkeyLexer());
  }
}
