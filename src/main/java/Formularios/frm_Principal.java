package Formularios;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class frm_Principal extends javax.swing.JFrame {

    // --- PALETA ---
    private static final Color COLOR_FONDO     = new Color(0x231F20);
    private static final Color COLOR_VINO      = new Color(0x700238);
    private static final Color COLOR_BLANCO    = new Color(0xECEBF3);
    private static final Color COLOR_GRIS_CARD = new Color(0x2d2d2d);


    // Navegación
    private java.awt.CardLayout cardLayout;

    // Pantallas (se crean 1 sola vez)
    private pnl_habitacion screenHabitaciones;
    private pnl_Inventario screenInventario;
    private pnl_Clientes screenClientes;
    private pnl_Usuarios screenUsuarios;
    private pnl_Reportes screenReportes;
    
    
   

    // Usuario logueado
    private Clases.Usuarios usuarioActual;

    // --- 2. CONSTRUCTORES ---
    public frm_Principal() {
        initComponents();
        configuracionInicial();
        // Por defecto, sin usuario: muestra habitaciones igual (o puedes mostrar reportes)
        showScreen("HABITACIONES");
    }

    public frm_Principal(Clases.Usuarios u) {
        initComponents();
        this.usuarioActual = u;
        configuracionInicial();

        if (usuarioActual != null) {
            lbl_usuario.setText(usuarioActual.getNom_usu());
            lbl_rol.setText(usuarioActual.getRol_usu());
            aplicarPermisosPorRol(usuarioActual.getRol_usu());
        }

        // Pantalla por defecto: Habitaciones
        showScreen("HABITACIONES");
    }

    // --- 3. CONFIGURACIÓN MAESTRA ---
    private void configuracionInicial() {
        this.setExtendedState(MAXIMIZED_BOTH);

        // Logos
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/Images/Logo_MLR.jpg"));
            Image img = icono.getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_SMOOTH);
            lbl_logo.setIcon(new ImageIcon(img));

            ImageIcon userIcon = new ImageIcon(getClass().getResource("/Images/user.png"));
            Image userImg = userIcon.getImage().getScaledInstance(lbl_user.getWidth(), lbl_user.getHeight(), Image.SCALE_SMOOTH);
            lbl_user.setIcon(new ImageIcon(userImg));
        } catch (Exception e) {
            System.err.println("Advertencia: Imágenes de logo/usuario no encontradas.");
        }

        styleSidebarButtons();
        configurarContenidoConCards();
    }

    // --- 4. DISEÑO DEL MENÚ LATERAL ---
    private void styleSidebarButtons() {

    JButton[] sidebarButtons = {
        btn_reportes,
        btn_inventario,
        btn_clientes,
        btn_habitaciones,
        btn_usuarios
    };

    // Si NO tienes los SVG listos, comenta todo este try para que no aparezcan cuadros raros
    try {
        btn_reportes.setIcon(new FlatSVGIcon("Images/icons/report.svg", 24, 24));
        btn_inventario.setIcon(new FlatSVGIcon("Images/icons/box.svg", 24, 24));
        btn_clientes.setIcon(new FlatSVGIcon("Images/icons/users.svg", 24, 24));
        btn_habitaciones.setIcon(new FlatSVGIcon("Images/icons/bed.svg", 24, 24));
        btn_usuarios.setIcon(new FlatSVGIcon("Images/icons/key.svg", 24, 24));
    } catch (Exception ignore) { /* sin iconos */ }

    // Textos correctos
    btn_habitaciones.setText("Habitaciones");
    btn_usuarios.setText("Usuarios");

    for (JButton btn : sidebarButtons) {
        // 👇 Opción B: botón visible (NO toolbar)
        btn.putClientProperty("JButton.buttonType", "roundRect");

        // Colores
        btn.setBackground(new Color(0x2d2d2d));   // gris card
        btn.setForeground(COLOR_BLANCO);

        // Tipografía / alineación
        btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);

        // Tamaño cómodo (evita botones flacos)
        btn.setPreferredSize(new java.awt.Dimension(140, 54));

        // Padding interno (FlatLaf respeta esto en muchos LAF)
        btn.setMargin(new java.awt.Insets(12, 16, 12, 16));

        // Interacción
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
    }

    // Botón seleccionado por defecto (Habitaciones)
    marcarActivo(btn_habitaciones);

    // Cerrar sesión (destacado)
    btn_cerrar_sesion.putClientProperty("JButton.buttonType", "roundRect");
    btn_cerrar_sesion.setBackground(COLOR_VINO);
    btn_cerrar_sesion.setForeground(Color.WHITE);
    btn_cerrar_sesion.setFocusPainted(false);

    // Config admin oculto por defecto
    btn_config.setVisible(false);
}

    
    private void configurarContenidoConCards() {
        pnl_contenido.setLayout(cardLayout = new java.awt.CardLayout());

        // crear pantallas una sola vez
        screenHabitaciones = new pnl_habitacion();  // ESTA será tu pantalla de habitaciones (grid + detalle)
        screenInventario   = new pnl_Inventario();
        screenClientes     = new pnl_Clientes();
        screenUsuarios     = new pnl_Usuarios();
        screenReportes     = new pnl_Reportes();

        pnl_contenido.add(screenHabitaciones, "HABITACIONES");
        pnl_contenido.add(screenInventario,   "INVENTARIO");
        pnl_contenido.add(screenClientes,     "CLIENTES");
        pnl_contenido.add(screenUsuarios,     "USUARIOS");
        pnl_contenido.add(screenReportes,     "REPORTES");
    }
    
    private void showScreen(String key) {
        cardLayout.show(pnl_contenido, key);
        pnl_contenido.revalidate();
        pnl_contenido.repaint();
    }
    
    private void marcarActivo(JButton activo) {
    JButton[] sidebarButtons = {
        btn_reportes,
        btn_inventario,
        btn_clientes,
        btn_habitaciones,
        btn_usuarios
    };

    for (JButton b : sidebarButtons) {
        b.setBackground(new Color(0x2d2d2d));  // normal
        b.setBorder(null);
    }

    // Activo: vino y borde suave
    activo.setBackground(COLOR_VINO);
    activo.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0x9c2b5b), 2, true));
}


    // --- 5. LÓGICA DE PERMISOS (Corregida) ---
    private void aplicarPermisosPorRol(String rol) {
    String r = rol == null ? "" : rol.trim().toLowerCase();

    // 1) Ocultar todo primero (más seguro)
    btn_reportes.setVisible(false);
    btn_inventario.setVisible(false);
    btn_clientes.setVisible(false);
    btn_habitaciones.setVisible(false);
    btn_usuarios.setVisible(false);
    btn_config.setVisible(false);

    // 2) Mostrar según rol
    switch (r) {
        case "admin" -> {
            btn_reportes.setVisible(true);
            btn_inventario.setVisible(true);
            btn_clientes.setVisible(true);
            btn_habitaciones.setVisible(true);
            btn_usuarios.setVisible(true);
            btn_config.setVisible(false); // si lo quieres solo admin
        }
        case "gerente" -> {
            btn_reportes.setVisible(true);
            btn_inventario.setVisible(true);
            btn_clientes.setVisible(true);
            btn_habitaciones.setVisible(true);
            // gerente NO ve usuarios
        }
        case "vendedor" -> {
            btn_inventario.setVisible(true);
            btn_habitaciones.setVisible(true);
        }
        default -> {
            // Por seguridad, mínimo habitaciones
            btn_habitaciones.setVisible(true);
        }
    }

    // 3) Si el botón activo quedó oculto, mandamos a una pantalla válida
    if (!btn_habitaciones.isVisible()) {
        // si por algún motivo no existe, busca el primero visible
        if (btn_inventario.isVisible()) {
            marcarActivo(btn_inventario);
            showScreen("INVENTARIO");
        } else if (btn_reportes.isVisible()) {
            marcarActivo(btn_reportes);
            showScreen("REPORTES");
        } else if (btn_clientes.isVisible()) {
            marcarActivo(btn_clientes);
            showScreen("CLIENTES");
        } else if (btn_usuarios.isVisible()) {
            marcarActivo(btn_usuarios);
            showScreen("USUARIOS");
        }
        return;
    }

    // Por defecto siempre cae a Habitaciones si está permitido
    marcarActivo(btn_habitaciones);
    showScreen("HABITACIONES");
}


    // --- 6. CARGA DINÁMICA DE HABITACIONES (El motor principal) ---
    
    
    private javax.swing.JButton crearCardHabitacion(int id, String cod, String estado, String tipo, double precio) {
    String est = estado == null ? "" : estado.trim().toUpperCase();

    javax.swing.JButton card = new javax.swing.JButton();
    card.putClientProperty("JButton.buttonType", "roundRect");
    card.setHorizontalAlignment(SwingConstants.LEFT);
    card.setIconTextGap(15);
    card.setCursor(new Cursor(Cursor.HAND_CURSOR));

    boolean libre = est.equals("LIBRE");
    String colorEstado = libre ? "#00FF88" : "#FF5555";

    // Icono (Maven: debe estar en src/main/resources/Images/icons/)
    try {
        String iconName = libre ? "bed_free.svg" : "bed_occupied.svg";
        card.setIcon(new FlatSVGIcon("Images/icons/" + iconName, 32, 32));
    } catch (Exception ignore) {}

    card.setBackground(libre ? COLOR_GRIS_CARD : COLOR_VINO);
    card.setForeground(COLOR_BLANCO);

    card.setText("<html><body style='width: 150px; padding: 6px;'>"
            + "<b style='color: white; font-size: 13px;'>" + cod + " - " + (libre ? "Disponible" : "Ocupada") + "</b><br>"
            + "<span style='color: #aaaaaa; font-size: 11px;'>Tipo: " + tipo + "</span><br>"
            + "<span style='color: #aaaaaa; font-size: 11px;'>$ " + String.format("%.2f", precio) + "</span><br><br>"
            + "<b style='color: " + colorEstado + "; font-size: 11px;'>" + est + "</b>"
            + "</body></html>");

    card.addActionListener(e -> abrirDetalleHabitacion(id));
    return card;
}

    // --- 7. NAVEGACIÓN ---
    private void abrirDetalleHabitacion(int id) {
        pnl_contenido.removeAll();
        // Carga el panel genérico (Asegúrate de haber creado la clase pnl_DetalleHabitacion)
        pnl_contenido.add(new pnl_habitacion(id), BorderLayout.CENTER);
        pnl_contenido.revalidate();
        pnl_contenido.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_lateral = new javax.swing.JPanel();
        btn_inventario = new javax.swing.JButton();
        btn_reportes = new javax.swing.JButton();
        btn_clientes = new javax.swing.JButton();
        btn_habitaciones = new javax.swing.JButton();
        btn_config = new javax.swing.JButton();
        btn_usuarios = new javax.swing.JButton();
        btn_cerrar_sesion = new javax.swing.JButton();
        pnl_superior = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        lbl_usuario = new javax.swing.JLabel();
        lbl_rol = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        pnl_contenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        pnl_lateral.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_lateral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_inventario.setText("Inventario");
        btn_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inventarioActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 140, 60));

        btn_reportes.setText("Reportes");
        btn_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reportesActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 140, 60));

        btn_clientes.setText("Clientes");
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 140, 60));

        btn_habitaciones.setText("Habitaciones");
        btn_habitaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_habitacionesActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_habitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 140, 60));

        btn_config.setText("jButton1");
        btn_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_config, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 40, -1));

        btn_usuarios.setText("Usuarios");
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_usuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 140, 60));

        btn_cerrar_sesion.setText("Cerrar Sesion");
        btn_cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrar_sesionActionPerformed(evt);
            }
        });
        pnl_lateral.add(btn_cerrar_sesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 640, -1, -1));

        pnl_superior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_logo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_usuario.setText("jLabel1");

        lbl_rol.setText("jLabel2");

        javax.swing.GroupLayout pnl_superiorLayout = new javax.swing.GroupLayout(pnl_superior);
        pnl_superior.setLayout(pnl_superiorLayout);
        pnl_superiorLayout.setHorizontalGroup(
            pnl_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_superiorLayout.createSequentialGroup()
                .addGroup(pnl_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_superiorLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(599, 599, 599)
                        .addComponent(lbl_user, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_superiorLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lbl_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_superiorLayout.setVerticalGroup(
            pnl_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_superiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_user, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        pnl_contenido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_contenido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnl_superior, javax.swing.GroupLayout.PREFERRED_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(pnl_lateral, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_contenido, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_superior, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_lateral, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(pnl_contenido, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reportesActionPerformed
        marcarActivo(btn_reportes);
        showScreen("REPORTES");
    }//GEN-LAST:event_btn_reportesActionPerformed

    private void btn_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inventarioActionPerformed
        marcarActivo(btn_inventario);
        showScreen("INVENTARIO");
    }//GEN-LAST:event_btn_inventarioActionPerformed

    private void btn_habitacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_habitacionesActionPerformed
        marcarActivo(btn_habitaciones);
        showScreen("HABITACIONES");
    }//GEN-LAST:event_btn_habitacionesActionPerformed

    private void btn_cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrar_sesionActionPerformed
        int resp = javax.swing.JOptionPane.showConfirmDialog(
                this, "¿Desea cerrar sesión?", "Salir",
                javax.swing.JOptionPane.YES_NO_OPTION
        );
        if (resp == javax.swing.JOptionPane.YES_OPTION) {
            Clases.Usuarios.usuarioLogueado = null;
            new Formularios.frm_Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btn_cerrar_sesionActionPerformed

    private void btn_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientesActionPerformed
        marcarActivo(btn_clientes);
        showScreen("CLIENTES");
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        marcarActivo(btn_usuarios);
        showScreen("USUARIOS");
    }//GEN-LAST:event_btn_usuariosActionPerformed

    private void btn_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configActionPerformed
        javax.swing.JOptionPane.showMessageDialog(this,
            "Config Admin: aquí luego pondremos 'Crear Habitación'.\n" +
            "Primero terminamos la estructura de pantallas."
        );
    }//GEN-LAST:event_btn_configActionPerformed

    private String nombreUsuario;
    private String rolUsuario;

    public void setUsuarioActual(String nombre, String rol) {
        this.nombreUsuario = nombre;
        this.rolUsuario = rol;

        lbl_usuario.setText(nombre);
        lbl_rol.setText(rol);

        aplicarPermisosPorRol(rol);
    }

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frm_Principal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar_sesion;
    private javax.swing.JButton btn_clientes;
    private javax.swing.JButton btn_config;
    private javax.swing.JButton btn_habitaciones;
    private javax.swing.JButton btn_inventario;
    private javax.swing.JButton btn_reportes;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_rol;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JLabel lbl_usuario;
    private javax.swing.JPanel pnl_contenido;
    private javax.swing.JPanel pnl_lateral;
    private javax.swing.JPanel pnl_superior;
    // End of variables declaration//GEN-END:variables
}
