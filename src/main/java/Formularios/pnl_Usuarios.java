package Formularios;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class pnl_Usuarios extends javax.swing.JPanel {

    public pnl_Usuarios() {
        initComponents();
        generarTablaUsuarios(); 
        cargarUsuarios();
    }

    private DefaultTableModel modeloUsuarios;

    private int idSeleccionado = -1;


    private String nombreOri = "";
    private String usuarioOri = "";
    private String correoOri = "";
    private String rolOri = "";
    private String estadoOri = "";
    private String contraOri = "";

    private String contraSeleccionada = "";

    private void generarTablaUsuarios() {
        String[] titulos = {
            "ID", "Nombre", "Usuario", "Correo", "Rol", "Estado"
        };

        modeloUsuarios = new DefaultTableModel(null, titulos);
        tabla_usuarios.setModel(modeloUsuarios);
    }
    
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
    int c = col(tabla_usuarios, colName);
    if (c == -1) return "";
    Object v = tabla_usuarios.getModel().getValueAt(modelRow, c);
    return v == null ? "" : v.toString();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_usuario = new javax.swing.JTextField();
        txt_correo = new javax.swing.JTextField();
        txt_contra = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_usuarios = new javax.swing.JTable();
        btn_agregar = new javax.swing.JButton();
        btn_editar = new javax.swing.JButton();
        btn_desactivar = new javax.swing.JButton();
        cb_rol = new javax.swing.JComboBox<>();
        btn_limpiar = new javax.swing.JButton();
        cb_estado = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nombre");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel2.setText("Usuario (rol)");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        jLabel3.setText("Correo");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));

        jLabel4.setText("Contraseña");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        jLabel5.setText("Rol");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, -1, -1));
        add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 150, -1));
        add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 150, -1));
        add(txt_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 150, -1));
        add(txt_contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 150, -1));

        tabla_usuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_usuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_usuarios);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, -1, -1));

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, -1, -1));

        btn_editar.setText("Editar");
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });
        add(btn_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, -1, -1));

        btn_desactivar.setText("Desactivar");
        btn_desactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_desactivarActionPerformed(evt);
            }
        });
        add(btn_desactivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        cb_rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerente", "Admin", "Vendedor" }));
        add(cb_rol, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, -1, -1));

        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });
        add(btn_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, -1, -1));

        cb_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        add(cb_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 90, -1));

        jLabel6.setText("Estado");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        try {
            String nombre = txt_nombre.getText();
            String usuario = txt_usuario.getText();
            String correo = txt_correo.getText();
            String clave = txt_contra.getText();
            String rol = cb_rol.getSelectedItem().toString();
            String estado = cb_estado.getSelectedItem().toString();

            String sql = "INSERT INTO usuarios "
                    + "(nom_usu, user_usu, correo_usu, pass_usu, rol_usu, estado_usu) VALUES "
                    + "('" + nombre + "', '" + usuario + "', '" + correo + "', '" + clave + "', "
                    + "'" + rol + "', '" + estado + "');";

            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String error = cnx.ejecutar(sql);

            if (error.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuario agregado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void tabla_usuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_usuariosMouseClicked
        int viewRow = tabla_usuarios.getSelectedRow();
    if (viewRow < 0) return;

    // Por si más adelante habilitas ordenar/filtrar
    int modelRow = tabla_usuarios.convertRowIndexToModel(viewRow);

    // Leer desde el modelo por NOMBRE de columna (sin hardcodear índices)
    String idStr   = valAtModel(modelRow, "ID");
    nombreOri      = valAtModel(modelRow, "Nombre");
    usuarioOri     = valAtModel(modelRow, "Usuario");
    correoOri      = valAtModel(modelRow, "Correo");
    rolOri         = valAtModel(modelRow, "Rol");
    estadoOri      = valAtModel(modelRow, "Estado");

    if (idStr.isBlank()) return;
    idSeleccionado = Integer.parseInt(idStr);

    // Cargar UI
    txt_nombre.setText(nombreOri);
    txt_usuario.setText(usuarioOri);
    txt_correo.setText(correoOri);
    cb_rol.setSelectedItem(rolOri);
    cb_estado.setSelectedItem(estadoOri);

    // Cargar contraseña desde BD
    try {
        Clases.cls_conexion cnx = new Clases.cls_conexion();
        String sql = "SELECT pass_usu FROM usuarios WHERE id_usu = " + idSeleccionado + ";";
        java.sql.ResultSet rs = cnx.Consulta(sql);

        if (rs != null && rs.next()) {
            contraOri = rs.getString("pass_usu");
            txt_contra.setText(contraOri);
        } else {
            contraOri = "";
            txt_contra.setText("");
        }

        if (rs != null) rs.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            e.getMessage(),
            "Error al cargar contraseña",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_tabla_usuariosMouseClicked

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        try {
          
            if (idSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario primero.");
                return;
            }

            
            String nombre = txt_nombre.getText().trim();
            String usuario = txt_usuario.getText().trim();
            String correo = txt_correo.getText().trim();
            String rol = cb_rol.getSelectedItem().toString();
            String estado = cb_estado.getSelectedItem().toString();
            String clave = txt_contra.getText().trim();


            boolean sinCambios
                    = nombre.equals(nombreOri)
                    && usuario.equals(usuarioOri)
                    && correo.equals(correoOri)
                    && rol.equals(rolOri)
                    && estado.equals(estadoOri)
                    && clave.equals(contraOri);

            if (sinCambios) {
                JOptionPane.showMessageDialog(this, "No hubo cambios.");
                return;
            }

          
            String sql = "UPDATE usuarios SET "
                    + "nom_usu = '" + nombre + "', "
                    + "user_usu = '" + usuario + "', "
                    + "correo_usu = '" + correo + "', "
                    + "rol_usu = '" + rol + "', "
                    + "estado_usu = '" + estado + "', "
                    + "pass_usu = '" + clave + "' "
                    + "WHERE id_usu = " + idSeleccionado + ";";

            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String error = cnx.ejecutar(sql);

            if (error.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuario editado correctamente.");
                cargarUsuarios();   

               
                nombreOri = nombre;
                usuarioOri = usuario;
                correoOri = correo;
                rolOri = rol;
                estadoOri = estado;
                contraOri = clave;

            } else {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_desactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_desactivarActionPerformed
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return;
        }

        String sql = "UPDATE usuarios SET estado_usu = 'INACTIVO' WHERE id_usu = " + idSeleccionado;

        Clases.cls_conexion cnx = new Clases.cls_conexion();
        String error = cnx.ejecutar(sql);

        if (error.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario desactivado.");
            cargarUsuarios();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_desactivarActionPerformed

    private void cargarUsuarios() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();

            String sql
                    = "SELECT id_usu AS ID, nom_usu AS Nombre, user_usu AS Usuario, "
                    + "correo_usu AS Correo, rol_usu AS Rol, estado_usu AS Estado "
                    + "FROM usuarios;";

            cnx.CargarTabla(sql, tabla_usuarios);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txt_nombre.setText("");
        txt_usuario.setText("");
        txt_correo.setText("");
        txt_contra.setText("");
        cb_rol.setSelectedIndex(0);
        cb_estado.setSelectedIndex(0);

        idSeleccionado = -1;
        nombreOri = usuarioOri = correoOri = rolOri = estadoOri = contraOri = "";

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_desactivar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JComboBox<String> cb_estado;
    private javax.swing.JComboBox<String> cb_rol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_usuarios;
    private javax.swing.JTextField txt_contra;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
