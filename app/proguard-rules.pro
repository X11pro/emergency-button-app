# ========================================
# Emergency Button App - ProGuard Rules
# ========================================

# Optimizaciones generales
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# Mantener atributos importantes
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses,EnclosingMethod
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations

# ========================================
# CLASES DE EMERGENCIA - NO TOCAR
# ========================================

# Mantener todas las clases principales de la app
-keep class com.emergency.sos.button.** { *; }
-keep class com.emergency.sos.button.utils.** { *; }
-keep class com.emergency.sos.button.widget.** { *; }
-keep class com.emergency.sos.button.receiver.** { *; }
-keep class com.emergency.sos.button.service.** { *; }
-keep class com.emergency.sos.button.adapter.** { *; }

# Mantener actividades y servicios
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.appwidget.AppWidgetProvider

# ========================================
# GOOGLE PLAY SERVICES
# ========================================

# Location Services
-keep class com.google.android.gms.location.** { *; }
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.tasks.** { *; }

# ========================================
# GSON - JSON SERIALIZATION
# ========================================

-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowshrinking,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# ========================================
# ROOM DATABASE
# ========================================

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-keep class * extends androidx.room.RoomDatabase$Callback

# ========================================
# ANDROIDX Y MATERIAL DESIGN
# ========================================

-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }

# ========================================
# PERMISOS Y MANIFEST
# ========================================

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# ========================================
# WARNINGS SUPRIMIDOS
# ========================================

-dontwarn sun.misc.**
-dontwarn com.google.android.gms.**
-dontwarn androidx.**
-dontwarn com.google.android.material.**

# ========================================
# OPTIMIZACIONES ADICIONALES
# ========================================

# Remover logs en release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Optimizar strings
-optimizations !code/simplification/arithmetic
