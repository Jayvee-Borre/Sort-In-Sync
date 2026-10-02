/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Jayvee
 */
public class CustomDropdown extends JComboBox<String> {
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
 
    public Color getBorderColor() {
        return borderColor;
    }
 
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        setBorder(new RoundedBorder());
    }
 
    public int getRadius() {
        return radius;
    }
 
    public void setRadius(int radius) {
        this.radius = radius;
        setBorder(new RoundedBorder());
    }

    /**
     * Text alignment for the selected value shown in the closed box.
     * JComboBox has no such property out of the box - this is what makes
     * it show up as a settable property (SwingConstants.LEFT/CENTER/RIGHT)
     * in the NetBeans Properties panel, same as JLabel's own
     * horizontalAlignment.
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        repaint();
    }

    /**
     * Floor width/height so short item text (e.g. "Light"/"Dark") doesn't
     * shrink the whole control noticeably smaller than dropdowns with
     * longer items (e.g. "Select A Resolution").
     */
    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        revalidate();
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        revalidate();
    }
 
    private boolean hover;
    private Color color;
    private Color colorHover;
    private Color borderColor;
    private int radius;
    private int horizontalAlignment = SwingConstants.LEFT;
    private int minWidth = 140;
    private int minHeight = 32;
 
    public CustomDropdown() {
        // Same classic accent palette as CustomButton, so every control matches
        setColor(Color.decode("#F1D42D"));       // Classic accent fill
        colorHover = getColor().brighter();      // lighter tint of the fill
        borderColor = Color.decode("#E3B800");   // Classic accent border
        radius = 12;
 
        setUI(new ArrowOnlyUI());
        setRenderer(new AlignedRenderer());
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setFocusable(false);
        setBorder(new RoundedBorder());
        setOpaque(false);
 
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
        });
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        return new Dimension(Math.max(d.width, minWidth), Math.max(d.height, minHeight));
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
 
    /**
     * Rounded outline drawn using borderColor, matching the radius used
     * for the fill so the border tracks the same shape.
     */
    private class RoundedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
 
        @Override
        public java.awt.Insets getBorderInsets(Component c) {
            return new java.awt.Insets(4, 10, 4, 10);
        }
    }

    private class ArrowOnlyUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton arrow = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(borderColor != null ? borderColor : getForeground());

                    int w = getWidth();
                    int h = getHeight();
                    int triW = Math.max(6, w / 2);
                    int triH = Math.max(4, h / 4);
                    int cx = w / 2;
                    int cy = h / 2;

                    int[] xs = { cx - triW / 2, cx + triW / 2, cx };
                    int[] ys = { cy - triH / 2, cy - triH / 2, cy + triH / 2 };
                    g2.fillPolygon(xs, ys, 3);
                    g2.dispose();
                }
            };
            arrow.setOpaque(false);
            arrow.setContentAreaFilled(false);
            arrow.setBorderPainted(false);
            arrow.setFocusPainted(false);
            arrow.setFocusable(false);
            return arrow;
        }
    }
    
    private class AlignedRenderer extends JLabel implements ListCellRenderer<String> {
        AlignedRenderer() {
            setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setText(value == null ? "" : value);
            setHorizontalAlignment(horizontalAlignment);
            setFont(CustomDropdown.this.getFont());

            if (index >= 0) {
                // Actually inside the open popup list - fine to show the
                // usual selection highlight here.
                setOpaque(true);
                if (isSelected) {
                    setBackground(colorHover);
                    setForeground(Color.BLACK);
                } else {
                    setBackground(color);
                    setForeground(CustomDropdown.this.getForeground());
                }
            } else {
                // The closed box's "current value" area - BasicComboBoxUI
                // calls this with index == -1. Never paint a
                // selected/focused look here, which is what caused the
                // stray box around the text.
                setOpaque(false);
                setForeground(CustomDropdown.this.getForeground());
            }

            return this;
        }
    }
}
