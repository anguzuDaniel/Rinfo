#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_qamar_myapplication_MainActivity_secureText(
        JNIEnv* env,
        jobject /* this */) {
    std::string baseURL = "https://test/";
    return env->NewStringUTF(baseURL.c_str());
}