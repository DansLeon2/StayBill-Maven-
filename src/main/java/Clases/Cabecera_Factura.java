package Clases;

import javax.swing.JTable;

public class Cabecera_Factura implements Inter_CRUD {

    private int num_fac;
    private String fec_fac;
    private String ced_cli;
    private double sub_fac;
    private double desc_fac;
    private double iva_fac;
    private double tot_fac;
    private Integer id_usuario;
    private Integer id_hab;

    private cls_conexion obj_conexion = new cls_conexion();

    public Cabecera_Factura() {
    }

    public Cabecera_Factura(int num_fac, String fec_fac, String ced_cli,
            double sub_fac, double desc_fac, double iva_fac,
            double tot_fac, Integer id_usuario, Integer id_hab) {
        this.num_fac = num_fac;
        this.fec_fac = fec_fac;
        this.ced_cli = ced_cli;
        this.sub_fac = sub_fac;
        this.desc_fac = desc_fac;
        this.iva_fac = iva_fac;
        this.tot_fac = tot_fac;
        this.id_usuario = id_usuario;
        this.id_hab = id_hab;
    }

    public int getNum_fac() {
        return num_fac;
    }

    public void setNum_fac(int num_fac) {
        this.num_fac = num_fac;
    }

    public String getFec_fac() {
        return fec_fac;
    }

    public void setFec_fac(String fec_fac) {
        this.fec_fac = fec_fac;
    }

    public String getCed_cli() {
        return ced_cli;
    }

    public void setCed_cli(String ced_cli) {
        this.ced_cli = ced_cli;
    }

    public double getSub_fac() {
        return sub_fac;
    }

    public void setSub_fac(double sub_fac) {
        this.sub_fac = sub_fac;
    }

    public double getDesc_fac() {
        return desc_fac;
    }

    public void setDesc_fac(double desc_fac) {
        this.desc_fac = desc_fac;
    }

    public double getIva_fac() {
        return iva_fac;
    }

    public void setIva_fac(double iva_fac) {
        this.iva_fac = iva_fac;
    }

    public double getTot_fac() {
        return tot_fac;
    }

    public void setTot_fac(double tot_fac) {
        this.tot_fac = tot_fac;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_hab() {
        return id_hab;
    }

    public void setId_hab(Integer id_hab) {
        this.id_hab = id_hab;
    }

    public cls_conexion getObj_conexion() {
        return obj_conexion;
    }

    public void setObj_conexion(cls_conexion obj_conexion) {
        this.obj_conexion = obj_conexion;
    }

    @Override
    public String mtd_guardar() {

        String cedulaSQL
                = (getCed_cli() == null || getCed_cli().trim().isEmpty())
                ? "NULL"
                : "'" + getCed_cli() + "'";

        String idUsuarioSQL
                = (getId_usuario() == null || getId_usuario() <= 0)
                        ? "NULL"
                        : getId_usuario().toString();

        String idHabSQL
                = (getId_hab() == null || getId_hab() <= 0)
                        ? "NULL"
                        : getId_hab().toString();

        String sql
                = "INSERT INTO cabecera_factura "
                + "(num_fac, fec_fac, ced_cli, sub_fac, desc_fac, iva_fac, tot_fac, id_usuario, id_hab) VALUES ("
                + getNum_fac() + ", "
                + "'" + getFec_fac() + "', "
                + cedulaSQL + ", "
                + getSub_fac() + ", "
                + getDesc_fac() + ", "
                + getIva_fac() + ", "
                + getTot_fac() + ", "
                + idUsuarioSQL + ", "
                + idHabSQL
                + ");";

        return obj_conexion.ejecutar(sql);
    }

    @Override
    public String mtd_actualizar() {

        String cedulaSQL
                = (getCed_cli() == null || getCed_cli().trim().isEmpty())
                ? "NULL"
                : "'" + getCed_cli() + "'";

        Integer idUsu = getId_usuario();
        String idUsuarioSQL
                = (idUsu == null || idUsu <= 0)
                        ? "NULL"
                        : idUsu.toString();

        Integer idHab = getId_hab();
        String idHabSQL
                = (idHab == null || idHab <= 0)
                        ? "NULL"
                        : idHab.toString();

        String sql
                = "UPDATE cabecera_factura SET "
                + "fec_fac   = '" + getFec_fac() + "', "
                + "ced_cli   = " + cedulaSQL + ", "
                + "sub_fac   = " + getSub_fac() + ", "
                + "desc_fac  = " + getDesc_fac() + ", "
                + "iva_fac   = " + getIva_fac() + ", "
                + "tot_fac   = " + getTot_fac() + ", "
                + "id_usuario = " + idUsuarioSQL + ", "
                + "id_hab     = " + idHabSQL + " "
                + "WHERE num_fac = " + getNum_fac() + ";";

        return obj_conexion.ejecutar(sql);
    }

    @Override
    public String mtd_eliminar(int codigo) {

        String error = obj_conexion.ejecutar(
                "DELETE FROM detalle_factura WHERE num_fac = " + codigo + ";"
        );
        if (!"".equals(error)) {
            return error;
        }

        error = obj_conexion.ejecutar(
                "DELETE FROM cabecera_factura WHERE num_fac = " + codigo + ";"
        );
        if (!"".equals(error)) {
            return error;
        }

        return "Se eliminaron los datos satisfactoriamente";
    }

    public void CargarTabla(JTable tabla) {
        String sql
                = "SELECT "
                + "cf.num_fac   AS NumFactura, "
                + "cf.fec_fac   AS Fecha, "
                + "cf.ced_cli   AS CedulaCliente, "
                + "cf.sub_fac   AS Subtotal, "
                + "cf.desc_fac  AS Descuento, "
                + "cf.iva_fac   AS IVA, "
                + "cf.tot_fac   AS Total, "
                + "cf.id_hab    AS Habitacion, "
                + "u.nom_usu    AS Usuario "
                + "FROM cabecera_factura cf "
                + "LEFT JOIN usuarios u ON cf.id_usuario = u.id_usu";
        obj_conexion.CargarTabla(sql, tabla);
    }
}
