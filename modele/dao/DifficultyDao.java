/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modele.metier.Difficulty;

/**
 *
 * @author JAOUEN
 */
public class DifficultyDao {
        /**
     * Extraction de la difficulté selon son id
     *
     * @param id
     * @return difficulty
     * @throws SQLException
     */
    public static Difficulty getDifficulty(int id) throws SQLException {
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        Difficulty difficulty = null;
        // préparer la requête
        String requete = "SELECT * FROM difficulty WHERE id=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            String libelle = rs.getString("libelle");
            difficulty = new Difficulty(leId, libelle);
        }
        pstmt.close();
        rs.close();
        return difficulty;
    }
}
