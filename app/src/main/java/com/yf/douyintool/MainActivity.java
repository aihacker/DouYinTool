package com.yf.douyintool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ss.android.common.applog.UserInfo;
import com.ss.sys.ces.a;
import com.yf.douyintool.Service.MyService;

/***
 * 来源：
 * https://github.com/coder-fly/douyin_sign
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG="yf";
    private static final String NULL_MD5_STRING = "00000000000000000000000000000000";
//    public String ck="install_id=94761796773; ttreq=1$9cf124c9495dc5a1da9e9a611eee577738dc8fc1; odin_tt=9ac4660ee23266248ee5edb5afe9a293118bbaa210b20316f36845e27e6bd44517b8a69241c8a4e09d91f53b0642fe61e70cf88eadfa6d9d59bc401369d06c40; qh[360]=1";
    public String ck="install_id=94761796773; qh[360]=1";
    public   String sessionid="";
    public String xtttoken="004cd1df7bd4fddd6674a80e6da09c27304b0a39de5343f08f1adb0f196433c3cc48286ee0aa915761b71e0201000f8ccf37";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void OpenServer(View view){
        switch (view.getId()){
            case R.id.id_bt_index:
                //启动服务:创建-->启动-->销毁
                //如果服务已经创建了，后续重复启动，操作的都是同一个服务，不会再重新创建了，除非你先销毁它
                UserInfo.setAppId(1128);
                UserInfo.initUser("a3668f0afac72ca3f6c1697d29e0e1bb1fef4ab0285319b95ac39fa42c38d05f");
                Intent intent = new Intent(this, MyService.class);
                Log.d(TAG, "operate: button");
                startService(intent);
                ((Button) view).setText("服务已开启");
        }
    }




}
