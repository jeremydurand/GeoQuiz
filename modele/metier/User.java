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
public class User {

    private int id;
    private String pseudo;
    private String password;
    private String mail;
    private boolean admin;
    private String ip;
    private String mac;
    private Date date;
    private String session;
    private String os;

    public User(int id, String pseudo, String password, String mail, boolean admin, String ip, String mac, Date date, String session, String os) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.mail = mail;
        this.admin = admin;
        this.ip = ip;
        this.mac = mac;
        this.date = date;
        this.session = session;
        this.os = os;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    
}
