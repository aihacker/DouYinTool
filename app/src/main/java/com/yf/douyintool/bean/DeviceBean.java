package com.yf.douyintool.bean;



import java.util.List;

public class DeviceBean {

    /**
     * magic_tag : ss_app_log
     * header : {"display_name":"抖音短视频","update_version_code":5502,"manifest_version_code":550,"aid":1128,"channel":"aweGW","appkey":"57bfa27c67e58e7d920028d3","package":"com.ss.android.ugc.aweme","app_version":"5.5.0","version_code":550,"sdk_version":"2.5.5.8","os":"Android","os_version":"5.1.1","os_api":22,"device_model":"SM-G925F","device_brand":"samsung","device_manufacturer":"samsung","cpu_abi":"armeabi-v7a","build_serial":"95294183","release_build":"2132ca7_20190320","density_dpi":192,"display_density":"mdpi","resolution":"1280x720","language":"zh","mc":"c8:de:47:39:c9:f6","timezone":8,"access":"wifi","not_request_sender":0,"carrier":"China Mobile GSM","mcc_mnc":"46000","rom":"eng.se.infra.20181117.120021","rom_version":"samsung-user 5.1.1 20171130.276299 release-keys","sig_hash":"aea615ab910015038f73c47e45d21466","device_id":"66868373655","openudid":"2021049774027898","udid":"864434952979918","clientudid":"8578d20c-8229-4b81-a031-4fc7747b00ad","serial_number":"95294183","sim_serial_number":[{"sim_serial_number":"89014103211118510720"}],"region":"CN","tz_name":"Asia/Shanghai","tz_offset":28800,"sim_region":"cn"}
     * _gen_time : 1553499369782
     */

    private String magic_tag;
    private HeaderBean header;
    private String _gen_time;

    public String getMagic_tag() {
        return magic_tag;
    }

