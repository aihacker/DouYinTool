package com.yf.douyintool;

import android.content.Context;
import android.os.Build;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LibUtil {
    private static List<String> a = new ArrayList();

    public static synchronized boolean a(Context context, String str) {
        String mapLibraryName;
        File file;
        File file2;
        synchronized (LibUtil.class) {
            if (a.contains(str)) {
                return true;
            }
            try {
                System.loadLibrary(str);
                a.add(str);
            } catch (UnsatisfiedLinkError error) {
                mapLibraryName = System.mapLibraryName(str);
                file = null;
                if (context != null) {
                }
                file2 = null;
                if (file2 != null) {
                }
                if (file == null) {
                }
            } catch (Throwable unused) {
                return false;
            }
            mapLibraryName = System.mapLibraryName(str);
            file = null;
            if (context != null) {
                if (context.getFilesDir() != null) {
                    file2 = new File(context.getFilesDir(), "libso");
                    if (!file2.exists()) {
                        new File(file2.getAbsolutePath()).mkdirs();
                    }
                    if (file2 != null) {
                        file = new File(file2, mapLibraryName);
                    }
                    if (file == null) {
                        return false;
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    if (a(context, str, file) != null) {
                        return false;
                    }
                    try {
                        System.load(file.getAbsolutePath());
                        a.add(str);
                    } catch (Throwable unused2) {
                        return false;
                    }
                }
            }
            file2 = null;
            if (file2 != null) {
            }
            if (file == null) {
            }
        }
        return true;
    }

    private static String a(Context context, String str, File file) {
        InputStream inputStream;
        Throwable th;
        Closeable closeable = null;
        ZipFile zipFile;
        String stringBuilder;
        try {
            zipFile = new ZipFile(new File(context.getApplicationInfo().sourceDir), 1);
            try {
                OutputStream fileOutputStream;
                StringBuilder stringBuilder2 = new StringBuilder("lib/");
                stringBuilder2.append(Build.CPU_ABI);
                stringBuilder2.append("/");
                stringBuilder2.append(System.mapLibraryName(str));
                ZipEntry entry = zipFile.getEntry(stringBuilder2.toString());
                if (entry == null) {
                    int indexOf = Build.CPU_ABI.indexOf(45);
                    StringBuilder stringBuilder3 = new StringBuilder("lib/");
                    String str2 = Build.CPU_ABI;
                    if (indexOf <= 0) {
                        indexOf = Build.CPU_ABI.length();
                    }
                    stringBuilder3.append(str2.substring(0, indexOf));
                    stringBuilder3.append("/");
                    stringBuilder3.append(System.mapLibraryName(str));
                    str = stringBuilder3.toString();
                    entry = zipFile.getEntry(str);
                    if (entry == null) {
                        StringBuilder stringBuilder4 = new StringBuilder("Library entry not found:");
                        stringBuilder4.append(str);
                        stringBuilder = stringBuilder4.toString();
                        b.a(null);
                        b.a(null);
                        b.a(zipFile);
                        return stringBuilder;
                    }
                }
                file.createNewFile();
                inputStream = zipFile.getInputStream(entry);
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        stringBuilder = th.getMessage();
                        b.a(closeable);
                        b.a(inputStream);
                        b.a(zipFile);
                        return stringBuilder;
                    } catch (Throwable th3) {
                        th = th3;
                        b.a(closeable);
                        b.a(inputStream);
                        b.a(zipFile);
                        throw th;
                    }
                }
                try {
                    byte[] bArr = new byte[16384];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read > 0) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                         //   c.a("android.os.FileUtils", file.getAbsolutePath(), Integer.valueOf(493), Integer.valueOf(-1), Integer.valueOf(-1));
                            b.a(fileOutputStream);
                            b.a(inputStream);
                            b.a(zipFile);
                            return null;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    closeable = fileOutputStream;
                    b.a(closeable);
                    b.a(inputStream);
                    b.a(zipFile);
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                inputStream = null;
                b.a(closeable);
                b.a(inputStream);
                b.a(zipFile);
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            inputStream = null;
//            zipFile = inputStream;
            b.a(closeable);
            b.a(inputStream);
  //          b.a(zipFile);
    //        throw th;
        }
        return null;
    }



}
