/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.metier;

import java.util.Date;

/**
 *
 * @author Benoit
 */
public class Score {

    private int id;
    private User user;
    private String session;
    private String os;
    private String ip;
    private String mac;
    private Theme theme;
    private Mode mode;
    private Difficulty difficulty;
    private float score;
    private Date date;

    public Score(int id, User user, String session, String os, String ip, String mac, Theme theme, Mode mode, Difficulty difficulty, float score, Date date) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.os = os;
        this.ip = ip;
        this.mac = mac;
        this.theme = theme;
        this.mode = mode;
        this.difficulty = difficulty;
        this.score = score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

}
