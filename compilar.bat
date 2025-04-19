@echo off
REM Script para compilar y ejecutar el proyecto MonosChinos MX en Windows

setlocal enabledelayedexpansion

REM Directorios
set SRC_DIR=src
set BIN_DIR=bin
set LIB_DIR=lib
set SQLITE_JAR=sqlite-jdbc-3.36.0.3.jar

REM Crear directorio de binarios si no existe
if not exist %BIN_DIR% (
  mkdir %BIN_DIR%
  echo Creado directorio %BIN_DIR%
)

REM Crear directorio de librerías si no existe
if not exist %LIB_DIR% (
  mkdir %LIB_DIR%
  echo Creado directorio %LIB_DIR%
)

REM Verificar si existe el archivo JAR de SQLite
if not exist %LIB_DIR%\%SQLITE_JAR% (
  echo Descargando SQLite JDBC...
  echo Por favor, descarga manualmente el archivo desde:
  echo https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/%SQLITE_JAR%
  echo y colócalo en el directorio %LIB_DIR%
  
  REM Preguntar si el usuario desea continuar
  set /p continuar=¿Has descargado el archivo? (S/N): 
  if /i "!continuar!" neq "S" exit /b
)

REM Compilar el proyecto
echo Compilando el proyecto...
javac -d %BIN_DIR% -cp "%LIB_DIR%\%SQLITE_JAR%" %SRC_DIR%\adaptadores\*.java %SRC_DIR%\componentes\*.java %SRC_DIR%\db\*.java %SRC_DIR%\factories\*.java %SRC_DIR%\gui\*.java %SRC_DIR%\observer\*.java %SRC_DIR%\pc\*.java %SRC_DIR%\software\*.java %SRC_DIR%\sucursales\*.java %SRC_DIR%\test\*.java %SRC_DIR%\util\*.java %SRC_DIR%\main\*.java

if %errorlevel% equ 0 (
  echo Compilación exitosa
  
  REM Mostrar opciones de ejecución
  echo.
  echo ¿Qué deseas ejecutar?
  echo 1. Aplicación principal (Consola)
  echo 2. Aplicación principal (Interfaz Gráfica)
  echo 3. Prueba de PC
  echo 4. Prueba de base de datos
  echo 5. Prueba del sistema completo
  echo 0. Salir
  echo.
  
  set /p opcion=Selecciona una opción: 
  
  if "!opcion!" == "1" (
    echo Ejecutando aplicación principal (Consola)...
    java -cp "%BIN_DIR%;%LIB_DIR%\%SQLITE_JAR%" main.Main
  ) else if "!opcion!" == "2" (
    echo Ejecutando aplicación principal (Interfaz Gráfica)...
    java -cp "%BIN_DIR%;%LIB_DIR%\%SQLITE_JAR%" gui.MainGUI
  ) else if "!opcion!" == "3" (
    echo Ejecutando prueba de PC...
    java -cp "%BIN_DIR%;%LIB_DIR%\%SQLITE_JAR%" test.TestPC
  ) else if "!opcion!" == "4" (
    echo Ejecutando prueba de base de datos...
    java -cp "%BIN_DIR%;%LIB_DIR%\%SQLITE_JAR%" test.TestDBManager
  ) else if "!opcion!" == "5" (
    echo Ejecutando prueba del sistema completo...
    java -cp "%BIN_DIR%;%LIB_DIR%\%SQLITE_JAR%" test.TestSistemaCompleto
  ) else if "!opcion!" == "0" (
    echo ¡Hasta pronto!
  ) else (
    echo Opción inválida
  )
) else (
  echo Error durante la compilación
)

echo.
pause
