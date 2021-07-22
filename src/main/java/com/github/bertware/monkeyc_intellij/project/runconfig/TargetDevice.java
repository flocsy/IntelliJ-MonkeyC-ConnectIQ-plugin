package com.github.bertware.monkeyc_intellij.project.runconfig;

public class TargetDevice {
  public static final TargetDevice DEFAULT_DEVICE = new TargetDevice("vivoactive", "vívoactive®");

  private String id;
  private String name;

  public TargetDevice() {
  }

  public TargetDevice(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name != null ? name : id;
  }

  public void setName(String name) {
    this.name = name;
  }

  // only id matters
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TargetDevice that = (TargetDevice) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public static TargetDevice fromId(String targetDeviceId) {
    return new TargetDevice(targetDeviceId, targetDeviceId);
  }

  @Override
  public String toString() {
    return getName();
  }
}
