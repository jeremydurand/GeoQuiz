/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modele.metier.Mode;

/**
 *
 * @author JAOUEN
 */
public class ModeDao {
        /**
     * Extraction du mode selon son id
     *
     * @param id
     * @return mode
     * @throws SQLException
     */
    public static Mode getMode(int id) throws SQLException {
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        Mode mode = null;
        // préparer la requête
        String requete = "SELECT * FROM modes WHERE id=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            String libelle = rs.getString("libelle");
            mode = new Mode(leId, libelle);
        }
        pstmt.close();
        rs.close();
        return mode;
    }
}
