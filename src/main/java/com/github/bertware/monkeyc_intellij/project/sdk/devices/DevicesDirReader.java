package com.github.bertware.monkeyc_intellij.project.sdk.devices;

import com.github.bertware.monkeyc_intellij.project.runconfig.TargetDevice;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetSdkVersion;
import com.intellij.openapi.projectRoots.Sdk;

import java.util.List;

public class DevicesDirReader implements DevicesReader {
    public DevicesDirReader(Sdk sdk) {

    }

    @Override
    public List<TargetDevice> readDevices() {
        return null;
    }

    @Override
    public List<TargetSdkVersion> getTargetSdkVersions() {
        return null;
    }
}
