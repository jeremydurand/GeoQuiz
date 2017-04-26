/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import geoquiz.GeoQuiz;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modele.dao.AppDao;
import modele.dao.Jdbc;
import modele.dao.ScoreDao;
import modele.dao.UserDao;
import modele.metier.User;
import util.Crypt;
import util.JTextFieldLimit;
import vues.VueAccueil;

/**
 *
 * @author Benoit
 */
public class CtrlAccueil implements WindowListener, ActionListener, DocumentListener, KeyListener, FocusListener {

    private VueAccueil vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private TimerTask timerTask;
    private int timerTime;
    private Timer timer;
    private String loading = "Connexion";
    private boolean up;
    private float version;
    private User user;
    private final float VERSION_APP = 1.3f;
    private boolean mdpCorrect;
    private boolean loginCorrect;

    public CtrlAccueil(final VueAccueil vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ctrlPrincipal = ctrl;
        // le contrôleur écoute la vue
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/globulus.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N
        vue.getjTextFieldPseudo().addKeyListener(this);
        vue.getjPasswordFieldPassword().addKeyListener(this);
        vue.getjLabelVersion().setVisible(false);
        vue.getjButtonJouer().setVisible(false);
        vue.getjButtonNewVersion().setVisible(false);
        vue.getjLabelNewVersion().setVisible(false);
        vue.getjTextFieldPseudo().setDocument(new JTextFieldLimit(15));
        vue.getjPasswordFieldPassword().setDocument(new JTextFieldLimit(50));
        vue.getjButtonJouer().addActionListener(this);
        vue.getjButtonNewVersion().addActionListener(this);
        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonReconnecter().addActionListener(this);
        vue.getjButtonCreerCompte().addActionListener(this);
        vue.getjTextFieldPseudo().setEnabled(false);
        vue.getjPasswordFieldPassword().setEnabled(false);
        vue.getjButtonJouer().setEnabled(false);
        vue.getjButtonCreerCompte().setEnabled(false);
        vue.getjButtonReconnecter().setVisible(false);
        vue.getjLabelEtatPseudo().setText("");
        vue.getjLabelEtatMdp().setText("");
        vue.getjTextFieldPseudo().addFocusListener(this);
        vue.getjCheckBoxMemoriserPseudo().addActionListener(this);
        vue.getjPasswordFieldPassword().addFocusListener(this);
        vue.getjCheckBoxMemoriserMdp().setEnabled(false);
        vue.getjCheckBoxMemoriserPseudo().setEnabled(false);
        vue.getjTextFieldPseudo().setText("Pseudo");
        vue.getjPasswordFieldPassword().setText("Mot de passe");
        vue.getjPasswordFieldPassword().setEchoChar((char) 0);
        vue.getjTextFieldPseudo().getDocument().addDocumentListener(this);
        vue.getjPasswordFieldPassword().getDocument().addDocumentListener(this);
        vue.getjTextFieldPseudo().setNextFocusableComponent(vue.getjPasswordFieldPassword());
        vue.getjPasswordFieldPassword().setNextFocusableComponent(vue.getjButtonJouer());
        vue.getjTextFieldPseudo().setForeground(Color.gray);
        vue.getjPasswordFieldPassword().setForeground(Color.gray);
        // préparer l'état iniitial de la vue
        //pas encore dispo
        vue.getjButtonMdpOublie().setVisible(false);

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        showPlayButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        showPlayButton();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        showPlayButton();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == vue.getjTextFieldPseudo()) {
                if (vue.getjTextFieldPseudo().getText().length() < 3) {
                    JOptionPane.showMessageDialog(vue, "Pseudo incorrect");
                } else {
                    vue.getjPasswordFieldPassword().requestFocus();
                }
            }
            if (e.getSource() == vue.getjPasswordFieldPassword()) {
                if (vue.getjTextFieldPseudo().getText().length() < 3 || vue.getjTextFieldPseudo().getText().equalsIgnoreCase("pseudo")) {
                    vue.getjTextFieldPseudo().requestFocus();
                } else {
                    if (vue.getjPasswordFieldPassword().getText().length() < 5) {
                        JOptionPane.showMessageDialog(vue, "Mot de passe incorrect");
                    } else {
                        connexion();
                    }
                }
            }
            // ctrlPrincipal.afficherAccueilChoix();
            //saveData();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == vue.getjTextFieldPseudo()) {
            if (vue.getjTextFieldPseudo().getText().equals("Pseudo")) {
                vue.getjTextFieldPseudo().setText("");
                vue.getjTextFieldPseudo().setForeground(Color.black);
            }
            vue.getjLabelEtatPseudo().setText("");
        }
        if (e.getSource() == vue.getjPasswordFieldPassword()) {
            if (vue.getjPasswordFieldPassword().getText().equals("Mot de passe")) {
                vue.getjPasswordFieldPassword().setText("");
                vue.getjPasswordFieldPassword().setEchoChar('•');
                vue.getjPasswordFieldPassword().setForeground(Color.black);
            }
            vue.getjLabelEtatMdp().setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == vue.getjTextFieldPseudo()) {
            if (vue.getjTextFieldPseudo().getText().equals("")) {
                vue.getjTextFieldPseudo().setText("Pseudo");
                vue.getjTextFieldPseudo().setForeground(Color.gray);
            }
            checkLogin();
        }
        if (e.getSource() == vue.getjPasswordFieldPassword()) {
            if (vue.getjPasswordFieldPassword().getText().equals("")) {
                vue.getjPasswordFieldPassword().setText("Mot de passe");
                vue.getjPasswordFieldPassword().setEchoChar((char) 0);
                vue.getjPasswordFieldPassword().setForeground(Color.gray);
            }
            checkMdp();
        }
    }

    class timerTask extends TimerTask {

        @Override
        public void run() {
            doTimer();
        }

    }

    private void doTimer() {
        if (timerTime == 0) {
            up = true;
        } else if (timerTime == 5) {
            up = false;
        }
        if (up) {
            timerTime++;
            loading += ".";
            vue.getjLabelMessage().setText(loading);
        } else {
            timerTime--;
            loading = loading.substring(0, loading.length() - 1);
            vue.getjLabelMessage().setText(loading);
        }
    }

    public void setUser() {
        ctrlPrincipal.setUser(userInfos());
    }

    //a externaliser
    private User userInfos() {
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        User user = new User();
        try {
            user.setSession(System.getProperty("user.name"));
            user.setOs(System.getProperty("os.name"));

            InetAddress ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            URL whatismyip = null;
            try {
                whatismyip = new URL("http://checkip.amazonaws.com");
            } catch (MalformedURLException ex) {
                Logger.getLogger(GeoQuiz.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
            } catch (IOException ex) {
                Logger.getLogger(GeoQuiz.class.getName()).log(Level.SEVERE, null, ex);
            }
            String externalIp = null;
            try {
                externalIp = in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(GeoQuiz.class.getName()).log(Level.SEVERE, null, ex);
            }
            user.setIp(externalIp);
            user.setMac(sb.toString());

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }
        this.user = user;
        vue.setCursor(null);
        return user;
    }

    public void stopLoading() {
        timerTask.cancel();
        timer.cancel();
        vue.getContentPane().setCursor(null);
    }

    private void loading() {
        timerTime = 0;

        timer = new Timer();
        timerTask = new timerTask();
        timer.scheduleAtFixedRate(timerTask, 0, 250);
    }

    public void enable() {
        vue.getjButtonReconnecter().setVisible(false);
        vue.getjLabelMessage().setVisible(false);
        try {
            version = AppDao.getVersionNum();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        vue.getjLabelVersion().setText("V" + VERSION_APP);
        vue.getjLabelVersion().setVisible(true);
        if (version == -1 && -1 != VERSION_APP) {
            vue.getjButtonNewVersion().setVisible(true);
            vue.getjButtonNewVersion().setText("Réessayer");
            vue.getjLabelNewVersion().setVisible(true);
            vue.getjLabelNewVersion().setText("Geo quiz est en maintenance.");
        } else if (version != VERSION_APP) {
            vue.getjButtonNewVersion().setText("Reconnecter");
            vue.getjButtonNewVersion().setVisible(true);
            vue.getjLabelNewVersion().setVisible(true);
            vue.getjLabelNewVersion().setText("Une nouvelle version de Géo Quiz est disponible ! V" + version);
        } else {
            setUser();
            displaySavedData();
            vue.getjTextFieldPseudo().setEnabled(true);
            vue.getjPasswordFieldPassword().setEnabled(true);
            vue.getjButtonJouer().setEnabled(true);
            vue.getjButtonCreerCompte().setEnabled(true);
            vue.getjCheckBoxMemoriserPseudo().setEnabled(true);
        }
    }

    public void displaySavedData() {
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ArrayList<String> savedData = null;
        try {
            savedData = UserDao.getSavedData(user.getMac());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!savedData.isEmpty()) {
            if (savedData.get(0) != null) {
                vue.getjTextFieldPseudo().setText(savedData.get(0));
                vue.getjCheckBoxMemoriserPseudo().setSelected(true);
                vue.getjCheckBoxMemoriserMdp().setEnabled(true);
                vue.getjTextFieldPseudo().setForeground(Color.black);
                vue.getjTextFieldPseudo().requestFocus();
            } else {

            }
            if (savedData.get(1) != null) {
                vue.getjPasswordFieldPassword().setText(Crypt.deCryptMdp(savedData.get(1)));
                vue.getjPasswordFieldPassword().setEchoChar('•');
                vue.getjCheckBoxMemoriserMdp().setEnabled(true);
                vue.getjCheckBoxMemoriserMdp().setSelected(true);
                vue.getjPasswordFieldPassword().setForeground(Color.black);
            } else {

            }
        }
        /*ObjectInputStream ois = null;
         try {
         ois = new ObjectInputStream(new FileInputStream("user.data"));
         String pseudp = (String) ois.readObject();
         vue.getjTextFieldPseudo().setText(pseudp);
         if (!pseudp.equals("")) {
         vue.getjCheckBoxSouvenirPseudo().setSelected(true);
         }
         } catch (FileNotFoundException fnfe) {
         Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, fnfe);
         } catch (IOException | ClassNotFoundException ex) {
         Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
         try {
         if (ois != null) {
         ois.close();
         }
         } catch (IOException ex) {
         Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/
        vue.setCursor(null);
    }

    public void connecter() {
        vue.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        loading(); //        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");
        Jdbc.creer("com.mysql.jdbc.Driver", "jdbc:mysql:", "//78.216.20.115/", "GEO", "geo", "geoadmin");
        //Jdbc.creer("com.mysql.jdbc.Driver", "jdbc:mysql:", "//192.168.0.28/", "GEO", "geo", "geoadmin");
        try {
            Jdbc.getInstance().connecter();
            enable();
            stopLoading();
        } catch (ClassNotFoundException ex) {
            vue.getjButtonReconnecter().setVisible(true);
            vue.getjLabelMessage().setText("Erreur de connexion!");
            stopLoading();
            JOptionPane.showMessageDialog(null, "Main - classe JDBC non trouvée :\n" + ex);
        } catch (SQLException ex) {
            vue.getjButtonReconnecter().setVisible(true);
            vue.getjLabelMessage().setText("Erreur de connexion!");
            stopLoading();
            JOptionPane.showMessageDialog(null, "Main - échec de connexion: \n" + ex);
        }
    }

    /**
     *
     */
    private void saveData() {
        /*ObjectOutputStream oos = null;
         try {
         oos = new ObjectOutputStream(new FileOutputStream("user.data"));
         oos.writeObject(userInfos());
         /*if (vue.getjCheckBoxSouvenirId().isSelected()) {
         oos.writeObject(vue.getjTextFieldPseudo().getText());
         } else {
         oos.writeObject("");
         }
         } catch (IOException ex) {
         JOptionPane.showMessageDialog(vue, ex, "Impossible de sauvegarder les informations saisies.", JOptionPane.ERROR_MESSAGE);
         } finally {
         try {
         if (oos != null) {
         oos.close();
         }
         } catch (IOException ex) {
         Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/
        //ctrlPrincipal.setUser(userInfos());
    }

    /**
     *
     */
    private void checkLogin() {
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            int code = UserDao.verifierInfosConnexion(vue.getjTextFieldPseudo().getText(), Crypt.cryptMdp(vue.getjPasswordFieldPassword().getText()));
            if (code == 1 || code == 0) {//mauvais login bon mdp
                vue.getjLabelEtatPseudo().setText("✘");
                vue.getjLabelEtatPseudo().setForeground(Color.red);
                loginCorrect = false;
            } else {
                vue.getjLabelEtatPseudo().setText("✔");
                vue.getjLabelEtatPseudo().setForeground(Color.green);
                loginCorrect = true;
                /*if (mdpCorrect) {
                 openApp();
                 }*/
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        vue.getContentPane().setCursor(null);
    }

    /**
     *
     */
    private void checkMdp() {
        if (loginCorrect) {
            vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                int code = UserDao.verifierInfosConnexion(vue.getjTextFieldPseudo().getText(), Crypt.cryptMdp(vue.getjPasswordFieldPassword().getText()));
                if (code == 10 || code == 0) {//mauvais login bon mdp
                    vue.getjLabelEtatMdp().setText("✘");
                    vue.getjLabelEtatMdp().setForeground(Color.red);
                    mdpCorrect = false;
                } else {
                    vue.getjLabelEtatMdp().setText("✔");
                    vue.getjLabelEtatMdp().setForeground(Color.green);
                    mdpCorrect = true;
                    /*if (loginCorrect) {
                     openApp();
                     }*/
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
            }
            vue.getContentPane().setCursor(null);
        } else {
            vue.getjLabelEtatMdp().setText("");
        }
    }

    private void showPlayButton() {
        int pseudo = vue.getjTextFieldPseudo().getText().length();
        int password = vue.getjPasswordFieldPassword().getText().length();
        if (pseudo > 2 && password > 4 && !vue.getjTextFieldPseudo().getText().equalsIgnoreCase("pseudo") && !vue.getjPasswordFieldPassword().getText().equalsIgnoreCase("Mot de passe")) {
            vue.getjButtonJouer().setVisible(true);
        } else {
            vue.getjButtonJouer().setVisible(false);
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connexion() {
        vue.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int code = -1;
        try {
            code = UserDao.verifierInfosConnexion(vue.getjTextFieldPseudo().getText(), Crypt.cryptMdp(vue.getjPasswordFieldPassword().getText()));
        } catch (SQLException ex) {
            Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (code == 11) {
            User aUser = null;
            try {
                //bon login et mdp
                aUser = UserDao.selectOneByPseudo(vue.getjTextFieldPseudo().getText());
            } catch (SQLException ex) {
                Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
            }
            user.setAdmin(aUser.isAdmin());
            user.setDate(aUser.getDate());
            user.setId(aUser.getId());
            user.setMail(aUser.getMail());
            user.setPassword(aUser.getPassword());
            user.setPseudo(aUser.getPseudo());
            if (vue.getjCheckBoxMemoriserPseudo().isSelected()) {
                if (vue.getjCheckBoxMemoriserMdp().isSelected()) {
                    try {
                        UserDao.updateSavedData(user.getMac(), aUser.getPseudo(), aUser.getPassword());
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        UserDao.updateSavedData(user.getMac(), aUser.getPseudo(), null);
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                /*try {
                    UserDao.updateSavedData(user.getMac(), null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            ctrlPrincipal.setUser(user);
            if (user.isAdmin()) {
                ctrlPrincipal.afficherProfesseur();
            } else {
                ctrlPrincipal.afficherAccueilChoix();
            }
        } else if (code == 0) {//mauvais login et mdp
            JOptionPane.showMessageDialog(vue, "Votre login et votre mot de passe ne sont pas corrects.");
            vue.getjTextFieldPseudo().requestFocus();
            vue.getjTextFieldPseudo().selectAll();
        } else if (code == 1) {//mauvais login bon mdp
            JOptionPane.showMessageDialog(vue, "Votre login est incorrect.");
            vue.getjTextFieldPseudo().requestFocus();
            vue.getjTextFieldPseudo().selectAll();
        } else if (code == 10) {//bon login mauvais mdp
            JOptionPane.showMessageDialog(vue, "Votre mot de passe est incorrect.");
            vue.getjPasswordFieldPassword().requestFocus();
            vue.getjPasswordFieldPassword().selectAll();
        }
        vue.getContentPane().setCursor(null);
    }

    // contrôle de la vue
    // méthodes d'action
    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    // ACCESSEURS et MUTATEURS
    public VueAccueil getVue() {
        return vue;
    }

    public void setVue(VueAccueil vue) {
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
        if (e.getSource().equals(vue.getjButtonNewVersion())) {
            try {
                openWebpage(new URL("https://drive.google.com/open?id=0B2mske8Pqv4IX3pCZGd5d2hIRDA").toURI());
            } catch (URISyntaxException | MalformedURLException ex) {
                Logger.getLogger(CtrlAccueil.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
        if (e.getSource().equals(vue.getjButtonJouer())) {
            connexion();
        }
        if (e.getSource().equals(vue.getjButtonQuitter())) {
            ctrlPrincipal.quitterApplication();
        }
        if (e.getSource() == vue.getjButtonReconnecter()) {
            connecter();
        }
        if (e.getSource() == vue.getjButtonCreerCompte()) {
            ctrlPrincipal.afficherCreerCompte();
        }
        if (e.getSource() == vue.getjCheckBoxMemoriserPseudo()) {
            if (vue.getjCheckBoxMemoriserPseudo().isSelected()) {
                vue.getjCheckBoxMemoriserMdp().setEnabled(true);
            } else {
                vue.getjCheckBoxMemoriserMdp().setEnabled(false);
                vue.getjCheckBoxMemoriserMdp().setSelected(false);
            }
        }
    }
}
