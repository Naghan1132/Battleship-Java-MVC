package src.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Modele.BatailleNavale;
import src.Modele.InfoJButton;
import src.Modele.Joueur.Joueur;
import src.Vue.GridPane;

public class BoxListener implements ActionListener {

    private GridPane gridPane;
    private Joueur j;
    private Boolean humain;
    private BatailleNavale p;

    public BoxListener(GridPane gridPane, Joueur j, Boolean humain, BatailleNavale p) {
        this.gridPane = gridPane;
        this.j = j;
        this.humain = humain; // sers à savoir si c'est a notre tour ou pas, si non alors ne pas activer les buttons
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        InfoJButton clickedBox = (InfoJButton) actionEvent.getSource();
        if (humain) {
            if (clickedBox.getTypeCase() == 0) {

                miseAJourListeBouton(clickedBox);
                clickedBox.setTypeCase(3);

                gridPane.getPlateau().getPlateau().execute(clickedBox.getCpt(), gridPane.getPan());

                if (gridPane.getPlateau().getPlateau().getJoueurCourant() == j) {
                    gridPane.getPlateau().getPlateau().setJoueurCourant(gridPane.getPlateau().getPlateau().getJoueur2());
                } else {
                    gridPane.getPlateau().getPlateau().setJoueurCourant(j);
                }


            } else if (clickedBox.getTypeCase() == 1) {

                miseAJourListeBouton(clickedBox);
                clickedBox.setTypeCase(2);

                gridPane.getPlateau().getPlateau().execute(clickedBox.getCpt(), gridPane.getPan());

                if (gridPane.getPlateau().getPlateau().getJoueurCourant() == j) {
                    gridPane.getPlateau().getPlateau().setJoueurCourant(gridPane.getPlateau().getPlateau().getJoueur2());
                } else {
                    gridPane.getPlateau().getPlateau().setJoueurCourant(j);
                }
            }

        } else { // on permet juste à l'user de mettre ses bateaux dans le plateau adverse, car la partie n'a pas commencé

            if(gridPane.getCptNbrBateauxPlacer() < gridPane.getNbrBateaux()){
                clickedBox.setEnabled(false);
                p.getPlateau().randomFill(gridPane.getPan(), clickedBox);
                gridPane.ajoutCptNbBateauxPlacer();

                gridPane.update();
            }
            else { // si le compteur > nbBateau alors on arrête le placement
                gridPane.getPlateau().setAllBoatPlaces(true);
            }

        }

        gridPane.getPlateau().getPlateau().verifieSiCouler(gridPane.getPlateau().getPlateau().getPlateauAdversaire(j), clickedBox);

        gridPane.update();

        p.next();


    }

    public void miseAJourListeBouton(InfoJButton jButton) {
        gridPane.getDisableButton().add(jButton);
        gridPane.getEnableButton().remove(jButton);
    }


}