/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modele.dao.UserDao;
import modele.metier.User;
import util.Crypt;
import util.JTextFieldLimit;
import vues.VueCreerCompte;

/**
 *
 * @author Benoit
 */
public class CtrlCreerCompte implements WindowListener, ActionListener, DocumentListener, FocusListener, KeyListener {

    private VueCreerCompte vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;
    private boolean mdpCorrect;
    private boolean loginCorrect;
    private boolean canSee = false;

    public CtrlCreerCompte(final VueCreerCompte vue, CtrlPrincipal ctrl) {
        this.vue = vue;
        this.ctrlPrincipal = ctrl;

        // le contrôleur écoute la vue
        vue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.vue.addWindowListener(this);
        this.vue.setResizable(false);
        vue.setLocationRelativeTo(null);
        this.vue.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/globe.png")).getImage());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/people.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(vue.getWidth(), vue.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.vue.getjLabelBackground().setIcon(imageIcon); // NOI18N
        vue.getjButtonAccueil().addActionListener(this);
        vue.getjButtonQuitter().addActionListener(this);
        vue.getjButtonValider().addActionListener(this);
        vue.getjTextFieldEmail().setVisible(false);
        vue.getjLabelEmail().setVisible(false);
        vue.getjLabelEtatEmail().setVisible(false);
        vue.getjTextFieldPseudo().setDocument(new JTextFieldLimit(15));
        //vue.getjPasswordFieldPassword().setDocument(new JTextFieldLimit(20));
        vue.getjTextFieldPseudo().setText("Pseudo");
        vue.getjLabelEtatEmail().setText("");
        vue.getjLabelEtatMdp().setText("");
        vue.getjLabelEtatPseudo().setText("");
        vue.getjPasswordFieldPassword().setText("Mot de passe");
        vue.getjPasswordFieldPassword().getDocument().addDocumentListener(this);
        vue.getjTextFieldPseudo().getDocument().addDocumentListener(this);
        vue.getjPasswordFieldPassword().addKeyListener(this);
        vue.getjTextFieldPseudo().addKeyListener(this);
        vue.getjTextFieldPseudo().addFocusListener(this);
        vue.getjPasswordFieldPassword().addFocusListener(this);
        vue.getjCheckBoxAfficher().addActionListener(this);
        vue.getjTextFieldPseudo().setForeground(Color.gray);
        vue.getjPasswordFieldPassword().setForeground(Color.gray);
        vue.getjPasswordFieldPassword().setEchoChar((char) 0);
        vue.getjCheckBoxAfficher().setEnabled(false);
        vue.getjButtonAfficher().setEnabled(false);
        vue.getjButtonAfficher().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (canSee) {
                    if (!vue.getjCheckBoxAfficher().isSelected()) {
                        vue.getjPasswordFieldPassword().setEchoChar('•');
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (canSee) {
                    vue.getjPasswordFieldPassword().setEchoChar((char) 0);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        vue.getjButtonAccueil().requestFocus();
        // préparer l'état iniitial de la vue

    }
    // contrôle de la vue

    // méthodes d'action
    /**
     *
     */
    private void checkLogin() {
        if (vue.getjTextFieldPseudo().getText().equalsIgnoreCase("pseudo") || vue.getjTextFieldPseudo().getText().length() < 3) {
            vue.getjLabelEtatPseudo().setText("✘");
            vue.getjLabelEtatPseudo().setForeground(Color.red);
        } else {
            vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                boolean pris = UserDao.verifierDisponibiltePseudo(vue.getjTextFieldPseudo().getText());
                if (pris) {//pseudo deja pris
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
    }

    /**
     *
     */
    private void checkMdp() {
        if (vue.getjPasswordFieldPassword().getText().length() < 5 || vue.getjPasswordFieldPassword().getText().length() > 50 || vue.getjPasswordFieldPassword().getText().equals("Mot de passe")) {//mauvais login bon mdp
            vue.getjLabelEtatMdp().setText("✘");
            vue.getjLabelEtatMdp().setForeground(Color.red);
            mdpCorrect = false;
        } else {
            vue.getjLabelEtatMdp().setText("✔");
            vue.getjLabelEtatMdp().setForeground(Color.green);
            mdpCorrect = true;
        }
    }

    private boolean checkForm() {
        if (vue.getjTextFieldPseudo().getText().length() < 3) {
            return true;
        } else if (vue.getjPasswordFieldPassword().getText().length() < 5) {
            return true;
        }
        return false;
    }

    private void valider() {
        String message = "";
        boolean erreur = false;
        if (vue.getjTextFieldPseudo().getText().length() < 3) {
            erreur = true;
            message += "\nVotre pseudo doit faire au moins 3 caractères";
        }
        if (vue.getjPasswordFieldPassword().getText().length() < 5) {
            erreur = true;
            message += "\nVotre mot de passe doit faire au moins 5 caractères";
        }
        if (vue.getjPasswordFieldPassword().getText().length() > 50) {
            erreur = true;
            message += "\nVeuillez choisir un mot de passe de moins de 50 caractères";
        }
        if (vue.getjTextFieldPseudo().getText().equalsIgnoreCase("pseudo")) {
            erreur = true;
            message += "\nPseudo incorrect";
        }
        if (vue.getjPasswordFieldPassword().getText().equals("Mot de passe")) {
            erreur = true;
            message += "\nMot de passe incorrect";
        }
        if (erreur) {
            JOptionPane.showMessageDialog(vue, message);
        } else {
            vue.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ArrayList<User> users = new ArrayList<>();
            try {
                users = UserDao.selectAll();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlCreerCompte.class.getName()).log(Level.SEVERE, null, ex);
            }
            int accountCount = 0;
            boolean pseudoDejaPris = false;
            User user = ctrlPrincipal.getUser();
            for (User aUser : users) {
                if (aUser.getPseudo().equalsIgnoreCase(vue.getjTextFieldPseudo().getText())) {
                    pseudoDejaPris = true;
                }
                if (aUser.getMac().equals(user.getMac())) {
                    accountCount++;
                }
            }
            if (accountCount > 3) {

            }
            if (pseudoDejaPris) {
                JOptionPane.showMessageDialog(vue, "Pseudo déjà pris !");
                vue.getjTextFieldPseudo().requestFocus();
                vue.getjTextFieldPseudo().selectAll();
            } else {
                user.setDate(Calendar.getInstance().getTime());
                user.setAdmin(false);
                user.setPseudo(vue.getjTextFieldPseudo().getText());
                String cryptedPassword = Crypt.cryptMdp(vue.getjPasswordFieldPassword().getText());
                user.setPassword(cryptedPassword);
                try {
                    UserDao.insert(user);
                    User newUser = UserDao.selectOneByPseudo(vue.getjTextFieldPseudo().getText());
                    ctrlPrincipal.setUser(newUser);
                    JOptionPane.showMessageDialog(ctrlPrincipal.getCurrentView(), "Bienvenue " + newUser.getPseudo() + " !");
                    ctrlPrincipal.afficherAccueilChoix();
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlCreerCompte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            vue.getContentPane().setCursor(null);
        }
    }

    /**
     * Quitter l'application, après demande de confirmation
     */
    private void quitter() {
        ctrlPrincipal.quitterApplication();
    }

    // ACCESSEURS et MUTATEURS
    public VueCreerCompte getVue() {
        return vue;
    }

    public void setVue(VueCreerCompte vue) {
        this.vue = vue;
    }

    // REACTIONS EVENEMENTIELLES
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getjButtonQuitter()) {
            quitter();
        }
        if (e.getSource() == vue.getjButtonAccueil()) {
            ctrlPrincipal.afficherAccueil(vue);
        }
        if (e.getSource() == vue.getjButtonValider()) {
            valider();
        }
        if (canSee) {
            if (e.getSource() == vue.getjCheckBoxAfficher()) {
                if (!vue.getjCheckBoxAfficher().isSelected()) {
                    vue.getjPasswordFieldPassword().setEchoChar('•');
                    vue.getjButtonAfficher().setEnabled(true);
                } else {
                    vue.getjPasswordFieldPassword().setEchoChar((char) 0);
                    vue.getjButtonAfficher().setEnabled(false);
                }
            }
        }
    }

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
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
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
                vue.getjCheckBoxAfficher().setEnabled(true);
                vue.getjButtonAfficher().setEnabled(true);
                canSee = true;
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
                vue.getjCheckBoxAfficher().setEnabled(false);
                vue.getjButtonAfficher().setEnabled(false);
                canSee = false;
            } else {
                canSee = true;
                vue.getjCheckBoxAfficher().setEnabled(true);
                vue.getjButtonAfficher().setEnabled(true);
            }
            checkMdp();
        }
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
                        valider();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
