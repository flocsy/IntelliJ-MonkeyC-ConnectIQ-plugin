package com.github.bertware.monkeyc_intellij.project.sdk;

import com.github.bertware.monkeyc_intellij.MonkeyCIcons;
import com.github.bertware.monkeyc_intellij.project.module.MonkeyConstants;
import com.github.bertware.monkeyc_intellij.project.runconfig.TargetSdkVersion;
import com.github.bertware.monkeyc_intellij.project.sdk.skeleton.SdkSkeleton;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MonkeySdkType extends SdkType {
  public static String COMPILER_INFO_XML = "compilerInfo.xml";

  // major compiler versions come from bin/compilerInfo.xml (provided since SDK 2.2.2)
  private static final List<TargetSdkVersion> OLD_TARGET_SDK_VERSIONS = Arrays.asList(
    TargetSdkVersion.VERSION_1_2_X,
    TargetSdkVersion.VERSION_1_3_X,
    TargetSdkVersion.VERSION_2_1_X,
    TargetSdkVersion.VERSION_2_2_X,
    TargetSdkVersion.VERSION_3_1_X,
    TargetSdkVersion.VERSION_3_2_X,
    TargetSdkVersion.VERSION_4_0_X
  );

  public MonkeySdkType() {
    super(MonkeyConstants.SDK_TYPE_ID);
  }

  @NotNull
  public static MonkeySdkType getInstance() {
    final MonkeySdkType instance = SdkType.findInstance(MonkeySdkType.class);
    return instance;
  }

  @Override
  public void setupSdkPaths(@NotNull Sdk sdk) {
    SdkSkeleton.updateSdk(sdk);
  }

  @Nullable
  @Override
  public String suggestHomePath() {
    try {
      String sdkPath = SdkHelper.getSdksPath();
      Path path = Paths.get(sdkPath);
      if (Files.isDirectory(path)) {
        return sdkPath;
      }
    } catch (Exception ignore) {
    }
    return null;
  }

  @Override
  @NotNull
  public Collection<String> suggestHomePaths() {
    Collection<String> result = null;
    try {
        String sdkPath = SdkHelper.getSdksPath();
        Path path = Paths.get(sdkPath);
        if (Files.isDirectory(path)) {
          File[] sdks = path.toFile().listFiles(File::isDirectory);
          if (sdks != null && sdks.length > 0) {
            result = Arrays.stream(sdks).map(File::getAbsolutePath).collect(Collectors.toList());
          }
        }
    } catch (Exception ignore) {
    }
    return result == null ? Collections.emptyList() : result;
  }

  // 16x16
  @Override
  public Icon getIcon() {
    return MonkeyCIcons.SDK;
  }

  // 16x16
  @Override
  @NotNull
  public Icon getIconForAddAction() {
    return MonkeyCIcons.ADD_SDK;
  }

  @Override
  public boolean isValidSdkHome(String path) {
    File homePath = new File(path);
    if (!homePath.isDirectory()) {
      return false;
    }

    File binPath = new File(homePath, "bin");
    if (!binPath.exists()) {
      return false;
    }

    return new File(binPath, COMPILER_INFO_XML).exists();
  }

  @NotNull
  @Override
  public String suggestSdkName(String currentSdkName, String sdkHome) {
    return getVersionString(sdkHome);
  }

  private final Map<String, String> myCachedVersionStrings = Collections.synchronizedMap(new HashMap<>());

  @NotNull
  @Override
  public String getVersionString(String sdkHome) {
    String versionString = myCachedVersionStrings.get(sdkHome);
    if (versionString == null) {
      versionString = detectCompilerVersion(sdkHome);
      if (!StringUtil.isEmpty(versionString)) {
        myCachedVersionStrings.put(sdkHome, versionString);
      }
    }

    if (versionString == null) {
      return "Unknown version";
    }
    return versionString;
  }

  private String detectCompilerVersion(@NotNull String homePath) {
    final File compilerInfoXml = new File(homePath, "bin/" + COMPILER_INFO_XML);

    if (!compilerInfoXml.exists()) {
      return null;
    }

    String version = null;
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(compilerInfoXml);
      //optional, but recommended
      //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
      doc.getDocumentElement().normalize();

      Node versionNode = doc.getElementsByTagName("version").item(0);
      version = versionNode.getTextContent();
    } catch (SAXException | ParserConfigurationException | IOException e) {
      e.printStackTrace();
    }

    return version;
  }

  public static List<TargetSdkVersion> getTargetSdkVersions(@Nullable String sdkBinPath) {
    if (sdkBinPath == null) {
      return OLD_TARGET_SDK_VERSIONS;
    }

    final File compilerInfoXml = new File(sdkBinPath, COMPILER_INFO_XML);
    if (!compilerInfoXml.exists()) {
      return OLD_TARGET_SDK_VERSIONS;
    }

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(compilerInfoXml);
      //optional, but recommended
      //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
      doc.getDocumentElement().normalize();

      NodeList targetSdkVersionsNodeList = doc.getElementsByTagName("targetSdkVersions");

      if (targetSdkVersionsNodeList.getLength() == 0) {
        return OLD_TARGET_SDK_VERSIONS;
      }

      Node targetSdkVersionsNode = targetSdkVersionsNodeList.item(0);
      NodeList childNodes = targetSdkVersionsNode.getChildNodes();

      List<TargetSdkVersion> targetSdkVersions = new ArrayList<>();
      for (int i = 0; i < childNodes.getLength(); i++) {
        Node targetVersion = childNodes.item(i);
        if (targetVersion.getNodeType() != Node.ELEMENT_NODE) {
          continue;
        }
        String targetVersionString = targetVersion.getTextContent();

        String[] number = targetVersionString.split("\\.");
        int major = Short.parseShort(number[0]);
        int minor = Short.parseShort(number[1]);
        String versionWildcard = major + "." + minor + ".x";
        TargetSdkVersion targetSdkVersion = new TargetSdkVersion(targetVersionString, versionWildcard);
        targetSdkVersions.add(targetSdkVersion);
      }
      return targetSdkVersions;

    } catch (SAXException | ParserConfigurationException | IOException e) {
      e.printStackTrace();
    }
    return OLD_TARGET_SDK_VERSIONS;
  }

  @Nullable
  @Override
  public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
    return null;
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Connect IQ SDK";
  }

  @Override
  public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {
  }

  public VirtualFile getBinDir(@NotNull Sdk sdk) {
    return sdk.getHomeDirectory().findChild("bin");
  }

  public static String getBinPath(@NotNull Sdk sdk) {
    return getConvertedHomePath(sdk) + "bin" + File.separator;
  }

  public static String getApiDocPath(@NotNull Sdk sdk) {
    return getConvertedHomePath(sdk) + "doc" + File.separator;
  }

  private static String getConvertedHomePath(@NotNull Sdk sdk) {
    String path = sdk.getHomePath().replace('/', File.separatorChar);
    if (!path.endsWith(File.separator)) {
      path += File.separator;
    }
    return path;
  }
}
