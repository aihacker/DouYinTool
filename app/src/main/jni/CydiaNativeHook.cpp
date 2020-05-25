//
// Created by Administrator on 2019/4/2.
//
#include <jni.h>
#include "substrate.h"
#include <android/log.h>
#include <unistd.h>
#include <dirent.h>

#define TAG "CydiaHook"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

MSConfig(MSFilterLibrary, "/system/lib/arm/libdvm.so");

void * get_base_of_lib_from_maps(const char *SoName)
{
    void * imagehandle = dlopen(SoName, RTLD_LOCAL | RTLD_LAZY);
    if (SoName == NULL)
        return NULL;
    if (imagehandle == NULL){
        return NULL;
    }
    uintptr_t * irc = NULL;
    FILE *f = NULL;
    char line[200] = {0};
    char *state = NULL;
    char *tok = NULL;
    char * baseAddr = NULL;
    if ((f = fopen("/proc/self/maps", "r")) == NULL)
        return NULL;
    while (fgets(line, 199, f) != NULL)
    {
        tok = strtok_r(line, "-", &state);
        baseAddr = tok;
        tok = strtok_r(NULL, "\t ", &state);
        tok = strtok_r(NULL, "\t ", &state); // "r-xp" field
        tok = strtok_r(NULL, "\t ", &state); // "0000000" field
        tok = strtok_r(NULL, "\t ", &state); // "01:02" field
        tok = strtok_r(NULL, "\t ", &state); // "133224" field
        tok = strtok_r(NULL, "\t ", &state); // path field

        if (tok != NULL) {
            int i;
            for (i = (int)strlen(tok)-1; i >= 0; --i) {
                if (!(tok[i] == ' ' || tok[i] == '\r' || tok[i] == '\n' || tok[i] == '\t'))
                    break;
                tok[i] = 0;
            }
            {
                size_t toklen = strlen(tok);
                size_t solen = strlen(SoName);
                if (toklen > 0) {
                    if (toklen >= solen && strcmp(tok + (toklen - solen), SoName) == 0) {
                        fclose(f);
                        return (uintptr_t*)strtoll(baseAddr,NULL,16);
                    }
                }
            }
        }
    }
    fclose(f);
    return NULL;
}

jint (*old_RegiserNatives)(JNIEnv *env, jclass clazz, const JNINativeMethod* methods,jint nMethods) = NULL;
jint new_RegiserNatives(JNIEnv *env, jclass clazz, const JNINativeMethod* methods,jint nMethods){
    //传入你想Hookd的So文件的绝对地址
    void * Soaddress = get_base_of_lib_from_maps("/data/data-lib/com.yf.douyintool/libcms.so");
    if (Soaddress != (void *) env){
//去掉你不想要的，如果有多个就自己修改把        LOGD("exclude So address %p",Soaddress);
        return old_RegiserNatives(env,clazz,methods,nMethods);
    }
    LOGD("------------------------------------------------");
    LOGD("env:%p,class:%p,methods:%p,methods_num:%d",env,clazz,methods,nMethods);
    LOGD("------------------------------------------------");
    for (int i = 0;i < nMethods;i++){
        LOGD("name:%s,sign:%s,address:%p",methods[i].name,methods[i].signature,methods[i].fnPtr);
    }
    LOGD("------------------------------------------------");
    return old_RegiserNatives(env,clazz,methods,nMethods);
}

void * Hook_Symbol(const char * LibraryName, const char * SymbolName){
    void * handle = dlopen(LibraryName,RTLD_GLOBAL | RTLD_NOW);
    if (handle != NULL){
        LOGD("Hook so %s success",LibraryName);
        void * symbol = dlsym(handle,SymbolName);
        if (symbol != NULL){
            LOGD("Hook function %s success",SymbolName);
            return symbol;
        } else{
            LOGD("error find functicon:%s",SymbolName);
            LOGD("dl error:%s",dlerror());
            return NULL;
        }
    } else{
        LOGD("error find so : %s",LibraryName);
        return NULL;
    }
}

MSInitialize{
    LOGD("CydiaHook MSinitialize.");
    MSImageRef image = MSGetImageByName("/system/lib/arm/libdvm.so");
    if (image != NULL){
        LOGD("Hook so libdvm.so success");
        void * function_address = MSFindSymbol(image,"_Z12dexFileParsePKhji");
        if (function_address != NULL){
            LOGD("get _Z12dexFileParsePKhji address success");
            LOGD("begin hook funvtion sub_668c4");
            void * RegiserNatives = (void *)((uint32_t)function_address - 0x43f0); //function_address:lib_base+0xc4990 差值：43f0
                    LOGD("RegiserNatives address:%p",RegiserNatives);
            MSHookFunction(RegiserNatives,(void*)&new_RegiserNatives, (void **)&old_RegiserNatives);
        } else{
            LOGD("error find functicon _Z12dexFileParsePKhji");
        }
    } else{
        LOGD("error find so : libdvm.so");
    }
}
