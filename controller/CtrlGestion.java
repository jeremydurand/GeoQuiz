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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vues.VueGestion;

/**
 *
 * @author Benoit
 */
public class CtrlGestion implements WindowListener, ActionListener {

    private VueGestion vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;

    public CtrlGestion(VueGestion vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

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
        // préparer l'état iniitial de la vue
        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonRetour().addActionListener(this);
        vue.getjRadioButtonChoixEleve().addActionListener(this);
        vue.getjRadioButtonChoixScore().addActionListener(this);
        invisible();
        
        ButtonGroup choixGroup = new ButtonGroup();
        choixGroup.add(vue.getjRadioButtonChoixEleve());
        choixGroup.add(vue.getjRadioButtonChoixScore());
       


    }
    // contrôle de la vue

    private void invisible() {
        //activer ce qu'il faut au début

        vue.getjLabelSouhait().setVisible(true);
        vue.getjLabelTitre().setVisible(true);
        vue.getjRadioButtonChoixEleve().setVisible(true);
        vue.getjRadioButtonChoixScore().setVisible(true);
        
        vue.getjComboBoxSupEleve().setVisible(false);
        vue.getjButtonSupEleve().setVisible(false);
        
        
        vue.getjComboBoxSupScoreDifficulty().setVisible(false);
        vue.getjComboBoxSupScoreEleve().setVisible(false);
        vue.getjComboBoxSupScoreMode().setVisible(false);
        vue.getjComboBoxSupScoreTheme().setVisible(false);
        vue.getjLabelExplication1().setVisible(false);
        vue.getjLabelExplication2().setVisible(false);
        vue.getjLabelExplication3().setVisible(false);
        vue.getjLabelExplication4().setVisible(false);
        vue.getjLabelExplication5().setVisible(false);
        vue.getjLabelExplication6().setVisible(false);
        vue.getjButtonSupScore().setVisible(false);
        vue.getjRadioButtonSupScoreDifficulty().setVisible(false);
        vue.getjRadioButtonSupScoreEleve().setVisible(false);
        vue.getjRadioButtonSupScoreMode().setVisible(false);
        vue.getjRadioButtonSupScoreTheme().setVisible(false);
        
        vue.getjLabelResult().setVisible(false);
    }
    
     private void displayEleve() {
        vue.getjComboBoxSupEleve().setVisible(true);
        vue.getjButtonSupEleve().setVisible(true);
    }
     
      private void displayScore() {
        vue.getjComboBoxSupScoreDifficulty().setVisible(true);
        vue.getjComboBoxSupScoreEleve().setVisible(true);
        vue.getjComboBoxSupScoreMode().setVisible(true);
        vue.getjComboBoxSupScoreTheme().setVisible(true);
        vue.getjLabelExplication1().setVisible(true);
        vue.getjLabelExplication2().setVisible(true);
        vue.getjLabelExplication3().setVisible(true);
        vue.getjLabelExplication4().setVisible(true);
        vue.getjLabelExplication5().setVisible(true);
        vue.getjLabelExplication6().setVisible(true);
        vue.getjButtonSupScore().setVisible(true);
        vue.getjRadioButtonSupScoreDifficulty().setVisible(true);
        vue.getjRadioButtonSupScoreEleve().setVisible(true);
        vue.getjRadioButtonSupScoreMode().setVisible(true);
        vue.getjRadioButtonSupScoreTheme().setVisible(true);
    }

    // méthodes d'action
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vue.getjButtonRetour()) {
            ctrlPrincipal.afficherProfesseur();
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            quitter();
        }
         if (e.getSource() == vue.getjRadioButtonChoixEleve()) {
            invisible();
            displayEleve();
         }
          if (e.getSource() == vue.getjRadioButtonChoixScore()) {
            invisible();
            displayScore();
          }
    }

    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    // ACCESSEURS et MUTATEURS
    public VueGestion getVue() {
        return vue;
    }

    public void setVue(VueGestion vue) {
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
