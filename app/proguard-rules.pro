# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=android
-keep public class * extends java.lang.Exception  # For Crashlytics


# Protobuf relies on reflection of field names
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
  <fields>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# https://www.guardsquare.com/manual/configuration/examples#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    static final long serialVersionUID;
    !static !transient <fields>;
}

-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# keep core application
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

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

# For Kotlin Coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keep class androidx.datastore.*.** {*;}

# Not sure where this is coming from. most likely wordnet
-dontwarn org.slf4j.impl.StaticLoggerBinder

## Room
#-keep class * extends androidx.room.RoomDatabase
#-keep @androidx.room.Entity class *
#-keep @androidx.room.Dao class *

-verbose
