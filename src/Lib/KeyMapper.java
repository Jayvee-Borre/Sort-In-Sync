/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jayvee
 */
public class KeyMapper {
    public static enum KEY_MAP {
        DEFAULT(null),
        LEFT("Left"),
        RIGHT("Right"),
        UP("Up"),
        DOWN("Down");
        
        private final String keymap;
        KEY_MAP(String map) {
            this.keymap = map;
        }
        
        public String getKeyMap() {
            return this.keymap;
        }
    };
    
    private KEY_MAP keymap = KEY_MAP.DEFAULT;

    public KeyMapper() {
        
    }
    
    public KEY_MAP getKeymap() {
        return keymap;
    }
    
    public void validateKey(int key) {
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            switch (key) {
                case KeyEvent.VK_UP:
                    System.out.println("Pressing up");
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("Pressing down");
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("Pressing left");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("Pressing right");
                    break;
            }
        }
    }
    
    public boolean compareKeys() {
        return false;
    }
}
