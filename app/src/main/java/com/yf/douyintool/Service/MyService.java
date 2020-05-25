package com.yf.douyintool.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.yf.douyintool.NetUtils;
import com.yf.douyintool.ServerManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.security.auth.login.LoginException;

public class MyService extends Service {
    private static final String TAG = "NigthTeam";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: MyService");
        new Thread() {
            @Override
            public void run() {
                super.run();
                InetAddress inetAddress = null;
                String ip = "0.0.0.0";
                int port = 8005;
                try {
                    inetAddress = InetAddress.getByName(ip);
                    Log.d(TAG, "onCreate: " + inetAddress.getHostAddress());
                    ServerManager serverManager = new ServerManager(getApplicationContext(), inetAddress, port);
                    serverManager.startServer();
                    Log.d(TAG, "run: address = " + NetUtils.getLocalIPAddress()+ ":" + port);
                    Thread.setDefaultUncaughtExceptionHandler(handler);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            //restartApp(); //发生崩溃异常时,重启应用
            Log.e(TAG,"发生崩溃异常, 需要重启应用...");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}