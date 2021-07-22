package com.github.bertware.monkeyc_intellij;

import com.intellij.lang.Language;

public class MonkeyCLanguage extends Language {

    public static final MonkeyCLanguage INSTANCE = new MonkeyCLanguage();

    private MonkeyCLanguage() {
        super("MonkeyC");
    }

}