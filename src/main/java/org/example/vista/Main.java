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

    public static void main(String[] args) {
        Main aplicacion = new Main();
        aplicacion.cargarDatos();
        Scanner sc = new Scanner(System.in);
        int resultado;
        Administrador adminLogeado = null;
        List<Libro> librosAdmin = null;

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
                adminLogeado = aplicacion.controladorAdministrador.obtenerPorNombreContraseña(nombre, contraseña);
                System.out.println("Bienvenido administrador: " + nombre);
                escribirLog(tipoInfo, "Usuario logeado como administrador.");
                librosAdmin = aplicacion.controladorLibro.obtenerPorAdministrador(adminLogeado.getId());
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

        /*
         * Menú administrador
         */
        if (resultado == 1) {
            while (true) {
                System.out.println("\n¿Sobre qué desea operar?");
                System.out.println("1. Libros");
                System.out.println("2. Compradores");
                System.out.println("0. Salir");

                int caso = sc.nextInt();

                switch (caso) {
                    //Operar con libros
                    case 1:
                        System.out.println("\n¿Qué operación desea realizar sobre libros?");
                        System.out.println("1. Añadir nuevo");
                        System.out.println("2. Modificar");
                        System.out.println("3. Eliminar");
                        System.out.println("0. Salir");

                        int operacionLibros = sc.nextInt();

                        switch (operacionLibros) {
                            case 1:
                                sc.nextLine();
                                System.out.println("Introduce el título:");
                                String titulo = sc.nextLine();
                                System.out.println("Introduce la descripción:");
                                String descripcion = sc.nextLine();
                                System.out.println("Introduce el precio:");
                                double precio = sc.nextDouble();

                                Libro nuevoLibro = new Libro(titulo, descripcion, precio, adminLogeado);
                                aplicacion.controladorLibro.crear(nuevoLibro);
                                System.out.println("Libro creado con éxito.");
                                aplicacion.libros = aplicacion.controladorLibro.obtenerTodos();
                                escribirLog(tipoInfo, "Libro creado: " + titulo);
                                break;

                            case 2:
                                System.out.println("\nSus libros: ");
                                for (Libro libro : librosAdmin) {
                                    System.out.println("ID: " + libro.getId() + " - " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
                                }
                                System.out.println("\nIntroduce el ID del libro a modificar:");
                                int idLibroModificado = sc.nextInt();
                                sc.nextLine();
                                Libro libroModificado = aplicacion.controladorLibro.obtenerPorId(idLibroModificado);
                                if (libroModificado != null) {
                                    System.out.println("Libro encontrado: " + libroModificado.getTitulo());
                                    System.out.println("\nNuevo título (Enter para mantener):");
                                    String tituloLibroModificado = sc.nextLine();
                                    if (!tituloLibroModificado.isEmpty())
                                        libroModificado.setTitulo(tituloLibroModificado);

                                    System.out.println("Nueva descripción (Enter para mantener):");
                                    String descripcionLibroModificado = sc.nextLine();
                                    if (!descripcionLibroModificado.isEmpty())
                                        libroModificado.setDescripcion(descripcionLibroModificado);

                                    System.out.println("Nuevo precio (0 para mantener):");
                                    double precioLibroModificado = sc.nextDouble();
                                    if (precioLibroModificado > 0) libroModificado.setPrecio(precioLibroModificado);

                                    aplicacion.controladorLibro.actualizar(libroModificado);
                                    System.out.println("Libro actualizado.");
                                    aplicacion.libros = aplicacion.controladorLibro.obtenerTodos();
                                    escribirLog(tipoInfo, "Libro modificado: " + libroModificado.getId());
                                } else {
                                    System.out.println("Libro no encontrado.");
                                }
                                break;

                            case 3:
                                System.out.println("\nSus libros: ");
                                for (Libro libro : librosAdmin) {
                                    System.out.println("ID: " + libro.getId() + " - " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
                                }
                                System.out.println("Introduce el ID del libro a eliminar:");
                                int idLibroEliminar = sc.nextInt();
                                Libro libroEliminar = aplicacion.controladorLibro.obtenerPorId(idLibroEliminar);
                                if (libroEliminar != null) {
                                    aplicacion.controladorLibro.eliminar(libroEliminar);
                                    System.out.println("Libro eliminado.");
                                    aplicacion.libros = aplicacion.controladorLibro.obtenerTodos();
                                    escribirLog(tipoInfo, "Libro eliminado: " + idLibroEliminar);
                                } else {
                                    System.out.println("Libro no encontrado.");
                                }
                                break;

                            case 0:
                                System.out.println("Volviendo al menú principal...");
                                break;
                        }
                        break;

                    //Operar con compradores
                    case 2:
                        System.out.println("\n¿Qué operación desea realizar sobre compradores?");
                        System.out.println("1. Añadir nuevo");
                        System.out.println("2. Modificar");
                        System.out.println("3. Eliminar");
                        System.out.println("0. Salir");

                        int operacionCompradores = sc.nextInt();

                        switch (operacionCompradores) {
                            case 1:
                                sc.nextLine();
                                System.out.println("\nIntroduce el nombre del comprador:");
                                String nombreAñadido = sc.nextLine();
                                System.out.println("Introduce la contraseña:");
                                String contraseñaAñadido = sc.nextLine();

                                Comprador nuevoComprador = new Comprador(nombreAñadido, contraseñaAñadido, false);
                                aplicacion.controladorComprador.crear(nuevoComprador);
                                System.out.println("Comprador creado con éxito.");
                                aplicacion.compradores = aplicacion.controladorComprador.obtenerTodos();
                                escribirLog(tipoInfo, "Comprador creado: " + nombreAñadido);
                                break;

                            case 2:
                                System.out.println("\nLista de compradores: ");
                                for (Comprador comprador : aplicacion.compradores) {
                                    System.out.println(comprador.toString());
                                }

                                System.out.println("\nIntroduce el ID del comprador a modificar:");
                                int idCompradorModificado = sc.nextInt();
                                sc.nextLine();
                                Comprador compradorModificado = aplicacion.controladorComprador.obtenerPorId(idCompradorModificado);
                                if (compradorModificado != null) {
                                    System.out.println("Comprador encontrado: " + compradorModificado.getNombre());
                                    System.out.println("Nuevo nombre (Enter para mantener):");
                                    String nombre = sc.nextLine();
                                    if (!nombre.isEmpty()) compradorModificado.setNombre(nombre);

                                    System.out.println("Nueva contraseña (Enter para mantener):");
                                    String contraseña = sc.nextLine();
                                    if (!contraseña.isEmpty()) compradorModificado.setContraseña(contraseña);

                                    aplicacion.controladorComprador.actualizar(compradorModificado);
                                    System.out.println("Comprador actualizado.");
                                    aplicacion.compradores = aplicacion.controladorComprador.obtenerTodos();
                                    escribirLog(tipoInfo, "Comprador modificado: " + idCompradorModificado);
                                } else {
                                    System.out.println("Comprador no encontrado.");
                                }
                                break;

                            case 3:
                                System.out.println("\nLista de compradores: ");
                                for (Comprador comprador : aplicacion.compradores) {
                                    System.out.println(comprador.toString());
                                }

                                System.out.println("Introduce el ID del comprador a eliminar:");
                                int idCompradorEliminado = sc.nextInt();
                                Comprador compradorEliminado = aplicacion.controladorComprador.obtenerPorId(idCompradorEliminado);
                                if (compradorEliminado != null) {
                                    aplicacion.controladorComprador.eliminar(compradorEliminado);
                                    System.out.println("Comprador eliminado.");
                                    aplicacion.compradores = aplicacion.controladorComprador.obtenerTodos();
                                    escribirLog(tipoInfo, "Comprador eliminado: " + idCompradorEliminado);
                                } else {
                                    System.out.println("Comprador no encontrado.");
                                }
                                break;

                            case 0:
                                System.out.println("Fin de la ejecución");
                                escribirLog(tipoInfo, "El administrador finalizó la ejecución");
                                return;

                        }
                        break;

                    case 0:
                        System.out.println("Fin de la ejecución");
                        escribirLog(tipoInfo, "El administrador finalizó la ejecución");
                        return;
                }
            }
        }

        /*
         * Menú de comprador
         */
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
                        break;

                    case 2:
                        // Mostrar todos los vendedores y sus libros
                        System.out.println("\n--- Todos los vendedores y sus libros ---");
                        for (Administrador admin : aplicacion.administradores) {
                            System.out.println("\nVendedor ID: " + admin.getId() + " - " + admin.getNombre());
                            if (librosAdmin.isEmpty()) {
                                System.out.println("(Sin libros)");
                            } else {
                                for (Libro libro : librosAdmin) {
                                    System.out.println("  - ID: " + libro.getId() + " | " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
                                }
                            }
                        }
                        escribirLog(tipoInfo, "Comprador consultó todos los vendedores y sus libros.");
                        break;

                    case 3:
                        // Mostrar todos los libros
                        System.out.println("\n--- Libros disponibles ---");
                        for (Libro libro : aplicacion.libros) {
                            System.out.println("ID: " + libro.getId() + " - " + libro.getTitulo() + " | Precio: " + libro.getPrecio() + "€");
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