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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import vues.VueMenuChoix;

/**
 *
 * @author Benoit
 */
public class ControlleurDeBase implements WindowListener, ActionListener {

    private VueMenuChoix vue; // LA VUE
    private CtrlPrincipal ctrlPrincipal;

    public ControlleurDeBase(VueMenuChoix vue, CtrlPrincipal ctrl) {
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
        // préparer l'état iniitial de la vue

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
