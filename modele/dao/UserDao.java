/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modele.metier.User;

/**
 *
 * @author Benoit
 */
public class UserDao {

    /**
     * Extraction de tous les utilisateurs
     *
     * @return collection d'utilisateurs
     * @throws SQLException
     */
    //ne devrait pas etre utilisé
    public static ArrayList<User> selectAll() throws SQLException {
        ArrayList<User> scores = new ArrayList<>();
        User aUser;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM users";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String pseudo = rs.getString("pseudo");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            boolean admin = rs.getBoolean("admin");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            Date date = rs.getDate("date");
            String session = rs.getString("session");
            String os = rs.getString("os");
            aUser = new User(id, pseudo, password, mail, admin, ip, mac, date, session, os);
            scores.add(aUser);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Verification de l'authentification sur le pseudo et le mot de passe
     * accéder aux fonctionnalités de l'application
     *
     * @param login identifiant (String chaine de caractere)
     * @param mdp mot de passe (String chaine de caractere)
     * @return code (int entier) : 11 si le login et le mot de passe sont
     * corrects 10 si seul le login est correct 0 (00) si les deux ne sont pas
     * corrects
     * @throws SQLException
     */
    public static int verifierInfosConnexion(String login, String mdp) throws SQLException {
        int code = -1;
        boolean bonMdp = false;
        boolean bonLogin = false;
        Jdbc jdbc = Jdbc.getInstance();
        String requete = "SELECT pseudo FROM users WHERE pseudo = ?";
        PreparedStatement pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, login);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            bonLogin = true;
            requete = "SELECT pseudo, password FROM users WHERE pseudo = ? AND password = ?";
            pstmt = jdbc.getConnexion().prepareStatement(requete);
            pstmt.setString(1, login);
            pstmt.setString(2, mdp);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bonMdp = true;
            }
        } else {
            requete = "SELECT password FROM users WHERE password = ?";
            pstmt = jdbc.getConnexion().prepareStatement(requete);
            pstmt.setString(1, mdp);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bonMdp = true;
            }
        }

        if (bonLogin) {
            if (bonMdp) {
                code = 11;
            } else {
                code = 10;
            }
        } else {
            if (!bonMdp) {
                code = 00;//sera lu comme 0
            } else {
                code = 01;//sera lu comme 1
            }
        }
        rs.close();
        pstmt.close();
        return code;
    }

    /**
     * Verification de la disponibilite d'un pseudo on ignore la casse
     *
     * @param pseudo (String chaine de caractere)
     * @return true si le pseudo est déjà pris, false sinon
     * @throws SQLException
     */
    public static boolean verifierDisponibiltePseudo(String pseudo) throws SQLException {
        Jdbc jdbc = Jdbc.getInstance();
        boolean found = false;
        String requete = "SELECT pseudo FROM users WHERE UPPER(pseudo) LIKE UPPER(?)";
        PreparedStatement pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, pseudo);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            found = true;
        }
        rs.close();
        pstmt.close();
        return found;
    }

    /**
     * Recuperer les donnees sauvegardée d'un pc
     *
     * @param mac (String chaine de caractere)
     * @return true si le pseudo est déjà pris, false sinon
     * @throws SQLException
     */
    public static ArrayList<String> getSavedData(String mac) throws SQLException {
        Jdbc jdbc = Jdbc.getInstance();
        ArrayList<String> result = new ArrayList<>();
        String requete = "SELECT pseudo, password FROM saves WHERE mac =?";
        PreparedStatement pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, mac);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result.add(rs.getString("pseudo"));
            result.add(rs.getString("password"));
        }
        rs.close();
        pstmt.close();
        return result;
    }

    /**
     * Recuperer l'id minimum des utilisateurs
     *
     * @return l'id minimum des utilisateurs
     * @throws SQLException
     */
    public static int getMinId() throws SQLException {
        Jdbc jdbc = Jdbc.getInstance();
        int id = 1;
        String requete = "SELECT MIN(id) FROM users";
        PreparedStatement pstmt = jdbc.getConnexion().prepareStatement(requete);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        rs.close();
        pstmt.close();
        return id;
    }

    /**
     * Recuperer les donnees sauvegardée d'un pc
     *
     * @param mac (String chaine de caractere)
     * @param pseudo
     * @param mdp
     * @throws SQLException
     */
    public static void updateSavedData(String mac, String pseudo, String mdp) throws SQLException {
        Jdbc jdbc = Jdbc.getInstance();
        String requete = "SELECT * FROM saves WHERE mac =?";
        PreparedStatement pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, mac);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            requete = "UPDATE `saves` SET `pseudo`=?,`password`=? WHERE mac=?";
            pstmt = jdbc.getConnexion().prepareStatement(requete);
            pstmt.setString(1, pseudo);
            pstmt.setString(2, mdp);
            pstmt.setString(3, mac);
            pstmt.executeUpdate();
        } else {
            requete = "INSERT INTO `saves`(`mac`, `pseudo`, `password`) VALUES (?,?,?)";
            pstmt = jdbc.getConnexion().prepareStatement(requete);
            pstmt.setString(1, mac);
            pstmt.setString(2, pseudo);
            pstmt.setString(3, mdp);
            pstmt.executeUpdate();
        }
        rs.close();
        pstmt.close();
    }

    /**
     * Extraction d'un utilisateur a partir de son id
     *
     * @param id
     * @return un utilisateur lié à l'id
     * @throws SQLException
     */
    public static User selectOneById(int id) throws SQLException {
        User aUser = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM users WHERE id=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            String pseudo = rs.getString("pseudo");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            boolean admin = rs.getBoolean("admin");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            Date date = rs.getDate("date");
            String session = rs.getString("session");
            String os = rs.getString("os");
            aUser = new User(leId, pseudo, password, mail, admin, ip, mac, date, session, os);
        }
        pstmt.close();
        rs.close();
        return aUser;
    }

    /**
     * Extraction d'un utilisateur a partir de son pseudo
     *
     * @param pseudo
     * @return un utilisateur lié au pseudo
     * @throws SQLException
     */
    public static User selectOneByPseudo(String pseudo) throws SQLException {
        User aUser = null;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM users WHERE pseudo=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, pseudo);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            String lePseudo = rs.getString("pseudo");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            boolean admin = rs.getBoolean("admin");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            Date date = rs.getDate("date");
            String session = rs.getString("session");
            String os = rs.getString("os");
            aUser = new User(leId, lePseudo, password, mail, admin, ip, mac, date, session, os);
        }
        pstmt.close();
        rs.close();
        return aUser;
    }

    /**
     * Insertion d'un nouvel utilisateur
     *
     * @param user
     * @throws SQLException
     */
    public static void insert(User user) throws SQLException {
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "INSERT INTO `users`(`pseudo`, `password`, `mail`, `admin`, `ip`, `mac`, `date`, `session`, `os` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, user.getPseudo());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getMail());
        pstmt.setBoolean(4, user.isAdmin());
        pstmt.setString(5, user.getIp());
        pstmt.setString(6, user.getMac());
        pstmt.setTimestamp(7, new java.sql.Timestamp(user.getDate().getTime()));
        pstmt.setString(8, user.getSession());
        pstmt.setString(9, user.getOs());
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Mise à jour des informations d'un utilisateur
     *
     * @param user
     * @throws SQLException
     */
    public static void update(User user) throws SQLException {
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "UPDATE `users` SET `pseudo`=?, `password`=?, `mail`=?, `admin`=?, `ip`=?, `mac`=?, `date`=?, `session`=?, `os`=? ";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, user.getPseudo());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getMail());
        pstmt.setBoolean(4, user.isAdmin());
        pstmt.setString(5, user.getIp());
        pstmt.setString(6, user.getMac());
        pstmt.setTimestamp(7, new java.sql.Timestamp(user.getDate().getTime()));
        pstmt.setString(8, user.getSession());
        pstmt.setString(9, user.getOs());
        pstmt.executeUpdate();
        pstmt.close();
    }
}
