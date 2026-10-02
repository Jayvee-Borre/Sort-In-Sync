/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JRadioButton;

/**
 *
 * @author Jayvee
 */
public class CustomRadioButton extends JRadioButton {

    // ---- interior fill of the ring (the "empty" part of the circle) ----
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    // ---- highlight painted behind the whole control on hover ----
    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
        repaint();
    }

    // ---- label text ----
    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
    }

    // ---- rounded-rect radius used for the hover highlight ----
    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    // ---- ring outline color while hovered ----
    public Color getRadiusOutlineColor() {
        return radiusOutlineColor;
    }

    public void setRadiusOutlineColor(Color radiusOutlineColor) {
        this.radiusOutlineColor = radiusOutlineColor;
        repaint();
    }

    // ---- ring outline color at rest ----
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        repaint();
    }

    // ---- fill color of the inner dot when selected ----
    public Color getRadioFillColor() {
        return radioFillColor;
    }

    public void setRadioFillColor(Color radioFillColor) {
        this.radioFillColor = radioFillColor;
        repaint();
    }

    public int getRadioSize() {
        return radioSize;
    }

    public void setRadioSize(int radioSize) {
        this.radioSize = radioSize;
        setIcon(new RadioIcon());
        revalidate();
        repaint();
    }

    public int getInnerCircleSize() {
        return innerCircleSize;
    }

    public void setInnerCircleSize(int innerCircleSize) {
        this.innerCircleSize = innerCircleSize;
        repaint();
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        repaint();
    }

    private Color backgroundColor = Color.WHITE;
    private Color hoverBackgroundColor;
    private Color textColor = Color.BLACK;

    private int borderRadius = 10;
    private Color radiusOutlineColor;
    private Color borderColor;
    private int borderThickness = 2;
    private Color radioFillColor;
    private int radioSize = 22;
    private int innerCircleSize = 11;
    private boolean hovered = false;

    public CustomRadioButton() {
        this(null);
    }

    public CustomRadioButton(String text) {
        super(text);

        // Same classic accent palette as CustomButton, so every control
        // in a form reads as one family.
        radioFillColor = Color.decode("#F1D42D");           // Classic accent fill (selected dot)
        radiusOutlineColor = radioFillColor.brighter();      // lighter tint, ring while hovered
        borderColor = Color.decode("#E3B800");               // Classic accent border, ring at rest
        hoverBackgroundColor = new Color(241, 212, 45, 40);  // soft accent-tinted hover glow

        setForeground(textColor);
        setFont(getFont().deriveFont(java.awt.Font.PLAIN, 14f));
        setIconTextGap(10);

        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(new RadioIcon());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                hovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Soft rounded-rect highlight behind the whole control, echoing
        // CustomButton's hover feedback.
        if (hovered) {
            g2.setColor(hoverBackgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        }

        g2.dispose();

        super.paintComponent(g); // paints the icon (RadioIcon) + label text
    }

    /**
     * Draws the actual radio circle: an outer ring (borderColor, or
     * radiusOutlineColor while hovered) with a filled interior, plus a
     * centered accent dot when selected. Kept as an Icon (rather than
     * fully overriding paint) so JRadioButton's own text layout,
     * mnemonics and accessibility keep working unmodified.
     */
    private class RadioIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Outer ring, "2px border" trick used by CustomButton: paint
            // a full circle in the ring color, then a slightly smaller
            // one on top in the interior color.
            g2.setColor(hovered ? radiusOutlineColor : borderColor);
            g2.fillOval(x, y, radioSize, radioSize);

            g2.setColor(backgroundColor);
            g2.fillOval(x + borderThickness, y + borderThickness,
                    radioSize - borderThickness * 2, radioSize - borderThickness * 2);

            if (isSelected()) {
                int inset = (radioSize - innerCircleSize) / 2;
                g2.setColor(radioFillColor);
                g2.fillOval(x + inset, y + inset, innerCircleSize, innerCircleSize);
            }

            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return radioSize;
        }

        @Override
        public int getIconHeight() {
            return radioSize;
        }
    }
}
