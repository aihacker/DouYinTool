package com.yf.douyintool;

import android.text.TextUtils;

public class SecurityUntil {
    static final char[] a;

    static {
        a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public static String d(String arg3) {
        int v0 = 0;
        try {
            if(TextUtils.isEmpty(((String)arg3))) {
                return null;
            }

            byte[] v1 = arg3.getBytes("UTF-8");
            while(v0 < v1.length) {
                v1[v0] = ((byte)(v1[v0] ^ 5));
                ++v0;
            }

            return a(v1, 0, v1.length);
        }
        catch(Exception v0_1) {
            return arg3;
        }
    }

    public static String a(byte[] arg8, int arg9, int arg10) {
        if(arg8 == null) {
            throw new NullPointerException("bytes is null");
        }

        if(arg9 >= 0 && arg9 + arg10 <= arg8.length) {
            char[] v3 = new char[arg10 * 2];
            int v0 = 0;
            int v2 = 0;
            while(v0 < arg10) {
                int v4 = arg8[v0 + arg9] & 255;
                int v5 = v2 + 1;
                v3[v2] = a[v4 >> 4];
                v2 = v5 + 1;
                v3[v5] = a[v4 & 15];
                ++v0;
            }

            return new String(v3, 0, arg10 * 2);
        }

        throw new IndexOutOfBoundsException();
    }

}
