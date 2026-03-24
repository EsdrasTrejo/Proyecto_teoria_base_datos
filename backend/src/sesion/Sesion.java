/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesion;
import modelo.usuario;
/**
 *
 * @author Usuario
 */
public class Sesion {
      private static usuario usuarioActual;

    public static usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(usuario usuario) {
        usuarioActual = usuario;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }
}
