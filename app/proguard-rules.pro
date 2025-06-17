# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn android.os.Trace
# Disable warnings for android.os.Trace methods used internally (non-sdk interfaces)
-assumenosideeffects class android.os.Trace {
    public static boolean isTagEnabled(long);
    public static void asyncTraceBegin(long, java.lang.String, int);
    public static void asyncTraceEnd(long, java.lang.String, int);
    public static void traceBegin(long, java.lang.String);
    public static void traceEnd(long);
    public static void beginSection(java.lang.String);
    public static void endSection();
}