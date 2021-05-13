package src.Modele.Joueur;

import src.Modele.InfoJButton;
import src.Modele.Plateau;

public class JoueurImpossible implements Joueur {

    protected String nomBot;

    public JoueurImpossible(String nomBot) {
        this.nomBot = nomBot;
    }

    public int chooseMove(Plateau jeu) {
        InfoJButton[][] p = jeu.getPlateauAdversaire(jeu.getJoueurCourant());
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (p[i][j].getTypeCase() == 1) {
                    return p[i][j].getCpt();
                }
            }
        }
        return 1;
    }

    public void doExecute(Plateau jeu) {
        Integer coup = chooseMove(jeu);
        jeu.execute(coup, jeu.getPlateauAdversaire(jeu.getJoueurCourant()));
        System.out.println("Le joueur Impossible à choisi le coup "+coup);
        jeu.changeJoueur_courant();
    }

    @Override
    public String getName() {
        return nomBot;
    }

    public Boolean isBot(){
        return true;
    }
}
