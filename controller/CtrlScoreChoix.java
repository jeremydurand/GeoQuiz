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
import vues.VueScoreChoix;

/**
 *
 * @author Benoit
 */
public class CtrlScoreChoix implements WindowListener, ActionListener {

    private VueScoreChoix vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private int theme;
    private int mode;
    private int difficulty;

    public CtrlScoreChoix(VueScoreChoix vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

        // le contrôleur écoute la vue
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/background_ranking.jpg")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelScoreChoix().setIcon(imageIcon); // NOI18N
        vue.getjButtonMenuPrincipal().addActionListener(this);
        vue.getjButtonQuitter().addActionListener(this);
        // préparer l'état iniitial de la vue

        if (ctrlPrincipal.getUser().isAdmin()) {
            vue.getjButtonMenuPrincipal().setText("Retour");
        }

        ButtonGroup themesGroup = new ButtonGroup();
        //ca pourrait etre des check box : on pourrait jouer dans plusieur theme a la fois....
        //a faire: les autres themes
        themesGroup.add(vue.getjRadioButtonCapitale());
        themesGroup.add(vue.getjRadioButtonDrapeau());
        /* themesGroup.add(vue.getjRadioButtonLieux());
         themesGroup.add(vue.getjRadioButtonSuperficie());
         themesGroup.add(vue.getjRadioButtonPopulation());
         themesGroup.add(vue.getjRadioButtonLangues());
         themesGroup.add(vue.getjRadioButtonContinent());
         themesGroup.add(vue.getjRadioButtonLocalisation());
         themesGroup.add(vue.getjRadioButtonFormes());*/

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(vue.getjRadioButtonClassique());
        modeGroup.add(vue.getjRadioButtonRapidite());
        modeGroup.add(vue.getjRadioButtonContreLaMontre());
        modeGroup.add(vue.getjRadioButtonSurvie());

        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(vue.getjRadioButtonFacile());
        difficultyGroup.add(vue.getjRadioButtonMoyen());
        difficultyGroup.add(vue.getjRadioButtonDifficile());
        difficultyGroup.add(vue.getjRadioButtonExpert());
        //expert pas encore dispo
        vue.getjRadioButtonExpert().setVisible(false);

        //seul le theme capitale est actif pour linstant
        //vue.getjRadioButtonCapitale().setSelected(true);
        vue.getjRadioButtonCapitale().addActionListener(this);
        vue.getjRadioButtonDrapeau().addActionListener(this);

        //reste a faire le mode survie
        vue.getjRadioButtonClassique().addActionListener(this);
        vue.getjRadioButtonContreLaMontre().addActionListener(this);
        vue.getjRadioButtonRapidite().addActionListener(this);
        vue.getjRadioButtonSurvie().addActionListener(this);// a faire
        //seul les difficultés facile moyen difficiles sont actives
        vue.getjRadioButtonFacile().addActionListener(this);
        vue.getjRadioButtonMoyen().addActionListener(this);
        vue.getjRadioButtonDifficile().addActionListener(this);
        //manque expert
        //reste a faire les autres difficultées
        vue.getjButtonAfficher().addActionListener(this);
        displayTheme();//premier affichage
    }

    // contrôle de la vue
    private void displayTheme() {
        vue.getjRadioButtonCapitale().setVisible(true);
        vue.getjRadioButtonDrapeau().setVisible(true);
        vue.getjButtonAfficher().setVisible(false);

        vue.getjRadioButtonClassique().setVisible(false);
        vue.getjRadioButtonContreLaMontre().setVisible(false);
        vue.getjRadioButtonRapidite().setVisible(false);
        vue.getjRadioButtonSurvie().setVisible(false);

        vue.getjRadioButtonFacile().setVisible(false);
        vue.getjRadioButtonMoyen().setVisible(false);
        vue.getjRadioButtonDifficile().setVisible(false);
        vue.getjRadioButtonExpert().setVisible(false);
    }

    private void displayMode() {
        vue.getjRadioButtonClassique().setVisible(true);
        vue.getjRadioButtonContreLaMontre().setVisible(true);
        vue.getjRadioButtonRapidite().setVisible(true);
        vue.getjRadioButtonSurvie().setVisible(true);
    }

    private void displayDifficulty() {
        vue.getjRadioButtonFacile().setVisible(true);
        vue.getjRadioButtonMoyen().setVisible(true);
        vue.getjRadioButtonDifficile().setVisible(true);
        //reste a faire les autres difficulté
    }

    private void displayButton() {
        vue.getjButtonAfficher().setVisible(true);
    }

    // méthodes d'action
    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getjButtonQuitter()) {
            ctrlPrincipal.quitterApplication();
        }
        if (e.getSource() == vue.getjButtonMenuPrincipal()) {
            if (ctrlPrincipal.getUser().isAdmin()) {
                ctrlPrincipal.afficherProfesseur();
            } else {
                ctrlPrincipal.afficherAccueilChoix();
            }
        }
        if (e.getSource() == vue.getjRadioButtonCapitale()) {
            theme = 1;
            displayMode();
        }
        if (e.getSource() == vue.getjRadioButtonDrapeau()) {
            theme = 2;
            displayMode();
        }
        if (e.getSource() == vue.getjRadioButtonClassique()) {
            mode = 1;
            displayDifficulty();
        }
        if (e.getSource() == vue.getjRadioButtonContreLaMontre()) {
            mode = 2;
            displayDifficulty();
        }
        if (e.getSource() == vue.getjRadioButtonRapidite()) {
            mode = 3;
            displayDifficulty();
        }
        if (e.getSource() == vue.getjRadioButtonSurvie()) {
            mode = 4;
            displayDifficulty();
        }
        if (e.getSource() == vue.getjRadioButtonFacile()) {
            difficulty = 0;
            displayButton();
        }
        if (e.getSource() == vue.getjRadioButtonMoyen()) {
            difficulty = 1;
            displayButton();
        }
        if (e.getSource() == vue.getjRadioButtonDifficile()) {
            difficulty = 2;
            displayButton();
        }
        if (e.getSource() == vue.getjButtonAfficher()) {
            ctrlPrincipal.afficherScores(theme, mode, difficulty);
        }
        //faire expert
    }

    // ACCESSEURS et MUTATEURS
    public VueScoreChoix getVue() {
        return vue;
    }

    public void setVue(VueScoreChoix vue) {
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
