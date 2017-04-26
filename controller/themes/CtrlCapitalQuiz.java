package controller.themes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import controller.CtrlPrincipal;
import controller.CtrlScores;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modele.dao.CountriesDao;
import modele.dao.DifficultyDao;
import modele.dao.ModeDao;
import modele.dao.ScoreDao;
import modele.dao.ThemeDao;
import modele.metier.Country;
import modele.metier.Score;
import modele.metier.User;
import vues.VueCapitalQuiz;

/**
 *
 * @author Benoit
 */
public class CtrlCapitalQuiz implements WindowListener, ActionListener, KeyListener {

    private VueCapitalQuiz vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private ArrayList<Country> countries = new ArrayList<>();
    private ArrayList<Country> countriesBis = new ArrayList<>();
    private ArrayList<Country> answers = new ArrayList<>();
    private List<Integer> answerAlreadyAsked = new ArrayList<>();
    private Country country;
    private int answerIndex;
    private int index;
    private int round;
    private float score;
    private String continent;
    private int level;
    private boolean inverse;
    private final int NB_ANSWERS = 4;
    private boolean active;
    private int TIME_TO_WAIT = 10;
    private int timeDifficulty = 1000;
    private Timer timer;
    private CtrlCapitalQuiz capitalQuiz = this;
    private float timerTime;
    private Thread clockThread;
    private float[] columnWidthPercentage = {15.0f, 70.0f, 15.0f};
    private ArrayList<Score> scores = new ArrayList<>();
    private User user;
    private TimerTask timerTask;
    private boolean userAlreadyPlayed = false;
    private float personalBest = -1;
    private boolean timeOut = false;
    private int indexButton = -1;
    private int ANSWER_WAIT = 1500;
    private int RIGHT_ANSWER_WAIT = 500;
    private Date date = null;
    private Clip musicClip;
    private Clip rightClip;
    private Clip wrongClip;
    private Clip timeOutClip;
    private int COEF_RAPIDITE = 100;
    private float coefTaille;
    private Clip clockClip;
    private boolean scoreAmeliore = false;
    private int rangJoueur = -1;
    private int mode;
    private boolean first = false;
    private int ANSWER_TO_REACH;
    private int answerLeft;
    private long startTime;
    private boolean isPlaying;
    private long estimatedTime;
    private long timeScore;
    private final int THEME = 1;
    private int difficulty;
    private int coundown;
    private float TIME_BONUS;
    private float timeLoose = 1.0f;
    private javax.swing.Timer coundownTimer;
    private int coefSurvie;

