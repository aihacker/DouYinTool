package com.yf.douyintool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;;

import com.bytedance.frameworks.core.encrypt.TTEncryptUtils;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.ss.android.common.applog.UserInfo;
import com.ss.sys.ces.a;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.framework.body.FileBody;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.yf.douyintool.bean.DeviceBean;

import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * 结合AndServer，实现抖音X-Gorgon算法，设备id生成接口
 * http://www.louislivi.com/archives/147
 */
@Controller
public class MyController {
    public static final String TAG = "MyController";
    private static final String NULL_MD5_STRING = "00000000000000000000000000000000";
    public String sessionid = "";
    public String ck="odin_tt=1";
    public String xtttoken = "004cd1df7bd4fddd6674a80e6da09c27304b0a39de5343f08f1adb0f196433c3cc48286ee0aa915761b71e0201000f8ccf37";
    public String comman_params = "&retry_type=no_retry&ac=wifi&&gps_access=1&js_sdk_version=&app_type=normal&manifest_version_code=570&address_book_access=2&mcc_mnc=46011&os_version=8.1.0&channel=wandoujia_aweme1&version_code=570&device_type=vivo%20X20A&language=zh&resolution=1080*2160&openudid=a8b382e730db8c41&update_version_code=5702&app_name=aweme&version_name=5.7.0&os_api=27&device_brand=vivo&ssmix=a&device_platform=android&dpi=480&aid=1128";

    public MyController(){
        UserInfo.setAppId(1128);
        UserInfo.initUser("a3668f0afac72ca3f6c1697d29e0e1bb1fef4ab0285319b95ac39fa42c38d05f");
    }

