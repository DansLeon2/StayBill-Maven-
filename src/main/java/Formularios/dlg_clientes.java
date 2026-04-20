package Formularios;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class dlg_clientes extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dlg_clientes.class.getName());

    private DefaultTableModel model;
    private String cedulaSeleccionada = null;

    public dlg_clientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public dlg_clientes(java.awt.Window owner) {
        super(owner, "Buscar Cliente", ModalityType.APPLICATION_MODAL);
        initComponents();
        configurarTabla();
        cargarClientes(null);
        setLocationRelativeTo(owner);

        SwingUtilities.updateComponentTreeUI(this); // ✅ fuerza estilos del Look&Feel actual
    }

    private void configurarTabla() {
        model = new DefaultTableModel(new Object[]{
            "Cédula", "Nombre", "Dirección", "Teléfono", "Email"
        }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabla_clientes.setModel(model);
        tabla_clientes.setRowHeight(25);
        tabla_clientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla_clientes.setAutoCreateRowSorter(true); // ordenar por columnas

    }

    public String getCedulaSeleccionada() {
        return cedulaSeleccionada;
    }

    private void initUI() {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        setContentPane(root);

        JPanel top = new JPanel();
        JLabel lbl = new JLabel("Buscar por cédula:");
        JTextField txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");
        top.add(lbl);
        top.add(txtBuscar);
        top.add(btnBuscar);
        top.add(btnLimpiar);

        model = new DefaultTableModel(new Object[]{
            "Cédula", "Nombre", "Dirección", "Teléfono", "Email"
        }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable tabla = new JTable(model);
        JScrollPane sp = new JScrollPane(tabla);

        JPanel bottom = new JPanel();
        JButton btnSeleccionar = new JButton("Seleccionar");
        JButton btnCerrar = new JButton("Cerrar");
        bottom.add(btnSeleccionar);
        bottom.add(btnCerrar);

        root.add(top);
        root.add(sp);
        root.add(bottom);

        // Eventos
        btnBuscar.addActionListener(e -> cargarClientes(txtBuscar.getText().trim()));
        btnLimpiar.addActionListener(e -> {
            txtBuscar.setText("");
            cargarClientes(null);
        });

        btnCerrar.addActionListener(e -> dispose());

        btnSeleccionar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
                return;
            }
            cedulaSeleccionada = model.getValueAt(fila, 0).toString();
            dispose();
        });

        // Doble click para seleccionar
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila != -1) {
                        cedulaSeleccionada = model.getValueAt(fila, 0).toString();
                        dispose();
                    }
                }
            }
        });
    }

    private void cargarClientes(String cedula) {
        try {
            model.setRowCount(0);

            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "SELECT ced_cli, nom_cli, dir_cli, tel_cli, email_cli FROM clientes ";

            if (cedula != null && !cedula.isBlank()) {
                sql += "WHERE ced_cli LIKE '%" + cedula + "%' ";
            }

            sql += "ORDER BY nom_cli;";

            java.sql.ResultSet rs = cnx.Consulta(sql);
            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ced_cli"),
                    rs.getString("nom_cli"),
                    rs.getString("dir_cli"),
                    rs.getString("tel_cli"),
                    rs.getString("email_cli")
                });
            }
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando clientes: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_buscar = new javax.swing.JButton();
        txt_buscarCedula = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_clientes = new javax.swing.JTable();
        btn_seleccionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));
        getContentPane().add(txt_buscarCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        jLabel1.setText("Buscar cliente");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        btn_seleccionar.setText("Seleccionar");
        btn_seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_seleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        cargarClientes(txt_buscarCedula.getText().trim());
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarActionPerformed
        int fila = tabla_clientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        // OJO: si hay sorter, usa convertRowIndexToModel
        int modelRow = tabla_clientes.convertRowIndexToModel(fila);
        cedulaSeleccionada = model.getValueAt(modelRow, 0).toString();
        dispose();

    }//GEN-LAST:event_btn_seleccionarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlg_clientes dialog = new dlg_clientes(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_seleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_clientes;
    private javax.swing.JTextField txt_buscarCedula;
    // End of variables declaration//GEN-END:variables
}
