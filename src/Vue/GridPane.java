package src.Vue;

import src.Constante;
import src.Control.BoxListener;
import src.Control.EcouteurModele;
import src.Control.ModeleEcoutable;
import src.Modele.BatailleNavale;
import src.Modele.InfoJButton;
import src.Modele.Joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPane extends JPanel implements EcouteurModele {

    private InfoJButton[][] infoJButtons;
    private BatailleNavale plateau;

    private Joueur joueur;

    private ArrayList<InfoJButton> disableButton;
    private ArrayList<InfoJButton> enableButton;

    private Integer nbrBateaux;
    private Integer cptNbrBateauxPlacer;
    private Boolean createdHumanGrid;

    public GridPane(BatailleNavale plateau, Joueur joueur) {
        this.createdHumanGrid = false;
        this.plateau = plateau;
        this.joueur = joueur;
        this.cptNbrBateauxPlacer = 0;
        this.nbrBateaux = plateau.getPlateau().getNbBateaux(); // pour le placement
        this.infoJButtons = plateau.getPlateau().getPlateauAdversaire(joueur); // Car on joue sur la plateau du joueur adverse
        disableButton = new ArrayList<>();
        enableButton = new ArrayList<>();

        createGrid();
        this.getPlateau().ajoutEcouteur(this); // On ajoute l'écouteur ICI
        this.setLayout(new GridLayout(Constante.ROW, Constante.COL)); // Ok
    }

    public Integer getCptNbrBateauxPlacer() {
        return cptNbrBateauxPlacer;
    }

    public BatailleNavale getPlateau() {
        return plateau;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public InfoJButton[][] getPan() {
        return infoJButtons;
    }

    public Integer getNbrBateaux() {
        return nbrBateaux;
    }

    public ArrayList<InfoJButton> getDisableButton() {
        return disableButton;
    }

    public ArrayList<InfoJButton> getEnableButton() {
        return enableButton;
    }

    public void desactiveButton() {
        for (int i = 0; i < Constante.ROW; i++) {
            for (int j = 0; j < Constante.COL; j++) {
                this.infoJButtons[i][j].setEnabled(false);
            }
        }
    }
    
    @Override
    public void modeleMisAJour(ModeleEcoutable source) {
        if (plateau.getAllBoatPlaces()) {
            play();
        }
    }

    public void ajoutCptNbBateauxPlacer() {
        cptNbrBateauxPlacer += 1;
    }

    public Boolean aSonTour() {
        if (getPlateau().getPlateau().getJoueurCourant() == joueur) {
            return true;
        } else {
            return false;
        }
    }

    public void play() {
        if (plateau.getAllBoatPlaces() && !createdHumanGrid && !joueur.isBot()) {
            createGridClickable();
        }
        else if (!plateau.getPlateau().estPerdu()) {
            if (!joueur.isBot() && aSonTour()) {
                activeButton();
            } else if (joueur.isBot() && aSonTour()) {
                joueur.doExecute(getPlateau().getPlateau());
            }
            update();

        } else {

            this.removeAll();
            this.revalidate();
            this.repaint();
            this.add(new Win(plateau.getPlateau().getWinner()));
        }

    }

    public void createGrid() {
        for (int i = 0; i < Constante.ROW; i++) {
            for (int j = 0; j < Constante.COL; j++) {
                (infoJButtons[i][j]).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.add(infoJButtons[i][j]);
                if (!plateau.getPlateau().getJoueur_Adversaire(joueur).isBot() && joueur.isBot() && !plateau.getAllBoatPlaces()) {
                    plateau.getPlateau().miseAZeroBateau(plateau.getPlateau().getPlateauAdversaire(joueur));
                    plateau.setAllBoatPlaces(false);
                    infoJButtons[i][j].addActionListener(new BoxListener(this, joueur, false, plateau));
                    infoJButtons[i][j].setEnabled(true);
                    //Pour que le joueur humain puisse placer ses bateaux
                }
            }
        }
        update();
    }

    public void createGridClickable() {
        for (int i = 0; i < Constante.ROW; i++) {
            for (int j = 0; j < Constante.COL; j++) {
                infoJButtons[i][j].addActionListener(new BoxListener(this, joueur, true, plateau));
                infoJButtons[i][j].setEnabled(true);
                enableButton.add(infoJButtons[i][j]);
                createdHumanGrid = true;
            }
        }
        update();
    }

    public void activeButton() {
        for (InfoJButton e : enableButton) {
            e.setEnabled(true);
        }
    }

    public void update() {
        for (int i = 0; i < Constante.ROW; i++) {
            for (int j = 0; j < Constante.COL; j++) {
                if (this.infoJButtons[i][j].getTypeCase() == 0) {
                    this.infoJButtons[i][j].setBackground(new Color(97, 181, 255)); // La mer, bleu
                } else if (this.infoJButtons[i][j].getTypeCase() == 1 && joueur.isBot()) { // Pour voir nos bateaux dans le plateau de l'adversaire
                    this.infoJButtons[i][j].setBackground(new Color(236, 149, 227)); //Rose pour nos bateaux dans le plateau adverse
                } else if (this.infoJButtons[i][j].getTypeCase() == 1) { // Les bateaux adverses sont invisibles
                    this.infoJButtons[i][j].setBackground(new Color(97, 181, 255));
                } else if (this.infoJButtons[i][j].getTypeCase() == 2) { // Tir sur un bateau
                    this.infoJButtons[i][j].setBackground(Color.red);
                } else if (this.infoJButtons[i][j].getTypeCase() == 3) { // Tir dans la mer
                    this.infoJButtons[i][j].setBackground(new Color(104, 246, 94));
                } else if (this.infoJButtons[i][j].getTypeCase() == 4) { // Bateau coulé
                    this.infoJButtons[i][j].setBackground(Color.darkGray);
                }
            }
        }
    }

}