package src.Vue;

import javax.swing.*;

import src.Modele.*;

import java.awt.Dimension;
import java.awt.GridLayout;


public class BatailleGUI extends JFrame {

    private GridPane pane1,pane2;
    private BatailleNavale plateau;

    public BatailleGUI(BatailleNavale plateau) {


        super("Bataille navale");//Titre de la fenetre

        this.plateau = plateau;

        this.setSize(new Dimension(500,500));

        /* Les deux plateaux, récupérer quel joueur on veux pour le jeu */

        GridLayout layout = new GridLayout(0,2,80,20);
        this.setLayout(layout);
        pane1 = new GridPane(plateau,plateau.getPlateau().getJoueur());
        pane2 = new GridPane(plateau,plateau.getPlateau().getJoueur2());

        this.add(pane1);
        this.add(pane2);

        this.pack();
        this.setLocationRelativeTo(null); //La fenetre pop au milleu de l'écran
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Pour que la fenetre se ferme quand on appuie sur la croix

    }


}
