
package src.Modele.Joueur;

import src.Modele.Plateau;

public class JoueurRandom implements Joueur {
    private String nomBot;

    public JoueurRandom(String nomBot) {
        this.nomBot = nomBot;
    }

    @Override
    public int chooseMove(Plateau jeu) {
        int coup = (int) Math.round(Math.random() * 80);
        while (!jeu.isValid(jeu.getPlateauAdversaire(this),coup)) {
            coup = (int) Math.round(Math.random() * 80);
        }
        return coup;
    }

    public void doExecute(Plateau jeu) {
        Integer coup = chooseMove(jeu);
        System.out.println("JRandom a choisi le coup "+coup);
        jeu.execute(coup, jeu.getPlateauAdversaire(this));
        jeu.changeJoueur_courant();
    }

    @Override
    public String getName() {
        return nomBot;
    }

    @Override
    public Boolean isBot(){
        return true;
    }
}
