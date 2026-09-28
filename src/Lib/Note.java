/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import javax.swing.ImageIcon;

/**
 *
 * @author Jayvee
 */
public class Note {
    public int timeMs; // E.g., 952
    public String keys; // E.g., "LR"
    public ImageIcon icon; // The trash image to display
    public boolean hit = false;
    
    public Note(int timeMs, String keys) {
        this.timeMs = timeMs;
        this.keys = keys;
    }
}
