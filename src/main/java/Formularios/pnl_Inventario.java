/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// Excel (Apache POI)
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// PDF (iText 5)
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 *
 * @author LENOVO
 */
public class pnl_Inventario extends javax.swing.JPanel {

    /**
     * Creates new form pnl_Inventario
     */
    public pnl_Inventario() {
        initComponents();
        cargarTiposProductoEnCombos();    
        cargarTablaProductos(null, null);
        
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_guardar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cb_tipoFiltro = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_id_pro = new javax.swing.JTextField();
        txt_nom_pro = new javax.swing.JTextField();
        txt_pre_pro = new javax.swing.JTextField();
        txt_can_pro = new javax.swing.JTextField();
        txt_desc_pro = new javax.swing.JTextField();
        cb_tipo_pro = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_inventario = new javax.swing.JTable();
        btn_pdf = new javax.swing.JButton();
        btn_excel = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, -1, -1));

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, -1, -1));

        jLabel1.setText("Tipo:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 40, -1));

        cb_tipoFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cb_tipoFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        jLabel2.setText("Buscar:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));
        add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 113, -1));

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, -1));

        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });
        add(btn_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, -1, -1));

        jLabel3.setText("ID:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        jLabel4.setText("Nombre:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        jLabel5.setText("Stock:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jLabel6.setText("Precio:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        jLabel7.setText("Tipo:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        jLabel8.setText("Descripcion: ");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, -1));

        txt_id_pro.setEditable(false);
        add(txt_id_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 103, -1));
        add(txt_nom_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 103, -1));
        add(txt_pre_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 103, -1));
        add(txt_can_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 103, -1));
        add(txt_desc_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 103, -1));

        cb_tipo_pro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cb_tipo_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 100, -1));

        tabla_inventario.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_inventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_inventario);

        jScrollPane2.setViewportView(jScrollPane1);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 540, 410));

        btn_pdf.setText("Exportar PDF");
        btn_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pdfActionPerformed(evt);
            }
        });
        add(btn_pdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 550, -1, 40));

        btn_excel.setText("Exportar Excel");
        btn_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excelActionPerformed(evt);
            }
        });
        add(btn_excel, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 550, -1, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
         String tipo = cb_tipoFiltro.getSelectedItem() != null
            ? cb_tipoFiltro.getSelectedItem().toString()
            : "Todos";
    String texto = txt_buscar.getText();
    cargarTablaProductos(tipo, texto);
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void tabla_inventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_inventarioMouseClicked
        int fila = tabla_inventario.getSelectedRow();
    if (fila == -1) return;

    txt_id_pro.setText(tabla_inventario.getValueAt(fila, 0).toString());
    txt_nom_pro.setText(tabla_inventario.getValueAt(fila, 1).toString());
    txt_pre_pro.setText(tabla_inventario.getValueAt(fila, 2).toString());
    txt_can_pro.setText(tabla_inventario.getValueAt(fila, 3).toString());
    txt_desc_pro.setText(tabla_inventario.getValueAt(fila, 4).toString());

    String tipo = tabla_inventario.getValueAt(fila, 5).toString();
    cb_tipo_pro.setSelectedItem(tipo);
    }//GEN-LAST:event_tabla_inventarioMouseClicked

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        if (cb_tipoFiltro.getItemCount() > 0) {
        cb_tipoFiltro.setSelectedIndex(0); 
    }
    txt_buscar.setText("");
    cargarTablaProductos();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        if (txt_id_pro.getText().trim().isEmpty()) {
        insertarProducto();    
    } else {
        actualizarProducto();  
    }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
         eliminarProducto();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excelActionPerformed
       exportarExcelXLSX();
    }//GEN-LAST:event_btn_excelActionPerformed

    private void btn_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pdfActionPerformed
        exportarPDF();
    }//GEN-LAST:event_btn_pdfActionPerformed

    
    private File elegirArchivo(String titulo, String ext, String desc) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(titulo);
    chooser.setFileFilter(new FileNameExtensionFilter(desc, ext));

    String fecha = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
    chooser.setSelectedFile(new File("inventario_" + fecha + "." + ext));

    int result = chooser.showSaveDialog(this);
    if (result != JFileChooser.APPROVE_OPTION) return null;

    File f = chooser.getSelectedFile();

    // fuerza extensión
    if (!f.getName().toLowerCase().endsWith("." + ext)) {
        f = new File(f.getAbsolutePath() + "." + ext);
    }

    return f;
}

    
    private void exportarExcelXLSX() {
    if (tabla_inventario.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
        return;
    }

    File file = elegirArchivo("Guardar Inventario (Excel)", "xlsx", "Excel (*.xlsx)");
    if (file == null) return;

    try (Workbook wb = new XSSFWorkbook()) {
        Sheet sheet = wb.createSheet("Inventario");

        TableModel model = tabla_inventario.getModel();

        // Estilo header
        CellStyle headerStyle = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Header row
        Row header = sheet.createRow(0);
        for (int c = 0; c < model.getColumnCount(); c++) {
            Cell cell = header.createCell(c);
            cell.setCellValue(model.getColumnName(c));
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        for (int r = 0; r < model.getRowCount(); r++) {
            Row row = sheet.createRow(r + 1);
            for (int c = 0; c < model.getColumnCount(); c++) {
                Object value = model.getValueAt(r, c);
                Cell cell = row.createCell(c);

                if (value == null) {
                    cell.setCellValue("");
                } else {
                    String s = value.toString();
                    // intenta números
                    if (s.matches("-?\\d+(\\.\\d+)?")) {
                        try {
                            cell.setCellValue(Double.parseDouble(s));
                        } catch (Exception ex) {
                            cell.setCellValue(s);
                        }
                    } else {
                        cell.setCellValue(s);
                    }
                }
            }
        }

        // Auto-size
        for (int c = 0; c < model.getColumnCount(); c++) {
            sheet.autoSizeColumn(c);
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            wb.write(out);
        }

        JOptionPane.showMessageDialog(this, "Excel generado correctamente:\n" + file.getAbsolutePath());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error exportando Excel:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    private void exportarPDF() {
    if (tabla_inventario.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
        return;
    }

    File file = elegirArchivo("Guardar Inventario (PDF)", "pdf", "PDF (*.pdf)");
    if (file == null) return;

    try {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();

        doc.add(new Paragraph("Reporte de Inventario - StayBill"));
        
        doc.add(new Paragraph(" "));

        TableModel model = tabla_inventario.getModel();
        PdfPTable table = new PdfPTable(model.getColumnCount());
        table.setWidthPercentage(100);

        // Headers
        for (int c = 0; c < model.getColumnCount(); c++) {
            table.addCell(model.getColumnName(c));
        }

        // Rows
        for (int r = 0; r < model.getRowCount(); r++) {
            for (int c = 0; c < model.getColumnCount(); c++) {
                Object val = model.getValueAt(r, c);
                table.addCell(val == null ? "" : val.toString());
            }
        }

        doc.add(table);
        doc.close();

        JOptionPane.showMessageDialog(this, "PDF generado correctamente:\n" + file.getAbsolutePath());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error exportando PDF:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    private void cargarTiposProductoEnCombos() {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            java.sql.ResultSet rs = cnx.Consulta(
                    "SELECT DISTINCT tipo_pro FROM productos ORDER BY tipo_pro;"
            );

          
            cb_tipoFiltro.removeAllItems();
            cb_tipoFiltro.addItem("Todos");

           
            cb_tipo_pro.removeAllItems();

            while (rs != null && rs.next()) {
                String tipo = rs.getString("tipo_pro");
                if (tipo != null && !tipo.trim().isEmpty()) {
                    cb_tipoFiltro.addItem(tipo);
                    cb_tipo_pro.addItem(tipo);
                }
            }

            if (rs != null) {
                rs.close();
            }

  
            if (cb_tipo_pro.getItemCount() == 0) {
                cb_tipo_pro.addItem("BEBIDA");
                cb_tipo_pro.addItem("SNACK");
                cb_tipo_pro.addItem("HIGIENE");
                cb_tipo_pro.addItem("COMIDA");
                cb_tipo_pro.addItem("SERVICIO");
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error al cargar tipos de producto",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarTablaProductos(String tipo, String texto) {
        try {
            Clases.cls_conexion cnx = new Clases.cls_conexion();
            StringBuilder sql = new StringBuilder(
                    "SELECT "
                    + "id_pro   AS ID, "
                    + "nom_pro  AS Nombre, "
                    + "pre_pro  AS Precio, "
                    + "can_pro  AS Stock, "
                    + "desc_pro AS Descripcion, "
                    + "tipo_pro AS Tipo "
                    + "FROM productos "
            );

            boolean tieneWhere = false;

           
            if (tipo != null && !"Todos".equalsIgnoreCase(tipo)) {
                sql.append("WHERE tipo_pro = '").append(tipo).append("' ");
                tieneWhere = true;
            }

    
            if (texto != null && !texto.trim().isEmpty()) {
                sql.append(tieneWhere ? "AND " : "WHERE ");
                sql.append("nom_pro LIKE '%").append(texto.trim()).append("%' ");
            }

            sql.append("ORDER BY nom_pro;");

            cnx.CargarTabla(sql.toString(), tabla_inventario);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error al cargar productos",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void cargarTablaProductos() {
        cargarTablaProductos(null, null);
    }

    private void limpiarFormulario() {
        txt_id_pro.setText("");
        txt_nom_pro.setText("");
        txt_pre_pro.setText("");
        txt_can_pro.setText("");
        txt_desc_pro.setText("");
        if (cb_tipo_pro.getItemCount() > 0) {
            cb_tipo_pro.setSelectedIndex(0);
        }
        tabla_inventario.clearSelection();
    }

    private String validarFormulario() {
        if (txt_nom_pro.getText().trim().isEmpty()) {
            return "Ingrese el nombre del producto.";
        }
        if (txt_pre_pro.getText().trim().isEmpty()) {
            return "Ingrese el precio del producto.";
        }
        if (txt_can_pro.getText().trim().isEmpty()) {
            return "Ingrese el stock del producto.";
        }
        if (cb_tipo_pro.getSelectedItem() == null) {
            return "Seleccione un tipo de producto.";
        }

        try {
            double precio = Double.parseDouble(txt_pre_pro.getText().trim());
            if (precio < 0) {
                return "El precio no puede ser negativo.";
            }
        } catch (NumberFormatException e) {
            return "El precio debe ser un número válido.";
        }

        try {
            int stock = Integer.parseInt(txt_can_pro.getText().trim());
            if (stock < 0) {
                return "El stock no puede ser negativo.";
            }
        } catch (NumberFormatException e) {
            return "El stock debe ser un número entero.";
        }

        return "";
    }
    
    private void insertarProducto() {
    String error = validarFormulario();
    if (!error.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, error);
        return;
    }

    String nombre = txt_nom_pro.getText().trim();
    double precio = Double.parseDouble(txt_pre_pro.getText().trim());
    int stock = Integer.parseInt(txt_can_pro.getText().trim());
    String desc = txt_desc_pro.getText().trim();
    String tipo = cb_tipo_pro.getSelectedItem().toString();

    String sql = "INSERT INTO productos "
            + "(nom_pro, pre_pro, can_pro, desc_pro, tipo_pro) VALUES ("
            + "'" + nombre + "', "
            + precio + ", "
            + stock + ", "
            + "'" + desc + "', "
            + "'" + tipo + "');";

    try {
        Clases.cls_conexion cnx = new Clases.cls_conexion();
        String resp = cnx.ejecutar(sql);
        if (!"".equals(resp)) {
            javax.swing.JOptionPane.showMessageDialog(
                this, resp, "Error al guardar producto",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(this, "Producto registrado correctamente.");
        cargarTablaProductos();
        limpiarFormulario();
        cargarTiposProductoEnCombos(); 
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this, e.getMessage(),
            "Error al guardar producto",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void actualizarProducto() {
    if (txt_id_pro.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para editar.");
        return;
    }

    String error = validarFormulario();
    if (!error.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, error);
        return;
    }

    int id = Integer.parseInt(txt_id_pro.getText().trim());
    String nombre = txt_nom_pro.getText().trim();
    double precio = Double.parseDouble(txt_pre_pro.getText().trim());
    int stock = Integer.parseInt(txt_can_pro.getText().trim());
    String desc = txt_desc_pro.getText().trim();
    String tipo = cb_tipo_pro.getSelectedItem().toString();

    String sql = "UPDATE productos SET "
            + "nom_pro = '" + nombre + "', "
            + "pre_pro = " + precio + ", "
            + "can_pro = " + stock + ", "
            + "desc_pro = '" + desc + "', "
            + "tipo_pro = '" + tipo + "' "
            + "WHERE id_pro = " + id + ";";

    try {
        Clases.cls_conexion cnx = new Clases.cls_conexion();
        String resp = cnx.ejecutar(sql);
        if (!"".equals(resp)) {
            javax.swing.JOptionPane.showMessageDialog(
                this, resp, "Error al actualizar producto",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
        cargarTablaProductos();
        limpiarFormulario();
        cargarTiposProductoEnCombos();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this, e.getMessage(),
            "Error al actualizar producto",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void eliminarProducto() {
    if (txt_id_pro.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para eliminar.");
        return;
    }

    int id = Integer.parseInt(txt_id_pro.getText().trim());

    int r = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "¿Seguro que desea eliminar este producto?",
        "Confirmar eliminación",
        javax.swing.JOptionPane.YES_NO_OPTION
    );
    if (r != javax.swing.JOptionPane.YES_OPTION) {
        return;
    }

    String sql = "DELETE FROM productos WHERE id_pro = " + id + ";";

    try {
        Clases.cls_conexion cnx = new Clases.cls_conexion();
        String resp = cnx.ejecutar(sql);
        if (!"".equals(resp)) {
            javax.swing.JOptionPane.showMessageDialog(
                this, resp,
                "No se pudo eliminar el producto (puede tener facturas asociadas).",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        cargarTablaProductos();
        limpiarFormulario();
        cargarTiposProductoEnCombos();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this, e.getMessage(),
            "Error al eliminar producto",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_excel;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_pdf;
    private javax.swing.JComboBox<String> cb_tipoFiltro;
    private javax.swing.JComboBox<String> cb_tipo_pro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabla_inventario;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_can_pro;
    private javax.swing.JTextField txt_desc_pro;
    private javax.swing.JTextField txt_id_pro;
    private javax.swing.JTextField txt_nom_pro;
    private javax.swing.JTextField txt_pre_pro;
    // End of variables declaration//GEN-END:variables
}
