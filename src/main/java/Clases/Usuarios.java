/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Clases.cls_conexion;
import Formularios.pnl_Usuarios;
import javax.swing.JTable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class Usuarios implements Inter_CRUD {

    public Usuarios() {
    }

    public Usuarios(Integer id_usu, String nom_usu, String user_usu, String pass_usu, String rol_usu, String correo_usu) {
        this.id_usu = id_usu;
        this.nom_usu = nom_usu;
        this.user_usu = user_usu;
        this.pass_usu = pass_usu;
        this.rol_usu = rol_usu;
        this.correo_usu = correo_usu;
    }

    private Integer id_usu;
    private String nom_usu;
    private String user_usu;
    private String pass_usu;
    private String rol_usu;
    private String correo_usu;

    private cls_conexion cnx = new cls_conexion();
    
    public static Usuarios usuarioLogueado;

    public Integer getId_usu() {
        return id_usu;
    }

    public void setId_usu(Integer id_usu) {
        this.id_usu = id_usu;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getUser_usu() {
        return user_usu;
    }

    public void setUser_usu(String user_usu) {
        this.user_usu = user_usu;
    }

    public String getPass_usu() {
        return pass_usu;
    }

    public void setPass_usu(String pass_usu) {
        this.pass_usu = pass_usu;
    }

    public String getRol_usu() {
        return rol_usu;
    }

    public void setRol_usu(String rol_usu) {
        this.rol_usu = rol_usu;
    }

    public String getCorreo_usu() {
        return correo_usu;
    }

    public void setCorreo_usu(String correo_usu) {
        this.correo_usu = correo_usu;
    }

    public cls_conexion getDb() {
        return cnx;
    }

    public void setDb(cls_conexion cnx) {
        this.cnx = cnx;
    }

    @Override
    public String mtd_guardar() {
        String sql = "INSERT INTO usuarios (nom_usu, user_usu, pass_usu, rol_usu, correo_usu) VALUES ("
                + "'" + getNom_usu() + "', "
                + "'" + getUser_usu() + "', "
                + "'" + getPass_usu() + "', "
                + "'" + getRol_usu() + "', "
                + (getCorreo_usu() == null ? "NULL" : "'" + getCorreo_usu() + "'")
                + ");";
        return cnx.ejecutar(sql);
    }

    @Override
    public String mtd_actualizar() {
        String sql = "UPDATE usuarios SET "
                + "nom_usu='" + getNom_usu() + "', "
                + "user_usu='" + getUser_usu() + "', "
                + "pass_usu='" + getPass_usu() + "', "
                + "rol_usu='" + getRol_usu() + "', "
                + "correo_usu=" + (getCorreo_usu() == null ? "NULL" : "'" + getCorreo_usu() + "'")
                + " WHERE id_usu=" + getId_usu() + ";";
        return cnx.ejecutar(sql);
    }

    @Override
    public String mtd_eliminar(int codigo) {
        String sql = "DELETE FROM usuarios WHERE id_usu=" + codigo + ";";
        return cnx.ejecutar(sql);
    }
    
    public void CargarTabla(JTable Tabla) {
        String sql = "SELECT id_usu AS ID, nom_usu AS Nombre, user_usu AS Usuario, rol_usu AS Rol, correo_usu AS Correo FROM usuarios;";
        cnx.CargarTabla(sql, Tabla);
    }

    // Login
    public Usuarios login(String correo, String pass) {
    try {
        String sql = "SELECT id_usu, nom_usu, user_usu, pass_usu, rol_usu, correo_usu "
           + "FROM usuarios "
           + "WHERE LOWER(correo_usu)=LOWER('" + correo.trim() + "') "
           + "AND pass_usu='" + pass + "' "
           + "AND estado_usu='ACTIVO' "
           + "LIMIT 1;";
        ResultSet rs = cnx.Consulta(sql);
        if (rs != null && rs.next()) {
            Usuarios u = new Usuarios(
                rs.getInt("id_usu"),
                rs.getString("nom_usu"),
                rs.getString("user_usu"),
                rs.getString("pass_usu"),
                rs.getString("rol_usu"),
                rs.getString("correo_usu")
            );
            rs.close();
            return u;
        }
        if (rs != null) rs.close();
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            null, e.getMessage(), "Error en login", javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    return null;
}

}
