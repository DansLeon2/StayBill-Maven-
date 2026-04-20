package Clases;

import javax.swing.JTable;

/**
 *
 * @author Danny
 */
public class Productos implements Inter_CRUD {

    public Productos() {
    }

    
    

    private int id_pro;
    private String nom_pro;
    private double pre_pro;
    private int can_pro;
    private String desc_pro;
    private String tipo_pro;

    public Productos(int id_pro, String nom_pro, double pre_pro, int can_pro, String desc_pro, String tipo_pro) {
        this.id_pro = id_pro;
        this.nom_pro = nom_pro;
        this.pre_pro = pre_pro;
        this.can_pro = can_pro;
        this.desc_pro = desc_pro;
        this.tipo_pro = tipo_pro;
    }

    private cls_conexion obj_conexion = new cls_conexion();

    @Override
    public String mtd_guardar() {
        String error = obj_conexion.ejecutar(
                "INSERT INTO productos "
                + "(id_pro, nom_pro, pre_pro, can_pro, desc_pro, id_marca) VALUES ("
                + getId_pro() + ", "
                + "'" + getNom_pro() + "', "
                + getPre_pro() + ", "
                + getCan_pro() + ", "
                + "'" + getDesc_pro() + "', "
                + getTipo_pro()
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
                "UPDATE productos SET "
                + "nom_pro = '" + getNom_pro() + "', "
                + "pre_pro = " + getPre_pro() + ", "
                + "can_pro = " + getCan_pro() + ", "
                + "desc_pro = '" + getDesc_pro() + "', "
                + "id_marca = " + getTipo_pro() + " "
                + "WHERE id_pro = " + getId_pro() + ";"
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
                "DELETE FROM productos WHERE id_pro = " + codigo + ";"
        );

        if ("".equals(error)) {
            return "Se eliminaron los datos satisfactoriamente";
        } else {
            return error;
        }
    }

    public void CargarDatos(JTable Tabla) {
        obj_conexion.CargarTabla(
            "SELECT "
            + "id_pro   AS ID, "
            + "nom_pro  AS Nombre, "
            + "pre_pro  AS Precio, "
            + "can_pro  AS Cantidad, "
            + "desc_pro AS Descripcion, "
            + "tipo_pro AS Tipo "
            + "FROM productos;",
            Tabla
    );
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public double getPre_pro() {
        return pre_pro;
    }

    public void setPre_pro(double pre_pro) {
        this.pre_pro = pre_pro;
    }

    public int getCan_pro() {
        return can_pro;
    }

    public void setCan_pro(int can_pro) {
        this.can_pro = can_pro;
    }

    public String getDesc_pro() {
        return desc_pro;
    }

    public void setDesc_pro(String desc_pro) {
        this.desc_pro = desc_pro;
    }

    public String getTipo_pro() {
        return tipo_pro;
    }

    public void setTipo_pro(String tipo_pro) {
        this.tipo_pro = tipo_pro;
    }

    public cls_conexion getObj_conexion() {
        return obj_conexion;
    }

    public void setObj_conexion(cls_conexion obj_conexion) {
        this.obj_conexion = obj_conexion;
    }

    

}