    public void setMagic_tag(String magic_tag) {
        this.magic_tag = magic_tag;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public String get_gen_time() {
        return _gen_time;
    }

    public void set_gen_time(String _gen_time) {
        this._gen_time = _gen_time;
    }

    public static class HeaderBean {
        /**
         * display_name : 抖音短视频
         * update_version_code : 5502
         * manifest_version_code : 550
         * aid : 1128
         * channel : aweGW
         * appkey : 57bfa27c67e58e7d920028d3
         * package : com.ss.android.ugc.aweme
         * app_version : 5.5.0
         * version_code : 550
         * sdk_version : 2.5.5.8
         * os : Android
         * os_version : 5.1.1
         * os_api : 22
         * device_model : SM-G925F
         * device_brand : samsung
         * device_manufacturer : samsung
         * cpu_abi : armeabi-v7a
         * build_serial : 95294183
         * release_build : 2132ca7_20190320
         * density_dpi : 192
         * display_density : mdpi
         * resolution : 1280x720
         * language : zh
         * mc : c8:de:47:39:c9:f6
         * timezone : 8
         * access : wifi
         * not_request_sender : 0
         * carrier : China Mobile GSM
         * mcc_mnc : 46000
         * rom : eng.se.infra.20181117.120021
         * rom_version : samsung-user 5.1.1 20171130.276299 release-keys
         * sig_hash : aea615ab910015038f73c47e45d21466
         * device_id : 66868373655
         * openudid : 2021049774027898
         * udid : 864434952979918
         * clientudid : 8578d20c-8229-4b81-a031-4fc7747b00ad
         * serial_number : 95294183
         * sim_serial_number : [{"sim_serial_number":"89014103211118510720"}]
         * region : CN
         * tz_name : Asia/Shanghai
         * tz_offset : 28800
         * sim_region : cn
         */

        private String display_name;
        private int update_version_code;
        private int manifest_version_code;
        private int aid;
        private String channel;
        private String appkey;
//        @com.google.gson.annotations.SerializedName("package")
//        @SerializedName("package")
//        private String package;
        private String packageX;
        private String app_version;
        private int version_code;
        private String sdk_version;
        private String os;
        private String os_version;
        private int os_api;
        private String device_model;
        private String device_brand;
        private String device_manufacturer;
        private String cpu_abi;
        private String build_serial;
        private String release_build;
        private int density_dpi;
        private String display_density;
        private String resolution;
        private String language;
        private String mc;
        private int timezone;
        private String access;
        private int not_request_sender;
        private String carrier;
        private String mcc_mnc;
        private String rom;
        private String rom_version;
        private String sig_hash;
        private String device_id;
        private String openudid;
        private String udid;
        private String clientudid;
        private String serial_number;
        private String region;
        private String tz_name;
        private int tz_offset;
        private String sim_region;
        private List<SimSerialNumberBean> sim_serial_number;

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public int getUpdate_version_code() {
            return update_version_code;
        }

        public void setUpdate_version_code(int update_version_code) {
            this.update_version_code = update_version_code;
        }

        public int getManifest_version_code() {
            return manifest_version_code;
        }

        public void setManifest_version_code(int manifest_version_code) {
            this.manifest_version_code = manifest_version_code;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getSdk_version() {
            return sdk_version;
        }

        public void setSdk_version(String sdk_version) {
            this.sdk_version = sdk_version;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public int getOs_api() {
            return os_api;
        }

        public void setOs_api(int os_api) {
            this.os_api = os_api;
        }

        public String getDevice_model() {
            return device_model;
        }

        public void setDevice_model(String device_model) {
            this.device_model = device_model;
        }

        public String getDevice_brand() {
            return device_brand;
        }

        public void setDevice_brand(String device_brand) {
            this.device_brand = device_brand;
        }

        public String getDevice_manufacturer() {
            return device_manufacturer;
        }

        public void setDevice_manufacturer(String device_manufacturer) {
            this.device_manufacturer = device_manufacturer;
        }

        public String getCpu_abi() {
            return cpu_abi;
        }

        public void setCpu_abi(String cpu_abi) {
            this.cpu_abi = cpu_abi;
        }

        public String getBuild_serial() {
            return build_serial;
        }

        public void setBuild_serial(String build_serial) {
            this.build_serial = build_serial;
        }

        public String getRelease_build() {
            return release_build;
        }

        public void setRelease_build(String release_build) {
            this.release_build = release_build;
        }

        public int getDensity_dpi() {
            return density_dpi;
        }

        public void setDensity_dpi(int density_dpi) {
            this.density_dpi = density_dpi;
        }

        public String getDisplay_density() {
            return display_density;
        }

        public void setDisplay_density(String display_density) {
            this.display_density = display_density;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getMc() {
            return mc;
        }

        public void setMc(String mc) {
            this.mc = mc;
        }

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

        public int getNot_request_sender() {
            return not_request_sender;
        }

        public void setNot_request_sender(int not_request_sender) {
            this.not_request_sender = not_request_sender;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getMcc_mnc() {
            return mcc_mnc;
        }

        public void setMcc_mnc(String mcc_mnc) {
            this.mcc_mnc = mcc_mnc;
        }

        public String getRom() {
            return rom;
        }

        public void setRom(String rom) {
            this.rom = rom;
        }

        public String getRom_version() {
            return rom_version;
        }

        public void setRom_version(String rom_version) {
            this.rom_version = rom_version;
        }

        public String getSig_hash() {
            return sig_hash;
        }

        public void setSig_hash(String sig_hash) {
            this.sig_hash = sig_hash;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getOpenudid() {
            return openudid;
        }

        public void setOpenudid(String openudid) {
            this.openudid = openudid;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public String getClientudid() {
            return clientudid;
        }

        public void setClientudid(String clientudid) {
            this.clientudid = clientudid;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getTz_name() {
            return tz_name;
        }

        public void setTz_name(String tz_name) {
            this.tz_name = tz_name;
        }

        public int getTz_offset() {
            return tz_offset;
        }

        public void setTz_offset(int tz_offset) {
            this.tz_offset = tz_offset;
        }

        public String getSim_region() {
            return sim_region;
        }

        public void setSim_region(String sim_region) {
            this.sim_region = sim_region;
        }

        public List<SimSerialNumberBean> getSim_serial_number() {
            return sim_serial_number;
        }

        public void setSim_serial_number(List<SimSerialNumberBean> sim_serial_number) {
            this.sim_serial_number = sim_serial_number;
        }

        public static class SimSerialNumberBean {
            /**
             * sim_serial_number : 89014103211118510720
             */

            private String sim_serial_number;

            public String getSim_serial_number() {
                return sim_serial_number;
            }

            public void setSim_serial_number(String sim_serial_number) {
                this.sim_serial_number = sim_serial_number;
            }
        }
    }
}
