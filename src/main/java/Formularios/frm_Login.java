package Formularios;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author LENOVO
 */
public class frm_Login extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frm_Login.class.getName());

    public frm_Login() {
        initComponents();
        try {

            java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource("Images/Logo_MLR.jpg");
// Nota que aquí NO lleva el "/" al inicio

            if (imgURL != null) {
                ImageIcon iconoOriginal = new ImageIcon(imgURL);
                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                        lbl_logo.getWidth(),
                        lbl_logo.getHeight(),
                        Image.SCALE_SMOOTH
                );
                lbl_logo.setIcon(new ImageIcon(imagenEscalada));
            } else {
                System.err.println("No se pudo encontrar la imagen en: /Images/Logo_MLR.jpg");

                lbl_logo.setText("Logo no encontrado");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar logo: " + e.getMessage());
        }

        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_fondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        btn_inicarsesion = new javax.swing.JButton();
        lbl_logo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_contra = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("StayBill");
        panel_fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 176, 136));

        txt_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usuarioActionPerformed(evt);
            }
        });
        panel_fondo.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 310, 262, 49));

        btn_inicarsesion.setText("Iniciar Sesion");
        btn_inicarsesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inicarsesionActionPerformed(evt);
            }
        });
        panel_fondo.add(btn_inicarsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 490, 140, 50));

        lbl_logo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel_fondo.add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 500, 460));

        jLabel2.setText("Usuario");
        panel_fondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, -1, -1));

        jLabel3.setText("Contraseña");
        panel_fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 410, -1, -1));
        panel_fondo.add(txt_contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 400, 260, 40));

        getContentPane().add(panel_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 1490, 890));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_inicarsesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inicarsesionActionPerformed
        try {
            String user = txt_usuario.getText().trim();
            String pass = new String(txt_contra.getPassword());

            if (user.length() > 0) {
                if (pass.length() > 0) {

                    Clases.Usuarios obj = new Clases.Usuarios();
                    Clases.cls_conexion cnx = new Clases.cls_conexion();
                    java.sql.ResultSet rs = cnx.Consulta(
                            "SELECT estado_usu FROM usuarios WHERE LOWER(correo_usu)=LOWER('" + user.trim() + "') LIMIT 1;"
                    );

                    if (rs != null && rs.next()) {
                        String estado = rs.getString("estado_usu");
                        rs.close();

                        if (estado != null && estado.trim().equalsIgnoreCase("INACTIVO")) {
                            javax.swing.JOptionPane.showMessageDialog(this, "Este usuario está INACTIVO. No puede ingresar.");
                            return;
                        }
                    } else {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                    Clases.Usuarios u = obj.login(user, pass);

                    if (u != null) {
                        Clases.Usuarios.usuarioLogueado = u;   
                        Formularios.frm_Principal ventana = new Formularios.frm_Principal(u);
                        ventana.setLocationRelativeTo(null);
                        ventana.setVisible(true);
                        this.dispose();

                    } else {
                        javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "Usuario o contraseña incorrectos."
                        );
                    }

                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Por favor ingrese la contraseña."
                    );
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Por favor ingrese el usuario."
                );
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }//GEN-LAST:event_btn_inicarsesionActionPerformed

    private void txt_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usuarioActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frm_Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_inicarsesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JPanel panel_fondo;
    private javax.swing.JPasswordField txt_contra;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
