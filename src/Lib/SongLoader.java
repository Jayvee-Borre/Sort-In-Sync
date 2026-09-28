/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jayvee
 */
public class SongLoader {
    private String fileName;
    private List<Note> notes = new ArrayList<>();
    public int bpm;
    public String audioFile;
    
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
                
                if (readingNotes) { 
                    String[] parts = line.split(",");
                    int time = Integer.parseInt(parts[0]);
                    String keys = parts[1];
                    notes.add(new Note(time, keys)); // Store the note
                } else { 
                    String[] parts = line.split("=");
                    if (parts[0].equals("bpm")) bpm = Integer.parseInt(parts[1]);
                    if (parts[0].equals("audioFile")) audioFile = parts[1];
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    
    public List<Note> getNotes() {
        return notes;
    }
}
