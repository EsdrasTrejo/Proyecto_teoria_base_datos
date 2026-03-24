/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_teoria_base_datos1;
import java.util.Scanner;
import dao.UsuarioDAO;
import modelo.usuario;
import sesion.Sesion;
import dao.CategoriaDAO;
import dao.PresupuestoDAO;
import modelo.Presupuesto;
import modelo.Categoria;
import dao.SubcategoriaDAO;
import java.util.ArrayList;
import modelo.Subcategoria;
import dao.PresupuestoDetalleDAO;
import modelo.PresupuestoDetalle;
import dao.TransaccionDAO;
import modelo.Transaccion;
import dao.ObligacionFijaDAO;
import modelo.ObligacionFija;
import dao.ReporteDAO;
import java.util.List;
import modelo.ReporteResumenMensual;
import service.ReportePdfService;
import sesion.Sesion;
import modelo.ReporteDistribucionGastosCategoria;
import modelo.ReporteCumplimientoPresupuesto;
import modelo.ReporteTendenciaGastosCategoria;
import modelo.ReporteEstadoObligacionFija;


/**
 *
 * @author Usuario
 */
public class Proyecto_Teoria_Base_Datos1 {
   static Scanner sc = new Scanner(System.in);
   static PresupuestoDAO presupuestoDAO = new PresupuestoDAO();
    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static CategoriaDAO categoriaDAO = new CategoriaDAO();
    static SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
    static PresupuestoDetalleDAO presupuestoDetalleDAO = new PresupuestoDetalleDAO();
    static TransaccionDAO transaccionDAO = new TransaccionDAO();
    static ObligacionFijaDAO obligacionFijaDAO = new ObligacionFijaDAO();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     mostrarInicio();
    }
    
    public static void mostrarInicio() {
        int opcion;

        do {
            System.out.println("\n======================================");
            System.out.println(" SISTEMA DE PRESUPUESTO PERSONAL");
            System.out.println("======================================");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

   public static void iniciarSesion() {
    System.out.println("\n--- INICIO DE SESION ---");

    System.out.print("Ingrese su correo: ");
    String correo = sc.nextLine();

    System.out.print("Ingrese su clave: ");
    String clave = sc.nextLine();

    usuario usuario = usuarioDAO.iniciarSesion(correo, clave);

    if (usuario != null) {
        Sesion.setUsuarioActual(usuario);
        System.out.println("Bienvenido, " + usuario.getNombre() + " " + usuario.getApellido());
        menuPrincipal();
    } else {
        System.out.println("Correo o clave incorrectos.");
    }
}

    public static void registrarUsuario() {
    System.out.println("\n--- REGISTRO DE USUARIO ---");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Apellido: ");
    String apellido = sc.nextLine();

    System.out.print("Correo: ");
    String correo = sc.nextLine();

    if (usuarioDAO.existeCorreo(correo)) {
        System.out.println("Ya existe un usuario registrado con ese correo.");
        return;
    }

    System.out.print("Clave: ");
    String clave = sc.nextLine();

    System.out.print("Salario mensual: ");
    double salarioMensual = Double.parseDouble(sc.nextLine());

    boolean registrado = usuarioDAO.registrarUsuario(nombre, apellido, correo, clave, salarioMensual, "sistema");

    if (registrado) {
        usuario usuario = usuarioDAO.iniciarSesion(correo, clave);
        Sesion.setUsuarioActual(usuario);

        System.out.println("Usuario registrado correctamente.");
        System.out.println("Bienvenido, " + usuario.getNombre() + " " + usuario.getApellido());

        menuPrincipal();
    } else {
        System.out.println("No se pudo registrar el usuario.");
    }
}

 public static void menuPrincipal() {
    int opcion;

    do {
            if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n======================================");
        System.out.println(" MENU PRINCIPAL");
        System.out.println(" Usuario activo: " + Sesion.getUsuarioActual().getNombre());
        System.out.println("======================================");
        System.out.println("1. Presupuestos");
        System.out.println("2. Presupuestos detalle");
        System.out.println("3. Categorias");
        System.out.println("4. Subcategorias");
        System.out.println("5. Obligaciones fijas");
        System.out.println("6. Transacciones");
        System.out.println("7. Mi usuario");
        System.out.println("8. Reportes ");
        System.out.println("0. Cerrar sesion");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                menuPresupuesto();
                break;
            case 2:
                menuPresupuestoDetalle();
                break;
            case 3:
             menuCategoria();
                break;
            case 4:
                menuSubcategoria();
                break;
            case 5:
                 menuObligacionFija();
                break;
            case 6:
                menuTransaccion();
                break;
            case 7:
              menuUsuario();
              break;
               case 8:
                menuReportes();
                break;
            case 0:
                Sesion.cerrarSesion();
                System.out.println("Sesion cerrada correctamente.");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
 public static void menuReportes() {
    int opcion;

    do {
        System.out.println("\n======================================");
        System.out.println(" MENU REPORTES");
        System.out.println("======================================");
        System.out.println("1. Reporte 1 - Resumen mensual de ingresos vs gastos vs ahorros");
        System.out.println("2. Reporte 2 - Distribucion de gastos por categoria");
         System.out.println("3. Reporte 3 - Cumplimiento de presupuesto por categoria y subcategoria");
         System.out.println("4. Reporte 4 - Tendencia de gastos por categoria en el tiempo");
         System.out.println("5. Reporte 5 - Estado de obligaciones fijas y cumplimiento de pagos");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                generarReporteResumenMensual();
                break;
            case 2:
                generarReporteDistribucionGastosCategoria();
                break;
             case 3:
                generarReporteCumplimientoPresupuesto();
                break;
            case 4:
                generarReporteTendenciaGastosCategoria();
                break;
            case 5:
                generarReporteEstadoObligacionesFijas();
                break;
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
 public static void generarReporteEstadoObligacionesFijas() {
    try {
        int idUsuario = Sesion.getUsuarioActual().getIdUsuario();

        System.out.println("\n======================================");
        System.out.println(" REPORTE 5 - ESTADO DE OBLIGACIONES FIJAS");
        System.out.println("======================================");

        System.out.print("Ingrese el año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        ReporteDAO reporteDAO = new ReporteDAO();
        ReportePdfService reportePdfService = new ReportePdfService();

        List<ReporteEstadoObligacionFija> datos =
                reporteDAO.obtenerReporteEstadoObligacionesFijas(idUsuario, anio, mes);

        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos para generar el reporte.");
            return;
        }

        String ruta = reportePdfService.exportarReporteEstadoObligacionesFijas(
                datos, idUsuario, anio, mes
        );

        System.out.println("Reporte generado correctamente.");
        System.out.println("Ruta del archivo: " + ruta);

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error al generar el reporte: " + e.getMessage());
    }
}
 public static void generarReporteTendenciaGastosCategoria() {
    try {
        int idUsuario = Sesion.getUsuarioActual().getIdUsuario();

        System.out.println("\n======================================");
        System.out.println(" REPORTE 4 - TENDENCIA DE GASTOS POR CATEGORIA");
        System.out.println("======================================");

        System.out.print("Ingrese el año desde: ");
        int anioDesde = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes desde: ");
        int mesDesde = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el año hasta: ");
        int anioHasta = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes hasta: ");
        int mesHasta = Integer.parseInt(sc.nextLine());

        ReporteDAO reporteDAO = new ReporteDAO();
        ReportePdfService reportePdfService = new ReportePdfService();

        List<ReporteTendenciaGastosCategoria> datos =
                reporteDAO.obtenerReporteTendenciaGastosCategoria(
                        idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
                );

        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos para generar el reporte.");
            return;
        }

        String ruta = reportePdfService.exportarReporteTendenciaGastosCategoria(
                datos, idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
        );

        System.out.println("Reporte generado correctamente.");
        System.out.println("Ruta del archivo: " + ruta);

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error al generar el reporte: " + e.getMessage());
    }
}
 
 public static void generarReporteCumplimientoPresupuesto() {
    try {
        System.out.println("\n======================================");
        System.out.println(" REPORTE 3 - CUMPLIMIENTO DE PRESUPUESTO");
        System.out.println("======================================");

        System.out.print("Ingrese el id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        ReporteDAO reporteDAO = new ReporteDAO();
        ReportePdfService reportePdfService = new ReportePdfService();

        List<ReporteCumplimientoPresupuesto> datos =
                reporteDAO.obtenerReporteCumplimientoPresupuesto(idPresupuesto, anio, mes);

        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos para generar el reporte.");
            return;
        }

        String ruta = reportePdfService.exportarReporteCumplimientoPresupuesto(
                datos, idPresupuesto, anio, mes
        );

        System.out.println("Reporte generado correctamente.");
        System.out.println("Ruta del archivo: " + ruta);

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error al generar el reporte: " + e.getMessage());
    }
}
 
 public static void generarReporteDistribucionGastosCategoria() {
    try {
        int idUsuario = Sesion.getUsuarioActual().getIdUsuario();

        System.out.println("\n======================================");
        System.out.println(" REPORTE 2 - DISTRIBUCION DE GASTOS POR CATEGORIA");
        System.out.println("======================================");

        System.out.print("Ingrese el año: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        ReporteDAO reporteDAO = new ReporteDAO();
        ReportePdfService reportePdfService = new ReportePdfService();

        List<ReporteDistribucionGastosCategoria> datos =
                reporteDAO.obtenerReporteDistribucionGastosCategoria(idUsuario, anio, mes);

        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos para generar el reporte.");
            return;
        }

        String ruta = reportePdfService.exportarReporteDistribucionGastosCategoria(
                datos, idUsuario, anio, mes
        );

        System.out.println("Reporte generado correctamente.");
        System.out.println("Ruta del archivo: " + ruta);

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error al generar el reporte: " + e.getMessage());
    }
}
 
 public static void generarReporteResumenMensual() {
    try {
        int idUsuario = Sesion.getUsuarioActual().getIdUsuario();

        System.out.println("\n======================================");
        System.out.println(" REPORTE 1 - RESUMEN MENSUAL");
        System.out.println("======================================");

        System.out.print("Ingrese el anio desde: ");
        int anioDesde = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes desde: ");
        int mesDesde = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el anio hasta: ");
        int anioHasta = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese el mes hasta: ");
        int mesHasta = Integer.parseInt(sc.nextLine());

        ReporteDAO reporteDAO = new ReporteDAO();
        ReportePdfService reportePdfService = new ReportePdfService();

        List<ReporteResumenMensual> datos = reporteDAO.obtenerReporteResumenMensual(
                idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
        );

        if (datos.isEmpty()) {
            System.out.println("No se encontraron datos para generar el reporte.");
            return;
        }

        String ruta = reportePdfService.exportarReporteResumenMensual(
                datos, idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
        );

        System.out.println("Reporte generado correctamente.");
        System.out.println("Ruta del archivo: " + ruta);

    } catch (NumberFormatException e) {
        System.out.println("Error: debe ingresar valores numericos validos.");
    } catch (Exception e) {
        System.out.println("Error al generar el reporte: " + e.getMessage());
    }
}
 
 
 public static void menuUsuario() {
    int opcion;

    do {
            if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== MI USUARIO ==========");
        System.out.println("1. Modificar usuario");
        System.out.println("2. Eliminar usuario");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                modificarUsuario();
                break;
            case 2:
                eliminarUsuario();
                break;
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
 
 public static void modificarUsuario() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- MODIFICAR USUARIO ---");
    System.out.println("Deje vacio un campo si desea conservar el valor actual.");

    System.out.println("Nombre actual: " + usuarioActual.getNombre());
    System.out.print("Nuevo nombre: ");
    String nombre = sc.nextLine();
    if (nombre.isEmpty()) {
        nombre = usuarioActual.getNombre();
    }

    System.out.println("Apellido actual: " + usuarioActual.getApellido());
    System.out.print("Nuevo apellido: ");
    String apellido = sc.nextLine();
    if (apellido.isEmpty()) {
        apellido = usuarioActual.getApellido();
    }

    System.out.println("Correo actual: " + usuarioActual.getCorreo());
    System.out.print("Nuevo correo: ");
    String correo = sc.nextLine();
    if (correo.isEmpty()) {
        correo = usuarioActual.getCorreo();
    }

    System.out.print("Nueva clave: ");
    String clave = sc.nextLine();
    if (clave.isEmpty()) {
        clave = usuarioActual.getClave();
    }

    System.out.println("Salario actual: " + usuarioActual.getSalarioMensual());
    System.out.print("Nuevo salario mensual: ");
    String salarioTexto = sc.nextLine();

    double salarioMensual;
    if (salarioTexto.isEmpty()) {
        salarioMensual = usuarioActual.getSalarioMensual();
    } else {
        salarioMensual = Double.parseDouble(salarioTexto);
    }

   boolean actualizado = usuarioDAO.actualizarUsuario(
    usuarioActual.getIdUsuario(),
    nombre,
    apellido,
    correo,
    salarioMensual,
    usuarioActual.isEstado() ? 1 : 0,
    clave,
    "sistema"
);

    if (actualizado) {
        usuario usuarioActualizado = usuarioDAO.iniciarSesion(correo, clave);
        Sesion.setUsuarioActual(usuarioActualizado);
        System.out.println("Usuario actualizado correctamente.");
    } else {
        System.out.println("No se pudo actualizar el usuario.");
    }
}
 
 public static void eliminarUsuario() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ELIMINAR USUARIO ---");
    System.out.print("¿Seguro que desea eliminar su cuenta? (s/n): ");
    String confirmacion = sc.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
        boolean eliminado = usuarioDAO.eliminarUsuario(
                usuarioActual.getIdUsuario(),
                usuarioActual.getCorreo()
        );

        if (eliminado) {
            System.out.println("Usuario eliminado correctamente.");
            Sesion.cerrarSesion();
            return;
        } else {
            System.out.println("No se pudo eliminar el usuario.");
        }
    } else {
        System.out.println("Operacion cancelada.");
    }
}
 
 public static void menuCategoria() {
    int opcion;

    do {
        if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== CATEGORIAS ==========");
        System.out.println("1. Registrar categoria");
        System.out.println("2. Listar categorias");
        System.out.println("3. Consultar categoria");
        System.out.println("4. Actualizar categoria");
        System.out.println("5. Eliminar categoria");
        System.out.println("6. Obtener Resumen Categoria Mes");
        System.out.println("7. Obtener total Categoria Mes ");
        System.out.println("8. Obtener total ejecutado categoria mes ");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                registrarCategoria();
                break;
            case 2:
                listarCategorias();
                break;
            case 3:
                consultarCategoria();
                break;
            case 4:
                actualizarCategoria();
                break;
            case 5:
                eliminarCategoria();
                break;
            case 6:
                obtenerResumenCategoriaMes(); 
            case 7:
                obtenerTotalCategoriaMes();
            case 8:
                obtenerTotalEjecutadoCategoriaMes();
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
 
 public static void obtenerTotalEjecutadoCategoriaMes() {
    try {
        CategoriaDAO dao = new CategoriaDAO();

        System.out.println("\n=== TOTAL EJECUTADO CATEGORIA ===");

        System.out.print("Ingrese id de la categoria: ");
        int idCategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double total = dao.obtenerTotalEjecutadoCategoriaMes(idCategoria, anio, mes);

        System.out.println("Total ejecutado de la categoria: L. " + total);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 
 public static void obtenerTotalCategoriaMes() {
    try {
        CategoriaDAO dao = new CategoriaDAO();

        System.out.println("\n=== TOTAL CATEGORIA DEL MES ===");

        System.out.print("Ingrese id de la categoria: ");
        int idCategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double total = dao.obtenerTotalCategoriaMes(idCategoria, idPresupuesto, anio, mes);

        System.out.println("Total de la categoria en el mes: L. " + total);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 public static void registrarCategoria() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- REGISTRAR CATEGORIA ---");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Descripcion: ");
    String descripcion = sc.nextLine();

    System.out.print("Tipo (ingreso/gasto): ");
    String tipo = sc.nextLine();

    boolean registrada = categoriaDAO.registrarCategoria(
            nombre,
            descripcion,
            tipo,
            usuarioActual.getCorreo(),
            usuarioActual.getIdUsuario()
    );

    if (registrada) {
        System.out.println("Categoria registrada correctamente.");
    } else {
        System.out.println("No se pudo registrar la categoria.");
    }
}
 public static void listarCategorias() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- LISTAR CATEGORIAS ---");
    System.out.print("Ingrese el tipo a listar (ingreso/gasto): ");
    String tipo = sc.nextLine();

    ArrayList<Categoria> lista = categoriaDAO.listarCategorias(usuarioActual.getIdUsuario(), tipo);

    if (lista.isEmpty()) {
        System.out.println("No se encontraron categorias.");
    } else {
        System.out.println("\nCategorias encontradas:");
        for (Categoria categoria : lista) {
            System.out.println("ID: " + categoria.getIdCategoria() + " | Tipo: " + categoria.getTipo());
        }
    }
}
 
 public static void consultarCategoria() {
    System.out.println("\n--- CONSULTAR CATEGORIA ---");
    System.out.print("Ingrese el id de la categoria: ");
    int idCategoria = Integer.parseInt(sc.nextLine());

    Categoria categoria = categoriaDAO.consultarCategoria(idCategoria);

    if (categoria != null) {
        System.out.println("\nCategoria encontrada:");
        System.out.println("ID: " + categoria.getIdCategoria());
        System.out.println("Nombre: " + categoria.getNombre());
        System.out.println("Descripcion: " + categoria.getDescripcion());
        System.out.println("Tipo: " + categoria.getTipo());
        System.out.println("Id usuario propietario: " + categoria.getIdUsuarioPropietario());
        System.out.println("Estado: " + categoria.isEstado());
    } else {
        System.out.println("No se encontro la categoria.");
    }
}
 
 public static void actualizarCategoria() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR CATEGORIA ---");
    System.out.print("Ingrese el id de la categoria a actualizar: ");
    int idCategoria = Integer.parseInt(sc.nextLine());

    Categoria categoriaActual = categoriaDAO.consultarCategoria(idCategoria);

    if (categoriaActual == null) {
        System.out.println("La categoria no existe.");
        return;
    }

    if (categoriaActual.getIdUsuarioPropietario() != usuarioActual.getIdUsuario()) {
        System.out.println("No puede modificar una categoria que no le pertenece.");
        return;
    }

    System.out.println("Nombre actual: " + categoriaActual.getNombre());
    System.out.print("Nuevo nombre: ");
    String nombre = sc.nextLine();
    if (nombre.isEmpty()) {
        nombre = categoriaActual.getNombre();
    }

    System.out.println("Descripcion actual: " + categoriaActual.getDescripcion());
    System.out.print("Nueva descripcion: ");
    String descripcion = sc.nextLine();
    if (descripcion.isEmpty()) {
        descripcion = categoriaActual.getDescripcion();
    }

    boolean actualizada = categoriaDAO.actualizarCategoria(
            idCategoria,
            nombre,
            descripcion,
            usuarioActual.getCorreo()
    );

    if (actualizada) {
        System.out.println("Categoria actualizada correctamente.");
    } else {
        System.out.println("No se pudo actualizar la categoria.");
    }
}
 public static void eliminarCategoria() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ELIMINAR CATEGORIA ---");
    System.out.print("Ingrese el id de la categoria a eliminar: ");
    int idCategoria = Integer.parseInt(sc.nextLine());

    Categoria categoriaActual = categoriaDAO.consultarCategoria(idCategoria);

    if (categoriaActual == null) {
        System.out.println("La categoria no existe.");
        return;
    }

    if (categoriaActual.getIdUsuarioPropietario() != usuarioActual.getIdUsuario()) {
        System.out.println("No puede eliminar una categoria que no le pertenece.");
        return;
    }

    System.out.print("¿Seguro que desea eliminar esta categoria? (s/n): ");
    String confirmacion = sc.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
        boolean eliminada = categoriaDAO.eliminarCategoria(idCategoria);

        if (eliminada) {
            System.out.println("Categoria eliminada correctamente.");
        } else {
            System.out.println("No se pudo eliminar la categoria.");
        }
    } else {
        System.out.println("Operacion cancelada.");
    }
}
 
 public static void menuSubcategoria() {
    int opcion;

    do {
        System.out.println("\n====== SUBCATEGORIAS ======");
        System.out.println("1. Registrar");
        System.out.println("2. Listar");
        System.out.println("3. Consultar");
        System.out.println("4. Actualizar");
        System.out.println("5. Eliminar");
        System.out.println("6. Obtener Balance Subcategoria");
        System.out.println("7. Obtener categoria por subcategoria");
        System.out.println("8. calcular proyeccion gasto mensual");
        System.out.println("9. Obtener promedio gasto subcategoria");
        System.out.println("0. Volver");

        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1: registrarSubcategoria(); break;
            case 2: listarSubcategorias(); break;
            case 3: consultarSubcategoria(); break;
            case 4: actualizarSubcategoria(); break;
            case 5: eliminarSubcategoria(); break;
            case 6: obtenerBalanceSubcategoria(); break;
            case 7: obtenerCategoriaPorSubcategoria();break;
            case 8: calcularProyeccionGastoMensual(); break;
            case 9: obtenerPromedioGastoSubcategoria(); break;
        }

    } while (opcion != 0);
}
 
 public static void obtenerPromedioGastoSubcategoria() {
    try {
        SubcategoriaDAO dao = new SubcategoriaDAO();

        System.out.println("\n=== PROMEDIO GASTO SUBCATEGORIA ===");

        System.out.print("Ingrese id del usuario: ");
        int idUsuario = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese cantidad de meses: ");
        int cantidadMeses = Integer.parseInt(sc.nextLine());

        double promedio = dao.obtenerPromedioGastoSubcategoria(
                idUsuario,
                idSubcategoria,
                cantidadMeses
        );

        System.out.println("Promedio de gasto: L. " + promedio);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 public static void calcularProyeccionGastoMensual() {
    try {
        SubcategoriaDAO dao = new SubcategoriaDAO();

        System.out.println("\n=== PROYECCION DE GASTO MENSUAL ===");

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double proyeccion = dao.calcularProyeccionGastoMensual(
                idSubcategoria,
                anio,
                mes
        );

        System.out.println("Proyeccion de gasto mensual: L. " + proyeccion);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 public static void obtenerBalanceSubcategoria() {
    try {
        SubcategoriaDAO dao = new SubcategoriaDAO();

        System.out.println("\n=== BALANCE SUBCATEGORIA ===");

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double balance = dao.obtenerBalanceSubcategoria(
                idPresupuesto,
                idSubcategoria,
                anio,
                mes
        );

        System.out.println("Balance de la subcategoria: L. " + balance);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 
 public static void obtenerCategoriaPorSubcategoria() {
    try {
        SubcategoriaDAO dao = new SubcategoriaDAO();

        System.out.println("\n=== OBTENER CATEGORIA POR SUBCATEGORIA ===");

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        int idCategoria = dao.obtenerCategoriaPorSubcategoria(idSubcategoria);

        System.out.println("La categoria padre es: " + idCategoria);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
 
 public static void registrarSubcategoria() {
    usuario u = Sesion.getUsuarioActual();

    System.out.print("ID Categoria: ");
    int idCategoria = Integer.parseInt(sc.nextLine());

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Descripcion: ");
    String descripcion = sc.nextLine();

    System.out.print("Es defecto (1=si, 0=no): ");
    int esDefecto = Integer.parseInt(sc.nextLine());

    boolean ok = subcategoriaDAO.registrarSubcategoria(
            idCategoria,
            nombre,
            descripcion,
            esDefecto,
            u.getCorreo()
    );

    System.out.println(ok ? "Subcategoria creada" : "Error");
}
 
 public static void listarSubcategorias() {
    System.out.println("\n--- LISTAR SUBCATEGORIAS ---");

    System.out.print("Ingrese el id de la categoria: ");
    int idCategoria = Integer.parseInt(sc.nextLine());

    ArrayList<Subcategoria> lista = subcategoriaDAO.listarSubcategorias(idCategoria);

    if (lista.isEmpty()) {
        System.out.println("No se encontraron subcategorias.");
    } else {
        System.out.println("\nSubcategorias encontradas:");
        for (Subcategoria s : lista) {
            System.out.println("ID: " + s.getIdSubcategoria() + " | Nombre: " + s.getNombre());
        }
    }
}

public static void consultarSubcategoria() {
    System.out.println("\n--- CONSULTAR SUBCATEGORIA ---");

    System.out.print("Ingrese el id de la subcategoria: ");
    int idSubcategoria = Integer.parseInt(sc.nextLine());

    Subcategoria s = subcategoriaDAO.consultarSubcategoria(idSubcategoria);

    if (s != null) {
        System.out.println("\nSubcategoria encontrada:");
        System.out.println("ID Subcategoria: " + s.getIdSubcategoria());
        System.out.println("ID Categoria: " + s.getIdCategoria());
        System.out.println("Nombre: " + s.getNombre());
        System.out.println("Descripcion: " + s.getDescripcion());
        System.out.println("Es defecto: " + s.isEsDefecto());
        System.out.println("Estado: " + s.isEstado());
    } else {
        System.out.println("No se encontro la subcategoria.");
    }
}

public static void actualizarSubcategoria() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR SUBCATEGORIA ---");

    System.out.print("Ingrese el id de la subcategoria a actualizar: ");
    int idSubcategoria = Integer.parseInt(sc.nextLine());

    Subcategoria subcategoriaActual = subcategoriaDAO.consultarSubcategoria(idSubcategoria);

    if (subcategoriaActual == null) {
        System.out.println("La subcategoria no existe.");
        return;
    }

    System.out.println("Nombre actual: " + subcategoriaActual.getNombre());
    System.out.print("Nuevo nombre: ");
    String nombre = sc.nextLine();
    if (nombre.isEmpty()) {
        nombre = subcategoriaActual.getNombre();
    }

    System.out.println("Descripcion actual: " + subcategoriaActual.getDescripcion());
    System.out.print("Nueva descripcion: ");
    String descripcion = sc.nextLine();
    if (descripcion.isEmpty()) {
        descripcion = subcategoriaActual.getDescripcion();
    }

    boolean actualizada = subcategoriaDAO.actualizarSubcategoria(
            idSubcategoria,
            nombre,
            descripcion,
            usuarioActual.getCorreo()
    );

    if (actualizada) {
        System.out.println("Subcategoria actualizada correctamente.");
    } else {
        System.out.println("No se pudo actualizar la subcategoria.");
    }
}

public static void eliminarSubcategoria() {
    System.out.println("\n--- ELIMINAR SUBCATEGORIA ---");

    System.out.print("Ingrese el id de la subcategoria a eliminar: ");
    int idSubcategoria = Integer.parseInt(sc.nextLine());

    Subcategoria subcategoriaActual = subcategoriaDAO.consultarSubcategoria(idSubcategoria);

    if (subcategoriaActual == null) {
        System.out.println("La subcategoria no existe.");
        return;
    }

    System.out.println("Subcategoria encontrada: " + subcategoriaActual.getNombre());
    System.out.print("¿Seguro que desea eliminar esta subcategoria? (s/n): ");
    String confirmacion = sc.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
        boolean eliminada = subcategoriaDAO.eliminarSubcategoria(idSubcategoria);

        if (eliminada) {
            System.out.println("Subcategoria eliminada correctamente.");
        } else {
            System.out.println("No se pudo eliminar la subcategoria.");
        }
    } else {
        System.out.println("Operacion cancelada.");
    }
}

public static void menuPresupuesto() {
    int opcion;

    do {
        if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== PRESUPUESTOS ==========");
        System.out.println("1. Registrar presupuesto");
        System.out.println("2. Listar presupuestos");
        System.out.println("3. Consultar presupuesto");
        System.out.println("4. Actualizar presupuesto");
        System.out.println("5. Eliminar presupuesto");
        System.out.println("6. Calcular monto ejecutado del mes");
        System.out.println("7. Calcular porcentaje de ejecucion");
        System.out.println("8. Calcular balance mensual");
        System.out.println("9. Cerrar Presupuesto");
        System.out.println("10. Crear Presupuesto completo");
        System.out.println("11. Calcular Monto Ejecutado");
        System.out.println("12. validar vigencia presupuesto");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                registrarPresupuesto();
                break;
            case 2:
                listarPresupuestos();
                break;
            case 3:
                consultarPresupuesto();
                break;
            case 4:
                actualizarPresupuesto();
                break;
            case 5:
                eliminarPresupuesto();
                break;
             case 6:
                calcularMontoEjecutadoMes();
                break;
            case 7:
                calcularMontoEjecutadoMes();
                break;
            case 8:
                calcularBalanceMensual();
                break;
            case 9:
                cerrarPresupuesto();
            case 10:
                crearPresupuestoCompleto();
            case 11:
                calcularMontoEjecutadoFN();
            case 12:
                validarVigenciaPresupuesto();
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
public static void validarVigenciaPresupuesto() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== VALIDAR VIGENCIA PRESUPUESTO ===");

        System.out.print("Ingrese fecha (yyyy-mm-dd): ");
        java.sql.Date fecha = java.sql.Date.valueOf(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        boolean vigente = dao.validarVigenciaPresupuesto(fecha, idPresupuesto);

        if (vigente) {
            System.out.println("El presupuesto esta vigente en esa fecha.");
        } else {
            System.out.println("El presupuesto NO esta vigente en esa fecha.");
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void calcularMontoEjecutadoFN() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== FN MONTO EJECUTADO ===");

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double monto = dao.calcularMontoEjecutado(idSubcategoria, anio, mes);

        System.out.println("Monto ejecutado (fn): L. " + monto);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void procesarObligacionesMes() {
    try {
        ObligacionFijaDAO dao = new ObligacionFijaDAO();

        System.out.println("\n=== PROCESAR OBLIGACIONES DEL MES ===");

        System.out.print("Ingrese id del usuario: ");
        int idUsuario = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        boolean resultado = dao.procesarObligacionesMes(idUsuario, anio, mes, idPresupuesto);

        if (resultado) {
            System.out.println("Obligaciones procesadas correctamente.");
        } else {
            System.out.println("No se pudieron procesar las obligaciones.");
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void crearPresupuestoCompleto() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== CREAR PRESUPUESTO COMPLETO ===");

        System.out.print("Ingrese id del usuario: ");
        int idUsuario = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese nombre del presupuesto: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("Ingrese year inicio: ");
        int yearInicio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes inicio: ");
        int mesInicio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese year fin: ");
        int yearFin = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes fin: ");
        int mesFin = Integer.parseInt(sc.nextLine());

        System.out.println("Ingrese lista de subcategorias en formato JSON:");
        System.out.println("ejemplo:");
        String json = "["
        + "{\"id_subcategoria\":1,\"monto_mensual\":1500.00},"
        + "{\"id_subcategoria\":2,\"monto_mensual\":2200.00}"
        + "]";
        System.out.println(json);
        String listaSubcategoriasJson = sc.nextLine();

        System.out.print("Ingrese creado por: ");
        String creadoPor = sc.nextLine();

        boolean resultado = dao.crearPresupuestoCompleto(
                idUsuario,
                nombre,
                descripcion,
                yearInicio,
                mesInicio,
                yearFin,
                mesFin,
                listaSubcategoriasJson,
                creadoPor
        );

        if (resultado) {
            System.out.println("Presupuesto completo creado correctamente.");
        } else {
            System.out.println("No se pudo crear el presupuesto completo.");
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void cerrarPresupuesto() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== CERRAR PRESUPUESTO ===");

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        String modificadoPor = Sesion.getUsuarioActual().getCorreo();

        boolean resultado = dao.cerrarPresupuesto(idPresupuesto, modificadoPor);

        if (resultado) {
            System.out.println("Presupuesto cerrado correctamente.");
        } else {
            System.out.println("No se pudo cerrar el presupuesto.");
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void registrarPresupuesto() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- REGISTRAR PRESUPUESTO ---");

    System.out.print("Nombre: ");
    String nombre = sc.nextLine();

    System.out.print("Anio periodo inicio: ");
    int anioInicio = Integer.parseInt(sc.nextLine());

    System.out.print("Mes periodo inicio: ");
    int mesInicio = Integer.parseInt(sc.nextLine());

    System.out.print("Anio periodo fin: ");
    int anioFin = Integer.parseInt(sc.nextLine());

    System.out.print("Mes periodo fin: ");
    int mesFin = Integer.parseInt(sc.nextLine());

    boolean registrado = presupuestoDAO.registrarPresupuesto(
            usuarioActual.getIdUsuario(),
            nombre,
            anioInicio,
            mesInicio,
            anioFin,
            mesFin,
            usuarioActual.getCorreo()
    );

    if (registrado) {
        System.out.println("Presupuesto registrado correctamente.");
    } else {
        System.out.println("No se pudo registrar el presupuesto.");
    }
}

public static void calcularBalanceMensual() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== CALCULAR BALANCE MENSUAL ===");

        System.out.print("Ingrese id del usuario: ");
        int idUsuario = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double[] balance = dao.calcularBalanceMensual(idUsuario, idPresupuesto, anio, mes);

        System.out.println("Total ingresos: L. " + balance[0]);
        System.out.println("Total gastos: L. " + balance[1]);
        System.out.println("Total ahorros: L. " + balance[2]);
        System.out.println("Balance final: L. " + balance[3]);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
public static void listarPresupuestos() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- LISTAR PRESUPUESTOS ---");

    ArrayList<Presupuesto> lista = presupuestoDAO.listarPresupuestos(usuarioActual.getIdUsuario());

    if (lista.isEmpty()) {
        System.out.println("No se encontraron presupuestos.");
    } else {
        System.out.println("\nPresupuestos encontrados:");
        for (Presupuesto p : lista) {
            System.out.println("ID: " + p.getIdPresupuesto()
                    + " | Nombre: " + p.getNombre());
        }
    }
}

public static void consultarPresupuesto() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- CONSULTAR PRESUPUESTO ---");
    System.out.print("Ingrese el id del presupuesto: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    Presupuesto p = presupuestoDAO.consultarPresupuesto(idPresupuesto);

    if (p == null) {
        System.out.println("No se encontro el presupuesto.");
        return;
    }

    if (p.getIdUsuario() != usuarioActual.getIdUsuario()) {
        System.out.println("No puede consultar un presupuesto que no le pertenece.");
        return;
    }

    System.out.println("\nPresupuesto encontrado:");
    System.out.println("ID: " + p.getIdPresupuesto());
    System.out.println("ID Usuario: " + p.getIdUsuario());
    System.out.println("Nombre: " + p.getNombre());
    System.out.println("Inicio: " + p.getMesPeriodoInicio() + "/" + p.getAnioPeriodoInicio());
    System.out.println("Fin: " + p.getMesPeriodoFin() + "/" + p.getAnioPeriodoFin());
    System.out.println("Estado: " + p.isEstado());
}

public static void actualizarPresupuesto() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR PRESUPUESTO ---");
    System.out.print("Ingrese el id del presupuesto a actualizar: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    Presupuesto actual = presupuestoDAO.consultarPresupuesto(idPresupuesto);

    if (actual == null) {
        System.out.println("El presupuesto no existe.");
        return;
    }

    if (actual.getIdUsuario() != usuarioActual.getIdUsuario()) {
        System.out.println("No puede modificar un presupuesto que no le pertenece.");
        return;
    }

    System.out.println("Nombre actual: " + actual.getNombre());
    System.out.print("Nuevo nombre: ");
    String nombre = sc.nextLine();
    if (nombre.isEmpty()) {
        nombre = actual.getNombre();
    }

    System.out.println("Anio inicio actual: " + actual.getAnioPeriodoInicio());
    System.out.print("Nuevo anio inicio: ");
    String inputAnioInicio = sc.nextLine();
    int anioInicio = inputAnioInicio.isEmpty() ? actual.getAnioPeriodoInicio() : Integer.parseInt(inputAnioInicio);

    System.out.println("Mes inicio actual: " + actual.getMesPeriodoInicio());
    System.out.print("Nuevo mes inicio: ");
    String inputMesInicio = sc.nextLine();
    int mesInicio = inputMesInicio.isEmpty() ? actual.getMesPeriodoInicio() : Integer.parseInt(inputMesInicio);

    System.out.println("Anio fin actual: " + actual.getAnioPeriodoFin());
    System.out.print("Nuevo anio fin: ");
    String inputAnioFin = sc.nextLine();
    int anioFin = inputAnioFin.isEmpty() ? actual.getAnioPeriodoFin() : Integer.parseInt(inputAnioFin);

    System.out.println("Mes fin actual: " + actual.getMesPeriodoFin());
    System.out.print("Nuevo mes fin: ");
    String inputMesFin = sc.nextLine();
    int mesFin = inputMesFin.isEmpty() ? actual.getMesPeriodoFin() : Integer.parseInt(inputMesFin);

    boolean actualizado = presupuestoDAO.actualizarPresupuesto(
            idPresupuesto,
            nombre,
            anioInicio,
            mesInicio,
            anioFin,
            mesFin,
            usuarioActual.getCorreo()
    );

    if (actualizado) {
        System.out.println("Presupuesto actualizado correctamente.");
    } else {
        System.out.println("No se pudo actualizar el presupuesto.");
    }
}


public static void eliminarPresupuesto() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ELIMINAR PRESUPUESTO ---");
    System.out.print("Ingrese el id del presupuesto a eliminar: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    Presupuesto actual = presupuestoDAO.consultarPresupuesto(idPresupuesto);

    if (actual == null) {
        System.out.println("El presupuesto no existe.");
        return;
    }

    if (actual.getIdUsuario() != usuarioActual.getIdUsuario()) {
        System.out.println("No puede eliminar un presupuesto que no le pertenece.");
        return;
    }

    System.out.println("Presupuesto encontrado: " + actual.getNombre());
    System.out.print("¿Seguro que desea eliminar este presupuesto? (s/n): ");
    String confirmacion = sc.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
        boolean eliminado = presupuestoDAO.eliminarPresupuesto(idPresupuesto);

        if (eliminado) {
            System.out.println("Presupuesto eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el presupuesto.");
        }
    } else {
        System.out.println("Operacion cancelada.");
    }
}

public static void menuPresupuestoDetalle() {
    int opcion;

    do {
        if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== PRESUPUESTO DETALLE ==========");
        System.out.println("1. Registrar detalle");
        System.out.println("2. Listar detalle por presupuesto");
        System.out.println("3. Consultar detalle");
        System.out.println("4. Actualizar detalle");
        System.out.println("5. Eliminar detalle");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                registrarPresupuestoDetalle();
                break;
            case 2:
                listarPresupuestoDetalle();
                break;
            case 3:
                consultarPresupuestoDetalle();
                break;
            case 4:
                actualizarPresupuestoDetalle();
                break;
            case 5:
                eliminarPresupuestoDetalle();
                break;
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}

public static void registrarPresupuestoDetalle() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- REGISTRAR PRESUPUESTO DETALLE ---");

    System.out.print("Ingrese el id del presupuesto: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    System.out.print("Ingrese el id de la subcategoria: ");
    int idSubcategoria = Integer.parseInt(sc.nextLine());

    System.out.print("Monto mensual: ");
    double montoMensual = Double.parseDouble(sc.nextLine());

    System.out.print("Observaciones: ");
    String observaciones = sc.nextLine();

    boolean registrado = presupuestoDetalleDAO.registrarPresupuestoDetalle(
            idPresupuesto,
            idSubcategoria,
            montoMensual,
            observaciones,
            usuarioActual.getCorreo()
    );

    if (registrado) {
        System.out.println("Detalle registrado correctamente.");
    } else {
        System.out.println("No se pudo registrar el detalle.");
    }
}

public static void listarPresupuestoDetalle() {
    System.out.println("\n--- LISTAR DETALLES DE PRESUPUESTO ---");

    System.out.print("Ingrese el id del presupuesto: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    ArrayList<PresupuestoDetalle> lista = presupuestoDetalleDAO.listarPresupuestoDetalle(idPresupuesto);

    if (lista.isEmpty()) {
        System.out.println("No se encontraron detalles.");
    } else {
        System.out.println("\nDetalles encontrados:");
        for (PresupuestoDetalle pd : lista) {
            System.out.println("ID Detalle: " + pd.getIdDetalle()
                    + " | ID Subcategoria: " + pd.getIdSubcategoria()
                    + " | Monto mensual: " + pd.getMontoMensual()
                    + " | Observaciones: " + pd.getObservaciones());
        }
    }
}

public static void consultarPresupuestoDetalle() {
    System.out.println("\n--- CONSULTAR PRESUPUESTO DETALLE ---");

    System.out.print("Ingrese el id del detalle: ");
    int idDetalle = Integer.parseInt(sc.nextLine());

    PresupuestoDetalle pd = presupuestoDetalleDAO.consultarPresupuestoDetalle(idDetalle);

    if (pd != null) {
        System.out.println("\nDetalle encontrado:");
        System.out.println("ID Detalle: " + pd.getIdDetalle());
        System.out.println("ID Presupuesto: " + pd.getIdPresupuesto());
        System.out.println("ID Subcategoria: " + pd.getIdSubcategoria());
        System.out.println("Monto mensual: " + pd.getMontoMensual());
        System.out.println("Observaciones: " + pd.getObservaciones());
        
    } else {
        System.out.println("No se encontro el detalle.");
    }
}

public static void actualizarPresupuestoDetalle() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR PRESUPUESTO DETALLE ---");

    System.out.print("Ingrese el id del detalle a actualizar: ");
    int idDetalle = Integer.parseInt(sc.nextLine());

    PresupuestoDetalle actual = presupuestoDetalleDAO.consultarPresupuestoDetalle(idDetalle);

    if (actual == null) {
        System.out.println("El detalle no existe.");
        return;
    }

    System.out.println("Monto mensual actual: " + actual.getMontoMensual());
    System.out.print("Nuevo monto mensual: ");
    String inputMonto = sc.nextLine();
    double montoMensual = inputMonto.isEmpty() ? actual.getMontoMensual() : Double.parseDouble(inputMonto);

    System.out.println("Observaciones actuales: " + actual.getObservaciones());
    System.out.print("Nuevas observaciones: ");
    String observaciones = sc.nextLine();
    if (observaciones.isEmpty()) {
        observaciones = actual.getObservaciones();
    }

    boolean actualizado = presupuestoDetalleDAO.actualizarPresupuestoDetalle(
            idDetalle,
            montoMensual,
            observaciones,
            usuarioActual.getCorreo()
    );

    if (actualizado) {
        System.out.println("Detalle actualizado correctamente.");
    } else {
        System.out.println("No se pudo actualizar el detalle.");
    }
}

public static void eliminarPresupuestoDetalle() {
    System.out.println("\n--- ELIMINAR PRESUPUESTO DETALLE ---");

    System.out.print("Ingrese el id del detalle a eliminar: ");
    int idDetalle = Integer.parseInt(sc.nextLine());

    PresupuestoDetalle actual = presupuestoDetalleDAO.consultarPresupuestoDetalle(idDetalle);

    if (actual == null) {
        System.out.println("El detalle no existe.");
        return;
    }

    System.out.println("Detalle encontrado. ID Subcategoria: " + actual.getIdSubcategoria()
            + " | Monto: " + actual.getMontoMensual());
    System.out.print("¿Seguro que desea eliminar este detalle? (s/n): ");
    String confirmacion = sc.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
        boolean eliminado = presupuestoDetalleDAO.eliminarPresupuestoDetalle(idDetalle);

        if (eliminado) {
            System.out.println("Detalle eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el detalle.");
        }
    } else {
        System.out.println("Operacion cancelada.");
    }
}

public static void menuTransaccion() {
    int opcion;

    do {
        if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== TRANSACCIONES ==========");
        System.out.println("1. Registrar transaccion");
        System.out.println("2. Listar transacciones");
        System.out.println("3. Consultar transaccion");
        System.out.println("4. Actualizar transaccion");
        System.out.println("5. Eliminar transaccion");
        System.out.println("6. Registrar transaccion completa");
        System.out.println("7. Calcular mont ejecutado mes ");
        System.out.println("8. calcular porcentaje ejecutado");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                registrarTransaccion();
                break;
            case 2:
                listarTransacciones();
                break;
            case 3:
                consultarTransaccion();
                break;
            case 4:
                actualizarTransaccion();
                break;
            case 5:
                eliminarTransaccion();
                break;
             case 6:
                registrarTransaccionCompleta();
                break;
            case 7:
                calcularMontoEjecutadoMes();
                break;
            case 8:
                calcularPorcentajeEjecutadoFN();
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}

public static void calcularPorcentajeEjecutadoFN() {
    try {
        TransaccionDAO dao = new TransaccionDAO();

        System.out.println("\n=== FN PORCENTAJE EJECUTADO ===");

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double porcentaje = dao.calcularPorcentajeEjecutadoFN(
                idSubcategoria,
                idPresupuesto,
                anio,
                mes
        );

        System.out.println("Porcentaje ejecutado (fn): " + porcentaje + " %");

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void registrarTransaccion() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- REGISTRAR TRANSACCION ---");

    Transaccion t = new Transaccion();

    t.setIdUsuario(usuarioActual.getIdUsuario());

    System.out.print("ID Presupuesto: ");
    t.setIdPresupuesto(Integer.parseInt(sc.nextLine()));

    System.out.print("Anio: ");
    t.setAnio(Integer.parseInt(sc.nextLine()));

    System.out.print("Mes: ");
    t.setMes(Integer.parseInt(sc.nextLine()));

    System.out.print("ID Subcategoria: ");
    t.setIdSubcategoria(Integer.parseInt(sc.nextLine()));

    System.out.print("ID Obligacion (0 si no aplica): ");
    int idObligacion = Integer.parseInt(sc.nextLine());
    t.setIdObligacion(idObligacion);

    System.out.print("Tipo (ingreso/gasto/ahorro): ");
    t.setTipo(sc.nextLine());

    System.out.print("Descripcion: ");
    t.setDescripcion(sc.nextLine());

    System.out.print("Monto: ");
    t.setMonto(Double.parseDouble(sc.nextLine()));

    System.out.print("Fecha (yyyy-mm-dd): ");
    t.setFecha(java.sql.Date.valueOf(sc.nextLine()));

    System.out.print("Metodo de pago: ");
    t.setMetodoPago(sc.nextLine());

    System.out.print("Numero de factura: ");
    t.setNumFactura(sc.nextLine());

    System.out.print("Observaciones: ");
    t.setObservaciones(sc.nextLine());

    try {
        transaccionDAO.insertarTransaccion(t, usuarioActual.getCorreo());
        System.out.println("Transaccion registrada correctamente.");
    } catch (Exception e) {
        System.out.println("No se pudo registrar la transaccion: " + e.getMessage());
    }
}

public static void listarTransacciones() {
    System.out.println("\n--- LISTAR TRANSACCIONES ---");

    System.out.print("Ingrese el id del presupuesto: ");
    int idPresupuesto = Integer.parseInt(sc.nextLine());

    try {
        ArrayList<Transaccion> lista = new ArrayList<>(transaccionDAO.listarTransacciones(idPresupuesto));

        if (lista.isEmpty()) {
            System.out.println("No se encontraron transacciones.");
        } else {
            System.out.println("\nTransacciones encontradas:");
            for (Transaccion t : lista) {
                System.out.println("ID: " + t.getIdTransaccion()
                        + " | Descripcion: " + t.getDescripcion());
            }
        }
    } catch (Exception e) {
        System.out.println("Error al listar transacciones: " + e.getMessage());
    }
}

public static void consultarTransaccion() {
    System.out.println("\n--- CONSULTAR TRANSACCION ---");

    System.out.print("Ingrese el id de la transaccion: ");
    int idTransaccion = Integer.parseInt(sc.nextLine());

    try {
        Transaccion t = transaccionDAO.consultarTransaccion(idTransaccion);

        if (t != null) {
            System.out.println("\nTransaccion encontrada:");
            System.out.println("ID: " + t.getIdTransaccion());
            System.out.println("ID Usuario: " + t.getIdUsuario());
            System.out.println("ID Presupuesto: " + t.getIdPresupuesto());
            System.out.println("Anio: " + t.getAnio());
            System.out.println("Mes: " + t.getMes());
            System.out.println("ID Subcategoria: " + t.getIdSubcategoria());
            System.out.println("ID Obligacion: " + t.getIdObligacion());
            System.out.println("Tipo: " + t.getTipo());
            System.out.println("Descripcion: " + t.getDescripcion());
            System.out.println("Monto: " + t.getMonto());
            System.out.println("Fecha: " + t.getFecha());
            System.out.println("Metodo de pago: " + t.getMetodoPago());
            System.out.println("Numero factura: " + t.getNumFactura());
            System.out.println("Observaciones: " + t.getObservaciones());
        } else {
            System.out.println("No se encontro la transaccion.");
        }
    } catch (Exception e) {
        System.out.println("Error al consultar transaccion: " + e.getMessage());
    }
}

public static void actualizarTransaccion() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR TRANSACCION ---");

    System.out.print("Ingrese el id de la transaccion a actualizar: ");
    int idTransaccion = Integer.parseInt(sc.nextLine());

    try {
        Transaccion actual = transaccionDAO.consultarTransaccion(idTransaccion);

        if (actual == null) {
            System.out.println("La transaccion no existe.");
            return;
        }

        System.out.println("Anio actual: " + actual.getAnio());
        System.out.print("Nuevo anio: ");
        String inputAnio = sc.nextLine();
        int anio = inputAnio.isEmpty() ? actual.getAnio() : Integer.parseInt(inputAnio);

        System.out.println("Mes actual: " + actual.getMes());
        System.out.print("Nuevo mes: ");
        String inputMes = sc.nextLine();
        int mes = inputMes.isEmpty() ? actual.getMes() : Integer.parseInt(inputMes);

        System.out.println("Descripcion actual: " + actual.getDescripcion());
        System.out.print("Nueva descripcion: ");
        String descripcion = sc.nextLine();
        if (descripcion.isEmpty()) {
            descripcion = actual.getDescripcion();
        }

        System.out.println("Monto actual: " + actual.getMonto());
        System.out.print("Nuevo monto: ");
        String inputMonto = sc.nextLine();
        double monto = inputMonto.isEmpty() ? actual.getMonto() : Double.parseDouble(inputMonto);

        System.out.println("Fecha actual: " + actual.getFecha());
        System.out.print("Nueva fecha (yyyy-mm-dd): ");
        String inputFecha = sc.nextLine();
        java.sql.Date fecha = inputFecha.isEmpty() ? actual.getFecha() : java.sql.Date.valueOf(inputFecha);

        System.out.println("Metodo de pago actual: " + actual.getMetodoPago());
        System.out.print("Nuevo metodo de pago: ");
        String metodoPago = sc.nextLine();
        if (metodoPago.isEmpty()) {
            metodoPago = actual.getMetodoPago();
        }

        System.out.println("Numero factura actual: " + actual.getNumFactura());
        System.out.print("Nuevo numero factura: ");
        String numFactura = sc.nextLine();
        if (numFactura.isEmpty()) {
            numFactura = actual.getNumFactura();
        }

        System.out.println("Observaciones actuales: " + actual.getObservaciones());
        System.out.print("Nuevas observaciones: ");
        String observaciones = sc.nextLine();
        if (observaciones.isEmpty()) {
            observaciones = actual.getObservaciones();
        }

        actual.setAnio(anio);
        actual.setMes(mes);
        actual.setDescripcion(descripcion);
        actual.setMonto(monto);
        actual.setFecha(fecha);
        actual.setMetodoPago(metodoPago);
        actual.setNumFactura(numFactura);
        actual.setObservaciones(observaciones);

        transaccionDAO.actualizarTransaccion(actual, usuarioActual.getCorreo());
        System.out.println("Transaccion actualizada correctamente.");

    } catch (Exception e) {
        System.out.println("No se pudo actualizar la transaccion: " + e.getMessage());
    }
}

public static void eliminarTransaccion() {
    System.out.println("\n--- ELIMINAR TRANSACCION ---");

    System.out.print("Ingrese el id de la transaccion a eliminar: ");
    int idTransaccion = Integer.parseInt(sc.nextLine());

    try {
        Transaccion actual = transaccionDAO.consultarTransaccion(idTransaccion);

        if (actual == null) {
            System.out.println("La transaccion no existe.");
            return;
        }

        System.out.println("Transaccion encontrada: " + actual.getDescripcion()
                + " | Monto: " + actual.getMonto());

        System.out.print("¿Seguro que desea eliminar esta transaccion? (s/n): ");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            transaccionDAO.eliminarTransaccion(idTransaccion);
            System.out.println("Transaccion eliminada correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }

    } catch (Exception e) {
        System.out.println("No se pudo eliminar la transaccion: " + e.getMessage());
    }
}

public static void registrarTransaccionCompleta() {
    try {
        TransaccionDAO dao = new TransaccionDAO();

        System.out.println("\n=== REGISTRAR TRANSACCION COMPLETA ===");

        System.out.print("Ingrese id del usuario: ");
        int idUsuario = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese tipo: ");
        String tipo = sc.nextLine();

        System.out.print("Ingrese descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("Ingrese monto: ");
        double monto = Double.parseDouble(sc.nextLine());

        System.out.print("Ingrese fecha (yyyy-mm-dd): ");
        java.sql.Date fecha = java.sql.Date.valueOf(sc.nextLine());

        System.out.print("Ingrese metodo de pago: ");
        String metodoPago = sc.nextLine();

        String creadoPor = Sesion.getUsuarioActual().getCorreo();

        boolean resultado = dao.registrarTransaccionCompleta(
                idUsuario,
                idPresupuesto,
                anio,
                mes,
                idSubcategoria,
                tipo,
                descripcion,
                monto,
                fecha,
                metodoPago,
                creadoPor
        );

        if (resultado) {
            System.out.println("Transaccion registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la transaccion.");
        }

    } catch (Exception e) {
        System.out.println("Error en el ingreso de datos: " + e.getMessage());
    }
}
public static void calcularMontoEjecutadoMes() {
    try {
        TransaccionDAO dao = new TransaccionDAO();

        System.out.println("\n=== CALCULAR MONTO EJECUTADO DEL MES ===");

        System.out.print("Ingrese id de la subcategoria: ");
        int idSubcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double monto = dao.calcularMontoEjecutadoMes(idSubcategoria, idPresupuesto, anio, mes);

        System.out.println("Monto ejecutado del mes: L. " + monto);

    } catch (Exception e) {
        System.out.println("Error al calcular monto ejecutado: " + e.getMessage());
    }
}



public static void menuObligacionFija() {
    int opcion;

    do {
        if (!Sesion.haySesionActiva()) {
            return;
        }

        System.out.println("\n========== OBLIGACIONES FIJAS ==========");
        System.out.println("1. Registrar obligacion fija");
        System.out.println("2. Listar obligaciones fijas");
        System.out.println("3. Consultar obligacion fija");
        System.out.println("4. Actualizar obligacion fija");
        System.out.println("5. Eliminar obligacion fija");
        System.out.println("6. Procesar obligaciones del mes");
        System.out.println("7. calculo dias hasta vencimiento");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opcion: ");
        opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1:
                registrarObligacionFija();
                break;
            case 2:
                listarObligacionesFijas();
                break;
            case 3:
                consultarObligacionFija();
                break;
            case 4:
                actualizarObligacionFija();
                break;
            case 5:
                eliminarObligacionFija();
                break;
            case 6:
                procesarObligacionesMes();
                break;
            case 7:
                diasHastaVencimiento();
                break;
            case 0:
                System.out.println("Regresando al menu principal...");
                break;
            default:
                System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}
public static void diasHastaVencimiento() {
    try {
        ObligacionFijaDAO dao = new ObligacionFijaDAO();

        System.out.println("\n=== DIAS HASTA VENCIMIENTO ===");

        System.out.print("Ingrese id de la obligacion: ");
        int idObligacion = Integer.parseInt(sc.nextLine());

        int dias = dao.diasHastaVencimiento(idObligacion);

        System.out.println("Dias restantes: " + dias);

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void obtenerResumenCategoriaMes() {
    try {
        PresupuestoDAO dao = new PresupuestoDAO();

        System.out.println("\n=== RESUMEN DE CATEGORIA ===");

        System.out.print("Ingrese id de la categoria: ");
        int idCategoria = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese id del presupuesto: ");
        int idPresupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        double[] resumen = dao.obtenerResumenCategoriaMes(
                idCategoria,
                idPresupuesto,
                anio,
                mes
        );

        System.out.println("Monto presupuestado: L. " + resumen[0]);
        System.out.println("Monto ejecutado: L. " + resumen[1]);
        System.out.println("Porcentaje: " + resumen[2] + " %");

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public static void registrarObligacionFija() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- REGISTRAR OBLIGACION FIJA ---");

    ObligacionFija o = new ObligacionFija();

    o.setIdUsuario(usuarioActual.getIdUsuario());

    System.out.print("ID Subcategoria: ");
    o.setIdSubcategoria(Integer.parseInt(sc.nextLine()));

    System.out.print("Nombre: ");
    o.setNombre(sc.nextLine());

    System.out.print("Descripcion: ");
    o.setDescripcion(sc.nextLine());

    System.out.print("Monto: ");
    o.setMonto(Double.parseDouble(sc.nextLine()));

    System.out.print("Dia de vencimiento: ");
    o.setDiaVencimiento(Integer.parseInt(sc.nextLine()));

    System.out.print("Fecha inicio (yyyy-mm-dd): ");
    o.setFechaInicio(java.sql.Date.valueOf(sc.nextLine()));

    System.out.print("Fecha fin (yyyy-mm-dd, enter si no aplica): ");
    String fechaFinTexto = sc.nextLine().trim();
    if (fechaFinTexto.isEmpty()) {
        o.setFechaFin(null);
    } else {
        o.setFechaFin(java.sql.Date.valueOf(fechaFinTexto));
    }

    try {
        obligacionFijaDAO.insertarObligacionFija(o, usuarioActual.getCorreo());
        System.out.println("Obligacion fija registrada correctamente.");
    } catch (Exception e) {
        System.out.println("No se pudo registrar la obligacion fija: " + e.getMessage());
    }
}

public static void listarObligacionesFijas() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- LISTAR OBLIGACIONES FIJAS ---");

    try {
        ArrayList<ObligacionFija> lista = new ArrayList<>(obligacionFijaDAO.listarObligacionesFijas(usuarioActual.getIdUsuario()));

        if (lista.isEmpty()) {
            System.out.println("No se encontraron obligaciones fijas.");
        } else {
            System.out.println("\nObligaciones fijas encontradas:");
            for (ObligacionFija o : lista) {
                System.out.println("ID: " + o.getIdObligacion()
                        + " | Nombre: " + o.getNombre());
            }
        }
    } catch (Exception e) {
        System.out.println("Error al listar obligaciones fijas: " + e.getMessage());
    }
}

public static void consultarObligacionFija() {
    System.out.println("\n--- CONSULTAR OBLIGACION FIJA ---");

    System.out.print("Ingrese el id de la obligacion fija: ");
    int idObligacion = Integer.parseInt(sc.nextLine());

    try {
        ObligacionFija o = obligacionFijaDAO.consultarObligacionFija(idObligacion);

        if (o != null) {
            System.out.println("\nObligacion fija encontrada:");
            System.out.println("ID: " + o.getIdObligacion());
            System.out.println("ID Subcategoria: " + o.getIdSubcategoria());
            System.out.println("ID Usuario: " + o.getIdUsuario());
            System.out.println("Nombre: " + o.getNombre());
            System.out.println("Descripcion: " + o.getDescripcion());
            System.out.println("Monto: " + o.getMonto());
            System.out.println("Dia vencimiento: " + o.getDiaVencimiento());
            System.out.println("Fecha inicio: " + o.getFechaInicio());
            System.out.println("Fecha fin: " + o.getFechaFin());
            System.out.println("Activo: " + o.getActivo());
        } else {
            System.out.println("No se encontro la obligacion fija.");
        }
    } catch (Exception e) {
        System.out.println("Error al consultar obligacion fija: " + e.getMessage());
    }
}

public static void actualizarObligacionFija() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ACTUALIZAR OBLIGACION FIJA ---");

    System.out.print("Ingrese el id de la obligacion fija a actualizar: ");
    int idObligacion = Integer.parseInt(sc.nextLine());

    try {
        ObligacionFija actual = obligacionFijaDAO.consultarObligacionFija(idObligacion);

        if (actual == null) {
            System.out.println("La obligacion fija no existe.");
            return;
        }

        System.out.println("Nombre actual: " + actual.getNombre());
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) {
            nombre = actual.getNombre();
        }

        System.out.println("Descripcion actual: " + actual.getDescripcion());
        System.out.print("Nueva descripcion: ");
        String descripcion = sc.nextLine();
        if (descripcion.isEmpty()) {
            descripcion = actual.getDescripcion();
        }

        System.out.println("Monto actual: " + actual.getMonto());
        System.out.print("Nuevo monto: ");
        String montoTexto = sc.nextLine();
        double monto = montoTexto.isEmpty() ? actual.getMonto() : Double.parseDouble(montoTexto);

        System.out.println("Dia vencimiento actual: " + actual.getDiaVencimiento());
        System.out.print("Nuevo dia vencimiento: ");
        String diaTexto = sc.nextLine();
        int diaVencimiento = diaTexto.isEmpty() ? actual.getDiaVencimiento() : Integer.parseInt(diaTexto);

        System.out.println("Fecha fin actual: " + actual.getFechaFin());
        System.out.print("Nueva fecha fin (yyyy-mm-dd, enter para mantener): ");
        String fechaFinTexto = sc.nextLine();
        java.sql.Date fechaFin = fechaFinTexto.isEmpty() ? actual.getFechaFin() : java.sql.Date.valueOf(fechaFinTexto);

        System.out.println("Estado actual (activo): " + actual.getActivo());
        System.out.print("Nuevo estado (1=activo, 0=inactivo): ");
        String activoTexto = sc.nextLine();
        int activo = activoTexto.isEmpty() ? actual.getActivo() : Integer.parseInt(activoTexto);

        actual.setNombre(nombre);
        actual.setDescripcion(descripcion);
        actual.setMonto(monto);
        actual.setDiaVencimiento(diaVencimiento);
        actual.setFechaFin(fechaFin);
        actual.setActivo(activo);

        obligacionFijaDAO.actualizarObligacionFija(actual, usuarioActual.getCorreo());
        System.out.println("Obligacion fija actualizada correctamente.");

    } catch (Exception e) {
        System.out.println("No se pudo actualizar la obligacion fija: " + e.getMessage());
    }
}

public static void eliminarObligacionFija() {
    usuario usuarioActual = Sesion.getUsuarioActual();

    System.out.println("\n--- ELIMINAR OBLIGACION FIJA ---");

    System.out.print("Ingrese el id de la obligacion fija a eliminar: ");
    int idObligacion = Integer.parseInt(sc.nextLine());

    try {
        ObligacionFija actual = obligacionFijaDAO.consultarObligacionFija(idObligacion);

        if (actual == null) {
            System.out.println("La obligacion fija no existe.");
            return;
        }

        System.out.println("Obligacion encontrada: " + actual.getNombre()
                + " | Monto: " + actual.getMonto());

        System.out.print("¿Seguro que desea eliminar esta obligacion fija? (s/n): ");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            obligacionFijaDAO.eliminarObligacionFija(idObligacion, usuarioActual.getCorreo());
            System.out.println("Obligacion fija eliminada correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }

    } catch (Exception e) {
        System.out.println("No se pudo eliminar la obligacion fija: " + e.getMessage());
    }
}
    
}
