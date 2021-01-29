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
#-repackageclasses 'o0oOo0o'
#
#-dontwarn android.**
#
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#
#-keep public class * extends android.view.View {
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}
#
#
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
#-keepclasseswithmembernames class * implements android.os.Parcelable {
#	public static final android.os.Parcelable$Creator *;
#}
#
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## Keep (ie. don't remove) all public constructors of all public classes, but still obfuscate+optimize their content. This is necessary because optimization removes constructors which I use through reflection.
#-keepclassmembers class * {
#    <init>(...);
#}
#
#-keepclassmembers class * extends java.lang.Enum {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}