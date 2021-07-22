package com.github.bertware.monkeyc_intellij.language.psi;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClassBodyMembers;

public interface MonkeyClass extends MonkeyComponent {
  MonkeyClassBodyMembers getBodyMembers();
}
