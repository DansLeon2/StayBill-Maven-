/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author LENOVO
 */
public class Habitaciones implements Inter_CRUD {

    public Habitaciones() {
    }

    public Habitaciones(Integer id_hab, String cod_hab, String tip_hab, Double pre_hab, String est_hab) {
        this.id_hab = id_hab;
        this.cod_hab = cod_hab;
        this.tip_hab = tip_hab;
        this.pre_hab = pre_hab;
        this.est_hab = est_hab;
    }

    private Integer id_hab;
    private String cod_hab;
    private String tip_hab;
    private Double pre_hab;
    private String est_hab;

    private cls_conexion obj_conexion = new cls_conexion();

    public Integer getId_hab() {
        return id_hab;
    }

    public void setId_hab(Integer id_hab) {
        this.id_hab = id_hab;
    }

    public String getCod_hab() {
        return cod_hab;
    }

    public void setCod_hab(String cod_hab) {
        this.cod_hab = cod_hab;
    }

    public String getTip_hab() {
        return tip_hab;
    }

    public void setTip_hab(String tip_hab) {
        this.tip_hab = tip_hab;
    }

    public Double getPre_hab() {
        return pre_hab;
    }

    public void setPre_hab(Double pre_hab) {
        this.pre_hab = pre_hab;
    }

    public String getEst_hab() {
        return est_hab;
    }

    public void setEst_hab(String est_hab) {
        this.est_hab = est_hab;
    }

    public cls_conexion getObj_conexion() {
        return obj_conexion;
    }

    public void setObj_conexion(cls_conexion obj_conexion) {
        this.obj_conexion = obj_conexion;
    }

    @Override
    public String mtd_guardar() {
        String error = obj_conexion.ejecutar(
                "INSERT INTO habitaciones "
                + "(id_hab, cod_hab, tip_hab, pre_hab, est_hab) VALUES ("
                + getId_hab() + ", "
                + "'" + getCod_hab() + "', "
                + "'" + getTip_hab() + "', "
                + getPre_hab() + ", "
                + "'" + getEst_hab() + "'"
                + ");"
        );

        if ("".equals(error)) {
            return "Se guardaron los datos satisfactoriamente";
        } else {
            return error;
        }
    }

    @Override
    public String mtd_actualizar() {
        String error = obj_conexion.ejecutar(
                "UPDATE habitaciones SET "
                + "cod_hab = '" + getCod_hab() + "', "
                + "tip_hab = '" + getTip_hab() + "', "
                + "pre_hab = " + getPre_hab() + ", "
                + "est_hab = '" + getEst_hab() + "' "
                + "WHERE id_hab = " + getId_hab() + ";"
        );

        if ("".equals(error)) {
            return "Se actualizaron los datos satisfactoriamente";
        } else {
            return error;
        }
    }

    @Override
    public String mtd_eliminar(int codigo) {
        String error = obj_conexion.ejecutar(
                "DELETE FROM habitaciones WHERE id_hab = " + codigo + ";"
        );

        if ("".equals(error)) {
            return "Se eliminaron los datos satisfactoriamente";
        } else {
            return error;
        }
    }

    public void CargarTabla(javax.swing.JTable tabla) {
        String sql
                = "SELECT "
                + "id_hab AS ID, "
                + "cod_hab AS Codigo, "
                + "tip_hab AS Tipo, "
                + "pre_hab AS Precio, "
                + "est_hab AS Estado "
                + "FROM habitaciones";
        obj_conexion.CargarTabla(sql, tabla);
    }

}
