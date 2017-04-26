/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import modele.dao.UserDao;
import modele.metier.User;
import vues.VueProfesseur;

/**
 *
 * @author Benoit
 */
public class CtrlProfesseur implements WindowListener, ActionListener {

    private VueProfesseur vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private DefaultComboBoxModel modelComboBoxModel = new DefaultComboBoxModel();
    private ArrayList<User> users;

    public CtrlProfesseur(VueProfesseur vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonDeconnexion().addActionListener(this);
        vue.getjButtonScores().addActionListener(this);
        vue.getjButtonJouer().addActionListener(this);
        vue.getjButtonRechercher().addActionListener(this);
        vue.getjButtonGestion().addActionListener(this);

        // le contrôleur écoute la vue
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/background_neutre.jpg")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N
        vue.getjComboBoxEleve().setModel(modelComboBoxModel);
        remplirComboBoxJoueurs();
        // préparer l'état iniitial de la vue

    }

    private void remplirComboBoxJoueurs() {
        users = new ArrayList<User>();
        try {
            users = UserDao.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlProfesseur.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(User user : users){
            modelComboBoxModel.addElement(user.getPseudo());
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vue.getjButtonScores()) {
            ctrlPrincipal.afficherScoreChoix();
        }
        if (e.getSource() == vue.getjButtonJouer()) {
            ctrlPrincipal.afficherAccueilChoix();
        }
        if (e.getSource() == vue.getjButtonDeconnexion()) {
            ctrlPrincipal.afficherAccueil(vue);
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            quitter();
        }
        if (e.getSource() == vue.getjButtonRechercher()) {
            ctrlPrincipal.afficherScoreEleve(users.get(vue.getjComboBoxEleve().getSelectedIndex()).getId());
        }
        if (e.getSource() == vue.getjButtonGestion()) {
            ctrlPrincipal.afficherGestion();
        }
    }

    // méthodes d'action
    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    // ACCESSEURS et MUTATEURS
    public VueProfesseur getVue() {
        return vue;
    }

    public void setVue(VueProfesseur vue) {
        this.vue = vue;
    }

    // REACTIONS EVENEMENTIELLES
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        quitter();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
