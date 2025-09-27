#!/bin/bash

# ========================================
# Emergency Button App - Keystore Generator
# ========================================

echo "🔐 Generando Keystore para Emergency Button App"
echo "================================================"

# Verificar si ya existe un keystore
if [ -f "app/release-keystore.jks" ]; then
    echo "⚠️  Ya existe un keystore. ¿Deseas sobrescribirlo? (y/N)"
    read -r response
    if [[ ! "$response" =~ ^[Yy]$ ]]; then
        echo "❌ Operación cancelada."
        exit 1
    fi
fi

echo ""
echo "📝 Por favor, proporciona la siguiente información:"
echo ""

# Solicitar información del keystore
read -p "🔑 Alias de la clave (ej: emergency-key): " KEY_ALIAS
read -p "🏪 Contraseña del keystore: " -s STORE_PASSWORD
echo ""
read -p "🔐 Contraseña de la clave: " -s KEY_PASSWORD
echo ""

# Validar que se proporcionaron los datos
if [ -z "$KEY_ALIAS" ] || [ -z "$STORE_PASSWORD" ] || [ -z "$KEY_PASSWORD" ]; then
    echo "❌ Error: Todos los campos son obligatorios."
    exit 1
fi

echo ""
echo "🏗️  Generando keystore..."

# Generar el keystore
keytool -genkey -v -keystore app/release-keystore.jks \
    -keyalg RSA -keysize 2048 -validity 10000 \
    -alias "$KEY_ALIAS" \
    -storepass "$STORE_PASSWORD" \
    -keypass "$KEY_PASSWORD" \
    -dname "CN=Emergency Button App, OU=Development, O=Emergency App, L=City, S=State, C=US"

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Keystore generado exitosamente!"
    echo "📁 Ubicación: app/release-keystore.jks"
    echo ""
    echo "🔧 Ahora actualiza el archivo build.gradle con:"
    echo "   storeFile file('release-keystore.jks')"
    echo "   storePassword '$STORE_PASSWORD'"
    echo "   keyAlias '$KEY_ALIAS'"
    echo "   keyPassword '$KEY_PASSWORD'"
    echo ""
    echo "⚠️  IMPORTANTE: Guarda esta información de forma segura!"
    echo "   Sin el keystore y las contraseñas no podrás actualizar tu app."
else
    echo "❌ Error al generar el keystore."
    exit 1
fi
