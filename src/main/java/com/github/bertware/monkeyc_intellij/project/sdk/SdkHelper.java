package com.github.bertware.monkeyc_intellij.project.sdk;

import com.github.bertware.monkeyc_intellij.project.sdk.devices.DevicesDirReader;
import com.github.bertware.monkeyc_intellij.project.sdk.devices.DevicesReader;
import com.github.bertware.monkeyc_intellij.project.sdk.devices.DevicesXmlReader;
import com.google.common.collect.ImmutableMap;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.github.bertware.monkeyc_intellij.Utils.createGeneralCommandLine;

public class SdkHelper {
	public static final String MONKEYDO_CMD = "MONKEYDO_CMD";
	public static final String MONKEYDO_TEST_PARAM = "MONKEYDO_TEST_PARAM";
	public static final String SHELL_CMD = "SHELL_CMD";
	public static final String SIMULATOR_CMD = "SIMULATOR_CMD";
	public static final String CONNECT_IQ_DIR = "CONNECT_IQ_DIR";

	private static final ImmutableMap<String, String> linux = ImmutableMap.<String, String>builder()
		.put(MONKEYDO_CMD, "monkeydo")
		.put(MONKEYDO_TEST_PARAM, "-t")
		.put(SHELL_CMD, "shell")
		.put(SIMULATOR_CMD, "simulator")
		.put(CONNECT_IQ_DIR, "~/.Garmin/ConnectIQ")
		.build();

	private static final ImmutableMap<String, String> mac = ImmutableMap.<String, String>builder()
		.put(MONKEYDO_CMD, "monkeydo")
		.put(MONKEYDO_TEST_PARAM, "-t")
		.put(SHELL_CMD, "shell")
		.put(SIMULATOR_CMD, "ConnectIQ.app")
		.put(CONNECT_IQ_DIR, "~/Library/Application Support/Garmin/ConnectIQ")
		.build();

	private static final ImmutableMap<String, String> win = ImmutableMap.<String, String>builder()
		.put(MONKEYDO_CMD, "monkeydo.bat")
		.put(MONKEYDO_TEST_PARAM, "/t")
		.put(SHELL_CMD, "shell.exe")
		.put(SIMULATOR_CMD, "simulator.exe")
		.build();

	@NotNull
	public static String get(String cmd) throws ExecutionException {
		String result = System.getenv(cmd);
		if (result == null) {
			if (SystemInfoRt.isWindows) {
				result = win.get(cmd);
			} else if (SystemInfoRt.isLinux) {
				result = linux.get(cmd);
			} else if (SystemInfoRt.isMac) {
				result = mac.get(cmd);
			} else {
				throw new ExecutionException("Unsupported OS " + SystemInfoRt.OS_NAME);
			}
		}
		if (result == null) {
			throw new ExecutionException("Unsupported CMD " + cmd);
		}
		if (result.startsWith("~/")) {
			result = result.replaceFirst("^~", System.getProperty("user.home"));
		}
		return result;
	}

	public static String getConnectIqPath(String fileName) throws ExecutionException {
		String connectIqDir = SdkHelper.get(SdkHelper.CONNECT_IQ_DIR);
		return connectIqDir + File.separator + fileName;
	}

	private static String getConnectIqConfigPath(String configFile) throws ExecutionException, IOException {
		File sdkManagerLocationConfig = new File(getConnectIqPath(configFile));
		try (BufferedReader br = new BufferedReader(new FileReader(sdkManagerLocationConfig))) {
			return br.readLine();
		}
	}

	public static String getSdkManagerPath() throws ExecutionException, IOException {
		return getConnectIqConfigPath("sdkmanager-location.cfg");
	}

	public static String getCurrentSdkPath() throws ExecutionException, IOException {
		return getConnectIqConfigPath("current-sdk.cfg");
	}

	public static String getSdksPath() throws ExecutionException {
		return getConnectIqPath("Sdks");
	}

	public static GeneralCommandLine createRunSimulatorCmd(Sdk sdk) throws ExecutionException {
	  String sdkBinPath = MonkeySdkType.getBinPath(sdk);
	  String simulatorExecutableName = get(SIMULATOR_CMD);
	  String exePath = sdkBinPath + simulatorExecutableName;
	  return createGeneralCommandLine(sdkBinPath, exePath).withRedirectErrorStream(true);
	}

	public static DevicesReader getDevicesReader(Sdk sdk) {
		DevicesXmlReader xmlReader = new DevicesXmlReader(sdk);
		if (xmlReader.isRelevant()) {
			return new DevicesXmlReader(sdk);
		}
		return new DevicesDirReader(sdk);
	}
}
