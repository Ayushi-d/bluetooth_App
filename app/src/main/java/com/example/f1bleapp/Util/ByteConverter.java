package com.example.f1bleapp.Util;

public class ByteConverter {
    private static final char[] UPPER_HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static String getHexStringFromByteArray(byte[] input, boolean isSpacingRequired) {
        StringBuilder HexBuilder = new StringBuilder(2 * input.length);
        for (int i = 0; i < input.length; i++) {
            HexBuilder.append(UPPER_HEX_CHARS[(input[i] & 0xF0) >> 4]); // Extract Higher Nibble
            HexBuilder.append(UPPER_HEX_CHARS[(input[i] & 0x0F)]); // Extract Lower Nibble

            if (isSpacingRequired) {
                if (i != input.length - 1) {
                    HexBuilder.append(" ");
                }
            }
        }
        return HexBuilder.toString();
    }
}