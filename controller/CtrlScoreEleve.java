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
import javax.swing.table.DefaultTableModel;
import modele.dao.ScoreDao;
import modele.dao.UserDao;
import modele.metier.Score;
import modele.metier.User;
import vues.VueScoreEleve;

/**
 *
 * @author Benoit
 */
public class CtrlScoreEleve implements WindowListener, ActionListener {

    private VueScoreEleve vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private DefaultComboBoxModel modelComboBoxModel = new DefaultComboBoxModel();
    private DefaultTableModel modelTableModel = new DefaultTableModel();
    private ArrayList<User> users;
    private ArrayList<Score> scores;

    public CtrlScoreEleve(VueScoreEleve vue, CtrlPrincipal ctrl, int id) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonRechercher().addActionListener(this);
        vue.getjButtonRetour().addActionListener(this);
        vue.getjComboBoxEleve().addActionListener(this);

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
        vue.getjComboBoxEleve().setModel(modelComboBoxModel);
        vue.getjTableScores().setModel(modelTableModel);
        modelTableModel.addColumn("Theme");
        modelTableModel.addColumn("Mode");
        modelTableModel.addColumn("Difficulté");
        modelTableModel.addColumn("Score");
        modelTableModel.addColumn("Rang");
        vue.getjTableScores().setAutoCreateRowSorter(true);
        remplirComboBoxJoueurs();
        int i = 0;
        for (User user : users) {
            if (user.getId() == id) {
                vue.getjComboBoxEleve().setSelectedIndex(i);
            }
            i++;
        }
        remplirTable();
        // préparer l'état iniitial de la vue
    }

    private void remplirComboBoxJoueurs() {
        users = new ArrayList<User>();
        try {
            users = UserDao.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScoreEleve.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (User user : users) {
            modelComboBoxModel.addElement(user.getPseudo());
        }
    }

    private void remplirTable() {
        scores = new ArrayList<Score>();
        if (modelTableModel.getRowCount() > 0) {
            for (int i = modelTableModel.getRowCount() - 1; i > -1; i--) {
                modelTableModel.removeRow(i);
            }
        }
        try {
            scores = ScoreDao.selectAllByUserId(users.get(vue.getjComboBoxEleve().getSelectedIndex()).getId());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScoreEleve.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[] rowData = new Object[5];
        for (Score score : scores) {
            rowData[0] = score.getTheme().getLibelle();
            rowData[1] = score.getMode().getLibelle();
            rowData[2] = score.getDifficulty().getLibelle();
            if (score.getMode().getId() == 3) {
                rowData[3] = score.getScore()+"''";
            }else{
                rowData[3] = (int)score.getScore();
            }
            try {
                int rang = 1;
                int rangUser = -1;
                boolean playerFound = false;
                ArrayList<Score> lesScores = ScoreDao.selectAllByThemeModeAndDifficulty(score.getTheme().getId(), score.getMode().getId(), score.getDifficulty().getId());
                for (Score unScore : lesScores) {
                    if (users.get(vue.getjComboBoxEleve().getSelectedIndex()).getId() == unScore.getUser().getId() && !playerFound) {
                        rangUser = rang;
                        playerFound = true;
                    }
                    rang++;
                }
                rang -= 1;
                rowData[4] = rangUser + "/" + rang;
            } catch (SQLException ex) {
                Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelTableModel.addRow(rowData);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vue.getjButtonRetour()) {
            ctrlPrincipal.afficherProfesseur();
        }
        if (e.getSource() == vue.getjButtonRechercher()) {
//
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            quitter();
        }
        if (e.getSource() == vue.getjComboBoxEleve()) {
            remplirTable();
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
    public VueScoreEleve getVue() {
        return vue;
    }

    public void setVue(VueScoreEleve vue) {
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
