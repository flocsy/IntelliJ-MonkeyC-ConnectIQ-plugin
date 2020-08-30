package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.MonkeyCLanguage;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MonkeyTokenType extends IElementType {

  public MonkeyTokenType(@NotNull @NonNls String debugName) {
    super(debugName, MonkeyCLanguage.INSTANCE);
  }

}
