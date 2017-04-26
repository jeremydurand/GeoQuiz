/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geoquiz;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import modele.dao.Jdbc;

/**
 *
 * @author Benoit
 */
public class Connexion {
    public static void connecter(){
         Jdbc.creer("com.mysql.jdbc.Driver", "jdbc:mysql:", "//78.216.20.115/", "GEO", "geo", "geoadmin");
         //Jdbc.creer("com.mysql.jdbc.Driver", "jdbc:mysql:", "//localhost/", "jdurand_geoquiz", "root", "joliverie");
        //Jdbc.creer("com.mysql.jdbc.Driver", "jdbc:mysql:", "//192.168.0.28/", "GEO", "geo", "geoadmin");
        try {
            Jdbc.getInstance().connecter();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Main - classe JDBC non trouvée :\n" + ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Main - échec de connexion: \n" + ex);
        }
    }
}
