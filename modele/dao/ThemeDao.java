/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modele.metier.Theme;

/**
 *
 * @author JAOUEN
 */
public class ThemeDao {
        /**
     * Extraction du theme selon son id
     *
     * @param id
     * @return theme
     * @throws SQLException
     */
    public static Theme getTheme(int id) throws SQLException {
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        Theme theme = null;
        // préparer la requête
        String requete = "SELECT * FROM themes WHERE id=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            String libelle = rs.getString("libelle");
            theme = new Theme(leId, libelle);
        }
        pstmt.close();
        rs.close();
        return theme;
    }
}
