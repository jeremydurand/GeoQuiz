/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vues.VueMenuChoix;

/**
 *
 * @author Benoit
 */
public class CtrlMenuChoix implements WindowListener, ActionListener {

    private VueMenuChoix vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private boolean trainingMode;
    private int theme;
    private int mode;
    private int difficulty;// pas nécessaire pour linstant

    public CtrlMenuChoix(VueMenuChoix vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

        // le contrôleur écoute la vue
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/background_choice.jpg")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N

        if (ctrlPrincipal.getUser().isAdmin()) {
            vue.getjButtonDeconnexion().setText("Retour");
        }

        ButtonGroup themesGroup = new ButtonGroup();
        //ca pourrait etre des check box : on pourrait jouer dans plusieurs themes à la fois...
        themesGroup.add(vue.getjRadioButtonCapitale());
        themesGroup.add(vue.getjRadioButtonDrapeau());
        themesGroup.add(vue.getjRadioButtonLieux());
        themesGroup.add(vue.getjRadioButtonSuperficie());
        themesGroup.add(vue.getjRadioButtonPopulation());
        themesGroup.add(vue.getjRadioButtonLangues());
        themesGroup.add(vue.getjRadioButtonContinent());
        themesGroup.add(vue.getjRadioButtonLocalisation());
        themesGroup.add(vue.getjRadioButtonFormes());

        ButtonGroup gameplayGroup = new ButtonGroup();
        gameplayGroup.add(vue.getjRadioButtonCompetition());
        gameplayGroup.add(vue.getjRadioButtonEntrainement());

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(vue.getjRadioButtonClassique());
        vue.getjRadioButtonClassique().setToolTipText("1 mauvaise réponse et c'est la mort");
        modeGroup.add(vue.getjRadioButtonRapidite());
        vue.getjRadioButtonRapidite().setToolTipText("15 bonnes réponses le plus vite possible");
        modeGroup.add(vue.getjRadioButtonContreLaMontre());
        vue.getjRadioButtonContreLaMontre().setToolTipText("le plus de bonnes réponses en 1 min");
        modeGroup.add(vue.getjRadioButtonSurvie());
        vue.getjRadioButtonSurvie().setToolTipText("survivre le plus longtemps possible");

        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonDeconnexion().addActionListener(this);
        vue.getjButtonBestScores().addActionListener(this);//a faire
        vue.getjButtonOptions().addActionListener(this);// a faire
        // préparer l'état iniitial de la vue
        trainingMode = false;
        trainingMode();
        //pour linstant seul le mode compet est actif
        //vue.getjRadioButtonCompetition().setSelected(true);
        vue.getjRadioButtonCompetition().addActionListener(this);

        //seul le theme capitale est actif pour linstant
        //vue.getjRadioButtonCapitale().setSelected(true);
        vue.getjRadioButtonCapitale().addActionListener(this);
        vue.getjRadioButtonDrapeau().addActionListener(this);
        vue.getjRadioButtonLieux().addActionListener(this);

        //reste a faire le mode survie
        vue.getjRadioButtonClassique().addActionListener(this);
        vue.getjRadioButtonContreLaMontre().addActionListener(this);
        vue.getjRadioButtonRapidite().addActionListener(this);
        vue.getjRadioButtonSurvie().addActionListener(this);// a faire
        //seul les difficultés facil moyen difficiles sont actives
        vue.getjButtonFacile().addActionListener(this);
        vue.getjButtonMoyen().addActionListener(this);
        vue.getjButtonDifficile().addActionListener(this);
        vue.getjButtonExpert().addActionListener(this);
        vue.getjButtonUltra().addActionListener(this);

        vue.getjButtonFeedback().setVisible(false);
        vue.getjButtonProfil().setVisible(false);
        //reste a faire les autres difficultées
        displayGameplay();//premier affichage

    }
    // contrôle de la vue

