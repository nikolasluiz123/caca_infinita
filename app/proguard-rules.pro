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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Crashlytics
-keep public class * extends java.lang.Exception

# Lucene
-adaptresourcefilenames META-INF/services/*
-adaptresourcefilecontents META-INF/services/*
-keep class * implements org.apache.lucene.util.NamedSPILoader$NamedSPI {
    public <init>();
}
-keep class org.apache.lucene.codecs.** { *; }
-keep class org.apache.lucene.analysis.** { *; }
-keepattributes Signature, *Annotation*, EnclosingMethod, InnerClasses
-dontwarn org.apache.lucene.**

# PDFBox & BouncyCastle
-keep class com.tom_roush.pdfbox.** { *; }
-dontwarn com.tom_roush.pdfbox.**
-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**
-keepattributes Signature, InnerClasses, EnclosingMethod, AnnotationDefault, RuntimeVisibleAnnotations

# Ignorar avisos de classes opcionais do PDFBox que não estamos usando
-dontwarn com.gemalto.jp2.JP2Decoder
-dontwarn com.tom_roush.pdfbox.filter.JPXFilter