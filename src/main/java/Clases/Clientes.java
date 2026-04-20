package Clases;

import javax.swing.JTable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Clientes implements Inter_CRUD {

    private String ced_cli;
    private String nom_cli;
    private String fec_reg_cli;
    private String dir_cli;
    private String tel_cli;
    private String email_cli;

    private cls_conexion cnx = new cls_conexion();

    public Clientes() {
    }

    public Clientes(String ced_cli, String nom_cli, String dir_cli, String tel_cli, String email_cli) {
        this.ced_cli = ced_cli;
        this.nom_cli = nom_cli;
        this.dir_cli = dir_cli;
        this.tel_cli = tel_cli;
        this.email_cli = email_cli;
    }

    public String getCed_cli() {
        return ced_cli;
    }

    public void setCed_cli(String ced_cli) {
        this.ced_cli = ced_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getFec_reg_cli() {
        return fec_reg_cli;
    }

    public void setFec_reg_cli(String fec_reg_cli) {
        this.fec_reg_cli = fec_reg_cli;
    }

    public String getDir_cli() {
        return dir_cli;
    }

    public void setDir_cli(String dir_cli) {
        this.dir_cli = dir_cli;
    }

    public String getTel_cli() {
        return tel_cli;
    }

    public void setTel_cli(String tel_cli) {
        this.tel_cli = tel_cli;
    }

    public String getEmail_cli() {
        return email_cli;
    }

    public void setEmail_cli(String email_cli) {
        this.email_cli = email_cli;
    }

    @Override
    public String mtd_guardar() {

        String sql = "INSERT INTO clientes (ced_cli, nom_cli, dir_cli, tel_cli, email_cli) VALUES ("
                + "'" + getCed_cli() + "', "
                + "'" + getNom_cli() + "', "
                + (getDir_cli() == null ? "NULL" : "'" + getDir_cli() + "'") + ", "
                + (getTel_cli() == null ? "NULL" : "'" + getTel_cli() + "'") + ", "
                + (getEmail_cli() == null ? "NULL" : "'" + getEmail_cli() + "'")
                + ");";
        return cnx.ejecutar(sql);
    }

    @Override
    public String mtd_actualizar() {
        String sql = "UPDATE clientes SET "
                + "nom_cli='" + getNom_cli() + "', "
                + "dir_cli=" + (getDir_cli() == null ? "NULL" : "'" + getDir_cli() + "'") + ", "
                + "tel_cli=" + (getTel_cli() == null ? "NULL" : "'" + getTel_cli() + "'") + ", "
                + "email_cli=" + (getEmail_cli() == null ? "NULL" : "'" + getEmail_cli() + "'")
                + " WHERE ced_cli='" + getCed_cli() + "';";
        return cnx.ejecutar(sql);
    }

    public String mtd_eliminar(String cedula) {
        String sql = "DELETE FROM clientes WHERE ced_cli='" + cedula + "';";
        return cnx.ejecutar(sql);
    }

    @Override
    public String mtd_eliminar(int codigo) {
        return mtd_eliminar(String.valueOf(codigo));
    }

    public void CargarTabla(JTable Tabla) {
        String sql = "SELECT ced_cli AS Cedula, nom_cli AS Nombre, fec_reg_cli AS Registro, dir_cli AS Direccion, tel_cli AS Telefono, email_cli AS Email FROM clientes ORDER BY nom_cli;";
        cnx.CargarTabla(sql, Tabla);
    }

    public boolean existeCliente(String cedula) {
        try {
            String sql = "SELECT COUNT(*) AS total FROM clientes WHERE ced_cli = '" + cedula + "';";
            ResultSet rs = cnx.Consulta(sql);
            if (rs != null && rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                return total > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error validando cliente: " + e.getMessage());
        }
        return false;
    }
}
