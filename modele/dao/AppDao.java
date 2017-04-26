/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JAOUEN
 */
public class AppDao {
        /**
     * Extraction de numéro de version de l'application
     *
     * @return float numéro de version de l'application
     * @throws SQLException
     */
    public static float getVersionNum() throws SQLException {
        float version = 0;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT version FROM app";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            version = rs.getFloat("version");
        }
        pstmt.close();
        rs.close();
        return version;
    }
}
