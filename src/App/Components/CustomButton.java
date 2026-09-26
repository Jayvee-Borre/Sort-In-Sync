/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Components;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Jayvee
 */
public class CustomButton extends JButton {
    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
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
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public CustomButton() {
        setColor(new Color(66, 133, 244));
        colorHover = new Color(90, 151, 255);
        colorClicked = new Color(48, 105, 209);
        borderColor = new Color(33, 89, 189);
        radius = 20;
        setContentAreaFilled(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorHover);
                hover = true;
            }
            
            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                hover = false;
            }
            
            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClicked);
            }
            
            @Override
            public void mouseReleased(MouseEvent me) {
                if (hover) {
                    setBackground(colorHover);
                } else {
                    setBackground(color);
                }
            }
        });
    }
    
    private boolean hover;
    private Color color;
    private Color colorHover;
    private Color colorClicked;
    private Color borderColor;
    private int radius;
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g; 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // border paint
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        
        // 2 px border
        g2.fillRoundRect(1, 1, getWidth() - 4, getHeight(), radius, radius);
        
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