    //au fur et a mesure que l'on intègrera des fonctionnalité en mode classé on les retira de cette procédure
    private void trainingMode() {
        //tout désactiver au début
        vue.getjRadioButtonCapitale().setVisible(false);

        vue.getjRadioButtonClassique().setVisible(false);
        vue.getjRadioButtonContreLaMontre().setVisible(false);
        vue.getjRadioButtonRapidite().setVisible(false);
        vue.getjRadioButtonSurvie().setVisible(false);
        vue.getjRadioButtonSprint().setVisible(false);

        vue.getjButtonFacile().setVisible(false);
        vue.getjButtonMoyen().setVisible(false);
        vue.getjButtonDifficile().setVisible(false);
        //utilitaires
        vue.getjButtonOptions().setVisible(trainingMode);
        //gameplay
        vue.getjRadioButtonEntrainement().setVisible(trainingMode);
        //theme
        vue.getjRadioButtonDrapeau().setVisible(trainingMode);
        vue.getjRadioButtonLieux().setVisible(trainingMode);
        vue.getjRadioButtonSuperficie().setVisible(trainingMode);
        vue.getjRadioButtonPopulation().setVisible(trainingMode);
        vue.getjRadioButtonLangues().setVisible(trainingMode);
        vue.getjRadioButtonContinent().setVisible(trainingMode);
        vue.getjRadioButtonLocalisation().setVisible(trainingMode);
        vue.getjRadioButtonFormes().setVisible(trainingMode);
        //modes
        //vue.getjRadioButtonSurvie().setVisible(trainingMode);
        vue.getjRadioButtonSprint().setVisible(trainingMode);
        //difficulté
        vue.getjButtonExpert().setVisible(trainingMode);
        vue.getjButtonStandard().setVisible(trainingMode);
        vue.getjButtonImpossible().setVisible(trainingMode);
        vue.getjButtonCroissante().setVisible(trainingMode);
        vue.getjButtonPiege().setVisible(trainingMode);
        vue.getjButtonSubtile().setVisible(trainingMode);
        vue.getjButtonSimilaire().setVisible(trainingMode);
        vue.getjButtonUltra().setVisible(trainingMode);
        //autres
        vue.getjCheckBonus().setVisible(trainingMode);
        vue.getjCheckBoxInverse().setVisible(trainingMode);
        //mode reponse
        vue.getjLabelModeReponse().setVisible(trainingMode);
        vue.getjRadioButtonDuo().setVisible(trainingMode);
        vue.getjRadioButtonCarre().setVisible(trainingMode);
        vue.getjRadioButtonCash().setVisible(trainingMode);
        //continent
        vue.getjLabelContinent().setVisible(trainingMode);
        vue.getjCheckBoxAfrique().setVisible(trainingMode);
        vue.getjCheckBoxAsie().setVisible(trainingMode);
        vue.getjCheckBoxAmeriqueDuNord().setVisible(trainingMode);
        vue.getjCheckBoxAmeriqueDuSud().setVisible(trainingMode);
        vue.getjCheckBoxEurope().setVisible(trainingMode);
        vue.getjCheckBoxAntartique().setVisible(trainingMode);
        vue.getjCheckBoxOceanie().setVisible(trainingMode);
        vue.getjRadioButtonMonde().setVisible(trainingMode);
    }

    private void displayGameplay() {
        vue.getjRadioButtonCompetition().setVisible(true);
    }

    private void displayTheme() {
        vue.getjRadioButtonCapitale().setVisible(true);
        vue.getjRadioButtonDrapeau().setVisible(true);
        vue.getjRadioButtonLieux().setVisible(true);
        //faire les autres themes
    }

    private void displayMode() {
        vue.getjRadioButtonClassique().setVisible(true);
        vue.getjRadioButtonContreLaMontre().setVisible(true);
        vue.getjRadioButtonRapidite().setVisible(true);
        //faire mode survie
        vue.getjRadioButtonSurvie().setVisible(true);
    }

    private void displayDifficulty() {
        vue.getjButtonFacile().setVisible(true);
        vue.getjButtonMoyen().setVisible(true);
        vue.getjButtonDifficile().setVisible(true);
        vue.getjButtonExpert().setVisible(true);
        vue.getjButtonUltra().setVisible(true);
        //reste a faire les autres difficulté
    }
    // méthodes d'action

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getjRadioButtonCompetition()) {
            displayTheme();
        }
        if (e.getSource() == vue.getjRadioButtonCapitale()) {
            theme = 1;
            displayMode();
        }
        if (e.getSource() == vue.getjRadioButtonDrapeau()) {
            theme = 2;
            displayMode();
        }
        if (e.getSource() == vue.getjRadioButtonLieux()) {
            theme = 3;
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
        if (e.getSource() == vue.getjButtonFacile()) {
            launchQuiz(0);
        }
        if (e.getSource() == vue.getjButtonMoyen()) {
            launchQuiz(1);
        }
        if (e.getSource() == vue.getjButtonDifficile()) {
            launchQuiz(2);
        }
        if (e.getSource() == vue.getjButtonExpert()) {
            launchQuiz(3);
        }
        if (e.getSource() == vue.getjButtonUltra()) {
            launchQuiz(4);
        }
        if (e.getSource() == vue.getjButtonDeconnexion()) {
            if (ctrlPrincipal.getUser().isAdmin()) {
                ctrlPrincipal.afficherProfesseur();
            } else {
                ctrlPrincipal.afficherAccueil(vue);
            }
        }
        if (e.getSource() == vue.getjButtonBestScores()) {
            ctrlPrincipal.afficherScoreChoix();
        }
        if (e.getSource() == vue.getjButtonOptions()) {
            //afficher les options (le son)
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            quitter();
        }
    }

    private void launchQuiz(int difficulty) {
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ctrlPrincipal.start(theme, mode, difficulty);
        vue.setCursor(null);
    }

    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    // ACCESSEURS et MUTATEURS
    public VueMenuChoix getVue() {
        return vue;
    }

    public void setVue(VueMenuChoix vue) {
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
