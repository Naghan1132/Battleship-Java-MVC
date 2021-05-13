
package src.Modele.Joueur;


import src.Modele.Plateau;

public class JoueurHumain implements Joueur {

    private String nomJoueur;
    private java.util.Scanner scanner;

    public JoueurHumain(String nomHumain, java.util.Scanner scanner) {
        this.nomJoueur = nomHumain;
        this.scanner = scanner;
    }

    @Override
    public int chooseMove(Plateau jeu) {
        String coup = this.scanner.next();
        while (!jeu.isValid(jeu.getPlateauAdversaire(this),Integer.parseInt(coup))) {
            System.out.println("Le coup n'est pas valide, recommence");
            coup = this.scanner.next();
        }
        return Integer.parseInt(coup);
    }

    public void doExecute(Plateau jeu){
        Integer coup = chooseMove(jeu);
        jeu.execute(coup,jeu.getPlateauAdversaire(this));
        jeu.changeJoueur_courant();
    }

    public Boolean isBot(){
        return false;
    }

    @Override
    public String getName() {
        return this.nomJoueur;
    }

    @Override
    public String toString() {
        return this.nomJoueur;
    }
}
