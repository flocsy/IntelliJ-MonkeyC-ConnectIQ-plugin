package com.github.bertware.monkeyc_intellij.project.sdk.devices;

import com.github.bertware.monkeyc_intellij.project.runconfig.TargetDevice;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetSdkVersion;
import com.github.bertware.monkeyc_intellij.project.sdk.SdkHelper;
import com.google.gson.Gson;
import com.intellij.execution.ExecutionException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.Sdk;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DevicesDirReader implements DevicesReader {
    protected static final Logger LOG = Logger.getInstance("#" + DevicesDirReader.class.getName());

    private final Sdk sdk;

    public DevicesDirReader(Sdk sdk) {
        this.sdk = sdk;
    }

    @Override
    public List<TargetDevice> readDevices() {
        List<TargetDevice> result = null;
        String devicesPath = null;
        try {
            devicesPath = SdkHelper.getConnectIqPath("Devices");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Path path = Paths.get(devicesPath);
        if (Files.isDirectory(path)) {
            File[] devices = path.toFile().listFiles(File::isDirectory);
            if (devices != null && devices.length > 0) {
                Gson gson = new Gson();
                result = Arrays.stream(devices)
                        .map(d -> new File(d.getAbsolutePath(), "compiler.json"))
                        .filter(File::canRead)
                        .map(f -> {
                            try {
                                return Files.newBufferedReader(f.toPath());
                            } catch (Exception e) {
                                LOG.warn("Failed to parse " + f.getAbsolutePath());
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .map(r -> gson.fromJson(r, CompilerJson.class))
                        .map(c -> new TargetDevice(c.deviceId, c.displayName))
                        .collect(Collectors.toList());
            }
        }

        return result;
    }

    @Override
    public List<TargetSdkVersion> getTargetSdkVersions() {
        return null;
    }

    static class CompilerJson {
        String deviceId;
        String displayName;
    }
}
