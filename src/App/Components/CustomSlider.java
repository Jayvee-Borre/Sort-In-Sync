/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Components;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Jayvee
 */
public class CustomSlider extends JSlider {
    public Color getColor() {
        return color;
    }
 
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
 
    public Color getColorHover() {
        return colorHover;
    }
 
    public void setColorHover(Color colorHover) {
        this.colorHover = colorHover;
    }
 
    public Color getColorClicked() {
        return colorClicked;
    }
 
    public void setColorClicked(Color colorClicked) {
        this.colorClicked = colorClicked;
    }
 
    public Color getBorderColor() {
        return borderColor;
    }
 
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
 
    public int getRadius() {
        return radius;
    }
 
    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
 
    private Color color;
    private Color colorHover;
    private Color colorClicked;
    private Color borderColor;
    private int radius;
 
    public CustomSlider() {
        // Same classic accent palette as CustomButton and CustomDropdown
        setColor(Color.decode("#F1D42D"));       // Classic accent fill
        colorHover = getColor().brighter();      // lighter tint of the fill
        colorClicked = Color.decode("#794F05");  // Classic accent pressed/bevel
        borderColor = Color.decode("#E3B800");   // Classic accent border
        radius = 8;
 
        setOpaque(false);
        setFocusable(false);
        setUI(new RoundedSliderUI(this));
    }
 
    /**
     * Paints a rounded track and a circular thumb, switching fill color
     * based on hover/pressed state so it reads consistently with
     * CustomButton's own state colors.
     */
    private class RoundedSliderUI extends BasicSliderUI {
 
        RoundedSliderUI(JSlider slider) {
            super(slider);
        }
 
        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
            Rectangle track = trackRect;
            int trackHeight = 6;
            int trackY = track.y + (track.height - trackHeight) / 2;
 
            g2.setColor(borderColor);
            g2.fillRoundRect(track.x, trackY, track.width, trackHeight, radius, radius);
            g2.setColor(color);
            g2.fillRoundRect(track.x + 1, trackY + 1, track.width - 2, trackHeight - 2, radius, radius);
 
            g2.dispose();
        }
 
        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
            Rectangle thumb = thumbRect;
            Color fill;
            if (this.isDragging()) {
                fill = colorClicked;
            } else if (isThumbHovered()) {
                fill = colorHover;
            } else {
                fill = color;
            }
 
            g2.setColor(borderColor);
            g2.fillOval(thumb.x, thumb.y, thumb.width, thumb.height);
            g2.setColor(fill);
            g2.fillOval(thumb.x + 2, thumb.y + 2, thumb.width - 4, thumb.height - 4);
 
            g2.dispose();
        }
 
        private boolean isThumbHovered() {
            java.awt.Point p = slider.getMousePosition();
            return p != null && thumbRect.contains(p);
        }
    }
}
