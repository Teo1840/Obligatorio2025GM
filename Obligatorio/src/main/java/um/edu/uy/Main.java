package um.edu.uy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String opcion = entrada.nextLine();
        while (Integer.parseInt(opcion)!=3) {
            System.out.print("Bienvenido a UMovie");
            System.out.print("MENU PRINCIPAL:");
            System.out.print("Seleccione la opción que desee:");
            System.out.print("1. Carga de Datos");
            System.out.print("2. Ejecutar Consultas");
            System.out.print("3. Salir");
            if (Integer.parseInt(opcion)==1) {
                //subir datos
            }
            if (Integer.parseInt(opcion)==2) {
                System.out.print("Seleccione la opción que desee:");
                System.out.print("1. Top 5 de las mejores películas con más calificaciones por idioma.");
                System.out.print("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
                System.out.print("3. Top 5 de las colecciones que más ingresos generaron.");
                System.out.print("4. Top 10 de los directores que mejor calificación tienen.");
                System.out.print("5. Actor con más calificaciones recibidas en cada mes del año.");
                System.out.print("6. Usuarios con más calificaciones por género");
                System.out.print("7. Salir");
            }
        }
    }
}