#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_smarttrash_smartrash_1tablet_SplashScreen_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
