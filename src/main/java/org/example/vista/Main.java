package org.example.vista;

import org.example.controlador.ControladorAdministrador;
import org.example.controlador.ControladorComprador;
import org.example.controlador.ControladorLibro;
import org.example.modelo.Administrador;
import org.example.modelo.Comprador;
import org.example.modelo.Libro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String tipoError = "E";
    private static final String tipoInfo = "I";
    private static final String tipoAviso = "W";
    private static final String rutaLog = "D:\\2 DAM\\Libreria\\src\\main\\java\\org\\example\\log";
    private static final String fechaInicioLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    // Listas para almacenar los datos de la base de datos
    private List<Administrador> administradores;
    private List<Comprador> compradores;
    private List<Libro> libros;

    // Controladores
    private ControladorAdministrador controladorAdministrador;
    private ControladorComprador controladorComprador;
    private ControladorLibro controladorLibro;

    // Utilizo un constructor de la clase para inicializar los controladores de las
    // clases.
    public Main() {
        this.controladorAdministrador = new ControladorAdministrador();
        this.controladorComprador = new ControladorComprador();
        this.controladorLibro = new ControladorLibro();
    }

    // Método escribir log
    private static void escribirLog(String tipo, String mensaje) {
        Date fechaActual = new Date();
        DateFormat fechaHoraActual = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaFormateada = fechaHoraActual.format(fechaActual);

        try {
            FileWriter archivoLog = new FileWriter(rutaLog + "\\" + fechaInicioLog + ".log", true);
            BufferedWriter logBuf = new BufferedWriter(archivoLog);
            logBuf.write(fechaFormateada + " - " + tipo + " - " + mensaje + "\n");
            logBuf.flush();
            logBuf.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo en el fichero de log: " + e.toString());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Error escribiendo en el fichero de log: " + e.toString());
            System.exit(1);
        }
    }

    // Método para cargar todos los datos de la base de datos
    private void cargarDatos() {
        try {
            // Cargar todos los administradores desde la base de datos
            administradores = controladorAdministrador.obtenerTodos();

            // Cargar todos los compradores desde la base de datos
            compradores = controladorComprador.obtenerTodos();

            // Cargar todos los libros desde la base de datos
            libros = controladorLibro.obtenerTodos();

            System.out.println("\n--- Datos cargados correctamente ---");
            System.out.println("Total administradores: " + administradores.size());
            System.out.println("Total compradores: " + compradores.size());
            System.out.println("Total libros: " + libros.size());

            escribirLog(tipoInfo, "Datos cargados correctamente");
        } catch (Exception e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
            escribirLog(tipoError, "Error al cargar los datos : " + e.getMessage());
        }

    }

    // Si es administrador retorna 1, si es comprador retorna 2. Si no se encuentra,
    // retornar 0.
    private int comprobarCredenciales(String nombre, String contraseña) {
        // Comprobar si es administrador
        for (Administrador admin : administradores) {
            if (admin.getNombre().equals(nombre) && admin.getContraseña().equals(contraseña)) {
                return 1;
            }
        }

        // Comprobar si es comprador
        for (Comprador comprador : compradores) {
            if (comprador.getNombre().equals(nombre) && comprador.getContraseña().equals(contraseña)) {
                return 2;
            }
        }

        // No se ha encontrado
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Main aplicacion = new Main();
        aplicacion.cargarDatos();
        Scanner sc = new Scanner(System.in);
        int resultado;

        // Login
        while (true) {
            // El usuario introduce sus credenciales
            System.out.println("Introduce tu nombre: ");
            String nombre = sc.nextLine();

            System.out.println("Introduce tu contraseña: ");
            String contraseña = sc.nextLine();

            // Se realiza la comprobación para ver si son válidas
            resultado = aplicacion.comprobarCredenciales(nombre, contraseña);

            if (resultado == 1) {
                System.out.println("Bienvenido administrador: " + nombre);
                escribirLog(tipoInfo, "Usuario logeado como administrador.");
                break;
            } else if (resultado == 2) {
                System.out.println("Bienvenido comprador: " + nombre);
                escribirLog(tipoInfo, "Usuario logeado como comprador.");
                break;
            } else {
                System.out.println("Credenciales incorrectas. Inténtalo de nuevo.");
                escribirLog(tipoError, "Error al realizar el login");
            }
        }

        // Menú Admin
        if (resultado == 1) {
            while (true) {

            }
        }

        // Menú Comprador
        if (resultado == 2) {
            while (true) {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1. Ver los libros de un vendedor"); // vendedor = admin
                System.out.println("2. Ver todos los vendedores y sus libros");
                System.out.println("3. Comprar un libro");
                System.out.println("0. Salir");

                int caso = sc.nextInt();

                switch (caso) {
                    case 1:
                        // Mostrar todos los vendedores
                        System.out.println("\n--- Vendedores disponibles ---");
                        for (Administrador admin : aplicacion.administradores) {
                            System.out.println("ID: " + admin.getId() + " - " + admin.getNombre());
                        }

                        // El usuario selecciona un vendedor por ID
                        System.out.println("\nIntroduce el ID del vendedor: ");
                        int idVendedor = sc.nextInt();

                        // Mostrar los libros de ese vendedor
                        List<Libro> librosVendedor = aplicacion.controladorLibro.obtenerPorAdministrador(idVendedor);
                        if (librosVendedor.isEmpty()) {
                            System.out.println("Este vendedor no tiene libros.");
                        } else {
                            System.out.println("\n--- Libros del vendedor ---");
                            for (Libro libro : librosVendedor) {
                                System.out.println("ID: " + libro.getId() + " - " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
                            }
                        }
                        escribirLog(tipoInfo, "Comprador consultó libros del vendedor con ID: " + idVendedor);
                        Thread.sleep(2000);
                        break;

                    case 2:
                        // Mostrar todos los vendedores y sus libros
                        System.out.println("\n--- Todos los vendedores y sus libros ---");
                        for (Administrador admin : aplicacion.administradores) {
                            System.out.println("\nVendedor ID: " + admin.getId() + " - " + admin.getNombre());
                            List<Libro> librosAdmin = aplicacion.controladorLibro.obtenerPorAdministrador(admin.getId());
                            if (librosAdmin.isEmpty()) {
                                System.out.println("(Sin libros)");
                            } else {
                                for (Libro libro : librosAdmin) {
                                    System.out.println("  - ID: " + libro.getId() + " | " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
                                }
                            }
                        }
                        escribirLog(tipoInfo, "Comprador consultó todos los vendedores y sus libros.");
                        Thread.sleep(2000);
                        break;

                    case 3:
                        // Mostrar todos los libros
                        System.out.println("\n--- Libros disponibles ---");
                        for (Libro libro : aplicacion.libros) {
                            System.out.println("ID: " + libro.getId() + " - " + libro.getTitulo() + " | Precio: "+ libro.getPrecio() + "€");
                        }

                        // El usuario selecciona un libro por ID
                        System.out.println("\nIntroduce el ID del libro que desea comprar: ");
                        int idLibro = sc.nextInt();

                        // Buscar el libro seleccionado
                        Libro libroComprado = aplicacion.controladorLibro.obtenerPorId(idLibro);
                        if (libroComprado != null) {
                            System.out.println("¡Has comprado \"" + libroComprado.getTitulo() + "\" por " + libroComprado.getPrecio() + "€! ¡Gracias por tu compra!");
                            escribirLog(tipoInfo, "Comprador compró el libro: " + libroComprado.getTitulo());
                        } else {
                            System.out.println("No se ha encontrado un libro con ese ID.");
                            escribirLog(tipoAviso, "Comprador intentó comprar un libro con ID inexistente: " + idLibro);
                        }
                        Thread.sleep(2000);
                        break;

                    case 0:
                        System.out.println("Fin de la ejecución. ¡Gracias por todo!");
                        escribirLog(tipoInfo, "El usuario salió.");
                        return;
                }

            }
        }
    }
}
