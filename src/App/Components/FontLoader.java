/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Components;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

/**
 *
 * @author USER
 */
public class FontLoader {
    private static Font pixelifySans;

    public static Font getPixelFont(float size) {
        if (pixelifySans == null) {
            try {
                // Read from resource path (must start with / and use forward slashes)
                InputStream is = FontLoader.class.getResourceAsStream("/Assets/Menu Screen/PixelifySans.ttf");

                if (is != null) {
                    // Create base font from stream
                    Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
                    
                    // Register with local graphics environment
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(baseFont);
                    
                    pixelifySans = baseFont;
                    System.out.println("[FontLoader]: PixelifySans loaded successfully!");
                } else {
                    System.out.println("[FontLoader Error]: Could not find /Assets/Menu Screen/PixelifySans.ttf");
                    return new Font("SansSerif", Font.BOLD, (int) size);
                }
            } catch (Exception e) {
                System.out.println("[FontLoader Exception]: " + e.getMessage());
                e.printStackTrace();
                return new Font("SansSerif", Font.BOLD, (int) size);
            }
        }

        // Derive size cleanly using PLAIN style
        return pixelifySans.deriveFont(Font.BOLD, size);
    }
}
