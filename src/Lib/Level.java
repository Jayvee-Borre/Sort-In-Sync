/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

/**
 *
 * @author Jayvee
 */
public class Level {
    private String levelName;
    private SongLoader song;

    private SongLoader loadSong() {
        switch (this.levelName) {
            case "Canon":
                return null;
            case "Level 2":
                return null;
            case "Level 3":
                return null;
            default:
                return null;
        }
    }
    
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public SongLoader getSong() {
        return song;
    }

    public void setSong(SongLoader song) {
        this.song = song;
    }
    
    
}
