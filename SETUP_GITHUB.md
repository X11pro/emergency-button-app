# Configuración del Repositorio GitHub

## Pasos para crear el repositorio en GitHub:

### 1. Crear el repositorio en GitHub
1. Ve a [GitHub.com](https://github.com)
2. Haz clic en el botón "+" en la esquina superior derecha
3. Selecciona "New repository"
4. Configura el repositorio:
   - **Repository name**: `emergency-button-app`
   - **Description**: `Emergency Button App - Android emergency app with Kotlin`
   - **Visibility**: Public (o Private según prefieras)
   - **Initialize with**: NO marcar ninguna opción (no README, no .gitignore, no license)
5. Haz clic en "Create repository"

### 2. Conectar el repositorio local con GitHub
Una vez creado el repositorio en GitHub, ejecuta estos comandos:

```bash
# Configurar el remote origin
git remote add origin https://github.com/TU_USUARIO/emergency-button-app.git

# Cambiar a la rama main
git branch -M main

# Subir el código
git push -u origin main
```

### 3. Verificar la sincronización
```bash
# Verificar el estado
git status

# Verificar el remote
git remote -v
```

## Estructura del proyecto subida:

```
EmergencyButton/
├── app/
│   ├── src/main/
│   │   ├── java/com/emergency/button/
│   │   │   ├── MainActivity.kt
│   │   │   ├── SettingsActivity.kt
│   │   │   ├── adapter/ContactsAdapter.kt
│   │   │   ├── utils/
│   │   │   │   ├── EmergencyManager.kt
│   │   │   │   ├── ContactManager.kt
│   │   │   │   ├── EmergencyPreferences.kt
│   │   │   │   └── PermissionManager.kt
│   │   │   ├── widget/EmergencyWidget.kt
│   │   │   └── receiver/BootReceiver.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   ├── drawable/
│   │   │   └── xml/
│   │   └── AndroidManifest.xml
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
└── .gitignore
```

## Características del proyecto:

- ✅ **Aplicación Android completa en Kotlin**
- ✅ **Botón de emergencia gigante**
- ✅ **GPS automático con ubicación**
- ✅ **SMS masivo a contactos**
- ✅ **Llamada automática**
- ✅ **Alertas físicas (vibración, linterna, sonido)**
- ✅ **Widget de pantalla de inicio**
- ✅ **Configuración de contactos**
- ✅ **Modo de prueba**
- ✅ **Interfaz Material Design 3**
- ✅ **Documentación completa**

## Próximos pasos:

1. Crear el repositorio en GitHub siguiendo los pasos arriba
2. Ejecutar los comandos de sincronización
3. Verificar que todo el código esté subido correctamente
4. Compilar y probar la aplicación en Android Studio
