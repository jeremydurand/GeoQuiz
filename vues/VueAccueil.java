/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Benoit
 */
public class VueAccueil extends javax.swing.JFrame {

    /**
     * Creates new form VueMenuGeneral
     */
    public VueAccueil() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMenu = new javax.swing.JPanel();
        jButtonReconnecter = new javax.swing.JButton();
        jLabelVersion = new javax.swing.JLabel();
        jLabelTitre = new javax.swing.JLabel();
        jLabelConnectezVous = new javax.swing.JLabel();
        jButtonNewVersion = new javax.swing.JButton();
        jTextFieldPseudo = new javax.swing.JTextField();
        jButtonJouer = new javax.swing.JButton();
        jLabelNewVersion = new javax.swing.JLabel();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jLabelEtatPseudo = new javax.swing.JLabel();
        jLabelEtatMdp = new javax.swing.JLabel();
        jButtonQuitter = new javax.swing.JButton();
        jButtonMdpOublie = new javax.swing.JButton();
        jCheckBoxMemoriserPseudo = new javax.swing.JCheckBox();
        jCheckBoxMemoriserMdp = new javax.swing.JCheckBox();
        jButtonCreerCompte = new javax.swing.JButton();
        jLabelMessage = new javax.swing.JLabel();
        jLabelBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Geo Quiz");
        setResizable(false);

        jPanelMenu.setLayout(null);

        jButtonReconnecter.setText("Reconnecter");
        jPanelMenu.add(jButtonReconnecter);
        jButtonReconnecter.setBounds(200, 340, 100, 50);

        jLabelVersion.setText("Version");
        jPanelMenu.add(jLabelVersion);
        jLabelVersion.setBounds(20, 380, 50, 14);

        jLabelTitre.setFont(new java.awt.Font("Perpetua Titling MT", 1, 36)); // NOI18N
        jLabelTitre.setForeground(new java.awt.Color(0, 153, 51));
        jLabelTitre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitre.setText("Geo quiz");
        jPanelMenu.add(jLabelTitre);
        jLabelTitre.setBounds(80, 0, 370, 60);

        jLabelConnectezVous.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabelConnectezVous.setForeground(new java.awt.Color(255, 0, 0));
        jLabelConnectezVous.setText("COnnectez vous :");
        jPanelMenu.add(jLabelConnectezVous);
        jLabelConnectezVous.setBounds(40, 60, 250, 23);

        jButtonNewVersion.setText("Télécharger");
        jPanelMenu.add(jButtonNewVersion);
        jButtonNewVersion.setBounds(190, 270, 120, 50);

        jTextFieldPseudo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanelMenu.add(jTextFieldPseudo);
        jTextFieldPseudo.setBounds(30, 90, 190, 20);

        jButtonJouer.setText("JOUER");
        jPanelMenu.add(jButtonJouer);
        jButtonJouer.setBounds(40, 160, 65, 23);

        jLabelNewVersion.setFont(new java.awt.Font("Perpetua Titling MT", 1, 14)); // NOI18N
        jLabelNewVersion.setForeground(new java.awt.Color(255, 0, 0));
        jLabelNewVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNewVersion.setText("Une nouvelle version de Géo Quiz est disponible !");
        jPanelMenu.add(jLabelNewVersion);
        jLabelNewVersion.setBounds(10, 190, 510, 20);

        jPasswordFieldPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordFieldPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldPasswordActionPerformed(evt);
            }
        });
        jPanelMenu.add(jPasswordFieldPassword);
        jPasswordFieldPassword.setBounds(30, 130, 190, 20);

        jLabelEtatPseudo.setText("Etat Pseudo");
        jPanelMenu.add(jLabelEtatPseudo);
        jLabelEtatPseudo.setBounds(230, 100, 58, 14);

        jLabelEtatMdp.setText("Etat Mdp");
        jPanelMenu.add(jLabelEtatMdp);
        jLabelEtatMdp.setBounds(230, 140, 43, 14);

        jButtonQuitter.setText("Quitter");
        jPanelMenu.add(jButtonQuitter);
        jButtonQuitter.setBounds(440, 370, 67, 23);

        jButtonMdpOublie.setText("Mot de passe oublié");
        jPanelMenu.add(jButtonMdpOublie);
        jButtonMdpOublie.setBounds(370, 120, 140, 23);

        jCheckBoxMemoriserPseudo.setText("Memoriser");
        jPanelMenu.add(jCheckBoxMemoriserPseudo);
        jCheckBoxMemoriserPseudo.setBounds(260, 90, 120, 23);

        jCheckBoxMemoriserMdp.setText("Memoriser");
        jPanelMenu.add(jCheckBoxMemoriserMdp);
        jCheckBoxMemoriserMdp.setBounds(260, 130, 120, 23);

        jButtonCreerCompte.setText("Creer Compte");
        jButtonCreerCompte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreerCompteActionPerformed(evt);
            }
        });
        jPanelMenu.add(jButtonCreerCompte);
        jButtonCreerCompte.setBounds(400, 90, 110, 23);

        jLabelMessage.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        jLabelMessage.setForeground(new java.awt.Color(0, 51, 204));
        jLabelMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMessage.setText("Connexion...");
        jPanelMenu.add(jLabelMessage);
        jLabelMessage.setBounds(100, 210, 290, 50);
        jPanelMenu.add(jLabelBackground);
        jLabelBackground.setBounds(0, 0, 510, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreerCompteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreerCompteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCreerCompteActionPerformed

    private void jPasswordFieldPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordFieldPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordFieldPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VueAccueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueAccueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueAccueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueAccueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VueAccueil().setVisible(true);
            }
        });
    }

    public JButton getjButtonJouer() {
        return jButtonJouer;
    }

    public JLabel getjLabelConnectezVous() {
        return jLabelConnectezVous;
    }

    public JLabel getjLabelTitre() {
        return jLabelTitre;
    }

    public JTextField getjTextFieldPseudo() {
        return jTextFieldPseudo;
    }

    public JButton getjButtonQuitter() {
        return jButtonQuitter;
    }

    public JLabel getjLabelBackground() {
        return jLabelBackground;
    }

    public JLabel getjLabelMessage() {
        return jLabelMessage;
    }

    public JLabel getjLabelVersion() {
        return jLabelVersion;
    }

    public JLabel getjLabelNewVersion() {
        return jLabelNewVersion;
    }

    public JButton getjButtonNewVersion() {
        return jButtonNewVersion;
    }

    public JButton getjButtonReconnecter() {
        return jButtonReconnecter;
    }

    public JPanel getjPanelMenu() {
        return jPanelMenu;
    }

    public JButton getjButtonCreerCompte() {
        return jButtonCreerCompte;
    }

    public JPasswordField getjPasswordFieldPassword() {
        return jPasswordFieldPassword;
    }

    public JCheckBox getjCheckBoxMemoriserMdp() {
        return jCheckBoxMemoriserMdp;
    }

    public JCheckBox getjCheckBoxMemoriserPseudo() {
        return jCheckBoxMemoriserPseudo;
    }

    public JLabel getjLabelEtatMdp() {
        return jLabelEtatMdp;
    }

    public JLabel getjLabelEtatPseudo() {
        return jLabelEtatPseudo;
    }

    public JButton getjButtonMdpOublie() {
        return jButtonMdpOublie;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreerCompte;
    private javax.swing.JButton jButtonJouer;
    private javax.swing.JButton jButtonMdpOublie;
    private javax.swing.JButton jButtonNewVersion;
    private javax.swing.JButton jButtonQuitter;
    private javax.swing.JButton jButtonReconnecter;
    private javax.swing.JCheckBox jCheckBoxMemoriserMdp;
    private javax.swing.JCheckBox jCheckBoxMemoriserPseudo;
    private javax.swing.JLabel jLabelBackground;
    private javax.swing.JLabel jLabelConnectezVous;
    private javax.swing.JLabel jLabelEtatMdp;
    private javax.swing.JLabel jLabelEtatPseudo;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelNewVersion;
    private javax.swing.JLabel jLabelTitre;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JTextField jTextFieldPseudo;
    // End of variables declaration//GEN-END:variables
}