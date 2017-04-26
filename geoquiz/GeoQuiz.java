/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geoquiz;

import controller.CtrlAccueil;
import controller.CtrlPrincipal;
import java.awt.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vues.VueAccueil;

/**
 *
 * @author Benoit
 */
public class GeoQuiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            // Paramétrage de l'apparance
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GeoQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        CtrlPrincipal leControleurPrincipal = new CtrlPrincipal();
        
        VueAccueil laVueAccueil = new VueAccueil();
        CtrlAccueil leControleurAccueil = new CtrlAccueil(laVueAccueil, leControleurPrincipal);
        leControleurPrincipal.setCtrlMenu(leControleurAccueil);
        leControleurAccueil.getVue().setLocationRelativeTo(null);//position de la fenetre centrée au démarrage
        // afficher la vue
        leControleurPrincipal.afficherAccueil(null);
        leControleurAccueil.connecter();
    }
    
}
