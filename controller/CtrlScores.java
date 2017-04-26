/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modele.dao.DifficultyDao;
import modele.dao.ModeDao;
import modele.dao.ScoreDao;
import modele.dao.ThemeDao;
import modele.metier.Score;
import modele.metier.User;
import vues.VueScores;

/**
 *
 * @author Benoit
 */
public class CtrlScores implements WindowListener, ActionListener {

    private VueScores vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private ArrayList<Score> scores = new ArrayList<>();
    float[] columnWidthPercentage = {15.0f, 70.0f, 15.0f};
    private int theme;
    private int mode;
    private int difficulty;
    private User user;

    public CtrlScores(VueScores vue, CtrlPrincipal ctrlPrincipal, int theme, int mode, int difficulty) {
        this.vue = vue;
        this.theme = theme;
        this.mode = mode;
        this.user = ctrlPrincipal.getUser();
        this.difficulty = difficulty;
        try {
            String title = ThemeDao.getTheme(theme).getLibelle() + " " + ModeDao.getMode(mode).getLibelle() + " " + DifficultyDao.getDifficulty(difficulty).getLibelle();
            vue.getjLabelTitre().setText(title);
            vue.setTitle("Classement " + title);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        //vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ctrlPrincipal = ctrlPrincipal;
        // le contrôleur écoute la vue
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonMenu().addActionListener(this);
        vue.getjButtonRafraichir().addActionListener(this);
        vue.getjButtonJouer().addActionListener(this);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/podium.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N
        vue.getjTableScores().setModel(tableModel);
        vue.getjTableScores().getTableHeader().setReorderingAllowed(false);
        vue.getjTableScores().setDefaultEditor(Object.class, null);
        tableModel.addColumn("Rang");
        tableModel.addColumn("Joueur");
        tableModel.addColumn("Score");
        vue.getjButtonMenu().setVisible(true);
        vue.getjButtonQuitter().setText("Retour");
        init();
    }

    private void init() {
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            scores = ScoreDao.selectAllByThemeModeAndDifficulty(theme, mode, difficulty);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Une ligne de la table est un tableau d'objets
        Object[] rowData = new Object[3];
        int rang = 1;
        int rangUser = -1;
        boolean playerFound = false;
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        for (Score unScore : scores) {
            if (rang == 1) {
                vue.getjLabelFirst().setOpaque(true);
                vue.getjLabelFirst().setBackground(new Color(255, 215, 0));
                vue.getjLabelFirst().setForeground(Color.BLACK);
                vue.getjLabelFirst().setText(unScore.getUser().getPseudo());
                vue.getjLabelScore1().setOpaque(true);
                vue.getjLabelScore1().setBackground(new Color(255, 215, 0));
                vue.getjLabelScore1().setForeground(Color.BLACK);
                if (mode == 3) {
                    vue.getjLabelScore1().setText(String.valueOf(unScore.getScore()) + "''");
                } else {
                    vue.getjLabelScore1().setText(String.valueOf((int) unScore.getScore()));
                }
            }
            if (rang == 2) {
                vue.getjLabelSecond().setOpaque(true);
                vue.getjLabelSecond().setBackground(new Color(192, 192, 192));
                vue.getjLabelSecond().setForeground(Color.BLACK);
                vue.getjLabelSecond().setText(unScore.getUser().getPseudo());
                vue.getjLabelScore2().setOpaque(true);
                vue.getjLabelScore2().setBackground(new Color(192, 192, 192));
                vue.getjLabelScore2().setForeground(Color.BLACK);
                if (mode == 3) {
                    vue.getjLabelScore2().setText(String.valueOf(unScore.getScore()) + "''");
                } else {
                    vue.getjLabelScore2().setText(String.valueOf((int) unScore.getScore()));
                }
            }
            if (rang == 3) {
                vue.getjLabelThird().setOpaque(true);
                vue.getjLabelThird().setBackground(new Color(80, 50, 20));
                vue.getjLabelThird().setForeground(Color.WHITE);
                vue.getjLabelThird().setText(unScore.getUser().getPseudo());
                vue.getjLabelScore3().setOpaque(true);
                vue.getjLabelScore3().setBackground(new Color(80, 50, 20));
                vue.getjLabelScore3().setForeground(Color.WHITE);
                if (mode == 3) {
                    vue.getjLabelScore3().setText(String.valueOf(unScore.getScore()) + "''");
                } else {
                    vue.getjLabelScore3().setText(String.valueOf((int) unScore.getScore()));
                }
            }
            if (user.getPseudo().equals(unScore.getUser().getPseudo()) && !playerFound) {
                rangUser = rang;
                playerFound = true;
            }
            if (rang >= 4) {
                rowData[0] = rang;
                rowData[1] = unScore.getUser().getPseudo();
                if (mode == 3) {
                    rowData[2] = unScore.getScore() + "''";
                } else {
                    rowData[2] = (int) unScore.getScore();
                }
                tableModel.addRow(rowData);
            }
            rang++;
        }
        rang -= 1;
        final int coloredRow = rangUser - 4;
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
                if (row == coloredRow) {
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
            if (rangUser == 1) {
                if (rang == 1) {
                    showFirst(1);
                    vue.getjLabelClassement().setText("Vous êtes classé " + rangUser + "er sur " + rang + " joueur! :D");
                } else {
                    if (rang == 2) {
                        showFirst(2);
                    }
                    vue.getjLabelClassement().setText("Vous êtes classé " + rangUser + "er sur " + rang + " joueurs! :D");
                }
            } else if (rangUser == 2) {
                showFirst(2);
                vue.getjLabelClassement().setText("Vous êtes classé " + rangUser + "eme sur " + rang + " joueurs!");
            } else if (rangUser == 3) {
                vue.getjLabelClassement().setText("Vous êtes classé " + rangUser + "eme sur " + rang + " joueurs!");
            } else {
                vue.getjLabelClassement().setText("Vous êtes classé " + rangUser + "eme sur " + rang + " joueurs.");
            }
        } else {
            vue.getjTableScores().scrollRectToVisible(new Rectangle(vue.getjTableScores().getCellRect(0, 0, true)));
            if (rang == 0) {
                showFirst(0);
                vue.getjLabelClassement().setText("Jouez pour vous devenir le premier joueur classé!");
            } else if (rang == 1) {
                showFirst(1);
                vue.getjLabelClassement().setText("Jouez pour vous classer avec l'autre joueur !");
            } else {
                if (rang == 2) {
                    showFirst(2);
                }
                vue.getjLabelClassement().setText("Jouez pour vous classer parmi les " + rang + " joueurs!");
            }
        }
        vue.setCursor(null);
    }

    private void showFirst(int nb) {
        if (nb == 0) {
            vue.getjLabelFirst().setVisible(false);
            vue.getjLabelScore1().setVisible(false);
            vue.getjLabelSecond().setVisible(false);
            vue.getjLabelScore2().setVisible(false);
            vue.getjLabelThird().setVisible(false);
            vue.getjLabelScore3().setVisible(false);
        } else if (nb == 1) {
            vue.getjLabelSecond().setVisible(false);
            vue.getjLabelScore2().setVisible(false);
            vue.getjLabelThird().setVisible(false);
            vue.getjLabelScore3().setVisible(false);
        } else if (nb == 2) {
            vue.getjLabelThird().setVisible(false);
            vue.getjLabelScore3().setVisible(false);
        } else {
            vue.getjLabelFirst().setVisible(true);
            vue.getjLabelScore1().setVisible(true);
            vue.getjLabelSecond().setVisible(true);
            vue.getjLabelScore2().setVisible(true);
            vue.getjLabelThird().setVisible(true);
            vue.getjLabelScore3().setVisible(true);
        }
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

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ctrlPrincipal.getCtrlScoreChoix().getVue().setVisible(true);
        vue.dispose();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getjButtonMenu()) {
            ctrlPrincipal.afficherAccueilChoix();
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            vue.setVisible(false);
            ctrlPrincipal.getCtrlScoreChoix().getVue().setVisible(true);
            ctrlPrincipal.setCurrentView(ctrlPrincipal.getCtrlScoreChoix().getVue());
        }
        if (e.getSource() == vue.getjButtonJouer()) {
            ctrlPrincipal.start(theme, mode, difficulty);
        }
        if (e.getSource() == vue.getjButtonRafraichir()) {
            init();
        }
    }

    public VueScores getVue() {
        return vue;
    }

    public void setVue(VueScores vue) {
        this.vue = vue;
    }

    public CtrlPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }

    public void setCtrlPrincipal(CtrlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
    }

}
