package com.ss.sys.ces;

import android.content.Context;

public class a {
    static {
        try {
            System.loadLibrary("cms");
        } catch (UnsatisfiedLinkError unused) {
        }
    }

    public static native byte[] d(byte[] bArr);

    public static native byte[] e(byte[] bArr);   //mas

    public static native void jni(int i, int i2);

    public static native void jns(int i, String str);

    public static native byte[] leviathan(int i, byte[] bArr);   //X-Gorgen

    public static native byte[] rb(Context context, String str, String str2);

    public static native void ws(int i);

    public static native Object meta(int i, Context context, Object obj);





}
