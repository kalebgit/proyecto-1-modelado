# proyecto-1-modelado

Comandos usados para instalar sqllite
```
# Opción 1: Usando wget
wget -P lib https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar

# Opción 2: Usando curl
curl -L https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar -o lib/sqlite-jdbc-3.36.0.3.jar
```

Pasos

Compilar el proyecto
```
# Primero, asegúrate de que tiene permisos de ejecución
chmod +x compilar.sh

# Luego, ejecútalo
./compilar.sh
```


Hay dos mains
uno para consola: Main
```
# En Linux/MacOS:
java -cp "bin:lib/sqlite-jdbc-3.36.0.3.jar" main.Main

# En Windows:
java -cp "bin;lib\sqlite-jdbc-3.36.0.3.jar" main.Main
```


otro para interfaz grafica con swing: MainGUI
```
# En Linux/MacOS:
java -cp "bin:lib/sqlite-jdbc-3.36.0.3.jar" gui.MainGUI

# En Windows:
java -cp "bin;lib\sqlite-jdbc-3.36.0.3.jar" gui.MainGUI
```
