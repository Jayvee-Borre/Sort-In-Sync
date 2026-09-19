/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import App.Main;
import javax.swing.JComboBox;

/**
 *
 * @author Jayvee
 */
public class Options {
    private final Main window;
    
    public Options(Main window) {
        this.window = window;
    }
    
    public void changeTheme(JComboBox item) {
        String theme = (String) item.getSelectedItem();
        if (theme == null || theme.equals("Default")) return;

        java.awt.Color bgColor;
        java.awt.Color fgColor;

        switch (theme) {
            case "Midnight Dark":
                bgColor = new java.awt.Color(43, 43, 43);
                fgColor = java.awt.Color.WHITE;
                break;
            case "Classic Light":
                bgColor = new java.awt.Color(240, 240, 240);
                fgColor = new java.awt.Color(30, 30, 30);
                break;
            case "Retro Synthwave":
                bgColor = new java.awt.Color(20, 10, 40);
                fgColor = new java.awt.Color(0, 255, 204);
                break;
            case "Cyberpunk Neon":
                bgColor = java.awt.Color.BLACK;
                fgColor = new java.awt.Color(255, 255, 0); 
                break;
            case "Vaporwave Sunset":
                bgColor = new java.awt.Color(255, 182, 193); 
                fgColor = new java.awt.Color(138, 43, 226);  
                break;
            default:
                return;
        }

        applyColorsRecursively(window.getContentPane(), bgColor, fgColor);
        javax.swing.SwingUtilities.updateComponentTreeUI(window);
    }
    
    public void changeResolution(JComboBox item) {
        String res = (String) item.getSelectedItem();
        if (res == null || res.equals("Select A Resolution")) return;

        int width = window.getWidth();
        int height = window.getHeight();

        switch (res) {
            case "1280x720": width = 1280; height = 720; break;
            case "1024x768": width = 1024; height = 768; break;
            case "960x540":  width = 960;  height = 540; break;
            case "800x600":  width = 800;  height = 600; break;
            case "640x480":  width = 640;  height = 480; break;
        }
        
        window.setSize(width, height);
        window.setLocationRelativeTo(null); // Keeps the window centered after resizing
    }
    
    public void changeFont(JComboBox item) {
        String fontChoice = (String) item.getSelectedItem();
        if (fontChoice == null) return;

        // Strip the " (Default)" tag if SansSerif is selected so the Font class can read it
        String fontName = fontChoice.replace(" (Default)", "");

        applyFontRecursively(window.getContentPane(), fontName);
        javax.swing.SwingUtilities.updateComponentTreeUI(window);
    }
    
    private void applyColorsRecursively(java.awt.Container container, java.awt.Color bg, java.awt.Color fg) {
        container.setBackground(bg);
        container.setForeground(fg);
        
        for (java.awt.Component c : container.getComponents()) {
            c.setBackground(bg);
            c.setForeground(fg);
            
            if (c instanceof java.awt.Container) {
                applyColorsRecursively((java.awt.Container) c, bg, fg);
            }
        }
    }
    
    private void applyFontRecursively(java.awt.Container container, String fontName) {
        java.awt.Font currentFont = container.getFont();
        if (currentFont != null) {
            container.setFont(new java.awt.Font(fontName, currentFont.getStyle(), currentFont.getSize()));
        }
        
        for (java.awt.Component c : container.getComponents()) {
            java.awt.Font cFont = c.getFont();
            if (cFont != null) {
                // Preserves the existing size and style of each label/button while swapping the family
                c.setFont(new java.awt.Font(fontName, cFont.getStyle(), cFont.getSize()));
            }
            
            if (c instanceof java.awt.Container) {
                applyFontRecursively((java.awt.Container) c, fontName);
            }
        }
    }
}
