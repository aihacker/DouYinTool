package com.bytedance.frameworks.core.encrypt;

public class TTEncryptUtils {
    private static native byte[] handleData(byte[] bArr, int i);

    static {
        System.loadLibrary("ttEncrypt");
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr != null && i > 0) {
            try {
                if (bArr.length == i) {
                    return handleData(bArr, i);
                }
            } catch (Throwable unused) {
                return null;
            }
        }
        return null;
    }

}
