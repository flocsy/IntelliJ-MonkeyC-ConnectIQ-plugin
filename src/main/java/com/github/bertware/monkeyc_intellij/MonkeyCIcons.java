package com.github.bertware.monkeyc_intellij;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;

import javax.swing.*;

public class MonkeyCIcons {
    public static final Icon FILE = load("/icons/mc_file.png"); // 16

    public static final Icon SDK = load("/icons/sdk.png"); // 16

    public static final Icon ADD_SDK = load("/icons/addsdk.png"); // 16
    public static final Icon MODULE24 = load("/icons/module.png"); // 24
    public static final Icon MODULE16 = load("/icons/sdk.png"); // 16
    public static final Icon MODULE_TEST_16 = new LayeredIcon(MODULE16, AllIcons.Nodes.JunitTestMark); // 16
    public static final Icon APP_SETTINGS13 = load("/icons/app_settings_13.png"); // 13

    public static final Icon SAVE16 = AllIcons.Actions.Menu_saveall;
    public static final Icon REFRESH16 = AllIcons.Actions.Refresh;

    private static Icon load(String path) {
        return IconLoader.getIcon(path, MonkeyCIcons.class);
    }
}
