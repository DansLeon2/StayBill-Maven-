package Clases;

import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class cls_conexion {

    Connection Conec;
    Statement St;

    public cls_conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // >>> Cambia al esquema StayBill <<<
            Conec = DriverManager.getConnection(
                    "jdbc:mysql://localhost/staybill?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""   // pon tu clave si tienes
            );
            if (Conec != null) {
                System.out.println("Successfully connected to StayBill");
            }
        } catch (SQLException excepcionSql) {
            JOptionPane.showMessageDialog(null, excepcionSql.getMessage(),
                    "Error en base de datos", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException claseNoEncontrada) {
            JOptionPane.showMessageDialog(null, claseNoEncontrada.getMessage(),
                    "No se encontró el controlador", JOptionPane.ERROR_MESSAGE);
        }
    }

    // INSERT / UPDATE / DELETE
    public String ejecutar(String sql) {
        String error = "";
        try {
            St = Conec.createStatement();
            St.executeUpdate(sql);
        } catch (Exception ex) {
            error = ex.getMessage();
        }
        return error;
    }

 
    public ResultSet Consulta(String sql) {
        ResultSet res = null;
        try {
            Statement s = Conec.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            res = s.executeQuery(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "No se encontró los Datos", JOptionPane.ERROR_MESSAGE);
        }
        return res;
    }

 
    public void CargarTabla(String instruccionSQL, JTable NombreTabla) {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            NombreTabla.setModel(modelo);
            ResultSet rs = Consulta(instruccionSQL);
            if (rs == null) return;
            ResultSetMetaData rsMd = rs.getMetaData();
            for (int i = 1; i <= rsMd.getColumnCount(); i++) {
                modelo.addColumn(rsMd.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[rsMd.getColumnCount()];
                for (int i = 0; i < rsMd.getColumnCount(); i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error cargando tabla", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void cargarCombo(String instruccionSQL, JComboBox jComboBox1) {
        try {
            DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
            ResultSet rs = Consulta(instruccionSQL);
            while (rs != null && rs.next()) {
                modeloCombo.addElement(rs.getObject("Nombre"));
            }
            if (rs != null) rs.close();
            jComboBox1.setModel(modeloCombo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "No se encontró los Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}
