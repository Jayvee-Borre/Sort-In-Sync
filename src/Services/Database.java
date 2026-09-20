/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jayvee
 */
public class Database {
    private static final String URL = "jdbc:sqlite:sortinsyncr.db";
    
    public Database() {
        try {
            // This forces Java to load the driver you just added
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite Driver loaded from JAR file");
            createTables();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: NetBeans cannot find the JAR file.");
            e.printStackTrace();
        }
    }
    
    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement pragma = conn.createStatement()) {
            pragma.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    private void createTables() {
        String[] ddl = {
            "CREATE TABLE IF NOT EXISTS User (" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT NOT NULL UNIQUE," +
                "UserPass TEXT NOT NULL)",

            "CREATE TABLE IF NOT EXISTS Options (" +
                "OptionsID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WindowResolution TEXT NOT NULL," +
                "Theme TEXT NOT NULL," +
                "Font TEXT NOT NULL," +
                "VolumeSlider INTEGER NOT NULL DEFAULT 50)",

            "CREATE TABLE IF NOT EXISTS Song (" +
                "SongID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT NOT NULL," +
                "BPM INTEGER NOT NULL," +
                "AudioPath TEXT NOT NULL," +
                "ChartPath TEXT NOT NULL)",

            "CREATE TABLE IF NOT EXISTS UserOptions (" +
                "UserOptionsID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "OptionsID INTEGER NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE," +
                "FOREIGN KEY (OptionsID) REFERENCES Options(OptionsID) ON DELETE CASCADE)",

            "CREATE TABLE IF NOT EXISTS Leaderboard (" +
                "LeaderboardID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SongID INTEGER NOT NULL," +
                "UserID INTEGER NOT NULL," +
                "Score INTEGER NOT NULL," +
                "FOREIGN KEY (SongID) REFERENCES Song(SongID) ON DELETE CASCADE," +
                "FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE)"
        };

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            for (String sql : ddl) {
                stmt.execute(sql);
            }
            System.out.println("Tables verified/created.");
        } catch (SQLException e) {
            System.out.println("Error creating tables.");
            e.printStackTrace();
        }
    }
}
