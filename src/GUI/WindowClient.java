package GUI;
import Tcp.TcpConnection;

import javax.swing.*;
import java.awt.event.*;

public class WindowClient extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldNom;
    private JTextField textFieldMdp;
    private JButton loginButton;
    private JButton logoutButton;
    private JCheckBox nouveauClientCheckBox;
    private JPanel Magasin;
    private JTextField textFieldArticle;
    private JTextField textFieldPrixunit;
    private JTextField textFieldStock;
    private JSpinner spinnerQantite;
    private JButton buttonPrevious;
    private JButton buttonNext;
    private JButton acheterButton;
    private JPanel Publiciter;
    private JPanel JpanelPanier;
    private JTable Panier;
    private JButton supprimerArticleButton;
    private JButton vidéeLePanierButton;
    private JButton confirmérAchatButton;
    private JLabel Image;
    private JButton buttonOK;
    private JButton buttonCancel;

    public WindowClient() {
        setContentPane(contentPane);
        setTitle("Le Maraicher en ligne  ");
       // setIconImage(new ImageIcon(this.getClass().getResource("Gui/Images/icon.jpg")).getImage());// ajout icon a l'app
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));// implemente fonction fenetre
        // Centrer la fenêtre
        setLocationRelativeTo(null);
        pack();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        acheterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        supprimerArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        vidéeLePanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        confirmérAchatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void loginok(){
        //login
        textFieldNom.setEnabled(false);
        textFieldMdp.setEnabled(false);
        loginButton.setEnabled(false);
        logoutButton.setEnabled(true);
        nouveauClientCheckBox.setEnabled(false);

        //Magasin
        buttonNext.setEnabled(true);
        buttonPrevious.setEnabled(true);
        spinnerQantite.setEnabled(true);
        acheterButton.setEnabled(true);

        //Panier

        supprimerArticleButton.setEnabled(true);
        vidéeLePanierButton.setEnabled(true);
        confirmérAchatButton.setEnabled(true);

    }

    public void logoutok(){

        //login
        textFieldNom.setEnabled(true);
        textFieldMdp.setEnabled(true);
        loginButton.setEnabled(true);
        nouveauClientCheckBox.setEnabled(true);
        logoutButton.setEnabled(false);

        //Magasin
        buttonNext.setEnabled(false);
        buttonPrevious.setEnabled(false);
        spinnerQantite.setEnabled(false);
        acheterButton.setEnabled(false);

        //Panier

        supprimerArticleButton.setEnabled(false);
        vidéeLePanierButton.setEnabled(false);
        confirmérAchatButton.setEnabled(false);

    }



    public static void main(String[] args) {
        WindowClient dialog = new WindowClient();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}