    public CtrlCapitalQuiz(VueCapitalQuiz vue, CtrlPrincipal ctrl, int mode, int difficulty) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;
        this.user = ctrlPrincipal.getUser();
        this.mode = mode;
        this.difficulty = difficulty;
        try {
            String title = ThemeDao.getTheme(THEME).getLibelle() + " " + ModeDao.getMode(mode).getLibelle() + " " + DifficultyDao.getDifficulty(difficulty).getLibelle();
            vue.setTitle(title);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // le contrôleur écoute la vue
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/background_capitales.jpg")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N
        vue.getjButtonMenu().addActionListener(this);
        vue.getjButtonRecommencer().addActionListener(this);
        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonRejouer().addActionListener(this);
        vue.getjButtonCapitale1().addActionListener(this);
        vue.getjButtonCapitale2().addActionListener(this);
        vue.getjButtonCapitale3().addActionListener(this);
        vue.getjButtonCapitale4().addActionListener(this);
        vue.getjTableScores().getTableHeader().setReorderingAllowed(false);
        vue.getjTableScores().setDefaultEditor(Object.class, null);
        if (mode == 1) {
            TIME_TO_WAIT = 10;
        } else if (mode == 2) {
            TIME_TO_WAIT = 60;
        } else if (mode == 3) {
            ANSWER_TO_REACH = 15;
        } else if (mode == 4) {
            ANSWER_WAIT = 300;
            coefSurvie = 1;
        }
        coefTaille = (float) vue.getjProgressBarTime().getMaximum() / (TIME_TO_WAIT);
        try {
            musicClip = AudioSystem.getClip();
            rightClip = AudioSystem.getClip();
            wrongClip = AudioSystem.getClip();
            timeOutClip = AudioSystem.getClip();
            clockClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            /*musicClip.open(AudioSystem.getAudioInputStream(
             CtrlCapitalQuiz.class.getResource("/sounds/" + "capitalMusic.wav")));*/
            rightClip.open(AudioSystem.getAudioInputStream(
                    CtrlCapitalQuiz.class.getResource("/sounds/" + "right.wav")));
            wrongClip.open(AudioSystem.getAudioInputStream(
                    CtrlCapitalQuiz.class.getResource("/sounds/" + "wrong.wav")));
            timeOutClip.open(AudioSystem.getAudioInputStream(
                    CtrlCapitalQuiz.class.getResource("/sounds/" + "timeOut.wav")));
            clockClip.open(AudioSystem.getAudioInputStream(
                    CtrlCapitalQuiz.class.getResource("/sounds/" + "clock.wav")));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        vue.getjButtonCapitale1().addKeyListener(this);
        vue.getjButtonCapitale2().addKeyListener(this);
        vue.getjButtonCapitale3().addKeyListener(this);
        vue.getjButtonCapitale4().addKeyListener(this);
        vue.getjButtonRecommencer().addKeyListener(this);
        vue.getjButtonRejouer().addKeyListener(this);
        vue.getjButtonMenu().addKeyListener(this);
        vue.getjButtonQuitter().addKeyListener(this);
        vue.getjButtonOk().setVisible(false);
        vue.getjTextFieldReponseCash().setVisible(false);
        /* FloatControl gainControl
         = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
         musicClip.loop(Clip.LOOP_CONTINUOUSLY);*/
 /* FloatControl gainControl
         = (FloatControl) clockClip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.*/
 /* FloatControl gainControl
         = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
         musicClip.loop(Clip.LOOP_CONTINUOUSLY);*/
 /* FloatControl gainControl
         = (FloatControl) clockClip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.*/

 /*vue.getjPanel1().setOpaque(false);
         /*vue.getjPanel1().setOpaque(false);
         vue.getjPanel1().setLayout(null);
         vue.getjPanel1().setBackground(new Color(0, 0, 0, 0));*/
        // préparer l'état iniitial de la vue
        init();

    }

    public void doStart() {
        start();
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            CtrlCapitalQuiz.class.getResource("/sounds/" + url));
                    clip.open(inputStream);
                    FloatControl gainControl
                            = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-20.0f);
                    clip.start();
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    private void afficherScores() {
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
    // contrôle de la vue

    private void init() {
        try {
            scores = ScoreDao.selectAllByThemeModeAndDifficulty(THEME, mode, difficulty);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        int rang = 1;
        for (Score unScore : scores) {
            if (user.getPseudo().equals(unScore.getUser().getPseudo())) {
                rangJoueur = rang;
            }
            rang++;
        }
        if (difficulty == -1) {
            try {
                countries = CountriesDao.selectAll();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                countries = CountriesDao.selectSelectAllByLevel(difficulty);
            } catch (SQLException ex) {
                Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        vue.getjLabelJoueur().setText(user.getPseudo());
    }

    private void start() {
        vue.getjLabelCountdown().setVisible(false);
        if (mode == 3 || mode == 2 || mode == 4) {
            coundown = 3;
            vue.getjLabelCountdown().setText(String.valueOf(coundown));
            if (mode == 3) {
                answerLeft = ANSWER_TO_REACH;
            }
            playSound("countdown.wav");
            startTimer();
        } else {
            coundown = 0;
            vue.getjLabelCountdown().setText("GO!");
            startTimer();
        }
    }

    private void startTimer() {
        show(false);
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doCountdown();
            }
        };
        vue.getjLabelCountdown().setVisible(true);
        coundownTimer = new javax.swing.Timer(350, taskPerformer);
        coundownTimer.start();
    }

    private void launch() {
        if (mode == 3) {
            //lancer le comptage du temps
            startTime = System.currentTimeMillis();
            clockThread = new clockThread();
            clockThread.start();
        } else if (mode == 4) {
            TIME_BONUS = 3f;//-1
            coefSurvie = 4;
            timerTime = 5 + 1;// on commence a la moitié du temps au début pour que le joueur la recharge et se mette dans le bain des le début
        }
        isPlaying = true;
        first = true;
        timeOut = false;
        timeDifficulty = 1000;
        round = 1;
        score = 0;
        doRandom();
    }

    class clockThread extends Thread {

        @Override
        public void run() {
            while (isPlaying) {
                estimatedTime = System.currentTimeMillis() - startTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");
                Date resultdate = new Date(estimatedTime);
                vue.getjLabelTime().setText(sdf.format(resultdate));
            }
        }
    }

    private void doCountdown() {
        if (coundown == -1) {
            coundownTimer.stop();
            vue.getjLabelCountdown().setVisible(false);
            launch();
        }
        if (coundown > 0) {
            vue.getjLabelCountdown().setText(String.valueOf(coundown));
        } else {
            vue.getjLabelCountdown().setText("GO!");
        }
        --coundown;
    }

    private void stop() {
        if (mode == 3) {
            clockThread.interrupt();
        }
        clockClip.stop();
        isPlaying = false;
        first = false;
        scoreAmeliore = false;
        answerAlreadyAsked.clear();
        vue.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            scores = ScoreDao.selectAllByThemeModeAndDifficulty(THEME, mode, difficulty);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean playerFound = false;
        for (Score unScore : scores) {
            if (unScore.getUser().getPseudo().equals(user.getPseudo()) && !playerFound) {
                userAlreadyPlayed = true;
                personalBest = unScore.getScore();
                playerFound = true;
            }
        }
        if (mode == 3) {
            float seconds = (timeScore / 1000.0f) % 60;
            int minutes = Math.round(TimeUnit.MILLISECONDS.toMinutes(timeScore) * 60);
            score = seconds + minutes;
            if (userAlreadyPlayed) {
                if (personalBest > score) {
                    try {
                        ScoreDao.update(new Score(-1, user, user.getSession(), user.getOs(), user.getIp(), user.getMac(), ThemeDao.getTheme(THEME), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date));
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    scoreAmeliore = true;
                    if (personalBest - score == 1) {
                        vue.getjLabelVerdict().setText("Vous avez amélioré votre score de " + (personalBest - score) + " seconde!");
                    } else {
                        vue.getjLabelVerdict().setText("Vous avez amélioré votre score de " + (personalBest - score) + " secondes!");
                    }
                    personalBest = score;
                } else if (personalBest == score) {
                    vue.getjLabelVerdict().setText("Vous avez égalé votre meilleur temps !");
                } else if (personalBest - score == 1) {
                    vue.getjLabelVerdict().setText("Encore " + (score - personalBest) + " seconde de moins pour améliorer votre record.");
                } else {
                    vue.getjLabelVerdict().setText("Encore " + (score - personalBest) + " secondes de moins pour améliorer votre record.");
                }
            } else if (score >= 60) {
                if (score - 120 == 1) {
                    vue.getjLabelVerdict().setText("Encore " + (score - 120) + " seconde de moins pour être classé.");
                } else {
                    vue.getjLabelVerdict().setText("Encore " + (score - 120) + " secondes de moins pour être classé.");
                }
            } else {
                try {
                    ScoreDao.insert(new Score(-1, user, user.getSession(), user.getOs(), user.getIp(), user.getMac(), ThemeDao.getTheme(THEME), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date));
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
                }
                vue.getjLabelVerdict().setText("Vous entrez dans le classement !");
                personalBest = score;
                scoreAmeliore = true;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");
            Date resultdate = new Date(timeScore);
            vue.getjLabelReponse().setText("Partie terminée en " + sdf.format(resultdate));
        } else {
            if (userAlreadyPlayed) {
                if (personalBest < score) {
                    try {
                        ScoreDao.update(new Score(-1, user, user.getSession(), user.getOs(), user.getIp(), user.getMac(), ThemeDao.getTheme(THEME), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date));
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    scoreAmeliore = true;
                    if (score - personalBest == 1) {
                        vue.getjLabelVerdict().setText("Vous avez amélioré votre score de " + String.valueOf((int) (score - personalBest)) + " point!");
                    } else {
                        vue.getjLabelVerdict().setText("Vous avez amélioré votre score de " + String.valueOf((int) (score - personalBest)) + " points!");
                    }
                    personalBest = score;
                } else if (personalBest == score) {
                    vue.getjLabelVerdict().setText("Vous avez égalé votre record !");
                } else if (score - personalBest == 1) {
                    vue.getjLabelVerdict().setText("Encore " + String.valueOf((int) (personalBest - score)) + " point de plus pour améliorer votre record.");
                } else {
                    vue.getjLabelVerdict().setText("Encore " + String.valueOf((int) (personalBest - score)) + " points de plus pour améliorer votre record.");
                }
            } else if (score < 5) {
                if (5 - score == 1) {
                    vue.getjLabelVerdict().setText("Encore " + String.valueOf((int) (5 - score)) + " point pour être classé.");
                } else {
                    vue.getjLabelVerdict().setText("Encore " + String.valueOf((int) (5 - score)) + " points pour être classé.");
                }
            } else {
                try {
                    ScoreDao.insert(new Score(-1, user, user.getSession(), user.getOs(), user.getIp(), user.getMac(), ThemeDao.getTheme(THEME), ModeDao.getMode(mode), DifficultyDao.getDifficulty(difficulty), score, date));
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
                }
                vue.getjLabelVerdict().setText("Vous entrez dans le classement !");
                personalBest = score;
                scoreAmeliore = true;
            }
            vue.getjLabelReponse().setText("Partie terminée avec " + String.valueOf((int) score) + " points");
        }
        display(false);
        active = true;
        afficherScores();
        vue.getjButtonRejouer().requestFocus();
    }

    private void display(boolean isPlaying) {
        vue.getjButtonRecommencer().setVisible(isPlaying);
        vue.getjLabelDrapeau().setVisible(isPlaying);
        vue.getjScrollPaneClassement().setVisible(!isPlaying);
        vue.getjLabelClassement().setVisible(!isPlaying);
        vue.getjLabelJoueur().setVisible(isPlaying);
        if (mode == 3) {
            vue.getjLabelTime().setVisible(isPlaying);
            vue.getjProgressBarTime().setVisible(false);
        } else {
            vue.getjLabelTime().setVisible(false);
            vue.getjProgressBarTime().setVisible(isPlaying);
        }
        vue.getjButtonCapitale1().setVisible(isPlaying);
        vue.getjButtonCapitale2().setVisible(isPlaying);
        vue.getjButtonCapitale3().setVisible(isPlaying);
        vue.getjButtonCapitale4().setVisible(isPlaying);
        vue.getjButtonCapitale1().setEnabled(isPlaying);
        vue.getjButtonCapitale2().setEnabled(isPlaying);
        vue.getjButtonCapitale3().setEnabled(isPlaying);
        vue.getjButtonCapitale4().setEnabled(isPlaying);
        vue.getjLabelPays().setVisible(isPlaying);
        vue.getjLabelScore().setVisible(isPlaying);
        vue.getjLabelVerdict().setVisible(!isPlaying);
        vue.getjButtonRejouer().setVisible(!isPlaying);
        vue.getjLabelReponse().setVisible(!isPlaying);
        vue.getjButtonCapitale1().setForeground(Color.BLACK);
        vue.getjButtonCapitale2().setForeground(Color.BLACK);
        vue.getjButtonCapitale3().setForeground(Color.BLACK);
        vue.getjButtonCapitale4().setForeground(Color.BLACK);

        vue.getjButtonCapitale1().setBackground(null);
        vue.getjButtonCapitale2().setBackground(null);
        vue.getjButtonCapitale3().setBackground(null);
        vue.getjButtonCapitale4().setBackground(null);
        vue.getjButtonMenu().setVisible(true);
        vue.getjButtonQuitter().setVisible(true);
    }

    private void show(boolean b) {
        vue.getjButtonQuitter().setVisible(b);
        vue.getjButtonMenu().setVisible(b);
        vue.getjButtonRecommencer().setVisible(b);
        vue.getjLabelDrapeau().setVisible(b);
        vue.getjScrollPaneClassement().setVisible(b);
        vue.getjLabelClassement().setVisible(b);
        vue.getjLabelJoueur().setVisible(b);
        vue.getjLabelTime().setVisible(b);
        vue.getjProgressBarTime().setVisible(b);
        vue.getjButtonCapitale1().setVisible(b);
        vue.getjButtonCapitale2().setVisible(b);
        vue.getjButtonCapitale3().setVisible(b);
        vue.getjButtonCapitale4().setVisible(b);
        vue.getjLabelPays().setVisible(b);
        vue.getjLabelScore().setVisible(b);
        vue.getjLabelVerdict().setVisible(b);
        vue.getjButtonRejouer().setVisible(b);
        vue.getjLabelReponse().setVisible(b);
    }

    private void showAnswer() {
        vue.getjLabelReponse().setVisible(true);
        if (answerIndex != 0) {
            if (indexButton == 0) {
                vue.getjButtonCapitale1().setBackground(Color.RED);
            }
        } else if (indexButton != -1) {
            vue.getjButtonCapitale1().setBackground(Color.GREEN);
        } else {
            vue.getjButtonCapitale1().setBackground(Color.RED);
        }
        if (answerIndex != 1) {
            if (indexButton == 1) {
                vue.getjButtonCapitale2().setBackground(Color.RED);
            }
        } else if (indexButton != -1) {
            vue.getjButtonCapitale2().setBackground(Color.GREEN);
        } else {
            vue.getjButtonCapitale2().setBackground(Color.RED);
        }
        if (answerIndex != 2) {
            if (indexButton == 2) {
                vue.getjButtonCapitale3().setBackground(Color.RED);
            }
        } else if (indexButton != -1) {
            vue.getjButtonCapitale3().setBackground(Color.GREEN);
        } else {
            vue.getjButtonCapitale3().setBackground(Color.RED);
        }
        if (answerIndex != 3) {
            if (indexButton == 3) {
                vue.getjButtonCapitale4().setBackground(Color.RED);
            }
        } else if (indexButton != -1) {
            vue.getjButtonCapitale4().setBackground(Color.GREEN);
        } else {
            vue.getjButtonCapitale4().setBackground(Color.RED);
        }
    }

    private void doRandom() {
        if (mode == 2) {
            if (first) {
                clockClip.loop(Clip.LOOP_CONTINUOUSLY);
                vue.getjProgressBarTime().setValue(Math.round(TIME_TO_WAIT * coefTaille));
            }
        } else {
            clockClip.loop(Clip.LOOP_CONTINUOUSLY);
            if (mode == 4) {
                vue.getjProgressBarTime().setValue(Math.round(timerTime * coefTaille));
            } else if (mode != 3) {
                vue.getjProgressBarTime().setValue(Math.round(TIME_TO_WAIT * coefTaille));
            }
        }
        active = true;
        indexButton = -1;
        if (mode != 3) {
            vue.getjLabelScore().setText(String.valueOf((int) score));
        } else {
            vue.getjLabelScore().setText(String.valueOf(answerLeft));
        }
        countriesBis = new ArrayList<>(countries);
        display(true);
        answers.clear();
        List<Integer> indexButtons = new ArrayList<>(NB_ANSWERS);
        for (int k = 0; k < NB_ANSWERS; k++) {
            indexButtons.add(k);
            answers.add(k, null);
        }
        if (answerAlreadyAsked.size() == countries.size()) {
            answerAlreadyAsked.clear();
        }
        Collections.shuffle(indexButtons);
        int i = 0;
        while (i < NB_ANSWERS) {
            boolean notSame = true;
            boolean countryAlreadyAsked = false;
            index = new Random().nextInt(countriesBis.size());
            if (i == 0) {
                for (Integer countryIndex : answerAlreadyAsked) {
                    if (countryIndex == index) {
                        countryAlreadyAsked = true;
                    }
                }
                if (!countryAlreadyAsked) {
                    answerAlreadyAsked.add(index);
                }
            }
            for (Country pays : answers) {
                if (pays != null) {
                    if (pays.getCapital().equals(countriesBis.get(index).getCapital())) {
                        notSame = false;
                    }
                }
            }
            if (countriesBis.get(index).getCapital() != null && notSame && !countryAlreadyAsked) {
                if (i == 0) {
                    country = countriesBis.get(index);
                    answerIndex = indexButtons.get(i);
                    vue.getjLabelPays().setText(country.getCountryName());
                    try {
                        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/flags/" + country.getCountryCode() + ".png")); // load the image to a imageIcon
                        int iconWidth = imageIcon.getIconWidth();
                        int iconHeight = imageIcon.getIconHeight();
                        int maxWidth = 200;
                        if (iconWidth > maxWidth) {
                            float coefDiminution = Float.valueOf(maxWidth) / Float.valueOf(iconWidth);
                            iconWidth *= coefDiminution;
                            iconHeight *= coefDiminution;
                            Image resized = imageIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                            imageIcon = new ImageIcon(resized);
                        }
                        Dimension size = new Dimension(iconWidth, iconHeight);
                        vue.getjLabelDrapeau().setText("");
                        vue.getjLabelDrapeau().setIcon(imageIcon);
                        vue.getjLabelDrapeau().setSize(size);
                    } catch (Exception e) {
                        vue.getjLabelDrapeau().setIcon(null);
                        vue.getjLabelDrapeau().setText("");
                    }
                }
                changeButtonAnswer(indexButtons.get(i));
                answers.set(indexButtons.get(i), countriesBis.get(index));
                countriesBis.remove(index);
                i++;
            }
        }
        if (mode == 4) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new timerTask(), 0, timeDifficulty);
            TIME_BONUS -= TIME_BONUS / COEF_RAPIDITE;
            coefSurvie += 1;
        } else if (mode == 2) {
            if (first) {
                timerTime = TIME_TO_WAIT + 1;
                timer = new Timer();
                timer.scheduleAtFixedRate(new timerTask(), 0, timeDifficulty);
            }
        } else if (mode != 3) {
            timerTime = TIME_TO_WAIT + 1;
            timer = new Timer();
            timer.scheduleAtFixedRate(new timerTask(), 0, timeDifficulty);
            timeDifficulty -= timeDifficulty / COEF_RAPIDITE;
        }
        if (first) {
            first = false;
        }
        vue.getjButtonCapitale1().requestFocus();//focus pour que les touches fonctionne
    }

    class timerTask extends TimerTask {

        @Override
        public void run() {
            vue.getjLabelTime().setText(String.valueOf(timerTime));
            doTimer();
        }

    }

    private void doTimer() {
        if (timerTime < 1.0f) {
            timeOut = true;
            checkAnswer(-1);
        }
        timerTime -= timeLoose;
        vue.getjProgressBarTime().setValue(Math.round(timerTime * coefTaille));
        if (Math.round(TIME_TO_WAIT / 5) >= timerTime) {
            vue.getjProgressBarTime().setForeground(Color.red);
        } else if (Math.round(TIME_TO_WAIT / 3) >= timerTime) {
            vue.getjProgressBarTime().setForeground(Color.orange);
        } else if (Math.round(TIME_TO_WAIT / 2) > timerTime) {
            vue.getjProgressBarTime().setForeground(Color.yellow);
        } else {
            vue.getjProgressBarTime().setForeground(Color.green);
        }
    }

    private void changeButtonAnswer(int indexButton) {
        switch (indexButton) {
            case 0:
                vue.getjButtonCapitale1().setText(countriesBis.get(index).getCapital());
                /* if (countriesBis.get(index).getCapital().length() < 16) {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 14);
                 vue.getjButtonCapitale1().setFont(f);
                 } else {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 8);
                 vue.getjButtonCapitale1().setFont(f);
                 }*/
                break;
            case 1:
                vue.getjButtonCapitale2().setText(countriesBis.get(index).getCapital());
                /* if (countriesBis.get(index).getCapital().length() < 16) {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 14);
                 vue.getjButtonCapitale2().setFont(f);
                 } else {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 8);
                 vue.getjButtonCapitale2().setFont(f);
                 }*/
                break;
            case 2:
                vue.getjButtonCapitale3().setText(countriesBis.get(index).getCapital());
                /*if (countriesBis.get(index).getCapital().length() < 16) {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 14);
                 vue.getjButtonCapitale3().setFont(f);
                 } else {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 8);
                 vue.getjButtonCapitale3().setFont(f);
                 }*/
                break;
            case 3:
                vue.getjButtonCapitale4().setText(countriesBis.get(index).getCapital());
                /* if (countriesBis.get(index).getCapital().length() < 16) {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 14);
                 vue.getjButtonCapitale4().setFont(f);
                 } else {
                 Font f = new Font(vue.getjButtonCapitale1().getFont().getName(), Font.BOLD, 8);
                 vue.getjButtonCapitale4().setFont(f);
                 }*/
                break;
        }
    }

    private void checkAnswer(int indexButton) {
        this.indexButton = indexButton;
        timeScore = estimatedTime;
        active = false;
        if (mode != 2 && mode != 3 || timeOut) {
            //couper le timer pour mode survie 4?
            timer.cancel();
            if (mode != 4) {
                clockClip.stop();
                vue.getjProgressBarTime().setVisible(false);
            }
        }

        showAnswer();
        if (indexButton != -1) {
            if (country == answers.get(indexButton)) {
                playSound("right.wav");
                vue.getjLabelReponse().setText("Correct");
                vue.getjLabelReponse().setForeground(Color.green);
                score += 1;
                doAction(true);
            } else {
                playSound("wrong.wav");
                vue.getjLabelReponse().setForeground(Color.red);
                vue.getjLabelReponse().setText("FAUX !");
                doAction(false);
            }
        } else {
            playSound("timeOut.wav");
            vue.getjLabelReponse().setForeground(Color.red);
            vue.getjLabelReponse().setText("TEMPS Écoulé !");
            doAction(false);
        }
    }

    private void doAction(final boolean correct) {
        if (!correct) {
            date = Calendar.getInstance().getTime();
        }
        if (mode == 3 && score == ANSWER_TO_REACH) {
            date = Calendar.getInstance().getTime();
            stop();
        } else {
            ActionListener listener = null;
            listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (correct) {
                        doRandom();
                    } else if (mode == 1) {
                        stop();
                    } else if (!timeOut) {
                        doRandom();
                    } else {
                        stop();
                    }
                }
            };

            javax.swing.Timer timer = null;
            if (mode == 2 || mode == 3 || mode == 4) {
                if (correct) {
                    if (mode == 4) {
                        if (timerTime + TIME_BONUS > TIME_TO_WAIT + 1) {
                            timerTime = TIME_TO_WAIT + 1;
                        } else {
                            timerTime += TIME_BONUS;//ou 1-(TIMEBONUS/coefDifficulte)
                        }
                    }
                    if (mode == 3) {
                        answerLeft--;
                    }
                    doRandom();
                } else {
                    timer = new javax.swing.Timer(ANSWER_WAIT, listener);
                }
            } else if (correct && mode == 1) {
                timer = new javax.swing.Timer(RIGHT_ANSWER_WAIT, listener);
            } else {
                timer = new javax.swing.Timer(ANSWER_WAIT, listener);
            }
            if (timer != null) {
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    // méthodes d'action
    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (active) {
            if (isPlaying) {
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    checkAnswer(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    checkAnswer(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    checkAnswer(2);
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    checkAnswer(3);
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    recommencer();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_M) {
                menu();
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                quitter();
            }
        }
    }

    private void recommencer() {
        isPlaying = false;
        if (mode == 3) {
            clockThread.interrupt();
        }
        stopTimerSound();
        start();
    }

    private void stopTimerSound() {
        musicClip.stop();
        clockClip.stop();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void menu() {
        isPlaying = false;
        if (mode == 3) {
            if (clockThread.isAlive()) {
                clockThread.interrupt();
            }
        }
        stopTimerSound();
        ctrlPrincipal.afficherAccueilChoix();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // ACCESSEURS et MUTATEURS
    public VueCapitalQuiz getVue() {
        return vue;
    }

    public void setVue(VueCapitalQuiz vue) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!active) {
            return;
        }
        if (e.getSource() == vue.getjButtonRejouer()) {
            start();
        }
        if (e.getSource() == vue.getjButtonRecommencer()) {
            recommencer();
        }
        if (e.getSource() == vue.getjButtonMenu()) {
            /*timer.cancel();
             // Confirmer avant de revenir au menu princupal
             String ObjButtons[] = new String[]{"Oui", "Non"};
             int rep = JOptionPane.showOptionDialog(vue, "Fermer la partie en cours ?", "Geo Quiz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons);
             if (rep == JOptionPane.YES_OPTION) {*/
            // revenir au menu principal
            menu();
            /* } else {
             timer = new Timer();
             timer.schedule(new timerTask(), 0, timeDifficulty);
             }*/
        }
        if (e.getSource() == vue.getjButtonQuitter()) {
            ctrlPrincipal.quitterApplication();
        }
        if (e.getSource() == vue.getjButtonCapitale1()) {
            checkAnswer(0);
        }
        if (e.getSource() == vue.getjButtonCapitale2()) {
            checkAnswer(1);
        }
        if (e.getSource() == vue.getjButtonCapitale3()) {
            checkAnswer(2);
        }
        if (e.getSource() == vue.getjButtonCapitale4()) {
            checkAnswer(3);
        }
    }
}