    @GetMapping("/device_id")
    public void device_register(HttpRequest request, HttpResponse response) {
        try {
            JSONObject device_message  =  new JSONObject(getDevice());
            response.setBody(new JsonBody(device_message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/user_info")
    public void user_info(HttpRequest request, HttpResponse response) {
        String user_id = request.getParameter("user_id");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url="https://aweme.snssdk.com/aweme/v1/user/?user_id="+user_id+"&ts="+ts+"&iid="+iid+"&device_id="+device_id+"&_rticket="+_ricket+comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/get_comments")
    public void get_comments(HttpRequest request, HttpResponse response) {
        String aweme_id = request.getParameter("aweme_id");
        String cursor = request.getParameter("cursor");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://api.amemv.com/aweme/v2/comment/list/?aweme_id="+aweme_id+"&cursor="+cursor+"&count=20&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid + comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/sign")
    public void sign(HttpRequest request, HttpResponse response) {
        String url = request.getParameter("url");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        if(device_id==null| iid==null){
            response.setBody(new StringBody("params error,please check."));
        }
        url = url+"&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid;
        JSONObject message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(message));
    }

    @PostMapping("/comments")
    public void comments(HttpRequest request, HttpResponse response) {
        String aweme_id = request.getParameter("aweme_id");

        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = " https://aweme.snssdk.com/aweme/v2/comment/list/?aweme_id="+aweme_id+"&cursor=0&count=20&address_book_access=1&gps_access=1&ts="+ts+"&js_sdk_version=&app_type=normal&os_api=22&device_type=PRO%206%20Plus&device_platform=android&ssmix=a&manifest_version_code=570&dpi=191&uuid=864757481134476&version_code=570&app_name=aweme&version_name=5.7.0&openudid=27c5a40b187e24c1&resolution=576*1024&os_version=5.1.1&language=zh&device_brand=Meizu&ac=wifi&update_version_code=5702&aid=1128&channel=wandoujia_aweme1&_rticket="+_ricket+"&mcc_mnc=46000";
        JSONObject device_message = signs_("","",ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/user_post")
    public void user_post(HttpRequest request, HttpResponse response) {
        String user_id = request.getParameter("user_id");
        String max_cursor = request.getParameter("max_cursor");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/aweme/post/?max_cursor="+max_cursor+"&user_id="+user_id+"&count=20&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid + "&retry_type=no_retry" +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/user_favorite")
    public void user_favorite(HttpRequest request, HttpResponse response) {
        String user_id = request.getParameter("user_id");
        String max_cursor = request.getParameter("max_cursor");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/aweme/favorite/?max_cursor="+max_cursor+"&user_id="+user_id+"&count=20&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid  +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/user_following")
    public void user_following(HttpRequest request, HttpResponse response) {
        String user_id = request.getParameter("user_id");
        String max_time = request.getParameter("max_time");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/user/following/list/?user_id="+user_id+"&max_time="+max_time+"&count=20&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid  +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/get_gon")
    public void get_gon(HttpRequest request, HttpResponse response){
        String url = request.getParameter("url");
        String iid = request.getParameter("iid");
        long time = 1579572797;
        String p=url.substring(url.indexOf("?")+1,url.length());
        String cookie = "install_id="+ iid +"; qh[360]=1";
        String s=getXGon(p,"",cookie,sessionid);
        Log.i(TAG,s+time);
        String XGon= ByteToStr(a.leviathan((int)time,StrToByte(s)));
        JSONObject device_message  =  new JSONObject();
        try{
            device_message.put("x-gorgon",XGon);
            device_message.put("time",time);
            device_message.put("url",url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setBody(new JsonBody(device_message));

    }


    @PostMapping("/user_followers")
    public void user_followers(HttpRequest request, HttpResponse response) {
        String user_id = request.getParameter("user_id");
        String max_time = request.getParameter("max_time");
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/user/follower/list/?user_id="+user_id+"&max_time="+max_time+"&count=20&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid  +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/hot_videos")
    public void hot_videos(HttpRequest request, HttpResponse response) {
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/hotsearch/aweme/billboard/?&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid  +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/positive_video")
    public void positive_video(HttpRequest request, HttpResponse response) {
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/hotsearch/positive_energy/billboard/?&ts="+ts+"&_rticket="+_ricket+"&device_id="+device_id+"&iid="+iid  +comman_params;
        JSONObject device_message = signs_(device_id,iid,ts,url);
        response.setBody(new JsonBody(device_message));
    }

    @PostMapping("/video_search")
    public void video_search(HttpRequest request, HttpResponse response) {
        String device_id = request.getParameter("device_id");
        String iid = request.getParameter("iid");
        String keyword = request.getParameter("keyword");
        String offset = request.getParameter("offset");
        String search_id = request.getParameter("search_id");
        if(search_id==null){
            search_id="";
        }
        System.out.print("ssss"+search_id);
        int ts= (int) (System.currentTimeMillis()/1000);
        String _ricket=System.currentTimeMillis()+"";
        String url = "https://aweme.snssdk.com/aweme/v1/search/item/?ts="+ts+"&js_sdk_version=&app_type=normal&os_api=23&device_type=MI%205s&device_platform=android&ssmix=a&iid="+iid+"&manifest_version_code=570&dpi=192&uuid=910000000127703&version_code=570&app_name=aweme&version_name=5.7.0&openudid=a631422c6936684a&device_id="+device_id+"&resolution=576*1024&os_version=6.0.1&language=zh&device_brand=Xiaomi&ac=wifi&update_version_code=5702&aid=1128&channel=wandoujia_aweme1&_rticket=" + _ricket;
        String url3 = "https://aweme.snssdk.com/aweme/v1/search/item/?cursor=0&keyword=xianggang&count=10&ts="+ts+"&js_sdk_version=&app_type=normal&os_api=23&device_type=MI%205s&device_platform=android&ssmix=a&iid="+iid+"&manifest_version_code=570&dpi=192&uuid=910000000127703&version_code=570&app_name=aweme&version_name=5.7.0&openudid=a631422c6936684a&device_id="+device_id+"&resolution=576*1024&os_version=6.0.1&language=zh&device_brand=Xiaomi&ac=wifi&update_version_code=5702&aid=1128&channel=wandoujia_aweme1&_rticket=" + _ricket;
//        String url2 = "https://aweme.snssdk.com/aweme/v1/search/item/?ts="+ts+"&js_sdk_version=&app_type=normal&os_api=23&device_type=MI%205s&device_platform=android&ssmix=a&iid="+iid+"&manifest_version_code=570&dpi=192&uuid=910000000127703&version_code=570&app_name=aweme&version_name=5.7.0&openudid=a631422c6936684a&device_id="+device_id+"&resolution=576*1024&os_version=6.0.1&language=zh&device_brand=Xiaomi&ac=wifi&update_version_code=5702&aid=1128&channel=wandoujia_aweme1&_rticket=" + _ricket;
//        url2 += "&keyword="+keyword+"&offset="+offset+"&count=10&source=video_search&is_pull_refresh=1&hot_search=0&search_id="+search_id+"&query_correct_type=1";
        String ss=url3.substring(url3.indexOf("?")+1,url.length()).replace("&","|").replace("=","|");
        String [] km=ss.split("\\|");
        System.out.println(Arrays.toString(km));
        String ascp=UserInfo.getUserInfo(ts,url,km,device_id);
        String as=ascp.substring(0,22);
        String cp=ascp.substring(22,ascp.length());
        String mas=ByteToStr(a.e(as.getBytes()));
        url=url+"&as="+as+"&cp="+cp+"&mas="+mas;
        long time=System.currentTimeMillis()/1000;
        String p=url.substring(url.indexOf("?")+1,url.length());
        String cookie = "install_id="+ iid +"; qh[360]=1";
        String s=getXGon(p,"",cookie,sessionid);
        String XGon= ByteToStr(a.leviathan((int)time,StrToByte(s)));
        JSONObject device_message  =  new JSONObject();
        try{
            device_message.put("as",as);
            device_message.put("cp",cp);
            device_message.put("mas",mas);
            device_message.put("x-gorgon",XGon);
            device_message.put("time",time);
            device_message.put("url",url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setBody(new JsonBody(device_message));
    }



    public static String getParam(String url, String name) {
        String params = url.substring(url.indexOf("?") + 1, url.length());
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return split.get(name);
    }

    public JSONObject signs_(String device_id,String iid,int ts,String url){
        String ss=url.substring(url.indexOf("?")+1,url.length()).replace("&","|").replace("=","|");
        String [] km=ss.split("\\|");
        Log.i(TAG,"ts="+ts);
        Log.i(TAG,"url="+url);
        Log.i(TAG,"km="+Arrays.toString(km));
        Log.i(TAG,"device_id="+device_id);
        String ascp=UserInfo.getUserInfo(ts,url,km,device_id);
        String as=ascp.substring(0,22);
        String cp=ascp.substring(22,ascp.length());
        String mas=ByteToStr(a.e(as.getBytes()));
        url=url+"&as="+as+"&cp="+cp+"&mas="+mas;
        long time=System.currentTimeMillis()/1000;
        String p=url.substring(url.indexOf("?")+1,url.length());
//        String cookie = "install_id="+ iid +"; qh[360]=1";
        String cookie = "";
        String s=getXGon(p,"",cookie,sessionid);
        String XGon= ByteToStr(a.leviathan((int)time,StrToByte(s)));
        JSONObject device_message  =  new JSONObject();
        try{
            device_message.put("as",as);
            device_message.put("cp",cp);
            device_message.put("mas",mas);
            device_message.put("x-gorgon",XGon);
            device_message.put("time",time);
            device_message.put("url",url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        doGetNet(url,time,XGon);
        return device_message;
    }

    public static String getAndroidId(Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    public static String ByteToStr(byte[] bArr) {

        int i = 0;

        char[] toCharArray = "0123456789abcdef".toCharArray();
        char[] cArr = new char[(bArr.length * 2)];
        while (i < bArr.length) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            cArr[i3] = toCharArray[i2 >>> 4];
            cArr[i3 + 1] = toCharArray[i2 & 15];
            i++;
        }
        return new String(cArr);

    }

    public static byte[] StrToByte(String str) {
        String str2 = str;
        Object[] objArr = new Object[1];
        int i = 0;
        objArr[0] = str2;

        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        while (i < length) {
            bArr[i / 2] = (byte) ((Character.digit(str2.charAt(i), 16) << 4) + Character.digit(str2.charAt(i + 1), 16));
            i += 2;
        }
        return bArr;
    }

    public String getXGon(String url, String stub, String ck, String sessionid) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(url)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(url).toLowerCase());
        }

        if (TextUtils.isEmpty(stub)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(stub);
        }

        if (TextUtils.isEmpty(ck)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(ck).toLowerCase());
        }

        if (TextUtils.isEmpty(sessionid)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(sessionid).toLowerCase());
        }
        return sb.toString();
    }

    public String encryption(String str) {
        String re_md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5.toUpperCase();
    }

    public String[] MapTo(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
//            System.out.println("key= "+ key + " and value= " + map.get(key));
            list.add(key);
            list.add(map.get(key));
        }
        String[] str = (String[]) list.toArray(new String[0]);
        return str;
    }

    public static String format_url(String str) {
        int indexOf = str.indexOf("?");
        int indexOf2 = str.indexOf("#");
        return indexOf == -1 ? null : indexOf2 == -1 ? str.substring(indexOf + 1) : indexOf2 < indexOf ? null : str.substring(indexOf + 1, indexOf2);
    }

    public String result;


    public String getDevice() {
        String uuid = DeviceUtil.getRanInt(15);;  //设备id
        String openudid = DeviceUtil.getRanInt(16);;  //android_id
        String _rticket = System.currentTimeMillis() + "";   //获取当前时间
        String url = "https://log.snssdk.com/service/2/device_register/?mcc_mnc=46000&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=570&version_name=5.7.0&device_platform=android&ssmix=a&device_type=DUK-AL20&device_brand=HUAWEI&language=zh&os_api=22&os_version=5.1.1&uuid=" + uuid + "&openudid=" + openudid + "&manifest_version_code=570&resolution=1024x576&dpi=191&update_version_code=5702&_rticket=" + _rticket + "&tt_data=a&config_retry=b";
        String stb = url.substring(url.indexOf("?") + 1, url.length());
        String STUB = encryption(stb).toUpperCase();
        String ck = "qh[360]=1";
        int time = (int) (System.currentTimeMillis() / 1000);

        String s = getXGon(url, STUB, ck, null);
        String XGon = ByteToStr(a.leviathan(time, StrToByte(s)));

        final RequestBody formBody = RequestBody.create(MediaType.parse("application/octet-stream;tt-data=a"), getDevice(openudid, uuid));
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("X-SS-STUB",stb)   //post md5
                .addHeader("X-SS-REQ-TICKET", System.currentTimeMillis() + "")
//                .addHeader("X-Khronos", time + "")
//                .addHeader("X-Gorgon",XGon)
                .addHeader("sdk-version", "1")
                .addHeader("Content-Type", "application/octet-stream;tt-data=a")
                .addHeader("Cookie", ck)
                .addHeader("X-Pods", "")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "okhttp/3.10.0.1")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                result = response.body().string();
            }
        });

        try{
            JSONObject device_message  =  new JSONObject(result);
            device_message.put("uuid",uuid);
            device_message.put("openudid",openudid);
            return device_message.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] compressWithgzip(byte[] bArr) throws Exception {
        Throwable th;
        GZIPOutputStream gZIPOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
            GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream2.write(bArr);
                gZIPOutputStream2.close();
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = gZIPOutputStream2;
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (gZIPOutputStream != null) {
            }
//            throw th;
        }
        return null;
    }


    public byte[] getDevice(String openudid, String udid) {
        //DeviceBean
        String Serial_number = DeviceUtil.getRanInt(8);
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.set_gen_time(System.currentTimeMillis() + "");
        deviceBean.setMagic_tag("ss_app_log");
        //HeaderBean
        DeviceBean.HeaderBean headerBean = new DeviceBean.HeaderBean();
        headerBean.setDisplay_name("抖音短视频");
        headerBean.setUpdate_version_code(5702);
        headerBean.setManifest_version_code(570);
        headerBean.setAid(1128);
        headerBean.setChannel("wandoujia_aweme1");
        headerBean.setAppkey("57bfa27c67e58e7d920028d3"); //appkey
        headerBean.setPackageX("com.ss.android.ugc.aweme");
        headerBean.setApp_version("5.7.0");
        headerBean.setVersion_code(570);
        headerBean.setSdk_version("2.5.5.8");
        headerBean.setOs("Android");
        headerBean.setOs_version("5.1.1");
        headerBean.setOs_api(22);
        headerBean.setDevice_model("DUK-AL20");
        headerBean.setDevice_brand("HUAWEI");
        headerBean.setDevice_manufacturer("HUAWEI");
        headerBean.setCpu_abi("armeabi-v7a");
        headerBean.setBuild_serial(Serial_number);  ////android.os.Build.SERIAL
        headerBean.setRelease_build("c4e7178_20190401");  // release版本
        headerBean.setDensity_dpi(191);
        headerBean.setDisplay_density("mdpi");
        headerBean.setResolution("1024x576");
        headerBean.setLanguage("zh");
//        headerBean.setMc("dc:87:9d:2d:cb:dc");  //mac 地址
        headerBean.setMc(DeviceUtil.getMac());  //mac 地址
        headerBean.setTimezone(8);
        headerBean.setAccess("wifi");
        headerBean.setNot_request_sender(0);
        headerBean.setCarrier("China Mobile GSM");
        headerBean.setMcc_mnc("46000");
        headerBean.setRom("EMUI-eng.se.infra.20191230.112159");  //Build.VERSION.INCREMENTAL
        headerBean.setRom_version("HUAWEI-user 5.1.1 20171130.276299 release-keys");  //Build.DISPLAY
        headerBean.setSig_hash("aea615ab910015038f73c47e45d21466");  //app md5加密  固定
        headerBean.setDevice_id("");   //获取之后的设备id
        headerBean.setOpenudid(openudid);  //openudid #############
        headerBean.setUdid(udid);  //真机的imei ############
        headerBean.setClientudid(UUID.randomUUID().toString());  //uuid
//        headerBean.setClientudid("3fa25ffa-1203-407d-9187-426e2d745156");  //uuid
        headerBean.setSerial_number(Serial_number);  //android.os.Build.SERIAL
        headerBean.setRegion("CN");
        headerBean.setTz_name("Asia\\/Shanghai");  //timeZone.getID();
        headerBean.setTz_offset(28800);  //String.valueOf(timeZone.getOffset(System.currentTimeMillis()) / 1000)
        headerBean.setSim_region("cn");
        List<DeviceBean.HeaderBean.SimSerialNumberBean> sim_serial_number = new ArrayList<>();
        DeviceBean.HeaderBean.SimSerialNumberBean bean = new DeviceBean.HeaderBean.SimSerialNumberBean();
//        bean.setSim_serial_number(DeviceUtil.getRanInt(20));
        bean.setSim_serial_number("89014103211118510720");
        sim_serial_number.add(bean);
        headerBean.setSim_serial_number(sim_serial_number);
        deviceBean.setHeader(headerBean);
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        timeZone.getID();
        //r
        Gson gson = new Gson();

        String r = gson.toJson(deviceBean);

        Log.i(TAG, r);
        try {
            byte[] bArr2 = r.getBytes("UTF-8");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr2);
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            bArr2 = TTEncryptUtils.a(bArr2, bArr2.length);
            return bArr2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void doPostNet(String url, RequestBody requestBody, long time, String XGon, String stub) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("X-SS-STUB", stub)
                .addHeader("X-SS-REQ-TICKET", System.currentTimeMillis() + "")
                .addHeader("X-Khronos", time + "")
                .addHeader("X-Gorgon", XGon)
                .addHeader("sdk-version", "1")
                .addHeader("Cookie", ck)
                .addHeader("X-Pods", "")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "okhttp/3.10.0.1")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("x-tt-token", xtttoken)  //登录成功头部返回的数据
//                .addHeader("Host","aweme.snssdk.com")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Log.i(TAG, response.body().string());
            }

        });
    }

    public void doGetNet(String url, long time, String XGon) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-SS-REQ-TICKET", System.currentTimeMillis() + "")
                .addHeader("X-Khronos", time + "")
                .addHeader("X-Gorgon", XGon)
                .addHeader("sdk-version", "1")
                .addHeader("Cookie", ck)
                .addHeader("X-Pods", "")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("User-Agent", "okhttp/3.10.0.1")
//                .addHeader("x-tt-token", xtttoken)
                .addHeader("Host","aweme.snssdk.com")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Log.e(TAG, response.body().string());
            }
        });
    }

}
