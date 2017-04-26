/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.common;

import controller.CtrlScores;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modele.dao.ScoreDao;
import modele.metier.Score;
import modele.metier.User;
import vues.VueCapitalQuiz;

/**
 *
 * @author JAOUEN
 */
public class CtrlAfficherScores {

    private ArrayList<Score> scores;
    private float[] columnWidthPercentage = {15.0f, 70.0f, 15.0f};
    private VueCapitalQuiz vue;
    private int THEME;
    private int mode;
    private int difficulty;
    private User user;
    private boolean scoreAmeliore = false;
    private int rangJoueur = -1;

    public CtrlAfficherScores(VueCapitalQuiz vue, int THEME, int mode, int difficulty, User user) {
        this.vue = vue;
        this.THEME = THEME;
        this.mode = mode;
        this.difficulty = difficulty;
        this.user = user;
    }

    public void afficherScores() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Rang");
        tableModel.addColumn("Joueur");
        tableModel.addColumn("Score");
        vue.getjTableScores().setModel(tableModel);
        try {
            scores = ScoreDao.selectAllByThemeModeAndDifficulty(THEME, mode, difficulty);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Une ligne de la table est un tableau d'objets
        Object[] rowData = new Object[3];
        int rang = 1;
        int rangUser = -1;
        boolean playerFound = false;
        for (Score unScore : scores) {
            rowData[0] = rang;
            if (user.getPseudo().equals(unScore.getUser().getPseudo()) && !playerFound) {
                rangUser = rang;
                playerFound = true;
            }
            rowData[1] = unScore.getUser().getPseudo();
            if (mode != 3) {
                rowData[2] = (int) unScore.getScore();
            } else {
                rowData[2] = unScore.getScore() + "''";
            }
            tableModel.addRow(rowData);
            rang++;
        }
        rang -= 1;
        final int coloredRow = rangUser - 1;
        resizeColumns();
        int leCoef = 0;
        if (vue.getjTableScores().getRowCount() != 0) {
            leCoef = 255 / vue.getjTableScores().getRowCount();
        }
        final int coef = leCoef;
        vue.getjTableScores().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (row == 0) {
                    setBackground(new Color(255, 215, 0));
                    setForeground(Color.BLACK);
                } else if (row == 1) {
                    setBackground(new Color(192, 192, 192));
                    setForeground(Color.BLACK);
                } else if (row == 2) {
                    setForeground(Color.WHITE);
                    setBackground(new Color(80, 50, 20));
                } else if (row == coloredRow) {
                    setForeground(Color.WHITE);
                    setBackground(new Color(255, 157, 0));
                } else {
                    setForeground(Color.BLACK);
                    int r = 255 - (255 - (row * coef));
                    setBackground(new Color(0 + r, 255, 255));
                }
                return this;
            }
        });
        if (rangUser != -1) {
            if (rangUser < vue.getjTableScores().getRowCount()) {
                vue.getjTableScores().setRowSelectionInterval(rangUser, rangUser - 1);
            }
            vue.getjTableScores().scrollRectToVisible(new Rectangle(vue.getjTableScores().getCellRect(rangUser - 1, 0, true)));
            if (scoreAmeliore) {
                //si ca se trouve quelqun améliore son score mais reste au meme rang
                if (rangJoueur != rangUser) {
                    if (rangUser == 1) {
                        if (rang == 1) {
                            vue.getjLabelClassement().setText("Vous êtes passé " + rangUser + "er sur " + rang + " joueur! :D");
                        } else {
                            vue.getjLabelClassement().setText("Vous êtes passé " + rangUser + "er sur " + rang + " joueurs! :D");
                        }
                    } else if (rangUser == 2) {
                        vue.getjLabelClassement().setText("Vous êtes passé " + rangUser + "eme sur " + rang + " joueurs!");
                    } else if (rangUser == 3) {
                        vue.getjLabelClassement().setText("Vous êtes passé " + rangUser + "eme sur " + rang + " joueurs!");
                    } else {
                        vue.getjLabelClassement().setText("Vous êtes passé " + rangUser + "eme sur " + rang + " joueurs.");
                    }
                } else if (rangUser == 1) {
                    if (rang == 1) {
                        vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "er sur " + rang + " joueur! :)");
                    } else {
                        vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "er sur " + rang + " joueurs! :)");
                    }
                } else if (rangUser == 2) {
                    vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "eme sur " + rang + " joueurs!");
                } else if (rangUser == 3) {
                    vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "eme sur " + rang + " joueurs!");
                } else {
                    vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "eme sur " + rang + " joueurs.");
                }
            } else if (rangUser == 1) {
                //si on a pas amélioré notre score et que l'on est premier alors notre classement n'a forcément pas bougé
                if (rang == 1) {
                    vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "er sur " + rang + "joueur :)");
                } else {
                    vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "er sur " + rang + "joueurs :)");
                }
            } else if (rangJoueur != rang) {
                vue.getjLabelClassement().setText("Vous êtes " + rangUser + "eme sur " + rang + "joueurs.");
            } else {
                vue.getjLabelClassement().setText("Vous êtes toujours " + rangUser + "eme sur " + rang + "joueurs.");
            }
        } else {
            vue.getjTableScores().scrollRectToVisible(new Rectangle(vue.getjTableScores().getCellRect(0, 0, true)));
            if (rang == 0) {
                vue.getjLabelClassement().setText("Rejouez pour vous devenir le premier joueur classé!");
            } else if (rang == 1) {
                vue.getjLabelClassement().setText("Rejouez pour vous classer avec l'autre joueur !");
            } else {
                vue.getjLabelClassement().setText("Rejouez pour vous classer parmi les " + rang + " joueurs!");
            }
        }
        rangJoueur = rangUser;
        vue.getContentPane().setCursor(null);
    }

    private void resizeColumns() {
        int tW = vue.getjTableScores().getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = vue.getjTableScores().getColumnModel();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }
}
