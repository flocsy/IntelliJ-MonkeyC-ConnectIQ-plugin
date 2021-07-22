package com.github.bertware.monkeyc_intellij.deserializer.type;

import java.nio.ByteBuffer;

public class MonkeyTypeBool extends MonkeyType<Boolean> {
  private byte type = Type.BOOLEAN;

  final boolean value;

  public MonkeyTypeBool(ByteBuffer bb) {
    value = bb.get() > 0;
  }

  public MonkeyTypeBool(Boolean value) {
    this.value = value;
  }

  @Override
  public Boolean getValue() {
    return value;
  }

  @Override
  public int getSize() {
    return 1;
  }

  @Override
  public byte[] serialize() {
    return new byte[]{
        type,
        (byte) (value ? 0x01 : 0x00)
    };
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
