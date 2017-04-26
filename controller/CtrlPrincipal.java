package controller;

import controller.themes.CtrlCapitalQuiz;
import controller.themes.CtrlFlagQuiz;
import controller.themes.CtrlPlaceQuiz;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import modele.metier.User;
import vues.VueCapitalQuiz;
import vues.VueCreerCompte;
import vues.VueMenuChoix;
import vues.VuePlaceQuiz;
import vues.VueProfesseur;
import vues.VueScoreChoix;
import vues.VueScoreEleve;
import vues.VueScores;
import vues.VueGestion;

public class CtrlPrincipal {

    CtrlAccueil ctrlAccueil;
    CtrlCapitalQuiz ctrlCapitalQuiz;
    CtrlScores ctrlScores;
    CtrlMenuChoix ctrlAccueilChoix;
    CtrlScoreChoix ctrlScoreChoix;
    CtrlCreerCompte ctrlCreerCompte;
    CtrlProfesseur ctrlProfesseur;
    CtrlGestion ctrlGestion;
    CtrlScoreEleve ctrlScoreEleve;
    CtrlFlagQuiz ctrlFlagQuiz;
    CtrlPlaceQuiz ctrlPlaces;
    JFrame currentView;
    User user;

    public void afficherAccueil(JFrame vue) {
        if (vue != null) {
            vue.dispose();
        }
        this.ctrlAccueil.getVue().setVisible(true);
        setCurrentView(this.ctrlAccueil.getVue());
    }

    public void start(int theme, int mode, int difficulty) {
        if (theme == 1) {
            afficherCapitalQuiz(mode, difficulty);
        } else if (theme == 2) {
            afficherFlagQuiz(mode, difficulty);
        } else if (theme == 3) {
            afficherPlacesQuiz(mode, difficulty);
        }
    }

