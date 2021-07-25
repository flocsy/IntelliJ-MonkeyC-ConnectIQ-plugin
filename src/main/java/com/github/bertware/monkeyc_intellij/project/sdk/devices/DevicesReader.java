package com.github.bertware.monkeyc_intellij.project.sdk.devices;

import com.github.bertware.monkeyc_intellij.project.runconfig.TargetDevice;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetSdkVersion;

import java.util.List;

public interface DevicesReader {
    List<TargetDevice> readDevices();
    List<TargetSdkVersion> getTargetSdkVersions();
}
