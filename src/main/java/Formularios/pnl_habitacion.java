package Formularios;

import Clases.Clientes;
import java.awt.Color;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class pnl_habitacion extends javax.swing.JPanel {

    private int idHabitacion;
    private boolean cargandoUI = false;

    private static final Color C_LIBRE = new Color(46, 204, 113);
    private static final Color C_OCUPADA = new Color(231, 76, 60);
    private static final Color C_MANT = new Color(241, 196, 15);
    private static final Color C_DEFAULT = new Color(180, 180, 180);

    private final java.util.Map<Integer, javax.swing.JButton> btnHab = new java.util.HashMap<>();

    private static final int ID_CARGO_HAB = 0;   // reservado (fila virtual)
    private double precioHabActual = 0.0;
    
    // precio real de la habitación seleccionada

    public pnl_habitacion(int id) {
        initComponents();

        bgEstadoHabitacion.add(rbtn_libre);
        bgEstadoHabitacion.add(rbtn_ocupado);
        bgEstadoHabitacion.add(rbtn_mant);

        initListenersEstado();        // ✅ AQUÍ

        initBotonesHabitacion();
        initFacturaYProductos();

        seleccionarHabitacion(id);

        refrescarColoresHabitaciones();
        btn_buscarCliente.setVisible(false);

    }

    private void initListenersEstado() {
        java.awt.event.ItemListener il = e -> {
            if (cargandoUI) {
                return;
            }
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                cambiarEstadoDesdeRadio();
            }
        };

        rbtn_libre.addItemListener(il);
        rbtn_ocupado.addItemListener(il);
        rbtn_mant.addItemListener(il);
    }

    public pnl_habitacion() {
        initComponents();

        this.idHabitacion = 1;

        bgEstadoHabitacion.add(rbtn_libre);
        bgEstadoHabitacion.add(rbtn_ocupado);
        bgEstadoHabitacion.add(rbtn_mant);

        initListenersEstado();        // ✅ AQUÍ

        initBotonesHabitacion();
        initFacturaYProductos();
        seleccionarHabitacion(1);

        refrescarColoresHabitaciones();

    }

    private javax.swing.table.DefaultTableModel modeloVentas;

    private void GenerarTablaVentas() {
        String[] titulos = {"ID Producto", "Nombre Producto", "Precio", "Cantidad", "Total"};
        modeloVentas = new javax.swing.table.DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla_venta.setModel(modeloVentas);
        
        configurarTablaVentaNoSelectableCargo();
    }

    private void toggleDatos(boolean estado) {
        lbl_cedula1.setVisible(estado);
        lbl_nom_cli1.setVisible(estado);
        lbl_direccion1.setVisible(estado);
        lbl_telefono1.setVisible(estado);
        lbl_correo1.setVisible(estado);

        txt_cedu_cliente.setVisible(estado);
        txt_nom_clie.setVisible(estado);
        txt_dir_cliente.setVisible(estado);
        txt_tel_cliente.setVisible(estado);
        txt_email_cliente.setVisible(estado);
        btn_buscarCliente.setVisible(estado);
    }

    private void initBotonesHabitacion() {
        registrar(btn_h1, 1);
        registrar(btn_h2, 2);
        registrar(btn_h3, 3);
        registrar(btn_h4, 4);
        registrar(btn_h5, 5);
        registrar(btn_h6, 6);
        registrar(btn_h7, 7);
        registrar(btn_h8, 8);
        registrar(btn_h9, 9);
        registrar(btn_h10, 10);
        registrar(btn_h11, 11);
        registrar(btn_h12, 12);
    }

    private void pintarBoton(javax.swing.JButton btn, String estado) {
        if (btn == null) {
            return;
        }

        String est = (estado == null) ? "" : estado.trim().toUpperCase();

        Color bg;
        Color fg;

        switch (est) {
            case "LIBRE" -> {
                bg = C_LIBRE;
                fg = Color.WHITE;
            }
            case "OCUPADA", "OCUPADO" -> {
                bg = C_OCUPADA;
                fg = Color.WHITE;
            }
            case "MANTENIMIENTO" -> {
                bg = C_MANT;
                fg = Color.BLACK;
            }
            default -> {
                bg = C_DEFAULT;
                fg = Color.BLACK;
            }
        }

        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.repaint();
    }

    private void refrescarColoresHabitaciones() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "SELECT id_hab, est_hab FROM habitaciones";
            java.sql.ResultSet rs = cnx.Consulta(sql);

            while (rs != null && rs.next()) {
                int id = rs.getInt("id_hab");
                String est = rs.getString("est_hab");
                pintarBoton(btnHab.get(id), est);
            }

            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error refrescando colores: " + e.getMessage());
        }
    }

    private void cambiarEstado(String nuevoEstado) {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "UPDATE habitaciones SET est_hab = '" + nuevoEstado + "' WHERE id_hab = " + idHabitacion;
            cnx.ejecutar(sql);

            // ✅ tiempo real (UI)
            lbl_estado.setText(nuevoEstado);

            pintarBoton(btnHab.get(idHabitacion), nuevoEstado);

            // ✅ si quieres que también se actualicen TODOS por si hay cambios externos:
            // refrescarColoresHabitaciones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void registrar(javax.swing.JButton btn, int id) {
        btnHab.put(id, btn);

        // Para que el color SI se vea (especialmente con Nimbus)
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        btn.addActionListener(e -> seleccionarHabitacion(id));
    }

    private void initFacturaYProductos() {
        GenerarTablaVentas();
        CargarTablaProductos();
        cargarSiguienteNumeroFactura();
        cargarTiposProducto();

        if (cb_fecha != null) {
            cb_fecha.setDate(new java.util.Date());
        }

        // consumidor final por defecto
        btn_cambiar_modo.setSelected(false);
        btn_cambiar_modo.setText("Consumidor Final");
        toggleDatos(false);

        txt_subtotal.setText("0.00");
        txt_descu.setText("0.00");
        txt_iva.setText("0.00");
        txt_total.setText("0.00");

        // si cambian descuento, recalcula
        cb_descu.addActionListener(e -> mtd_calculo());
    }

    private void cargarDatosHabitacion() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "SELECT cod_hab, est_hab, tip_hab, pre_hab FROM habitaciones WHERE id_hab = " + idHabitacion;
            java.sql.ResultSet rs = cnx.Consulta(sql);

            if (rs != null && rs.next()) {
                String cod = rs.getString("cod_hab");
                String estado = rs.getString("est_hab");
                String tipo = rs.getString("tip_hab");
                double precio = rs.getDouble("pre_hab");
                
                precioHabActual = precio;   // ✅ guardar precio para usarlo en la tabla de venta

                // ✅ Estos son los que deben cambiar (valores)
                lbl_numero.setText(cod);                       // ej: H1
                lbl_estado.setText(estado);                    // LIBRE / OCUPADA / MANTENIMIENTO
                lbl_tipo.setText(tipo);                        // Simple / Doble / etc
                lbl_precio.setText(String.format("$ %.2f", precio));

                // ✅ sincroniza radio buttons
                setEstadoRadio(estado);
            }

            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando detalles: " + e.getMessage());
        }
    }

    private void asegurarCargoHabitacionEnVenta() {
        if (modeloVentas == null) {
            return;
        }

        // 1) Elimina si ya existe (para no duplicar)
        for (int i = modeloVentas.getRowCount() - 1; i >= 0; i--) {
            Object v = modeloVentas.getValueAt(i, 0);
            if (v != null && Integer.parseInt(v.toString()) == ID_CARGO_HAB) {
                modeloVentas.removeRow(i);
            }
        }

        // 2) Inserta como primera fila (se ve como producto)
        String cod = lbl_numero.getText();          // H1, H2...
        String tipo = lbl_tipo.getText();           // Sencilla, Doble...
        String nombre = "CARGO HABITACIÓN " + cod + " (" + tipo + ")";

        double precio = precioHabActual;            // valor real de la habitación
        int cantidad = 1;
        double total = precio * cantidad;

        modeloVentas.insertRow(0, new Object[]{
            ID_CARGO_HAB, nombre, precio, cantidad, total
        });

        mtd_calculo(); // recalcula subtotal/iva/total
    }

    private void setEstadoRadio(String estado) {
        cargandoUI = true;
        try {
            String est = (estado == null) ? "" : estado.trim().toUpperCase();
            switch (est) {
                case "LIBRE" ->
                    rbtn_libre.setSelected(true);
                case "OCUPADO", "OCUPADA" ->
                    rbtn_ocupado.setSelected(true);
                case "MANTENIMIENTO" ->
                    rbtn_mant.setSelected(true);
                default ->
                    bgEstadoHabitacion.clearSelection();
            }
        } finally {
            cargandoUI = false;
        }
    }

    private void cambiarEstadoDesdeRadio() {
        if (cargandoUI) {
            return;
        }

        String nuevoEstado = null;

        if (rbtn_libre.isSelected()) {
            nuevoEstado = "LIBRE";
        } else if (rbtn_ocupado.isSelected()) {
            nuevoEstado = "OCUPADO";   // ✅ usa OCUPADO (recomendado)
        } else if (rbtn_mant.isSelected()) {
            nuevoEstado = "MANTENIMIENTO";
        }

        if (nuevoEstado == null) {
            return;
        }

        cambiarEstado(nuevoEstado);   // ✅ usa tu método cambiarEs
    }

    private void mapHab(javax.swing.JButton btn, int id) {
        btn.addActionListener(e -> seleccionarHabitacion(id));
    }

    private void limpiarFacturaParaNuevaHabitacion() {
        modeloVentas.setRowCount(0);

        txt_cedu_cliente.setText("");
        txt_nom_clie.setText("");
        txt_dir_cliente.setText("");
        txt_tel_cliente.setText("");
        txt_email_cliente.setText("");

        txt_cantidad.setText("");
        txt_buscarProd.setText("");
        cb_descu.setSelectedIndex(0);

        txt_subtotal.setText("0.00");
        txt_descu.setText("0.00");
        txt_iva.setText("0.00");
        txt_total.setText("0.00");

        btn_cambiar_modo.setSelected(false);
        btn_cambiar_modo.setText("Consumidor Final");
        toggleDatos(false);
    }

    private void seleccionarHabitacion(int id) {
        this.idHabitacion = id;

        cargarDatosHabitacion();

        limpiarFacturaParaNuevaHabitacion();

        asegurarCargoHabitacionEnVenta();   // ✅ AQUÍ (siempre)

        cargarSiguienteNumeroFactura();
        CargarTablaProductos();
    }

    private void CargarTablaProductos() {
        CargarTablaProductos("Todos", null);
    }

    private void CargarTablaProductos(String tipo, String texto) {
        filtrarProductosPorTipo(tipo, texto);
    }

    private void cargarTiposProducto() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            java.sql.ResultSet rs = cnx.Consulta("SELECT DISTINCT tipo_pro FROM productos ORDER BY tipo_pro;");

            cb_filtro.removeAllItems();
            cb_filtro.addItem("Todos");
            while (rs != null && rs.next()) {
                cb_filtro.addItem(rs.getString("tipo_pro"));
            }
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tipos: " + e.getMessage());
        }
    }

    private void filtrarProductosPorTipo(String tipo, String texto) {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            StringBuilder sql = new StringBuilder(
                    "SELECT id_pro AS ID, nom_pro AS Nombre, pre_pro AS Precio, can_pro AS Cantidad, desc_pro AS Descripcion, tipo_pro AS Tipo "
                    + "FROM productos "
            );

            boolean where = false;

            if (tipo != null && !"Todos".equalsIgnoreCase(tipo)) {
                sql.append("WHERE tipo_pro='").append(tipo).append("' ");
                where = true;
            }

            if (texto != null && !texto.trim().isEmpty()) {
                sql.append(where ? "AND " : "WHERE ");
                sql.append("nom_pro LIKE '%").append(texto.trim()).append("%' ");
            }

            sql.append("ORDER BY nom_pro;");
            cnx.CargarTabla(sql.toString(), tabla_productos);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error filtrando productos: " + e.getMessage());
        }
    }

    private int getCantidadYaAgregada(int idProd) {
        int suma = 0;
        for (int i = 0; i < tabla_venta.getRowCount(); i++) {
            int id = Integer.parseInt(tabla_venta.getValueAt(i, 0).toString());
            if (id == idProd) {
                suma += Integer.parseInt(tabla_venta.getValueAt(i, 3).toString());
            }
        }
        return suma;
    }

    private void mtd_calculo() {
        double subtotal = 0.0;
        for (int i = 0; i < tabla_venta.getRowCount(); i++) {
            subtotal += Double.parseDouble(tabla_venta.getValueAt(i, 4).toString());
        }
        txt_subtotal.setText(String.format(java.util.Locale.US, "%.2f", subtotal));

        double porcDesc = Double.parseDouble(cb_descu.getSelectedItem().toString());
        double descuento = (porcDesc * subtotal) / 100.0;
        txt_descu.setText(String.format(java.util.Locale.US, "%.2f", descuento));

        double base = subtotal - descuento;
        double iva = base * 0.14;

        txt_iva.setText(String.format(java.util.Locale.US, "%.2f", iva));
        txt_total.setText(String.format(java.util.Locale.US, "%.2f", base + iva));
    }

    private void cargarSiguienteNumeroFactura() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            java.sql.ResultSet rs = cnx.Consulta("SELECT IFNULL(MAX(num_fac), 0) + 1 AS siguiente FROM cabecera_factura;");
            if (rs != null && rs.next()) {
                txt_num_factu.setText(rs.getString("siguiente"));
            } else {
                txt_num_factu.setText("1");
            }
            if (rs != null) {
                rs.close();
            }
            txt_num_factu.setEditable(false);
        } catch (Exception e) {
            txt_num_factu.setText("1");
        }
    }

    private String nullIfBlank(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
    
    private boolean esFilaCargoHabitacion(int row) {
    if (row < 0) return false;
    try {
        Object v = tabla_venta.getValueAt(row, 0); // col 0 = ID Producto
        if (v == null) return false;
        return Integer.parseInt(v.toString()) == ID_CARGO_HAB;
    } catch (Exception e) {
        return false;
    }
}
    
    private void configurarTablaVentaNoSelectableCargo() {

    // 1) Extra: bloquear edición aunque algo cambie
    tabla_venta.setDefaultEditor(Object.class, null);

    // 2) Selección normal, pero prohibimos seleccionar la fila cargo
    tabla_venta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla_venta.setColumnSelectionAllowed(false);

    // ⚠️ Reemplazamos el SelectionModel para bloquear selección del cargo
    tabla_venta.setSelectionModel(new DefaultListSelectionModel() {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            if (esFilaCargoHabitacion(index0) || esFilaCargoHabitacion(index1)) {
                return; // ❌ no permitir seleccionar
            }
            super.setSelectionInterval(index0, index1);
        }

        @Override
        public void addSelectionInterval(int index0, int index1) {
            if (esFilaCargoHabitacion(index0) || esFilaCargoHabitacion(index1)) {
                return; // ❌ no permitir seleccionar
            }
            super.addSelectionInterval(index0, index1);
        }
    });

    // 3) Por si acaso, si el mouse intenta seleccionar, lo limpiamos
    tabla_venta.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            int row = tabla_venta.rowAtPoint(e.getPoint());
            if (esFilaCargoHabitacion(row)) {
                tabla_venta.clearSelection();
            }
        }
    });
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgEstadoHabitacion = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        pnl_habitaciones = new javax.swing.JPanel();
        btn_h10 = new javax.swing.JButton();
        btn_h11 = new javax.swing.JButton();
        btn_h1 = new javax.swing.JButton();
        btn_h12 = new javax.swing.JButton();
        btn_h2 = new javax.swing.JButton();
        btn_h3 = new javax.swing.JButton();
        btn_h4 = new javax.swing.JButton();
        btn_h5 = new javax.swing.JButton();
        btn_h6 = new javax.swing.JButton();
        btn_h7 = new javax.swing.JButton();
        btn_h8 = new javax.swing.JButton();
        btn_h9 = new javax.swing.JButton();
        pnl_factura = new javax.swing.JPanel();
        txt_email_cliente = new javax.swing.JTextField();
        lbl_direccion1 = new javax.swing.JLabel();
        lbl_telefono1 = new javax.swing.JLabel();
        btn_guardar = new javax.swing.JButton();
        lbl_correo1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        txt_iva = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        txt_descu = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_eliminar = new javax.swing.JButton();
        btn_cancelar_fac = new javax.swing.JButton();
        txt_cedu_cliente = new javax.swing.JTextField();
        lbl_cedula1 = new javax.swing.JLabel();
        lbl_nom_cli1 = new javax.swing.JLabel();
        txt_nom_clie = new javax.swing.JTextField();
        txt_dir_cliente = new javax.swing.JTextField();
        txt_tel_cliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_venta = new javax.swing.JTable();
        txt_num_factu = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cb_fecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel20 = new javax.swing.JLabel();
        cb_descu = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        btn_buscarCliente = new javax.swing.JButton();
        pnl_datosHabitacion = new javax.swing.JPanel();
        lbl_hab_selec = new javax.swing.JLabel();
        lbl_numero = new javax.swing.JLabel();
        label100 = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbl_tipo = new javax.swing.JLabel();
        label99 = new javax.swing.JLabel();
        lbl_precio = new javax.swing.JLabel();
        btn_cambiar_modo = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        pnl_productos = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cb_filtro = new javax.swing.JComboBox<>();
        txt_buscarProd = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla_productos = new javax.swing.JTable();
        pnl_estado = new javax.swing.JPanel();
        rbtn_libre = new javax.swing.JRadioButton();
        rbtn_ocupado = new javax.swing.JRadioButton();
        rbtn_mant = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_habitaciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_habitaciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_h10.setText("H10");
        btn_h10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h10ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        btn_h11.setText("H11");
        btn_h11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h11ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h11, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, -1, -1));

        btn_h1.setText("H1");
        btn_h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h1ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        btn_h12.setText("H12");
        btn_h12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h12ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, -1, -1));

        btn_h2.setText("H2");
        btn_h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h2ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

        btn_h3.setText("H3");
        btn_h3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h3ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        btn_h4.setText("H4");
        btn_h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h4ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        btn_h5.setText("H5");
        btn_h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h5ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        btn_h6.setText("H6");
        btn_h6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h6ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        btn_h7.setText("H7");
        btn_h7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h7ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));

        btn_h8.setText("H8");
        btn_h8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h8ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, -1));

        btn_h9.setText("H9");
        btn_h9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_h9ActionPerformed(evt);
            }
        });
        pnl_habitaciones.add(btn_h9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

        jPanel1.add(pnl_habitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 880, 110));

        jScrollPane1.setViewportView(jPanel1);

        pnl_factura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_factura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnl_factura.add(txt_email_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 110, -1));

        lbl_direccion1.setText("Direccion");
        pnl_factura.add(lbl_direccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lbl_telefono1.setText("Telefono");
        pnl_factura.add(lbl_telefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, -1, -1));

        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        pnl_factura.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 450, -1, -1));

        lbl_correo1.setText("Correo");
        pnl_factura.add(lbl_correo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));

        jLabel13.setText("Subtotal:");
        pnl_factura.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        jLabel14.setText("IVA:");
        pnl_factura.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, -1, -1));

        jLabel15.setText("Total:");
        pnl_factura.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, -1, -1));

        txt_subtotal.setEditable(false);
        pnl_factura.add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 105, -1));

        txt_iva.setEditable(false);
        pnl_factura.add(txt_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 105, -1));

        txt_total.setEditable(false);
        pnl_factura.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 105, -1));

        txt_descu.setEditable(false);
        pnl_factura.add(txt_descu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 106, -1));

        jLabel16.setText("Descuento:");
        pnl_factura.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, -1, -1));

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        pnl_factura.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, -1, -1));

        btn_cancelar_fac.setText("Canclear Factura");
        btn_cancelar_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_facActionPerformed(evt);
            }
        });
        pnl_factura.add(btn_cancelar_fac, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, -1, -1));
        pnl_factura.add(txt_cedu_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 110, -1));

        lbl_cedula1.setText("Cedula Cliente");
        pnl_factura.add(lbl_cedula1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lbl_nom_cli1.setText("Nombre Cliente");
        pnl_factura.add(lbl_nom_cli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));
        pnl_factura.add(txt_nom_clie, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 110, -1));
        pnl_factura.add(txt_dir_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 110, -1));
        pnl_factura.add(txt_tel_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 110, -1));

        tabla_venta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tabla_venta);

        pnl_factura.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 137));

        txt_num_factu.setEditable(false);
        pnl_factura.add(txt_num_factu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 105, -1));

        jLabel19.setText("Fecha");
        pnl_factura.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, -1, 20));
        pnl_factura.add(cb_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 110, 20));

        jLabel20.setText("Descuento:");
        pnl_factura.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        cb_descu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2", "5" }));
        pnl_factura.add(cb_descu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, -1, -1));

        jLabel21.setText("Numero Factura");
        pnl_factura.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        btn_buscarCliente.setText("Buscar Cliente");
        btn_buscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarClienteActionPerformed(evt);
            }
        });
        pnl_factura.add(btn_buscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        pnl_datosHabitacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_datosHabitacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_hab_selec.setText("Habitacion Seleccionada:");
        pnl_datosHabitacion.add(lbl_hab_selec, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, -1));

        lbl_numero.setText("(hab #)");
        pnl_datosHabitacion.add(lbl_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        label100.setText("Estado:");
        pnl_datosHabitacion.add(label100, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        lbl_estado.setText("(estado actual)");
        pnl_datosHabitacion.add(lbl_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        jLabel26.setText("Tipo:");
        pnl_datosHabitacion.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, -1));

        lbl_tipo.setText("(tipo hab)");
        pnl_datosHabitacion.add(lbl_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

        label99.setText("Precio fijo:");
        pnl_datosHabitacion.add(label99, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        lbl_precio.setText("(tipo hab)");
        pnl_datosHabitacion.add(lbl_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, -1, -1));

        btn_cambiar_modo.setText("Consumidor Final");
        btn_cambiar_modo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiar_modoActionPerformed(evt);
            }
        });
        pnl_datosHabitacion.add(btn_cambiar_modo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jLabel3.setText("Factura:");
        pnl_datosHabitacion.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, -1, -1));

        pnl_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_productos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setText("Cantidad:");
        pnl_productos.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 56, -1));
        pnl_productos.add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 93, -1));

        jLabel18.setText("Buscar Tipo");
        pnl_productos.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        cb_filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "BEBIDA", "SNACK", "HIGIENE", "COMIDA", "SERVICIO" }));
        pnl_productos.add(cb_filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));
        pnl_productos.add(txt_buscarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 87, -1));

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        pnl_productos.add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        pnl_productos.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, -1, -1));

        tabla_productos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tabla_productos);

        pnl_productos.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 422, 380));

        pnl_estado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_estado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgEstadoHabitacion.add(rbtn_libre);
        rbtn_libre.setText("Libre");
        rbtn_libre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_libreActionPerformed(evt);
            }
        });
        pnl_estado.add(rbtn_libre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        bgEstadoHabitacion.add(rbtn_ocupado);
        rbtn_ocupado.setText("Ocupado");
        rbtn_ocupado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_ocupadoActionPerformed(evt);
            }
        });
        pnl_estado.add(rbtn_ocupado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        bgEstadoHabitacion.add(rbtn_mant);
        rbtn_mant.setText("Mantenimiento");
        rbtn_mant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_mantActionPerformed(evt);
            }
        });
        pnl_estado.add(rbtn_mant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel4.setText("Estado");
        pnl_estado.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnl_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnl_datosHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 1049, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnl_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(pnl_datosHabitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        int fila = tabla_productos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        if (txt_cantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cantidad.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txt_cantidad.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
            return;
        }

        int idProd = Integer.parseInt(tabla_productos.getValueAt(fila, 0).toString());
        String nom = tabla_productos.getValueAt(fila, 1).toString();
        double precio = Double.parseDouble(tabla_productos.getValueAt(fila, 2).toString());
        int stock = Integer.parseInt(tabla_productos.getValueAt(fila, 3).toString());

        int ya = getCantidadYaAgregada(idProd);
        int disponible = stock - ya;

        if (cantidad > disponible) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + disponible);
            return;
        }

        double totalLinea = precio * cantidad;
        modeloVentas.addRow(new Object[]{idProd, nom, precio, cantidad, totalLinea});

        txt_cantidad.setText("");
        mtd_calculo();
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_cambiar_modoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiar_modoActionPerformed
        if (btn_cambiar_modo.isSelected()) {
            btn_cambiar_modo.setText("Con Datos");
            toggleDatos(true);
            btn_buscarCliente.setVisible(true);      // ✅
        } else {
            btn_cambiar_modo.setText("Consumidor Final");
            toggleDatos(false);
            btn_buscarCliente.setVisible(false);     // ✅
            txt_cedu_cliente.setText("");
            txt_nom_clie.setText("");
            txt_dir_cliente.setText("");
            txt_tel_cliente.setText("");
            txt_email_cliente.setText("");
        }


    }//GEN-LAST:event_btn_cambiar_modoActionPerformed

    private void btn_h9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h9ActionPerformed

    }//GEN-LAST:event_btn_h9ActionPerformed

    private void btn_h8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h8ActionPerformed

    }//GEN-LAST:event_btn_h8ActionPerformed

    private void btn_h7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h7ActionPerformed

    }//GEN-LAST:event_btn_h7ActionPerformed

    private void btn_h6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h6ActionPerformed

    }//GEN-LAST:event_btn_h6ActionPerformed

    private void btn_h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h5ActionPerformed

    }//GEN-LAST:event_btn_h5ActionPerformed

    private void btn_h4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h4ActionPerformed

    }//GEN-LAST:event_btn_h4ActionPerformed

    private void btn_h3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h3ActionPerformed

    }//GEN-LAST:event_btn_h3ActionPerformed

    private void btn_h2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h2ActionPerformed

    }//GEN-LAST:event_btn_h2ActionPerformed

    private void btn_h12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h12ActionPerformed

    }//GEN-LAST:event_btn_h12ActionPerformed

    private void btn_h1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h1ActionPerformed

    }//GEN-LAST:event_btn_h1ActionPerformed

    private void btn_h11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h11ActionPerformed

    }//GEN-LAST:event_btn_h11ActionPerformed

    private void btn_h10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_h10ActionPerformed

    }//GEN-LAST:event_btn_h10ActionPerformed

    private void rbtn_mantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_mantActionPerformed

    }//GEN-LAST:event_rbtn_mantActionPerformed

    private void rbtn_ocupadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_ocupadoActionPerformed

    }//GEN-LAST:event_rbtn_ocupadoActionPerformed

    private void rbtn_libreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_libreActionPerformed

    }//GEN-LAST:event_rbtn_libreActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        String tipo = cb_filtro.getSelectedItem() != null ? cb_filtro.getSelectedItem().toString() : "Todos";
        String texto = txt_buscarProd.getText();
        CargarTablaProductos(tipo, texto);
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        int fila = tabla_venta.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila de la venta.");
            return;
        }
        int idProd = Integer.parseInt(tabla_venta.getValueAt(fila, 0).toString());
        if (idProd == ID_CARGO_HAB) {
            JOptionPane.showMessageDialog(this, "El cargo de habitación no se puede eliminar.");
            return;
        }
        modeloVentas.removeRow(fila);
        mtd_calculo();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_cancelar_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_facActionPerformed
        limpiarFacturaParaNuevaHabitacion();
        asegurarCargoHabitacionEnVenta();   // ✅ AQUÍ
        cargarSiguienteNumeroFactura();
        CargarTablaProductos();

    }//GEN-LAST:event_btn_cancelar_facActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        try {
            // 1) Validaciones base
            if (txt_num_factu.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se pudo generar el número de factura.");
                return;
            }
            if (cb_fecha.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione la fecha.");
                return;
            }
            if (tabla_venta.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Debe agregar productos a la venta.");
                return;
            }

            // Asegura que los totales estén calculados
            if (txt_total.getText().trim().isEmpty() || "0.00".equals(txt_total.getText().trim())) {
                mtd_calculo();
            }

            // 2) Cliente (solo si está en modo "Con Datos")
            // 2) Cliente (solo si está en modo "Con Datos")
            String cedulaFinal = null;

            if (btn_cambiar_modo.isSelected()) { // Con Datos
                cedulaFinal = txt_cedu_cliente.getText().trim();
                String nombre = txt_nom_clie.getText().trim();

                if (cedulaFinal.isEmpty() || nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Cédula y Nombre son obligatorios para facturar con datos.");
                    return;
                }

                Clientes cli = new Clientes();

                // Si existe, opcionalmente actualiza datos (recomendado)
                // Si no existe, lo crea
                if (!cli.existeCliente(cedulaFinal)) {
                    cli.setCed_cli(cedulaFinal);
                    cli.setNom_cli(nombre);
                    cli.setDir_cli(nullIfBlank(txt_dir_cliente.getText()));
                    cli.setTel_cli(nullIfBlank(txt_tel_cliente.getText()));
                    cli.setEmail_cli(nullIfBlank(txt_email_cliente.getText()));

                    String r = cli.mtd_guardar();
                    if (r != null && !r.trim().isEmpty() && !r.toLowerCase().contains("satisfactoriamente")) {
                        JOptionPane.showMessageDialog(this, "Error guardando cliente: " + r);
                        return;
                    }
                } else {
                    // ✅ Si quieres que al facturar con datos se actualice el cliente:
                    cli.setCed_cli(cedulaFinal);
                    cli.setNom_cli(nombre);
                    cli.setDir_cli(nullIfBlank(txt_dir_cliente.getText()));
                    cli.setTel_cli(nullIfBlank(txt_tel_cliente.getText()));
                    cli.setEmail_cli(nullIfBlank(txt_email_cliente.getText()));

                    String r = cli.mtd_actualizar();
                    if (r != null && !r.trim().isEmpty() && !r.toLowerCase().contains("satisfactoriamente")) {
                        JOptionPane.showMessageDialog(this, "Error actualizando cliente: " + r);
                        return;
                    }
                }
            }

            // 3) Validación de stock en BD (para evitar negativos si algo cambió)
            if (!validarStockEnBD()) {
                return; // ya muestra mensaje dentro
            }

            // 4) Construir cabecera
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int numFac = Integer.parseInt(txt_num_factu.getText().trim());

            double subtotal = parseDoubleSafe(txt_subtotal.getText());
            double descuento = parseDoubleSafe(txt_descu.getText());
            double iva = parseDoubleSafe(txt_iva.getText());
            double total = parseDoubleSafe(txt_total.getText());

            Integer idUsuario = (Clases.Usuarios.usuarioLogueado != null)
                    ? Clases.Usuarios.usuarioLogueado.getId_usu()
                    : null;

            System.out.println("usuarioLogueado = " + (Clases.Usuarios.usuarioLogueado == null ? "NULL" : Clases.Usuarios.usuarioLogueado.getId_usu()));

            Clases.Cabecera_Factura cab = new Clases.Cabecera_Factura(
                    numFac,
                    sdf.format(cb_fecha.getDate()),
                    cedulaFinal, // null => consumidor final
                    subtotal,
                    descuento,
                    iva,
                    total,
                    idUsuario,
                    this.idHabitacion
            );

            String respCab = cab.mtd_guardar();
            if (respCab != null && !respCab.trim().isEmpty() && !respCab.toLowerCase().contains("satisfactoriamente")) {
                JOptionPane.showMessageDialog(this, "Error al guardar cabecera: " + respCab);
                return;
            }

            // 5) Guardar detalle + descontar stock
            Clases.cls_conexion cnx = new Clases.cls_conexion();

            for (int i = 0; i < tabla_venta.getRowCount(); i++) {
                int idProd = Integer.parseInt(tabla_venta.getValueAt(i, 0).toString());
                if (idProd == ID_CARGO_HAB) {
                    continue; // ✅ NO se guarda como detalle, NO descuenta stock
                }
                int cant = Integer.parseInt(tabla_venta.getValueAt(i, 3).toString());
                double totLinea = Double.parseDouble(tabla_venta.getValueAt(i, 4).toString());

                Clases.Detalle_Factura det = new Clases.Detalle_Factura(numFac, idProd, cant, totLinea);
                String respDet = det.mtd_guardar();
                if (respDet != null && !respDet.trim().isEmpty() && !respDet.toLowerCase().contains("satisfactoriamente")) {
                    JOptionPane.showMessageDialog(this, "Error al guardar detalle: " + respDet);
                    return;
                }

                // Descarga stock en BD
                String sqlStock = "UPDATE productos SET can_pro = can_pro - " + cant + " WHERE id_pro = " + idProd + ";";
                String respStock = cnx.ejecutar(sqlStock);
                if (respStock != null && !respStock.trim().isEmpty() && !respStock.toLowerCase().contains("satisfactoriamente")) {
                    // Si tu ejecutar devuelve "" cuando ok, perfecto. Si devuelve texto, lo mostramos si parece error.
                    // Aquí no hacemos rollback porque tu cls_conexion no maneja transacciones explícitas.
                    JOptionPane.showMessageDialog(this, "Advertencia actualizando stock: " + respStock);
                }
            }

            actualizarEstadoHabitacionSilencioso("OCUPADO");
            lbl_estado.setText("OCUPADO");
            pintarBoton(btnHab.get(idHabitacion), "OCUPADO");
            setEstadoRadio("OCUPADO"); // opcional, pero si lo dejas, OK por cargandoUI
            refrescarColoresHabitaciones();

            // 7) OK + limpiar
            JOptionPane.showMessageDialog(this, "¡Venta guardada exitosamente!", "Sistema StayBill", JOptionPane.INFORMATION_MESSAGE);

            limpiarFacturaParaNuevaHabitacion();
            asegurarCargoHabitacionEnVenta();

            cargarSiguienteNumeroFactura();
            CargarTablaProductos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error crítico al guardar: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_buscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarClienteActionPerformed
        java.awt.Window w = SwingUtilities.getWindowAncestor(this);
        dlg_clientes dlg = new dlg_clientes(w);
        dlg.setVisible(true);

        String ced = dlg.getCedulaSeleccionada();
        if (ced != null) {
            cargarClientePorCedula(ced);
        }
    }//GEN-LAST:event_btn_buscarClienteActionPerformed

    private void cargarClientePorCedula(String cedula) {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "SELECT ced_cli, nom_cli, dir_cli, tel_cli, email_cli "
                    + "FROM clientes WHERE ced_cli = '" + cedula + "' LIMIT 1;";

            java.sql.ResultSet rs = cnx.Consulta(sql);
            if (rs != null && rs.next()) {
                txt_cedu_cliente.setText(rs.getString("ced_cli"));
                txt_nom_clie.setText(rs.getString("nom_cli"));
                txt_dir_cliente.setText(rs.getString("dir_cli"));
                txt_tel_cliente.setText(rs.getString("tel_cli"));
                txt_email_cliente.setText(rs.getString("email_cli"));
            }
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando cliente: " + e.getMessage());
        }
    }

    private double parseDoubleSafe(String s) {
        try {
            if (s == null) {
                return 0.0;
            }
            String t = s.trim().replace(",", ".");
            if (t.isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(t);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private boolean validarStockEnBD() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();

            for (int i = 0; i < tabla_venta.getRowCount(); i++) {
                int idProd = Integer.parseInt(tabla_venta.getValueAt(i, 0).toString());
                int cant = Integer.parseInt(tabla_venta.getValueAt(i, 3).toString());

// ✅ SALTAR cargo habitación (no es producto, no tiene stock)
                if (idProd == ID_CARGO_HAB) {
                    continue;
                }

                java.sql.ResultSet rs = cnx.Consulta("SELECT can_pro FROM productos WHERE id_pro = " + idProd + ";");
                int stockBD = 0;
                if (rs != null && rs.next()) {
                    stockBD = rs.getInt("can_pro");
                }
                if (rs != null) {
                    rs.close();
                }

                if (cant > stockBD) {
                    JOptionPane.showMessageDialog(this,
                            "Stock insuficiente en BD para el producto ID " + idProd
                            + ". Disponible: " + stockBD + " | Pedido: " + cant);
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error validando stock: " + e.getMessage());
            return false;
        }
    }

// Actualiza el estado sin mostrar el JOptionPane de tu cambiarEstado()
    private void actualizarEstadoHabitacionSilencioso(String nuevoEstado) {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            String sql = "UPDATE habitaciones SET est_hab = '" + nuevoEstado + "' WHERE id_hab = " + idHabitacion + ";";
            cnx.ejecutar(sql);
            cargarDatosHabitacion();
        } catch (Exception e) {
            // Silencioso, pero si quieres ver errores:
            // JOptionPane.showMessageDialog(this, "Error actualizando estado: " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgEstadoHabitacion;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_buscarCliente;
    private javax.swing.JToggleButton btn_cambiar_modo;
    private javax.swing.JButton btn_cancelar_fac;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_h1;
    private javax.swing.JButton btn_h10;
    private javax.swing.JButton btn_h11;
    private javax.swing.JButton btn_h12;
    private javax.swing.JButton btn_h2;
    private javax.swing.JButton btn_h3;
    private javax.swing.JButton btn_h4;
    private javax.swing.JButton btn_h5;
    private javax.swing.JButton btn_h6;
    private javax.swing.JButton btn_h7;
    private javax.swing.JButton btn_h8;
    private javax.swing.JButton btn_h9;
    private javax.swing.JComboBox<String> cb_descu;
    private org.jdesktop.swingx.JXDatePicker cb_fecha;
    private javax.swing.JComboBox<String> cb_filtro;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel label100;
    private javax.swing.JLabel label99;
    private javax.swing.JLabel lbl_cedula1;
    private javax.swing.JLabel lbl_correo1;
    private javax.swing.JLabel lbl_direccion1;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JLabel lbl_hab_selec;
    private javax.swing.JLabel lbl_nom_cli1;
    private javax.swing.JLabel lbl_numero;
    private javax.swing.JLabel lbl_precio;
    private javax.swing.JLabel lbl_telefono1;
    private javax.swing.JLabel lbl_tipo;
    private javax.swing.JPanel pnl_datosHabitacion;
    private javax.swing.JPanel pnl_estado;
    private javax.swing.JPanel pnl_factura;
    private javax.swing.JPanel pnl_habitaciones;
    private javax.swing.JPanel pnl_productos;
    private javax.swing.JRadioButton rbtn_libre;
    private javax.swing.JRadioButton rbtn_mant;
    private javax.swing.JRadioButton rbtn_ocupado;
    private javax.swing.JTable tabla_productos;
    private javax.swing.JTable tabla_venta;
    private javax.swing.JTextField txt_buscarProd;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_cedu_cliente;
    private javax.swing.JTextField txt_descu;
    private javax.swing.JTextField txt_dir_cliente;
    private javax.swing.JTextField txt_email_cliente;
    private javax.swing.JTextField txt_iva;
    private javax.swing.JTextField txt_nom_clie;
    private javax.swing.JTextField txt_num_factu;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_tel_cliente;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
