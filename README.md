# Emergency Button App

## Descripción

**Emergency Button** es una aplicación móvil de emergencia diseñada con **simplicidad extrema para salvar vidas**. En situaciones de crisis, cada segundo cuenta y la complejidad puede ser la diferencia entre recibir ayuda a tiempo o no.

## Características Principales

### 🚨 **Botón de Emergencia Gigante**
- Botón rojo grande que ocupa casi toda la pantalla
- Interfaz minimalista sin distracciones
- Activación con cuenta regresiva de 5 segundos para evitar activaciones accidentales

### 📍 **Ubicación GPS Automática**
- Obtiene ubicación exacta del usuario
- Envía coordenadas con enlace directo a Google Maps
- Funciona sin conexión a internet

### 📱 **SMS Masivo Automático**
- Envía mensaje de emergencia a hasta 3 contactos configurados
- Incluye ubicación exacta y mensaje personalizable
- Funciona con red celular básica

### 📞 **Llamada Automática**
- Realiza llamada automática al primer contacto de emergencia
- Integración nativa con el sistema de llamadas del dispositivo

### 🔦 **Alertas Físicas**
- **Vibración intensa**: Patrón de vibración de emergencia
- **Linterna parpadeante**: Activa la linterna del dispositivo por 10 segundos
- **Sonido de sirena**: Alerta sonora de emergencia

### 📱 **Widget de Pantalla de Inicio**
- Widget accesible directamente desde la pantalla principal
- No requiere abrir la aplicación
- Siempre visible y accesible

## Instalación

### Requisitos
- Android 7.0 (API 24) o superior
- Permisos de ubicación, SMS, teléfono y contactos
- Cámara con linterna (opcional)

### Pasos de Instalación

1. **Descargar el APK**
   ```bash
   # Clonar el repositorio
   git clone https://github.com/tu-usuario/emergency-button.git
   cd emergency-button
   ```

2. **Compilar la aplicación**
   ```bash
   # Usando Android Studio
   ./gradlew assembleDebug
   ```

3. **Instalar en dispositivo**
   ```bash
   # Instalar APK
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Configuración Inicial

1. **Abrir la aplicación** por primera vez
2. **Conceder permisos** cuando se soliciten:
   - Ubicación
   - SMS
   - Teléfono
   - Contactos
   - Cámara (para linterna)
3. **Configurar contactos de emergencia** (máximo 3)
4. **Personalizar mensaje de emergencia**
5. **Agregar widget** a la pantalla de inicio

## Uso

### Configuración
1. Toca "Settings" en la pantalla principal
2. Agrega hasta 3 contactos de emergencia
3. Personaliza tu mensaje de emergencia
4. Prueba el sistema con "Test Emergency"

### Activación de Emergencia
1. **Desde la app**: Toca el botón rojo gigante
2. **Desde el widget**: Toca el widget en la pantalla de inicio
3. **Cuenta regresiva**: 5 segundos para cancelar
4. **Ejecución automática**: SMS, llamada y alertas físicas

### Cancelación
- Toca "CANCEL" durante la cuenta regresiva
- La emergencia se cancela sin enviar alertas

## Funcionalidades Técnicas

### Arquitectura
- **Kotlin**: Lenguaje principal
- **MVVM**: Patrón de arquitectura
- **Material Design 3**: Interfaz moderna
- **Room Database**: Almacenamiento local
- **Coroutines**: Operaciones asíncronas

### Permisos Requeridos
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
```

### Características de Seguridad
- **Sin internet requerido**: Funciona completamente offline
- **Resistente a fallos**: Si un componente falla, los demás siguen funcionando
- **Modo de prueba**: Prueba completa sin enviar alertas reales
- **Configuración persistente**: Los contactos se guardan permanentemente

## Escenarios de Uso

### 🏥 **Emergencias Médicas**
- Personas con condiciones cardíacas
- Adultos mayores que viven solos
- Pacientes con diabetes o epilepsia

### 🚨 **Situaciones de Peligro**
- Mujeres caminando solas de noche
- Personas en barrios peligrosos
- Cualquiera que se sienta amenazado

### 🚗 **Accidentes**
- Conductores en accidentes automovilísticos
- Excursionistas perdidos
- Personas que sufren caídas o lesiones

### 💼 **Trabajadores Solitarios**
- Empleados en turnos nocturnos
- Técnicos que van a domicilios
- Personal de seguridad

## Desarrollo

### Estructura del Proyecto
```
app/src/main/java/com/emergency/button/
├── MainActivity.kt              # Actividad principal
├── SettingsActivity.kt          # Configuración
├── adapter/
│   └── ContactsAdapter.kt       # Adaptador de contactos
├── utils/
│   ├── EmergencyManager.kt      # Lógica de emergencia
│   ├── ContactManager.kt        # Gestión de contactos
│   ├── EmergencyPreferences.kt  # Preferencias
│   └── PermissionManager.kt     # Gestión de permisos
├── widget/
│   └── EmergencyWidget.kt       # Widget de pantalla de inicio
└── receiver/
    └── BootReceiver.kt          # Receptor de boot
```

### Dependencias Principales
```gradle
implementation 'com.google.android.gms:play-services-location:21.1.0'
implementation 'androidx.room:room-runtime:2.6.1'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
implementation 'com.google.android.material:material:1.11.0'
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Soporte

Para soporte técnico o reportar bugs, por favor abre un issue en GitHub.

## Descargo de Responsabilidad

Esta aplicación está diseñada para situaciones de emergencia reales. Los usuarios son responsables de:
- Configurar contactos de emergencia apropiados
- Probar el sistema regularmente
- Usar la aplicación solo en emergencias reales
- Mantener los permisos habilitados

**En caso de emergencia real, siempre llama al número de emergencia local (911 en EE.UU., 112 en Europa, etc.)**
