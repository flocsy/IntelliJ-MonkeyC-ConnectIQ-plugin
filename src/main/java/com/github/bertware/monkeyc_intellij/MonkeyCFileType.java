package com.github.bertware.monkeyc_intellij;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MonkeyCFileType extends LanguageFileType {

    public static final MonkeyCFileType INSTANCE = new MonkeyCFileType();

    private MonkeyCFileType() {
        super(MonkeyCLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "MonkeyC file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Monkey C file for Garmin Connect IQ development";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "mc";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return MonkeyCIcons.FILE;
    }

}