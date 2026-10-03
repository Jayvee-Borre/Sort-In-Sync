/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;
import App.Components.CustomButton;
import App.Components.CustomDropdown;
import App.Components.CustomSlider;
import App.Main;
import Services.Database;
import java.awt.Color;
import javax.swing.JComboBox;

/**
 *
 * @author Jayvee
 */
public class Options {
    // UserID value meaning "nobody logged in" (matches Database.getUserId's not-found value)
    public static final int GUEST = -1;
    
    private final Main window;
    private final Database database;
    // Set default values so it doesnt bug out since we dont have a theme.
    private Color bgColor = Color.decode("#C7242C"); 
    private Color fgColor = Color.decode("#000000");
 
    // Who settings get saved for. While GUEST, changes still apply but are not saved.
    private int currentUserId = GUEST;
 
    // Last known value of every column in the Options row. saveUserOptions always
    // writes the whole row, so we keep all four here to avoid overwriting the
    // settings that didn't change with blanks/defaults.
    private String currentResolution = "Default";
    private String currentTheme = "Default";
    private String currentFont = "SansSerif";
    private int currentVolume = 50; // same as the VolumeSlider column default
    
    public Options(Main window, Database database) {
        this.window = window;
        this.database = database;
    }
    
    /**
     * Call on login (with the user's ID) and on sign out (with GUEST).
     * Remembers the user's saved settings so later saves only change the
     * setting that was actually edited. Does NOT apply them to the UI.
     */
    public void setCurrentUser(int userId) {
        this.currentUserId = userId;
 
        Database.OptionsData saved = (userId == GUEST) ? null : database.getUserOptions(userId);
        if (saved != null) {
            currentResolution = saved.resolution;
            currentTheme = saved.theme;
            currentFont = saved.font;
            currentVolume = saved.volume;
        } else {
            // Guest, or a user who has never saved settings yet
            currentResolution = "Default";
            currentTheme = "Default";
            currentFont = "SansSerif";
            currentVolume = 50;
        }
    }
    
    public void changeVolume(int volume) {
        currentVolume = volume;
        persist();
    }
    
    public int getVolume() {
        return currentVolume;
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
 
        currentTheme = theme.getLabel();
        persist();
    }
    
    public void changeResolution(JComboBox item) {
        String res = (String) item.getSelectedItem();
        if (res == null || res.equals("Select A Resolution")) return;
 
        int width;
        int height;
 
        switch (res) {
            case "1280x720": width = 1280; height = 720; break;
            case "1024x768": width = 1024; height = 768; break;
            case "960x540":  width = 960;  height = 540; break;
            case "800x600":  width = 800;  height = 600; break;
            case "640x480":  width = 640;  height = 480; break;
            default:
                return; // Unknown value: change nothing, save nothing
        }
        
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
 
        currentResolution = res;
        persist();
    }
    
    public void changeFont(JComboBox item) {
        String fontChoice = (String) item.getSelectedItem();
        if (fontChoice == null) return;
 
        String fontName = fontChoice.replace(" (Default)", "");
 
        applyFontRecursively(window.getContentPane(), fontName);
        javax.swing.SwingUtilities.updateComponentTreeUI(window);
 
        currentFont = fontName;
        persist();
    }
    
    // Writes the full current settings row for the logged-in user.
    private void persist() {
        if (currentUserId == GUEST) return; // Not logged in: change applies but isn't saved
 
        boolean saved = database.saveUserOptions(
                currentUserId, currentResolution, currentTheme, currentFont, currentVolume);
 
        if (saved) {
            System.out.println("[Update]: Options saved for user " + currentUserId);
        } else {
            System.out.println("[Error]: Could not save options for user " + currentUserId);
        }
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
