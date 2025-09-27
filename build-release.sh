#!/bin/bash

# ========================================
# Emergency Button App - Release Builder
# ========================================

echo "🚀 Emergency Button App - Build de Release"
echo "==========================================="

# Verificar que estamos en el directorio correcto
if [ ! -f "app/build.gradle" ]; then
    echo "❌ Error: Ejecuta este script desde el directorio raíz del proyecto."
    exit 1
fi

# Verificar que existe el keystore
if [ ! -f "app/release-keystore.jks" ]; then
    echo "⚠️  No se encontró el keystore. Ejecutando generador..."
    echo ""
    ./generate-keystore.sh
    if [ $? -ne 0 ]; then
        echo "❌ Error al generar keystore. Abortando build."
        exit 1
    fi
fi

echo ""
echo "🧹 Limpiando build anterior..."
./gradlew clean

echo ""
echo "🔨 Compilando versión de release..."
./gradlew assembleRelease

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build de release completado exitosamente!"
    echo ""
    echo "📱 Archivos generados:"
    echo "   APK: app/build/outputs/apk/release/app-release.apk"
    echo "   AAB: app/build/outputs/bundle/release/app-release.aab"
    echo ""
    echo "📊 Información del APK:"
    ls -lh app/build/outputs/apk/release/app-release.apk 2>/dev/null || echo "   APK no encontrado"
    echo ""
    echo "📊 Información del AAB:"
    ls -lh app/build/outputs/bundle/release/app-release.aab 2>/dev/null || echo "   AAB no encontrado"
    echo ""
    echo "🎉 ¡Listo para subir a Google Play Store!"
    echo ""
    echo "📋 Próximos pasos:"
    echo "   1. Prueba el APK en un dispositivo real"
    echo "   2. Sube el AAB a Google Play Console"
    echo "   3. Completa la información de la tienda"
    echo "   4. Publica tu app"
else
    echo "❌ Error en el build de release."
    echo "🔍 Revisa los logs arriba para más detalles."
    exit 1
fi
