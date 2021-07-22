package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.MonkeyCLanguage;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MonkeyElementType extends IElementType {
  public MonkeyElementType(@NotNull @NonNls String debugName) {
    super(debugName, MonkeyCLanguage.INSTANCE);
  }
}
