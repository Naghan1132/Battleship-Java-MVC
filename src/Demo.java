package src;

import src.Modele.Joueur.*;
import src.Modele.Plateau;
import src.Vue.HomeGUI;

import java.util.Scanner;


public class Demo { // test du commit

    public static void main(String[] args) { // Demande à l'user si il veut jouer avec l'interface ou en console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel est votre nom, vous humain ?");
        String nom = scanner.next();
        JoueurHumain humain = new JoueurHumain(nom, scanner);

        System.out.println("Jouer en console (1) ou GUI (2) ?");
        String choix = scanner.next();
        if (Integer.parseInt(choix) == 1) {
            System.out.println("Quel adversaire choisissez-vous ?\n Facile : (1), Moyen : (2), Impossible :(3)");
            String choixAdversaire = scanner.next();

            if (Integer.parseInt(choixAdversaire) == 1) {
                JoueurRandom adversaire = new JoueurRandom("Adversaire Facile");

                Plateau jeu = new Plateau(humain, adversaire);
                System.out.println("plateau de " + humain.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(humain)));
                System.out.println("_______________________________________");
                System.out.println("plateau de " + adversaire.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(adversaire)));

                System.out.println("_______________________________________");
                while (!jeu.estPerdu()) {
                    System.out.println();
                    System.out.println(jeu.getJoueurCourant().getName() + " : vous devez couler les bateaux de votre adversaire");
                    System.out.println("Flotte ennemie :");
                    System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getJoueurCourant())));
                    jeu.getJoueurCourant().doExecute(jeu); // joue un coup et change de joueur courant
                    System.out.println("_______________________________________");
                }
                System.out.println("Le grand gagnant est " + jeu.getWinner().getName());
                System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getWinner())));
            } else if (Integer.parseInt(choixAdversaire) == 2) {
                JoueurIntelligent adversaire = new JoueurIntelligent("Adversaire Moyen");

                Plateau jeu = new Plateau(humain, adversaire);
                System.out.println("plateau de " + humain.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(humain)));
                System.out.println("_______________________________________");
                System.out.println("plateau de " + adversaire.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(adversaire)));

                System.out.println("_______________________________________");
                while (!jeu.estPerdu()) {
                    System.out.println();
                    System.out.println(jeu.getJoueurCourant().getName() + " : vous devez couler les bateaux de votre adversaire");
                    System.out.println("Flotte ennemie :");
                    System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getJoueurCourant())));
                    jeu.getJoueurCourant().doExecute(jeu); // joue un coup et change de joueur courant
                    System.out.println("_______________________________________");
                }
                System.out.println("Le grand gagnant est " + jeu.getWinner().getName());
                System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getWinner())));
            } else {
                JoueurImpossible adversaire = new JoueurImpossible("Adversaire Impossible");

                Plateau jeu = new Plateau(humain, adversaire);
                System.out.println("plateau de " + humain.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(humain)));
                System.out.println("_______________________________________");
                System.out.println("plateau de " + adversaire.getName());
                System.out.println(jeu.plateauToString(jeu.getPlateau(adversaire)));

                System.out.println("_______________________________________");
                while (!jeu.estPerdu()) {
                    System.out.println();
                    System.out.println(jeu.getJoueurCourant().getName() + " : vous devez couler les bateaux de votre adversaire");
                    System.out.println("Flotte ennemie :");
                    System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getJoueurCourant())));
                    jeu.getJoueurCourant().doExecute(jeu); // joue un coup et change de joueur courant
                    System.out.println("_______________________________________");
                }
                System.out.println("Le grand gagnant est " + jeu.getWinner().getName());
                System.out.println(jeu.plateauToString(jeu.getPlateauAdversaire(jeu.getWinner())));
            }
        } else {

            /* Home Menu */
            new HomeGUI();

            //______________//

        }
    }
}
