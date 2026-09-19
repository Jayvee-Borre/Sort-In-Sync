/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Jayvee
 */
public class Database {
    public Database() {
        try {
            // This forces Java to load the driver you just added
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite Driver loaded from JAR file");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: NetBeans cannot find the JAR file.");
            e.printStackTrace();
        }
    }
}
