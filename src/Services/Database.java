/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            createDummyData();
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
    
    public int getSongIdByName(String title) {
        String sql = "SELECT SongID FROM Song WHERE Title = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("SongID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
                "MaxCombo INTEGER NOT NULL DEFAULT 0," +
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
    
    // ------------------------------------------------------------------
    // User authentication
    // ------------------------------------------------------------------

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO User (Username, UserPass) VALUES (?, ?)";
        try (Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Fails here if Username already exists (UNIQUE constraint)
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE Username = ? AND UserPass = ?";
        try (Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getUserId(String username) {
        String sql = "SELECT UserID FROM User WHERE Username = ?";
        try (Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // ------------------------------------------------------------------
    // Options (settings persistence)
    // ------------------------------------------------------------------

    // Saves/updates a user's settings. Inserts a new Options row + UserOptions
    // link the first time; updates the existing linked Options row after that.
    
    public boolean saveUserOptions(int userId, String resolution, String theme, String font, int volume) {
        String existingOptionsSql = "SELECT OptionsID FROM UserOptions WHERE UserID = ?";
        try (Connection conn = connect()) {
            int optionsId = -1;
            try (PreparedStatement ps = conn.prepareStatement(existingOptionsSql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) optionsId = rs.getInt("OptionsID");
                }
            }
            
            if (optionsId == -1) {
                // First time saving settings for this user: insert Options row, then link it
                String insertOptions = "INSERT INTO Options (WindowResolution, Theme, Font, VolumeSlider) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertOptions, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, resolution);
                    ps.setString(2, theme);
                    ps.setString(3, font);
                    ps.setInt(4, volume);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) optionsId = keys.getInt(1);
                    }
                }
                
                String linkSql = "INSERT INTO UserOptions (UserID, OptionsID) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(linkSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, optionsId);
                    ps.executeUpdate();
                }
            } else {
                // Already has settings: update the linked Options row in place
                String updateSql = "UPDATE Options SET WindowResolution = ?, Theme = ?, Font = ?, VolumeSlider = ? WHERE OptionsID = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, resolution);
                    ps.setString(2, theme);
                    ps.setString(3, font);
                    ps.setInt(4, volume);
                    ps.setInt(5, optionsId);
                    ps.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public OptionsData getUserOptions(int userId) {
        String sql = "SELECT o.OptionsID, o.WindowResolution, o.Theme, o.Font, o.VolumeSlider " +
                     "FROM Options o JOIN UserOptions uo ON o.OptionsID = uo.OptionsID " +
                     "WHERE uo.UserID = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OptionsData(
                        rs.getInt("OptionsID"),
                        rs.getString("WindowResolution"),
                        rs.getString("Theme"),
                        rs.getString("Font"),
                        rs.getInt("VolumeSlider")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No saved setting yet for this user
    }
    
    // ------------------------------------------------------------------
    // Songs
    // ------------------------------------------------------------------
    
    public int insertSong(String title, int bpm, String audioPath, String chartPath) {
        String sql = "INSERT INTO Song (Title, BPM, AudioPath, ChartPath) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setInt(2, bpm);
            ps.setString(3, audioPath);
            ps.setString(4, chartPath);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT SongID, Title, BPM, AudioPath, ChartPath FROM Song";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                songs.add(new Song(
                    rs.getInt("SongID"),
                    rs.getString("Title"),
                    rs.getInt("BPM"),
                    rs.getString("AudioPath"),
                    rs.getString("ChartPath")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
    
    // ------------------------------------------------------------------
    // Leaderboard
    // ------------------------------------------------------------------
    
    public boolean recordScore(int userId, int songId, int score, int maxCombo) {
        String sql = "INSERT INTO Leaderboard (SongID, UserID, Score, MaxCombo) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, songId);
            ps.setInt(2, userId);
            ps.setInt(3, score);
            ps.setInt(4, maxCombo); // <-- ADDED
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<LeaderboardEntry> getLeaderboardForSong(int songId) {
        List<LeaderboardEntry> entries = new ArrayList<>();
        String sql = "SELECT u.Username, l.Score, l.MaxCombo FROM Leaderboard l " + // <-- ADDED l.MaxCombo
                     "JOIN User u ON l.UserID = u.UserID " +
                     "WHERE l.SongID = ? ORDER BY l.Score DESC";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entries.add(new LeaderboardEntry(rs.getString("Username"), rs.getInt("Score"), rs.getInt("MaxCombo"))); // <-- ADDED MaxCombo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
    
    // ------------------------------------------------------------------
    // Dummy/seed data
    // ------------------------------------------------------------------

    // Safe to call every startup: only seeds if the User table is empty,
    // so it never throws a UNIQUE-constraint error on repeat runs.
    public void createDummyData() {
        String checkSql = "SELECT COUNT(*) FROM User";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Dummy data already present, skipping seed.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Seeding dummy data...");

        // 3 users (plaintext passwords)
        registerUser("eco_ace", "leaf123");
        registerUser("recycle_rio", "binit456");
        registerUser("guest", "guestpass");

        int u1 = getUserId("eco_ace");
        int u2 = getUserId("recycle_rio");
        int u3 = getUserId("guest");

        // 3 distinct settings profiles
        saveUserOptions(u1, "1280x720", "Dark", "SansSerif", 70);
        saveUserOptions(u2, "1024x768", "Light", "SansSerif", 50);
        saveUserOptions(u3, "800x600", "Light", "Monospaced", 40);

        // Insert ALL 5 game levels. The Title MUST exactly match the LevelSelect labels
        int canonId = insertSong("Canon", 126, "Songs/Canon.wav", "Charts/Canon/");
        int cancanId = insertSong("CanCan", 140, "Songs/CanCan.wav", "Charts/CanCan/");
        int twotigersId = insertSong("TwoTigers", 110, "Songs/TwoTigers.wav", "Charts/TwoTigers/");
        int springId = insertSong("Spring", 120, "Songs/Spring.wav", "Charts/Spring/");
        int beyerId = insertSong("BeyerNo8", 100, "Songs/BeyerNo8.wav", "Charts/BeyerNo8/");

        // Seed a few dummy scores for testing
        recordScore(u1, canonId, 9850, 30);
        recordScore(u2, canonId, 8700, 40);
        recordScore(u3, canonId, 6400, 82);
        
        recordScore(u1, cancanId, 12500, 26);
        recordScore(u2, twotigersId, 8000, 42);
        recordScore(u1, springId, 15000, 12);
        recordScore(u3, beyerId, 4200, 12);

        System.out.println("Dummy data seeded.");
    }
    
    // ------------------------------------------------------------------
    // Simple data holder classes
    // ------------------------------------------------------------------

    public static class Song {
        public final int songId;
        public final String title;
        public final int bpm;
        public final String audioPath;
        public final String chartPath;

        public Song(int songId, String title, int bpm, String audioPath, String chartPath) {
            this.songId = songId;
            this.title = title;
            this.bpm = bpm;
            this.audioPath = audioPath;
            this.chartPath = chartPath;
        }
    }
    
    public static class OptionsData {
        public final int optionsId;
        public final String resolution;
        public final String theme;
        public final String font;
        public final int volume;

        public OptionsData(int optionsId, String resolution, String theme, String font, int volume) {
            this.optionsId = optionsId;
            this.resolution = resolution;
            this.theme = theme;
            this.font = font;
            this.volume = volume;
        }
    }
    
    public static class LeaderboardEntry {
        public final String username;
        public final int score;
        public final int maxCombo;

        public LeaderboardEntry(String username, int score, int maxCombo) {
            this.username = username;
            this.score = score;
            this.maxCombo = maxCombo;
        }
    }
}
