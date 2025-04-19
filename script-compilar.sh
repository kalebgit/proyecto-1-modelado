#!/bin/bash

# Script para compilar y ejecutar el proyecto MonosChinos MX

# Colores para mensajes
VERDE='\033[0;32m'
ROJO='\033[0;31m'
AZUL='\033[0;34m'
SIN_COLOR='\033[0m'

# Directorios
SRC_DIR="src"
BIN_DIR="bin"
LIB_DIR="lib"
SQLITE_JAR="sqlite-jdbc-3.36.0.3.jar"

# Crear directorio de binarios si no existe
if [ ! -d "$BIN_DIR" ]; then
  mkdir -p "$BIN_DIR"
  echo -e "${VERDE}Creado directorio $BIN_DIR${SIN_COLOR}"
fi

# Crear directorio de librerías si no existe
if [ ! -d "$LIB_DIR" ]; then
  mkdir -p "$LIB_DIR"
  echo -e "${VERDE}Creado directorio $LIB_DIR${SIN_COLOR}"
fi

# Descargar SQLite JDBC si no existe
if [ ! -f "$LIB_DIR/$SQLITE_JAR" ]; then
  echo -e "${AZUL}Descargando SQLite JDBC...${SIN_COLOR}"
  wget -P "$LIB_DIR" "https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/$SQLITE_JAR"
  if [ $? -ne 0 ]; then
    echo -e "${ROJO}Error al descargar SQLite JDBC${SIN_COLOR}"
    echo -e "${AZUL}Por favor, descarga manualmente desde https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/$SQLITE_JAR y colócalo en el directorio $LIB_DIR${SIN_COLOR}"
  else
    echo -e "${VERDE}SQLite JDBC descargado correctamente${SIN_COLOR}"
  fi
fi

# Compilar el proyecto
echo -e "${AZUL}Compilando el proyecto...${SIN_COLOR}"
javac -d "$BIN_DIR" -cp "$LIB_DIR/$SQLITE_JAR" $(find "$SRC_DIR" -name "*.java")

if [ $? -eq 0 ]; then
  echo -e "${VERDE}Compilación exitosa${SIN_COLOR}"
  
  # Mostrar opciones de ejecución
  echo -e "${AZUL}¿Qué deseas ejecutar?${SIN_COLOR}"
  echo "1. Aplicación principal (Consola)"
  echo "2. Aplicación principal (Interfaz Gráfica)"
  echo "3. Prueba de PC"
  echo "4. Prueba de base de datos"
  echo "5. Prueba del sistema completo"
  echo "0. Salir"
  
  read -p "Selecciona una opción: " opcion
  
  case $opcion in
    1)
      echo -e "${AZUL}Ejecutando aplicación principal (Consola)...${SIN_COLOR}"
      java -cp "$BIN_DIR:$LIB_DIR/$SQLITE_JAR" main.Main
      ;;
    2)
      echo -e "${AZUL}Ejecutando aplicación principal (Interfaz Gráfica)...${SIN_COLOR}"
      java -cp "$BIN_DIR:$LIB_DIR/$SQLITE_JAR" gui.MainGUI
      ;;
    3)
      echo -e "${AZUL}Ejecutando prueba de PC...${SIN_COLOR}"
      java -cp "$BIN_DIR:$LIB_DIR/$SQLITE_JAR" test.TestPC
      ;;
    4)
      echo -e "${AZUL}Ejecutando prueba de base de datos...${SIN_COLOR}"
      java -cp "$BIN_DIR:$LIB_DIR/$SQLITE_JAR" test.TestDBManager
      ;;
    5)
      echo -e "${AZUL}Ejecutando prueba del sistema completo...${SIN_COLOR}"
      java -cp "$BIN_DIR:$LIB_DIR/$SQLITE_JAR" test.TestSistemaCompleto
      ;;
    0)
      echo -e "${VERDE}¡Hasta pronto!${SIN_COLOR}"
      ;;
    *)
      echo -e "${ROJO}Opción inválida${SIN_COLOR}"
      ;;
  esac
else
  echo -e "${ROJO}Error durante la compilación${SIN_COLOR}"
fi
