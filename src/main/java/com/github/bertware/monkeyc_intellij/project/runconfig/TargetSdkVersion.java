package com.github.bertware.monkeyc_intellij.project.runconfig;

import org.jetbrains.annotations.NotNull;

public class TargetSdkVersion {
  public static final TargetSdkVersion VERSION_1_2_X = new TargetSdkVersion("1.2.1", "1.2.x");
  public static final TargetSdkVersion VERSION_1_3_X = new TargetSdkVersion("1.3.1", "1.3.x");
  public static final TargetSdkVersion VERSION_2_1_X = new TargetSdkVersion("2.1.1", "2.1.x");
  public static final TargetSdkVersion VERSION_2_2_X = new TargetSdkVersion("2.2.0", "2.2.x");
  public static final TargetSdkVersion VERSION_3_1_X = new TargetSdkVersion("3.1.9", "3.1.x");
  public static final TargetSdkVersion VERSION_3_2_X = new TargetSdkVersion("3.2.1", "3.2.x");

  private String id;
  private String name;

  public TargetSdkVersion(@NotNull String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name != null ? name : id;
  }

  public static TargetSdkVersion fromId(String targetSdkVersionId) {
    return new TargetSdkVersion(targetSdkVersionId, targetSdkVersionId);
  }

  // only id matters
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TargetSdkVersion that = (TargetSdkVersion) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return getName();
  }
}

