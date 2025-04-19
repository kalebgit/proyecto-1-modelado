package util;

import java.util.Scanner;
/**
 * Clase de utilidad para la interacción con la terminal.
 * Proporciona métodos para mostrar mensajes formateados, menús y capturar entradas del usuario.
 * 
 * @autores Emiliano Kaleb Jimenez Rivera, Bedoya Tellez Ariadna Valeria, Sanchez Soto Saul
 * @version 1.0
 */

public class TerminalUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void printNotification(String message) {
        // Calcula el ancho basado en la longitud del mensaje
        int width = message.length() + 4;
        String border = "┌" + "─".repeat(width) + "┐";
        String middle = "│ " + message + " │";
        System.out.println(border);
        System.out.println("│  NOTIFICACIÓN" + " ".repeat(width - 15) + "│");
        System.out.println("├" + "─".repeat(width) + "┤");
        System.out.println(middle);
        System.out.println("└" + "─".repeat(width) + "┘");
    }

    /**
     * Imprime un mensaje informativo estándar.
     * @param message El texto del mensaje a mostrar.
     */
    public static void info(String message) {
        printBox(message, "ℹ️ INFO");
    }

    /**
     * Imprime un mensaje de advertencia al usuario.
     * @param message El texto del mensaje de advertencia.
     */
    public static void warning(String message) {
        printBox(message, "⚠️ ADVERTENCIA");
    }

    /**
     * Imprime un mensaje de error importante.
     * @param message El texto del mensaje de error.
     */
    public static void error(String message) {
        printBox(message, "❌ ERROR");
    }

    /**
     * Imprime un mensaje de confirmación de éxito.
     * @param message El texto del mensaje de éxito.
     */
    public static void success(String message) {
        printBox(message, "✅ ÉXITO");
    }

    /**
     * Método interno que formatea e imprime un mensaje dentro de un recuadro ASCII,
     * con un título dado (nivel de mensaje).
     * @param message Mensaje a mostrar.
     * @param title   Título del recuadro que indica el nivel (INFO, ADVERTENCIA, etc.).
     */
    private static void printBox(String message, String title) {
        // Determina el ancho interior del recuadro basado en el texto más largo (título o mensaje)
        // agregando un margen de 2 espacios a cada lado
        int contentLength = Math.max(message.length(), title.length());
        int innerWidth = contentLength + 4;

        // Construye las líneas del recuadro
        String topBorder    = "┌" + "─".repeat(innerWidth) + "┐";
        String bottomBorder = "└" + "─".repeat(innerWidth) + "┘";
        String separator    = "├" + "─".repeat(innerWidth) + "┤";

        // Línea de título (con dos espacios de margen a la izquierda)
        String titleLine = "│  " + title;
        int titleUsed = 2 + title.length();  // caracteres de contenido usados (dos espacios + texto título)
        if (titleUsed < innerWidth) {
            titleLine += " ".repeat(innerWidth - titleUsed);  // rellena el resto con espacios
        }
        titleLine += "│";  // cierra borde derecho

        // Línea de mensaje (con dos espacios de margen a la izquierda)
        String messageLine = "│  " + message;
        int messageUsed = 2 + message.length();
        if (messageUsed < innerWidth) {
            messageLine += " ".repeat(innerWidth - messageUsed);
        }
        messageLine += "│";

        // Imprime el recuadro completo a la terminal
        System.out.println(topBorder);
        System.out.println(titleLine);
        System.out.println(separator);
        System.out.println(messageLine);
        System.out.println(bottomBorder);
    }

    /**
     * Muestra un menú interactivo y devuelve la opción elegida.
     * @param title El título del menú.
     * @param options Lista de opciones a mostrar.
     * @return La opción seleccionada por el usuario.
     */
    public static int showMenu(String title, String... options) {
        // Determinar el ancho necesario para el menú
        int maxLength = title.length();
        for (String option : options) {
            maxLength = Math.max(maxLength, option.length());
        }
        int menuWidth = maxLength + 8; // 8 para acomodar números, espacios y bordes

        String topBorder = "╔" + "═".repeat(menuWidth) + "╗";
        String separator = "╠" + "═".repeat(menuWidth) + "╣";
        String bottomBorder = "╚" + "═".repeat(menuWidth) + "╝";

        System.out.println(topBorder);
        System.out.printf("║ %-" + menuWidth + "s ║%n", title);
        System.out.println(separator);

        for (int i = 0; i < options.length; i++) {
            System.out.printf("║ %d. %-" + (menuWidth - 3) + "s ║%n", i + 1, options[i]);
        }
        System.out.printf("║ 0. %-" + (menuWidth - 3) + "s ║%n", "Salir");
        System.out.println(bottomBorder);

        return getUserChoice(options.length);
    }

    /**
     * Obtiene la elección del usuario, validando la entrada.
     * @param maxOption Número máximo de opciones disponibles.
     * @return La opción elegida por el usuario.
     */
    private static int getUserChoice(int maxOption) {
        int choice = -1;
        while (choice < 0 || choice > maxOption) {
            System.out.print("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
            } else {
                scanner.next(); // Descartar entrada no válida
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        return choice;
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static String inputString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    public static int inputInt(String prompt) {
        int number;
        while (true) {
            System.out.print(prompt + " ");
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                return number;
            } else {
                scanner.nextLine(); // Descartar entrada inválida
                error("Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }
}
