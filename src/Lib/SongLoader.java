/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Jayvee
 */
public class SongLoader {
    private String fileName;
    
    public SongLoader(String fileName) {
        this.fileName = fileName;
    }
    
    public SongLoader() {
        
    }

    public void loadChart(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingNotes = false;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.isEmpty() || line.startsWith("//") || line.equals("[METADATA]")) continue;
                
                if (line.equals("[NOTES]")) {
                    readingNotes = true;
                    continue;
                }
                
                if (readingNotes) { // We are reading notes
                    String[] parts = line.split(",");
                    // parts[0] -> beats in ms
                    // parts[1] -> keybind beat
                    System.out.println(parts[0] + parts[1]);
                } else { // We are reading metadata
                    String[] parts = line.split("=");
                    // parts[0] -> metadata title
                    // parts[1] -> metadata value
                    System.out.println(parts[0] + parts[1]);
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
