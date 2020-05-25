package com.ss.sys.secuni.b;

import android.content.Context;

public class c {
    static {
        try {
            System.loadLibrary("cms");
        } catch (Throwable unused) {
        }
    }

    public static native byte[] n0(Context context);

    public static native int n1(Context context, String str);

}
