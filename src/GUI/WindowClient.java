package GUI;
import Tcp.TcpConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.text.DecimalFormat;

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
    private JTable tablePanier;
    private JButton supprimerArticleButton;
    private JButton viderLePanierButton;
    private JButton confirmerButton;
    private JLabel Image;
    private JTextField textFieldTotal;
    private JButton buttonOK;
    private JButton buttonCancel;

    private final TcpConnection serverConnection;
    private int articleEnCours;
    private int nbArticlePanier;
    private String nomclient;
    public WindowClient() {

        articleEnCours = 1;



        setContentPane(contentPane);
        setTitle("Le Maraicher en ligne  ");
       // setIconImage(new ImageIcon(this.getClass().getResource("GUI/Images/ail.jpg")).getImage());// ajout icon a l'app
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));// implemente fonction fenetre
        // Centrer la fenêtre
        setLocationRelativeTo(null);
        pack();
        setSize(750,600); // le pack() etire fort la table donc on fixe la taille pour que se soit plus jolie

        // changer les proprietes de la JTable
        tablePanier.setDefaultEditor(Object.class, null);
        tablePanier.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePanier.setRowSelectionAllowed(true);
        tablePanier.setColumnSelectionAllowed(false);

        /*DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Article", "Prix à l'unité", "Quantité"});
        tablePanier.setModel(model);*/
        textFieldTotal.setText("0.00");

        logoutok();

        /******CONNEXION SERVEUR******/

        try {
            serverConnection = new TcpConnection("192.168.157.128", 50000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /****************FERMERFENETRE*******************/

        addWindowListener(new WindowListener() {

            @Override
            public void windowClosing(WindowEvent e) {
                OVESP_CancelAll();
                try {
                    serverConnection.Close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }

            @Override
            public void windowOpened(WindowEvent e) {

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

        });

        /************************************************/

        /****************CLIQUEBOUTON*******************/
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OVESP_Login())
                    OVESP_Consult(articleEnCours);

                loginok();
                nomclient = textFieldNom.getText();

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OVESP_CancelAll();
                viderCaddie();
                articleEnCours = 1;
                OVESP_Consult(articleEnCours);
                logoutok();
            }
        });
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(articleEnCours > 1)
                    OVESP_Consult(articleEnCours - 1);

            }
        });
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(articleEnCours < 21)
                    OVESP_Consult(articleEnCours + 1);

            }
        });
        acheterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spinnerQantite.getValue().equals(0)){
                    JOptionPane.showMessageDialog(null, "Entrez une quantité", "Erreur d'achat", JOptionPane.ERROR_MESSAGE);
                }
                if (nbArticlePanier >=10){
                    JOptionPane.showMessageDialog(null, "Pannier pleins", "Erreur d'achat", JOptionPane.ERROR_MESSAGE);

                }
                else {
                    if (OVESP_Achat()){
                        OVESP_Caddie();
                        OVESP_Consult(articleEnCours);
                    }
                }

            }
        });
        supprimerArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablePanier.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un article", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
                else{
                    OVESP_Cancel();
                    OVESP_Caddie();
                    OVESP_Consult(articleEnCours);
                }

            }
        });
        viderLePanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OVESP_CancelAll();
                OVESP_Caddie();
                OVESP_Consult(articleEnCours);

            }
        });
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OVESP_Confirmer();
                viderCaddie();
            }
        });

        /************************************************/

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
        viderLePanierButton.setEnabled(true);
        confirmerButton.setEnabled(true);

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
        viderLePanierButton.setEnabled(false);
        confirmerButton.setEnabled(false);

    }

    /**
     * Permet d'afficher un article sur le GUI
     * @param nom Nom de l'article
     * @param stock Quantité de l'article
     * @param prix Prix de l'article
     * @param image Nom du fichier de l'article ("src/Images/" sera ajouté dans la fonction)
     */
    public void setArticle(String nom, String stock, String prix, String image){
        textFieldArticle.setText(nom);
        textFieldStock.setText(stock);
        textFieldPrixunit.setText(prix);
        Image.setIcon(new ImageIcon("src/GUI/Images/" + image));
    }

    /**
     * Permet de vider le panier et mettre à 0 le prix dans le GUI
     */
    public void viderCaddie(){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Article", "Prix à l'unité", "Quantité"});
        tablePanier.setModel(model);
        textFieldTotal.setText("0.00");
    }

    //OVESP
    //********** Fonction de protocol OVESP **********************************
    /**
     * Connecte le client à son compte Vérification de l’existence et du mot de passe du client / Création d’un nouveau client dans la table clients
     */
    public boolean OVESP_Login(){
        String data;
        String response;

        if (textFieldNom.getText().isEmpty() || textFieldMdp.getText().isEmpty()){// si un des deux champs est vide
            JOptionPane.showMessageDialog(null, "Remplisez les champs !", "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Envoie de la requete
        int value = nouveauClientCheckBox.isSelected() ? 1 : 0; // 1 = true = coche // 0 = false = pas coche
        data = "LOGIN#" + value + "#" + textFieldNom.getText() + "#" + textFieldMdp.getText();
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");

        System.out.println("DEBUG : " + champs[1]);

        if (champs[1].equals("OK")){
            JOptionPane.showMessageDialog(null, champs[2], "Connection réussie !", JOptionPane.INFORMATION_MESSAGE);
            loginok();
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, champs[2], "Erreur de connection", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
    public void OVESP_Consult(int article){
        String data;
        String response;

        // Envoie de la requete
        data = "CONSULT#" + article;
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");
        int id = Integer.parseInt(champs[1]);
        if (id > 0)
        {
            articleEnCours = id;
            System.out.println("DEBUG : " + champs[1]);
            System.out.println("DEBUG : " + champs[2]);
            System.out.println("DEBUG : " + champs[3]);
            System.out.println("DEBUG : " + champs[4]);
            System.out.println("DEBUG : " + champs[5]);

            setArticle(champs[2],champs[4],champs[3],champs[5]);
        }
    }
    public boolean OVESP_Achat() {
        String data;
        String response;
        int quantite = (int) spinnerQantite.getValue();
        data = "ACHAT#" + articleEnCours + "#" + quantite;


        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");
        if (Integer.parseInt(champs[1]) <= 0) {
            switch (Integer.parseInt(champs[1])) {
                case 0 -> JOptionPane.showMessageDialog(null, "Stock insufisant", "Erreur d'achat", JOptionPane.ERROR_MESSAGE);
                case -1 -> JOptionPane.showMessageDialog(null, "Article non trouvé", "Erreur d'achat", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
        else{
            JOptionPane.showMessageDialog(null, "Achat réussie", "Achat réussie", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
    }

    public void OVESP_Caddie(){
        String data;
        String response;
        int nbArticle;
        double prixTotal = 0;

        // Envoie de la requete
        data = "CADDIE" ;
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");

        nbArticlePanier = Integer.parseInt(champs[1]);
        System.out.println("DEBUG : " + champs[1]);



        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Article", "Prix à l'unité", "Quantité"});

        for (int i = 0; i<nbArticlePanier; i++){
            // Définir les noms des colonnes
            System.out.println("DEBUG : " + champs[(i*4)+3]);
            System.out.println("DEBUG : " + champs[(i*4)+5]);
            System.out.println("DEBUG : " + champs[(i*4)+4]);

            model.addRow(new Object[]{champs[(i*4)+3], champs[(i*4)+5], champs[(i*4)+4]});
            prixTotal += Float.parseFloat(champs[(i*4)+5]) * Integer.parseInt(champs[(i*4)+4]);
        }


        // Formater le prix avec deux chiffres après la virgule
        DecimalFormat df = new DecimalFormat("#.##");



        textFieldTotal.setText(df.format(prixTotal));
        tablePanier.setModel(model);

    }
    public void OVESP_Cancel() {
        String data;
        String response;
        data = "CANCEL#" + tablePanier.getSelectedRow()+"#"+nbArticlePanier;
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");
        if (! champs[1].equals("OK"))
            JOptionPane.showMessageDialog(null, "Erreur durant la suppresion de l'article", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);

    }
    public void OVESP_CancelAll(){
        String data;
        String response;
        data = "CANCELALL#" +nbArticlePanier;
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");
        if (! champs[1].equals("OK"))
            JOptionPane.showMessageDialog(null, "Erreur durant la suppresion du panier", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);

    }
    public void OVESP_Confirmer(){
        String data;
        String response;

        String prixt = textFieldTotal.getText();
        prixt = prixt.replace(',', '.');
        float prixtotal = Float.parseFloat(prixt);

        data = "CONFIRMER#"+ nomclient + "#" + prixtotal + "#" + nbArticlePanier;
        try {
            serverConnection.Send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Reception et parsing de la reponse
        try {
            response = serverConnection.Receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] champs = response.split("#");
        int numfac = Integer.parseInt(champs[1]);
        if(numfac > 0)
            JOptionPane.showMessageDialog(null, "La commande à bien été envoyé au Maraîcher. Numéro de facture : " + champs[1], "Commande réussi", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du passage de la commande", "Erreur de commande", JOptionPane.ERROR_MESSAGE);

    }





    public static void main(String[] args) {
        WindowClient dialog = new WindowClient();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }


}
