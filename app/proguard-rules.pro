# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=android
-keep public class * extends java.lang.Exception  # For Crashlytics

# Protobuf relies on refelection of field names
-keep class * extends com.google.protobuf.GeneratedMessageLite

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
}

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNull(java.lang.Object);
    static void checkNotNull(java.lang.Object, java.lang.String);
}

-verbose