# Script para sincronizar Emergency Button App con GitHub
# Ejecutar este script después de crear el repositorio en GitHub

Write-Host "=== Emergency Button App - Sincronizacion con GitHub ===" -ForegroundColor Green
Write-Host ""

# Verificar si estamos en el directorio correcto
if (-not (Test-Path "build.gradle")) {
    Write-Host "Error: No se encontro build.gradle. Asegurate de estar en el directorio EmergencyButton" -ForegroundColor Red
    exit 1
}

Write-Host "Verificando estructura del proyecto..." -ForegroundColor Yellow

# Verificar archivos importantes
$requiredFiles = @(
    "build.gradle",
    "app/src/main/AndroidManifest.xml",
    "app/src/main/java/com/emergency/button/MainActivity.kt",
    "README.md"
)

foreach ($file in $requiredFiles) {
    if (Test-Path $file) {
        Write-Host "  $file" -ForegroundColor Green
    } else {
        Write-Host "  $file (FALTANTE)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== Configuracion de Git ===" -ForegroundColor Cyan

# Verificar si Git esta inicializado
if (-not (Test-Path ".git")) {
    Write-Host "Inicializando repositorio Git..." -ForegroundColor Yellow
    git init
}

# Verificar el estado actual
Write-Host "Estado actual del repositorio:" -ForegroundColor Yellow
git status

Write-Host ""
Write-Host "=== Instrucciones para completar la sincronizacion ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "1. Crea el repositorio en GitHub:" -ForegroundColor White
Write-Host "   - Ve a https://github.com" -ForegroundColor Gray
Write-Host "   - Haz clic en 'New repository'" -ForegroundColor Gray
Write-Host "   - Nombre: emergency-button-app" -ForegroundColor Gray
Write-Host "   - Descripcion: Emergency Button App - Android emergency app with Kotlin" -ForegroundColor Gray
Write-Host "   - NO inicialices con README, .gitignore o license" -ForegroundColor Gray
Write-Host ""

Write-Host "2. Una vez creado, ejecuta estos comandos:" -ForegroundColor White
Write-Host ""

$username = Read-Host "Ingresa tu nombre de usuario de GitHub"
$repoUrl = "https://github.com/$username/emergency-button-app.git"

Write-Host ""
Write-Host "Comandos a ejecutar:" -ForegroundColor Yellow
Write-Host "git remote add origin $repoUrl" -ForegroundColor White
Write-Host "git branch -M main" -ForegroundColor White
Write-Host "git push -u origin main" -ForegroundColor White

Write-Host ""
Write-Host "Quieres que ejecute estos comandos automaticamente? (y/n)" -ForegroundColor Cyan
$response = Read-Host

if ($response -eq "y" -or $response -eq "Y") {
    Write-Host ""
    Write-Host "Ejecutando comandos..." -ForegroundColor Yellow
    
    # Agregar remote
    Write-Host "Agregando remote origin..." -ForegroundColor Yellow
    git remote add origin $repoUrl
    
    # Cambiar a rama main
    Write-Host "Cambiando a rama main..." -ForegroundColor Yellow
    git branch -M main
    
    # Push
    Write-Host "Subiendo codigo a GitHub..." -ForegroundColor Yellow
    git push -u origin main
    
    Write-Host ""
    Write-Host "Sincronizacion completada!" -ForegroundColor Green
    Write-Host "Repositorio disponible en: $repoUrl" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "Ejecuta manualmente los comandos mostrados arriba." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Verificacion final ===" -ForegroundColor Cyan
Write-Host "Para verificar que todo esta sincronizado:" -ForegroundColor White
Write-Host "git remote -v" -ForegroundColor Gray
Write-Host "git status" -ForegroundColor Gray

Write-Host ""
Write-Host "Emergency Button App esta lista para usar!" -ForegroundColor Green
