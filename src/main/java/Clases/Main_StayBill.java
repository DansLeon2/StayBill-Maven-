package Clases;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author LENOVO
 */
public class Main_StayBill {

    public static void main(String[] args) {
        cls_conexion obj = new cls_conexion();

        try {
            // Usar el ClassLoader de la clase principal
            java.io.InputStream is = Main_StayBill.class.getResourceAsStream("/Temas/StayBill.theme.json");
            if (is != null) {
                UIManager.setLookAndFeel(com.formdev.flatlaf.IntelliJTheme.createLaf(is));
            } else {
                System.err.println("Advertencia: No se encontró el archivo del tema.");
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // --- 1. REDONDEZ (Consistente) ---
        UIManager.put("Button.arc", 15);            // Valor equilibrado para el look de tarjetas
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("CheckBox.arc", 12);

// --- 2. COLORES GLOBALES (Paleta StayBill) ---
        UIManager.put("Panel.background", new Color(0x231f20));      // Fondo Negro/Gris oscuro
        UIManager.put("Label.foreground", new Color(0xecebf3));      // Letras Blancas
        UIManager.put("Button.background", new Color(0x700238));     // Color Vino
        UIManager.put("Button.foreground", new Color(0xecebf3));     // Letras blancas en botones
        UIManager.put("Component.borderColor", new Color(0x6d7175)); // Gris medio para bordes

// --- 3. COMPONENTES ESPECÍFICOS ---
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.underlineColor", new Color(0x700238)); // Vino para pestañas
        UIManager.put("TextComponent.background", new Color(0x2b2b2b));
        UIManager.put("TextComponent.foreground", new Color(0xecebf3));
        UIManager.put("Component.focusColor", new Color(0x700238));      // El foco al escribir será vino

// --- 4. TABLAS (Para que se vean modernas) ---
        UIManager.put("Table.background", new Color(0x231f20));
        UIManager.put("Table.foreground", new Color(0xecebf3));
        UIManager.put("Table.selectionBackground", new Color(0x700238));
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.rowHeight", 25); // Filas un poco más altas

// --- 5. SCROLLBARS ---
        UIManager.put("ScrollBar.thumb", new Color(0x6d7175));
        UIManager.put("ScrollBar.track", new Color(0x231f20));

        java.awt.EventQueue.invokeLater(() -> new Formularios.frm_Login().setVisible(true));

    }
}
