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
import modele.metier.Difficulty;
import modele.metier.Mode;
import modele.metier.Score;
import modele.metier.Theme;
import modele.metier.User;

/**
 *
 * @author Benoit
 */
public class ScoreDao {

    /**
     * Extraction de tous les scores
     *
     * @return collection de scores
     * @throws SQLException
     */
    //ne devrait pas etre utilisé
    public static ArrayList<Score> selectAll() throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        Score aScore;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM scores ORDER BY score DESC";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int idUser = rs.getInt("id_user");
            User aUser = UserDao.selectOneById(idUser);
            String session = rs.getString("session");
            String os = rs.getString("os");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            int theme = rs.getInt("theme");
            int mode = rs.getInt("mode");
            int difficulty = rs.getInt("difficulty");
            float score = rs.getFloat("score");
            Date date = rs.getDate("date");
            aScore = new Score(id, aUser, session, os, ip, mac, ThemeDao.getTheme(theme), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date);
            scores.add(aScore);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Extraction de tous les scores d'un joueur
     *
     * @return collection de scores
     * @throws SQLException
     */
    public static ArrayList<Score> selectAllByUserId(int id) throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        Score aScore;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM scores WHERE id_user =? ORDER BY score DESC";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int leId = rs.getInt("id");
            int idUser = rs.getInt("id_user");
            User aUser = UserDao.selectOneById(idUser);
            String session = rs.getString("session");
            String os = rs.getString("os");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            int theme = rs.getInt("theme");
            int mode = rs.getInt("mode");
            int difficulty = rs.getInt("difficulty");
            float score = rs.getFloat("score");
            Date date = rs.getDate("date");
            aScore = new Score(leId, aUser, session, os, ip, mac, ThemeDao.getTheme(theme), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date);
            scores.add(aScore);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Extraction de tous les scores selon le mode
     *
     * @param theme
     * @param mode
     * @param difficulty
     * @return collection de scores
     * @throws SQLException
     */
    public static ArrayList<Score> selectAllByThemeModeAndDifficulty(int theme, int mode, int difficulty) throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        Score aScore;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "";
        if (mode == 3) {
            requete = "SELECT * FROM scores WHERE theme=? AND mode=? AND difficulty=? ORDER BY score ASC";
        } else {
            requete = "SELECT * FROM scores WHERE theme=? AND mode=? AND difficulty=? ORDER BY score DESC";
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, theme);
        pstmt.setInt(2, mode);
        pstmt.setInt(3, difficulty);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int idUser = rs.getInt("id_user");
            User aUser = UserDao.selectOneById(idUser);
            String session = rs.getString("session");
            String os = rs.getString("os");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            int leTheme = rs.getInt("theme");
            int leMode = rs.getInt("mode");
            int laDifficulty = rs.getInt("difficulty");
            float score = rs.getFloat("score");
            Date date = rs.getDate("date");
            aScore = new Score(id, aUser, session, os, ip, mac, ThemeDao.getTheme(theme), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date);
            scores.add(aScore);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Extraction des score à partir d'un certain rang
     *
     * @return collection de scores
     * @throws SQLException
     */
    //ne devrait pas etre utilisé
    public static ArrayList<Score> selectBest(int rank) throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        Score aScore;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM scores ORDER BY score DESC LIMIT 0 , ?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, rank);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int idUser = rs.getInt("id_user");
            User aUser = UserDao.selectOneById(idUser);
            String session = rs.getString("session");
            String os = rs.getString("os");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            int theme = rs.getInt("theme");
            int mode = rs.getInt("mode");
            int difficulty = rs.getInt("difficulty");
            float score = rs.getFloat("score");
            Date date = rs.getDate("date");
            aScore = new Score(id, aUser, session, os, ip, mac, ThemeDao.getTheme(theme), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date);
            scores.add(aScore);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Extraction des score d'un mode à partir d'un certain rang
     *
     * @param rank
     * @param theme
     * @param mode
     * @param difficulty
     * @return collection de scores
     * @throws SQLException
     */
    public static ArrayList<Score> selectBestByThemeModeAndDifficulty(int rank, int theme, int mode, int difficulty) throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        Score aScore;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "";
        if (mode == 3) {
            requete = "SELECT * FROM scores WHERE theme=? AND mode=? AND difficulty=? ORDER BY score ASC LIMIT 0 , ?";
        } else {
            requete = "SELECT * FROM scores WHERE theme=? AND mode=? AND difficulty=? ORDER BY score DESC LIMIT 0 , ?";
        }
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, rank);
        pstmt.setInt(2, theme);
        pstmt.setInt(3, mode);
        pstmt.setInt(4, difficulty);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int idUser = rs.getInt("id_user");
            User aUser = UserDao.selectOneById(idUser);
            String session = rs.getString("session");
            String os = rs.getString("os");
            String ip = rs.getString("ip");
            String mac = rs.getString("mac");
            int leTheme = rs.getInt("theme");
            int leMode = rs.getInt("mode");
            int laDifficulty = rs.getInt("difficulty");
            float score = rs.getFloat("score");
            Date date = rs.getDate("date");
            aScore = new Score(id, aUser, session, os, ip, mac, ThemeDao.getTheme(theme), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date);
            scores.add(aScore);
        }
        pstmt.close();
        rs.close();
        return scores;
    }

    /**
     * Insertion d'un nouveau score
     *
     * @param score
     * @throws SQLException
     */
    public static void insert(Score score) throws SQLException {
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "INSERT INTO `scores`(`id_user`, `session`, `os`, `ip`, `mac`, `theme`, `mode`, `difficulty`, `score` , `date`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, score.getUser().getId());
        pstmt.setString(2, score.getSession());
        pstmt.setString(3, score.getOs());
        pstmt.setString(4, score.getIp());
        pstmt.setString(5, score.getMac());
        pstmt.setInt(6, score.getTheme().getId());
        pstmt.setInt(7, score.getMode().getId());
        pstmt.setInt(8, score.getDifficulty().getId());
        pstmt.setFloat(9, score.getScore());
        pstmt.setTimestamp(10, new java.sql.Timestamp(score.getDate().getTime()));
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Mise à jour du meilleur score d'un joueur
     *
     * @param score
     * @throws SQLException
     */
    public static void update(Score score) throws SQLException {
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "UPDATE `scores` SET `session`=?,`os`=?,`ip`=?,`mac`=?,`score`=?,`date`=? WHERE `id_user`=? AND `theme`=? AND `mode`=? AND `difficulty`=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, score.getSession());
        pstmt.setString(2, score.getOs());
        pstmt.setString(3, score.getIp());
        pstmt.setString(4, score.getMac());
        pstmt.setFloat(5, score.getScore());
        pstmt.setTimestamp(6, new java.sql.Timestamp(score.getDate().getTime()));
        pstmt.setInt(7, score.getUser().getId());
        pstmt.setInt(8, score.getTheme().getId());
        pstmt.setInt(9, score.getMode().getId());
        pstmt.setInt(10, score.getDifficulty().getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
}
