package Clases;

public class Detalle_Factura implements Inter_CRUD {

    public Detalle_Factura() {
    }

  
    public Detalle_Factura(int id_detalle, int num_fac, int id_pro, int can_pro, double tot_linea) {
        this.id_detalle = id_detalle;
        this.num_fac = num_fac;
        this.id_pro = id_pro;
        this.can_pro = can_pro;
        this.tot_linea = tot_linea;
    }

    public Detalle_Factura(int num_fac, int id_pro, int can_pro, double tot_linea) {
        this.num_fac = num_fac;
        this.id_pro = id_pro;
        this.can_pro = can_pro;
        this.tot_linea = tot_linea;
    }

    private int id_detalle;
    private int num_fac;
    private int id_pro;
    private int can_pro;
    private double tot_linea;

    private cls_conexion obj_conexion = new cls_conexion();

    public cls_conexion getObj_conexion() {
        return obj_conexion;
    }

    public void setObj_conexion(cls_conexion obj_conexion) {
        this.obj_conexion = obj_conexion;
    }

    public Integer getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Integer getNum_fac() {
        return num_fac;
    }

    public void setNum_fac(Integer num_fac) {
        this.num_fac = num_fac;
    }

    public Integer getId_pro() {
        return id_pro;
    }

    public void setId_pro(Integer id_pro) {
        this.id_pro = id_pro;
    }

    public Integer getCan_pro() {
        return can_pro;
    }

    public void setCan_pro(Integer can_pro) {
        this.can_pro = can_pro;
    }

    public Double getTot_linea() {
        return tot_linea;
    }

    public void setTot_linea(Double tot_linea) {
        this.tot_linea = tot_linea;
    }

    @Override
    public String mtd_guardar() {
        String sql
                = "INSERT INTO detalle_factura "
                + "(num_fac, id_pro, can_pro, tot_linea) VALUES ("
                + getNum_fac() + ", "
                + getId_pro() + ", "
                + getCan_pro() + ", "
                + getTot_linea()
                + ");";

        return obj_conexion.ejecutar(sql);
    }

    @Override
    public String mtd_actualizar() {
        String sql
                = "UPDATE detalle_factura SET "
                + "num_fac   = " + getNum_fac() + ", "
                + "id_pro    = " + getId_pro() + ", "
                + "can_pro   = " + getCan_pro() + ", "
                + "tot_linea = " + getTot_linea() + " "
                + "WHERE id_detalle = " + getId_detalle() + ";";

        return obj_conexion.ejecutar(sql);
    }

    @Override
    public String mtd_eliminar(int codigo) {
        String sql = "DELETE FROM detalle_factura WHERE id_detalle = " + codigo + ";";
        return obj_conexion.ejecutar(sql);
    }

    public void CargarTabla(javax.swing.JTable tabla) {
        String sql
                = "SELECT "
                + "id_detalle AS ID, "
                + "num_fac    AS NumFactura, "
                + "id_pro     AS IdProducto, "
                + "can_pro    AS Cantidad, "
                + "tot_linea  AS TotalLinea "
                + "FROM detalle_factura";
        obj_conexion.CargarTabla(sql, tabla);
    }

}