    public void afficherCapitalQuiz(int mode, int difficulty) {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        capitalQuiz(mode, difficulty);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                 ctrlCapitalQuiz.getVue().setSize(screenSize);
                 ctrlCapitalQuiz.getVue().setExtendedState(JFrame.MAXIMIZED_BOTH);*/
//                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                ctrlCapitalQuiz.getVue().setSize(screenSize);
//                ctrlCapitalQuiz.getVue().setExtendedState(JFrame.MAXIMIZED_BOTH);
                ctrlCapitalQuiz.getVue().setLocationRelativeTo(getCurrentView());
                ctrlCapitalQuiz.getVue().setVisible(true);
                setCurrentView(ctrlCapitalQuiz.getVue());
                ctrlCapitalQuiz.doStart();
            }
        });
    }

    public void afficherFlagQuiz(int mode, int difficulty) {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        flagQuiz(mode, difficulty);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                 ctrlCapitalQuiz.getVue().setSize(screenSize);
                 ctrlCapitalQuiz.getVue().setExtendedState(JFrame.MAXIMIZED_BOTH);*/
                ctrlFlagQuiz.getVue().setLocationRelativeTo(getCurrentView());
                ctrlFlagQuiz.getVue().setVisible(true);
                setCurrentView(ctrlFlagQuiz.getVue());
                ctrlFlagQuiz.doStart();
            }
        });
    }

    public void afficherPlacesQuiz(int mode, int difficulty) {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        places(mode, difficulty);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                 ctrlCapitalQuiz.getVue().setSize(screenSize);
                 ctrlCapitalQuiz.getVue().setExtendedState(JFrame.MAXIMIZED_BOTH);*/
                ctrlPlaces.getVue().setLocationRelativeTo(getCurrentView());
                ctrlPlaces.getVue().setVisible(true);
                setCurrentView(ctrlPlaces.getVue());
                ctrlPlaces.doStart();
            }
        });
    }

    public void afficherAccueilChoix() {
        getCurrentView().setVisible(false);
        if (this.ctrlAccueilChoix == null || getCurrentView() == ctrlAccueil.getVue()) {
            try {
                if (getCurrentView() == ctrlProfesseur.getVue()) {
                    choix();
                }
            } catch (NullPointerException e) {
                choix();
            }
        }
        ctrlAccueilChoix.getVue().setLocationRelativeTo(getCurrentView());
        ctrlAccueilChoix.getVue().setVisible(true);
        setCurrentView(ctrlAccueilChoix.getVue());
    }

    public void afficherCreerCompte() {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        creerCompte();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlCreerCompte.getVue().setLocationRelativeTo(getCurrentView());
                ctrlCreerCompte.getVue().setVisible(true);
                setCurrentView(ctrlCreerCompte.getVue());
            }
        });
    }

    public void afficherScoreEleve(int id) {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        scoreEleve(id);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlScoreEleve.getVue().setLocationRelativeTo(getCurrentView());
                ctrlScoreEleve.getVue().setVisible(true);
                setCurrentView(ctrlScoreEleve.getVue());
            }
        });
    }

    public void afficherScoreChoix() {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        scoreChoix();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlScoreChoix.getVue().setLocationRelativeTo(getCurrentView());
                ctrlScoreChoix.getVue().setVisible(true);
                setCurrentView(ctrlScoreChoix.getVue());
            }
        });
    }

    public void afficherProfesseur() {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        professeur();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlProfesseur.getVue().setLocationRelativeTo(getCurrentView());
                ctrlProfesseur.getVue().setVisible(true);
                setCurrentView(ctrlProfesseur.getVue());
            }
        });
    }
    
    public void afficherGestion() {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        gestion();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlGestion.getVue().setLocationRelativeTo(getCurrentView());
                ctrlGestion.getVue().setVisible(true);
                setCurrentView(ctrlGestion.getVue());
            }
        });
    }


    public void afficherScores(int theme, int mode, int difficulty) {
        getCurrentView().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        scores(theme, mode, difficulty);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getCurrentView().getContentPane().setCursor(null);
                getCurrentView().setVisible(false);
                ctrlScores.getVue().setLocationRelativeTo(getCurrentView());
                ctrlScores.getVue().setVisible(true);
                setCurrentView(ctrlScores.getVue());
            }
        });
    }

    public void quitterApplication() {
        // Confirmer avant de quitter
        String ObjButtons[] = new String[]{"Oui", "Non"};
        int rep = JOptionPane.showOptionDialog(getCurrentView(), "Quitter le jeu\nEtes-vous sûr(e) ?", "Geo Quiz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons);
        if (rep == JOptionPane.YES_OPTION) {
            // mettre fin à l'application
            System.exit(0);
        }
    }

    public JFrame getCurrentView() {
        return currentView;
    }

    public void setCurrentView(JFrame currentView) {
        this.currentView = currentView;
    }

    private void capitalQuiz(int mode, int difficulty) {
        ctrlCapitalQuiz = new CtrlCapitalQuiz(new VueCapitalQuiz(), this, mode, difficulty);
    }

    private void flagQuiz(int mode, int difficulty) {
        ctrlFlagQuiz = new CtrlFlagQuiz(new VueCapitalQuiz(), this, mode, difficulty);
    }

    private void places(int mode, int difficulty) {
        ctrlPlaces = new CtrlPlaceQuiz(new VuePlaceQuiz(), this, mode, difficulty);
    }

    private void scores(int theme, int mode, int difficulty) {
        ctrlScores = new CtrlScores(new VueScores(), this, theme, mode, difficulty);
    }

    private void scoreChoix() {
        ctrlScoreChoix = new CtrlScoreChoix(new VueScoreChoix(), this);
    }

    private void choix() {
        ctrlAccueilChoix = new CtrlMenuChoix(new VueMenuChoix(), this);
    }

    private void creerCompte() {
        ctrlCreerCompte = new CtrlCreerCompte(new VueCreerCompte(), this);
    }

    private void professeur() {
        ctrlProfesseur = new CtrlProfesseur(new VueProfesseur(), this);
    }
    
    private void gestion() {
        ctrlGestion = new CtrlGestion(new VueGestion(), this);
    }


    private void scoreEleve(int id) {
        ctrlScoreEleve = new CtrlScoreEleve(new VueScoreEleve(), this, id);
    }

    public CtrlAccueil getCtrlMenu() {
        return ctrlAccueil;
    }

    public void setCtrlMenu(CtrlAccueil ctrlAccueil) {
        this.ctrlAccueil = ctrlAccueil;
    }

    public CtrlCapitalQuiz getCtrlCapitalQuiz() {
        return ctrlCapitalQuiz;
    }

    public void setCtrlCapitalQuiz(CtrlCapitalQuiz ctrlCapitalQuiz) {
        this.ctrlCapitalQuiz = ctrlCapitalQuiz;
    }

    public CtrlScores getCtrlScores() {
        return ctrlScores;
    }

    public void setCtrlScores(CtrlScores ctrlScores) {
        this.ctrlScores = ctrlScores;
    }

    public CtrlMenuChoix getCtrlMenuChoix() {
        return ctrlAccueilChoix;
    }

    public void setCtrlMenuChoix(CtrlMenuChoix ctrlAccueilChoix) {
        this.ctrlAccueilChoix = ctrlAccueilChoix;
    }

    public CtrlScoreChoix getCtrlScoreChoix() {
        return ctrlScoreChoix;
    }

    public void setCtrlScoreChoix(CtrlScoreChoix ctrlScoreChoix) {
        this.ctrlScoreChoix = ctrlScoreChoix;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
