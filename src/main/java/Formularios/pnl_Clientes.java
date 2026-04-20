package Formularios;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class pnl_Clientes extends javax.swing.JPanel {

    private DefaultTableModel modeloClientes;

    private String cedulaSeleccionada = ""; 
    private String nombreOri = "";
    private String direcOri = "";
    private String telfOri = "";
    private String correoOri = "";

    public pnl_Clientes() {
        initComponents();

        generarTablaClientes();
        cargarClientes();

        btn_editar.setEnabled(false);
        txt_ced.setEditable(false);

        // Listener de selección (no hardcodea columnas)
        tabla_clientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla_clientes.getSelectedRow() >= 0) {
                cargarClienteSeleccionadoDesdeTabla();
            }
        });
    }

    private void generarTablaClientes() {
        String[] titulos = {"Cédula", "Nombre", "Fecha Registro", "Dirección", "Teléfono", "Email"};
        modeloClientes = new DefaultTableModel(null, titulos) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla_clientes.setModel(modeloClientes);
    }

    // ---------------- HELPERS (sin hardcodear índices) ----------------
    private int col(javax.swing.JTable t, String columnName) {
        for (int i = 0; i < t.getColumnCount(); i++) {
            String name = t.getColumnName(i);
            if (name != null && name.trim().equalsIgnoreCase(columnName.trim())) {
                return i;
            }
        }
        return -1;
    }

    private String valAtModel(int modelRow, String colName) {
        int c = col(tabla_clientes, colName);
        if (c == -1) return "";
        Object v = tabla_clientes.getModel().getValueAt(modelRow, c);
        return v == null ? "" : v.toString();
    }

    // ---------------- CARGAR SELECCIÓN A TXT ----------------
    private void cargarClienteSeleccionadoDesdeTabla() {
        int viewRow = tabla_clientes.getSelectedRow();
        if (viewRow < 0) return;

        int modelRow = tabla_clientes.convertRowIndexToModel(viewRow);

        cedulaSeleccionada = valAtModel(modelRow, "Cédula");
        nombreOri  = valAtModel(modelRow, "Nombre");
        direcOri   = valAtModel(modelRow, "Dirección");
        telfOri    = valAtModel(modelRow, "Teléfono");
        correoOri  = valAtModel(modelRow, "Email");

        txt_ced.setText(cedulaSeleccionada);
        txt_nombre.setText(nombreOri);
        txt_direc.setText(direcOri);
        txt_telf.setText(telfOri);
        txt_correo.setText(correoOri);

        btn_editar.setEnabled(true);
    }

    private void cargarClientes() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();

            String sql = "SELECT ced_cli AS 'Cédula', nom_cli AS 'Nombre', fec_reg_cli AS 'Fecha Registro', " +
                         "dir_cli AS 'Dirección', tel_cli AS 'Teléfono', email_cli AS 'Email' " +
                         "FROM clientes ORDER BY nom_cli;";

            cnx.CargarTabla(sql, tabla_clientes);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txt_ced.setText("");
        txt_nombre.setText("");
        txt_direc.setText("");
        txt_telf.setText("");
        txt_correo.setText("");

        cedulaSeleccionada = "";
        nombreOri = direcOri = telfOri = correoOri = "";

        btn_editar.setEnabled(false);
        tabla_clientes.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_clientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_ced = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        txt_telf = new javax.swing.JTextField();
        txt_direc = new javax.swing.JTextField();
        btn_editar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla_clientes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 446, 343));

        jLabel1.setText("Cedula");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel2.setText("Nombre");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel3.setText("Telefono");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jLabel4.setText("Direccion");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 59, -1));
        jPanel1.add(txt_ced, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 157, -1));
        jPanel1.add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 157, -1));
        jPanel1.add(txt_telf, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 157, -1));
        jPanel1.add(txt_direc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 157, -1));

        btn_editar.setText("Editar");
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, -1, -1));

        jLabel6.setText("Correo");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));
        jPanel1.add(txt_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 157, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 890, 410));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        if (cedulaSeleccionada == null || cedulaSeleccionada.isBlank()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente en la tabla.");
            return;
        }

        String nombre = txt_nombre.getText().trim();
        String direc  = txt_direc.getText().trim();
        String telf   = txt_telf.getText().trim();
        String correo = txt_correo.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        boolean sinCambios =
                nombre.equals(nombreOri) &&
                direc.equals(direcOri) &&
                telf.equals(telfOri) &&
                correo.equals(correoOri);

        if (sinCambios) {
            JOptionPane.showMessageDialog(this, "No hubo cambios.");
            return;
        }

        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();

            String sql = "UPDATE clientes SET " +
                         "nom_cli = '" + nombre + "', " +
                         "dir_cli = '" + direc + "', " +
                         "tel_cli = '" + telf + "', " +
                         "email_cli = '" + correo + "' " +
                         "WHERE ced_cli = '" + cedulaSeleccionada + "';";

            String error = cnx.ejecutar(sql);

            if (error == null || error.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");

                // recarga
                cargarClientes();

                // actualiza "ori" para próximas ediciones
                nombreOri = nombre;
                direcOri = direc;
                telfOri = telf;
                correoOri = correo;

                // opcional: limpiar o mantener
                // limpiarCampos();

            } else {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_editar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_clientes;
    private javax.swing.JTextField txt_ced;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_direc;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_telf;
    // End of variables declaration//GEN-END:variables
}
