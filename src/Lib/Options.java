/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;
import App.Components.CustomButton;
import App.Components.CustomDropdown;
import App.Components.CustomSlider;
import App.Main;
import java.awt.Color;
import javax.swing.JComboBox;

/**
 *
 * @author Jayvee
 */
public class Options {
    private final Main window;
    // Set default values so it doesnt bug out since we dont have a theme.
    private Color bgColor = Color.decode("#C7242C"); 
    private Color fgColor = Color.decode("#000000");
    
    public Options(Main window) {
        this.window = window;
    }
    
    public void changeTheme(JComboBox item) {
        String label = (String) item.getSelectedItem();
        if (label == null) return;

        Theme theme = Theme.fromLabel(label);
        if (theme == null) return;

        bgColor = theme.getBgColor();
        fgColor = theme.getFgColor();

        applyTheme(window.getContentPane(), theme);
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
        window.setLocationRelativeTo(null);
    }
    
    public void changeFont(JComboBox item) {
        String fontChoice = (String) item.getSelectedItem();
        if (fontChoice == null) return;
 
        String fontName = fontChoice.replace(" (Default)", "");
 
        applyFontRecursively(window.getContentPane(), fontName);
        javax.swing.SwingUtilities.updateComponentTreeUI(window);
    }
    
    private void applyTheme(java.awt.Container container, Theme theme) {
        container.setBackground(theme.getBgColor());
        container.setForeground(theme.getFgColor());
        
        for (java.awt.Component c : container.getComponents()) {
            if (c instanceof CustomButton) {
                CustomButton btn = (CustomButton) c;
                btn.setColor(theme.getAccentColor());
                btn.setColorHover(computeHoverColor(theme.getAccentColor()));
                btn.setColorClicked(theme.getAccentClickedColor());
                btn.setBorderColor(theme.getAccentBorderColor());
                btn.setForeground(theme.getAccentTextColor());
                continue;
            } else if (c instanceof CustomDropdown) {
                CustomDropdown dd = (CustomDropdown) c;
                dd.setColor(theme.getAccentColor());
                dd.setColorHover(computeHoverColor(theme.getAccentColor()));
                dd.setBorderColor(theme.getAccentBorderColor());
                dd.setForeground(theme.getAccentTextColor());
                continue; 
            } else if (c instanceof CustomSlider) {
                CustomSlider sl = (CustomSlider) c;
                sl.setColor(theme.getAccentColor());
                sl.setColorHover(computeHoverColor(theme.getAccentColor()));
                sl.setColorClicked(theme.getAccentClickedColor());
                sl.setBorderColor(theme.getAccentBorderColor());
                continue;
            } else {
                c.setBackground(theme.getBgColor());
                c.setForeground(theme.getFgColor());
            }
            
            if (c instanceof java.awt.Container) {
                applyTheme((java.awt.Container) c, theme);
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
                c.setFont(new java.awt.Font(fontName, cFont.getStyle(), cFont.getSize()));
            }
            
            if (c instanceof java.awt.Container) {
                applyFontRecursively((java.awt.Container) c, fontName);
            }
        }
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getFgColor() {
        return fgColor;
    }
    
    public Color computeHoverColor(Color base) {
        int r = Math.min(255, base.getRed() + 40);
        int g = Math.min(255, base.getGreen() + 40);
        int b = Math.min(255, base.getBlue() + 40);
        return new Color(r, g, b);
    }
}
