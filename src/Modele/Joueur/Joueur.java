
package src.Modele.Joueur;

import src.Modele.Plateau;

public interface Joueur {
    public int chooseMove(Plateau jeu);
    public void doExecute(Plateau jeu); // sinon ça bug pour les algo de recherches
    public Boolean isBot();
    public String getName();

}
