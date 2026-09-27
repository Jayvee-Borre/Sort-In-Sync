/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import java.awt.Color;

/**
 *
 * @author Jayvee
 */
public enum Theme {
    LIGHT("Light",
            Color.decode("#C7242C"), new Color(30, 30, 30),
            Color.decode("#F1D42D"), Color.decode("#E3B800"), Color.decode("#794F05"), Color.BLACK),

    DARK("Dark",
            Color.decode("#2F3130"), Color.WHITE,
            Color.decode("#CACACA"), Color.decode("#B0B0B0"), Color.decode("#535353"), Color.BLACK);

    private final String label;
    private final Color bgColor;
    private final Color fgColor;
    private final Color accentColor;
    private final Color accentBorderColor;
    private final Color accentClickedColor;
    private final Color accentTextColor;

    Theme(String label, Color bgColor, Color fgColor,
            Color accentColor, Color accentBorderColor, Color accentClickedColor, Color accentTextColor) {
        this.label = label;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.accentColor = accentColor;
        this.accentBorderColor = accentBorderColor;
        this.accentClickedColor = accentClickedColor;
        this.accentTextColor = accentTextColor;
    }

    public String getLabel() {
        return label;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getFgColor() {
        return fgColor;
    }

    public Color getAccentColor() {
        return accentColor;
    }

    public Color getAccentBorderColor() {
        return accentBorderColor;
    }

    public Color getAccentClickedColor() {
        return accentClickedColor;
    }

    public Color getAccentTextColor() {
        return accentTextColor;
    }

    public static Theme fromLabel(String label) {
        for (Theme t : values()) {
            if (t.label.equals(label)) {
                return t;
            }
        }
        return null;
    }

    public static String[] labels() {
        Theme[] values = values();
        String[] labels = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            labels[i] = values[i].label;
        }
        return labels;
    }
}
