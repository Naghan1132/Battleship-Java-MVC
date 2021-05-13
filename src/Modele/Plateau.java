package src.Modele;

import src.Constante;
import src.Modele.Joueur.Joueur;

import java.util.ArrayList;
import java.util.Random;

public class Plateau {

    private InfoJButton[][] plateau, plateau2;
    private final int TAILLE = 81;
    private int[] nbBoat;
    private Bateau[] listeBateau;
    private Bateau[] listeBateau2;
    private Joueur joueur, joueur2, joueurCourant;

    public Plateau(Joueur joueur, Joueur joueur2) {
        this.joueur = joueur;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur;

        this.plateau = new InfoJButton[Constante.ROW][Constante.COL];
        this.plateau2 = new InfoJButton[Constante.ROW][Constante.COL];

        this.nbBoat = new int[5];
        this.listeBateau = new Bateau[5];
        this.listeBateau2 = new Bateau[5];

        initPlat();

        createBoat(listeBateau); //J1
        addAllBoat(listeBateau, plateau);

        createBoat(listeBateau2); //J2
        addAllBoat(listeBateau2, plateau2);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public InfoJButton[][] getPlateau(Joueur j) { // Retourne le plateau du joueur
        if (j == joueur) {
            return plateau;
        } else {
            return plateau2;
        }
    }

    public InfoJButton[][] getPlateauAdversaire(Joueur j) { // Retourne le plateau où le joueur joue ses coups
        if (j == joueur) {
            return plateau2;
        } else {
            return plateau;
        }
    }

    public Integer getNbBateaux() {
        return nbBoat.length;
    }

    public void setJoueurCourant(Joueur j) {
        this.joueurCourant = j;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Joueur getJoueur_Adversaire(Joueur j) {
        if (j == joueur) {
            return joueur2;
        } else {
            return joueur;
        }
    }

    public void changeJoueur_courant() {
        if (joueurCourant == joueur) {
            joueurCourant = joueur2;
        } else {
            joueurCourant = joueur;
        }
    }

    public void initPlat() { // commun pour les 2 joueurs
        Integer cpt = 0;
        for (int i = 0; i < Constante.ROW; i++) {
            for (int j = 0; j < Constante.COL; j++) {
                this.plateau[i][j] = new InfoJButton();
                this.plateau[i][j].setCpt(cpt);
                this.plateau2[i][j] = new InfoJButton();
                this.plateau2[i][j].setCpt(cpt);
                cpt += 1;
            }
        }
    }

    public void createBoat(Bateau[] list) { // OK  créer 5 bateaux de taille aléatoire
        for (int i = 0; i < nbBoat.length; i++) {
            int rand = new Random().nextInt(5) + 1; //[0...5] // 6 !!!
            list[i] = new Bateau(rand);
        }
    }

    public void randomFill(InfoJButton[][] p, InfoJButton b) {
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (p[i][j] == b) {
                    int rand = new Random().nextInt(5) + 1; //[0...5] // 6 !!!
                    Bateau bateau = new Bateau(rand);
                    addBoat(bateau, p, true, b);
                }
            }
        }

    }

    public void miseAZeroBateau(InfoJButton[][] p) { // pour la GUI principalement
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (p[i][j].getTypeCase() == 1) {
                    p[i][j].setTypeCase(0);
                }
            }
        }
    }

    public void setBateau(InfoJButton[][] p, InfoJButton b) { // pour la GUI principalement
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (p[i][j].getCpt() == b.getCpt()) { // button cliqué
                    p[i][j].setTypeCase(1);
                }
            }
        }
    }

    public boolean estOccuper(Integer tailleB, Integer i, Integer j, String HoriVerti, InfoJButton[][] p) {
        if (HoriVerti == "Hori") {
            for (int k = 0; k < tailleB; k++) {
                if (p[i][j + k].getTypeCase() == 1) {
                    return true;
                }
            }
        } else {
            for (int k = 0; k < tailleB; k++) {
                if (p[i + k][j].getTypeCase() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addBoat(Bateau bateau, InfoJButton[][] p, Boolean debut, InfoJButton b) {
        String id = bateau.getId();
        int cpt = 0;

        if (!debut) {

            //Tirer un indice aléatoire :
            int nbAlea = (int) Math.round(Math.random() * 80); //Math.random() * ( Max - Min )

            //Horizontal ou Vertical (random)
            int HoriVerti = (int) Math.round(Math.random() * 1);

            //Horizontal = 0
            if (HoriVerti == 0) {

                for (int i = 0; i < p.length; i++) {
                    for (int j = 0; j < p[i].length; j++) {
                        if (nbAlea == cpt) {
                            if (j + bateau.getTaille() > 8) { // On check si on peut mettre le bateau
                                addBoat(bateau, p, false, null);
                            } else if (estOccuper(bateau.getTaille(), i, j, "Hori", p)) { // on check si c'est occuper
                                addBoat(bateau, p, false, null);
                            } else {
                                //Après mettre le bateau sur 1 ligne
                                for (int taille = 0; taille < bateau.getTaille(); taille++) {
                                    p[i][j + taille].setTypeCase(1);
                                    p[i][j + taille].setId(id); // on mets l'id correspondant
                                }
                            }
                        }
                        cpt += 1;
                    }
                }
            } else {

                //Vertical = 1
                for (int i = 0; i < p.length; i++) {
                    for (int j = 0; j < p[i].length; j++) {
                        if (nbAlea == cpt) {
                            if (i + bateau.getTaille() > 8) { // On check si ya la place pour mettre le bateau
                                addBoat(bateau, p, false, null);
                            } else if (estOccuper(bateau.getTaille(), i, j, "Verti", p)) { // on check si c'est occuper par un autre bateau
                                addBoat(bateau, p, false, null);
                            }
                            //Après mettre le bateau sur 1 colonne
                            else {
                                for (int taille = 0; taille < bateau.getTaille(); taille++) {
                                    p[i + taille][j].setTypeCase(1);
                                    p[i + taille][j].setId(id); // on mets l'id correspondant
                                }
                            }
                        }
                        cpt += 1;
                    }
                }
            }
        } else { // on ajoute via un début déterminer
            //Horizontal ou Vertical (random)
            int HoriVerti = (int) Math.round(Math.random() * 1);

            //Horizontal = 0
            if (HoriVerti == 0) {

                for (int i = 0; i < p.length; i++) {
                    for (int j = 0; j < p[i].length; j++) {
                        if (b.getCpt() == cpt) {
                            if (j + bateau.getTaille() > 8) { // On check si on peut mettre le bateau
                                addBoat(bateau, p, true, b);
                            } else if (estOccuper(bateau.getTaille(), i, j, "Hori", p)) { // on check si c'est occuper
                                addBoat(bateau, p, true, b);
                            } else {
                                //Après mettre le bateau sur 1 ligne
                                for (int taille = 0; taille < bateau.getTaille(); taille++) {
                                    p[i][j + taille].setTypeCase(1);
                                    p[i][j + taille].setId(id); // on mets l'id correspondant
                                }
                            }
                        }
                        cpt += 1;
                    }
                }
            } else {

                //Vertical = 1
                for (int i = 0; i < p.length; i++) {
                    for (int j = 0; j < p[i].length; j++) {
                        if (b.getCpt() == cpt) {
                            if (i + bateau.getTaille() > 8) { // On check si ya la place pour mettre le bateau
                                addBoat(bateau, p, true, b);
                            } else if (estOccuper(bateau.getTaille(), i, j, "Verti", p)) { // on check si c'est occuper par un autre bateau
                                addBoat(bateau, p, true, b);
                            }
                            //Après mettre le bateau sur 1 colonne
                            else {
                                for (int taille = 0; taille < bateau.getTaille(); taille++) {
                                    p[i + taille][j].setTypeCase(1);
                                    p[i + taille][j].setId(id); // on mets l'id correspondant
                                }
                            }
                        }
                        cpt += 1;
                    }
                }
            }
        }

    }

    public void addAllBoat(Bateau[] listeB, InfoJButton[][] p) {
        for (Bateau bateau : listeB) {
            addBoat(bateau, p, false, null);
        }
    }

    public boolean isValid(InfoJButton[][] p, int coup) {
        if (coup < 0 || coup > 80) {
            return false;
        }
        int cpt = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (cpt == coup) {
                    //pour ne pas tirer 2 fois au même endroit, si une case est égale à 3 ou à 4
                    if (p[i][j].getTypeCase() == 0) {
                        return true;
                    } else if (p[i][j].getTypeCase() == 1) {
                        return true; // donc true
                    } else {
                        return false;
                    }
                }
                cpt += 1;
            }
        }
        return false;
    }

    public void execute(int coup, InfoJButton[][] p) {
        int cpt = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (cpt == coup) {
                    if (p[i][j].getTypeCase() == 0) { // on loupe le tir
                        p[i][j].setTypeCase(3);
                    } else if (p[i][j].getTypeCase() == 1) { // on touche un bateau
                        p[i][j].setTypeCase(2);
                        verifieSiCouler(p, p[i][j]);
                    }
                }
                cpt += 1;
            }
        }
    }

    public boolean verifieSiCouler(InfoJButton[][] plateau, InfoJButton p) {
        ArrayList<InfoJButton> listePortionsBateau = new ArrayList<>(); // la liste des valeurs du bateau d'id, 1 = vie, 3 = touché
        String id = p.getId();
        for (int k = 0; k < plateau.length; k++) {
            for (int l = 0; l < plateau[k].length; l++) {
                if (plateau[k][l].getId() == id) {
                    listePortionsBateau.add(plateau[k][l]);
                }
            }
        }
        for (InfoJButton a : listePortionsBateau) {
            if (a.getTypeCase() != 2) { // si un des élément n'est pas égal à '2' alors le bateau n'est pas entièrement coulé
                return false;
            }
        }
        setCouler(listePortionsBateau); // Si on arrive içi tout est coulé donc on set tout en 4
        return true;
    }

    public void setCouler(ArrayList<InfoJButton> list) {
        for (InfoJButton a : list) { // Tout le bateau est en '2' alors on le mets en 4 car il est coulé
            a.setTypeCase(4);
        }
    }

    public boolean estPerdu() {
        int cpt = 0;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j].getTypeCase() == 1) { //nombre de case 1 restantes pour le plateau1
                    cpt += 1;
                }
            }
        }
        int cpt2 = 0;
        for (int i = 0; i < plateau2.length; i++) {
            for (int j = 0; j < plateau2[i].length; j++) {
                if (plateau2[i][j].getTypeCase() == 1) { // nombre de case 1 restantes pour le plateau2
                    cpt2 += 1;
                }
            }
        }
        return cpt == 0 || cpt2 == 0;
    }

    public Joueur getWinner() { // Vu que doExecute change de joueur_courant le gagnant n'est pas le joueur courant
        int cpt = 0;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j].getTypeCase() == 1) { //nombre de case 1 restantes pour le plateau1
                    cpt += 1;
                }
            }
        }
        int cpt2 = 0;
        for (int i = 0; i < plateau2.length; i++) {
            for (int j = 0; j < plateau2[i].length; j++) {
                if (plateau2[i][j].getTypeCase() == 1) { //nombre de case 1 restantes pour le plateau2
                    cpt2 += 1;
                }
            }
        }

        if (cpt == 0) {
            return joueur2;
        }
        if (cpt2 == 0) {
            return joueur;
        }
        return null;
    }

    public String plateauToString(InfoJButton[][] plateau) {

        /*
        0 = mer (aucun coup joué), 1 = case bateau en vie, 2 = case touché ,
        3 = loupé, 4 = coulé (bateau entier donc)
        */

        String[][] plateau2 = new String[10][10];
        plateau2[0][0] = "   ";
        plateau2[0][1] = " A ";
        plateau2[0][2] = " B ";
        plateau2[0][3] = " C ";
        plateau2[0][4] = " D ";
        plateau2[0][5] = " E ";
        plateau2[0][6] = " F ";
        plateau2[0][7] = " G ";
        plateau2[0][8] = " H ";
        plateau2[0][9] = " I ";

        plateau2[1][0] = " 1 ";
        plateau2[2][0] = " 2 ";
        plateau2[3][0] = " 3 ";
        plateau2[4][0] = " 4 ";
        plateau2[5][0] = " 5 ";
        plateau2[6][0] = " 6 ";
        plateau2[7][0] = " 7 ";
        plateau2[8][0] = " 8 ";
        plateau2[9][0] = " 9 ";

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j].getTypeCase() == 0) {
                    plateau2[i + 1][j + 1] = " . ";
                } else if (plateau[i][j].getTypeCase() == 1) {
                    plateau2[i + 1][j + 1] = " . ";// = " B "
                } else if (plateau[i][j].getTypeCase() == 2) {
                    plateau2[i + 1][j + 1] = " X ";
                } else if (plateau[i][j].getTypeCase() == 3) {
                    plateau2[i + 1][j + 1] = " ! ";
                } else {
                    plateau2[i + 1][j + 1] = " C "; // C = Coulé
                }
            }
        }

        String chaine1 = plateau2[0][0] + " " + plateau2[0][1] + " " + plateau2[0][2] + " " + plateau2[0][3] + " " + plateau2[0][4] + " " + plateau2[0][5] + " " + plateau2[0][6] + " " + plateau2[0][7] + " " + plateau2[0][8] + " " + plateau2[0][9];
        String chaine2 = plateau2[1][0] + " " + plateau2[1][1] + " " + plateau2[1][2] + " " + plateau2[1][3] + " " + plateau2[1][4] + " " + plateau2[1][5] + " " + plateau2[1][6] + " " + plateau2[1][7] + " " + plateau2[1][8] + " " + plateau2[1][9];
        String chaine3 = plateau2[2][0] + " " + plateau2[2][1] + " " + plateau2[2][2] + " " + plateau2[2][3] + " " + plateau2[2][4] + " " + plateau2[2][5] + " " + plateau2[2][6] + " " + plateau2[2][7] + " " + plateau2[2][8] + " " + plateau2[2][9];
        String chaine4 = plateau2[3][0] + " " + plateau2[3][1] + " " + plateau2[3][2] + " " + plateau2[3][3] + " " + plateau2[3][4] + " " + plateau2[3][5] + " " + plateau2[3][6] + " " + plateau2[3][7] + " " + plateau2[3][8] + " " + plateau2[3][9];
        String chaine5 = plateau2[4][0] + " " + plateau2[4][1] + " " + plateau2[4][2] + " " + plateau2[4][3] + " " + plateau2[4][4] + " " + plateau2[4][5] + " " + plateau2[4][6] + " " + plateau2[4][7] + " " + plateau2[4][8] + " " + plateau2[4][9];
        String chaine6 = plateau2[5][0] + " " + plateau2[5][1] + " " + plateau2[5][2] + " " + plateau2[5][3] + " " + plateau2[5][4] + " " + plateau2[5][5] + " " + plateau2[5][6] + " " + plateau2[5][7] + " " + plateau2[5][8] + " " + plateau2[5][9];
        String chaine7 = plateau2[6][0] + " " + plateau2[6][1] + " " + plateau2[6][2] + " " + plateau2[6][3] + " " + plateau2[6][4] + " " + plateau2[6][5] + " " + plateau2[6][6] + " " + plateau2[6][7] + " " + plateau2[6][8] + " " + plateau2[6][9];
        String chaine8 = plateau2[7][0] + " " + plateau2[7][1] + " " + plateau2[7][2] + " " + plateau2[7][3] + " " + plateau2[7][4] + " " + plateau2[7][5] + " " + plateau2[7][6] + " " + plateau2[7][7] + " " + plateau2[7][8] + " " + plateau2[7][9];
        String chaine9 = plateau2[8][0] + " " + plateau2[8][1] + " " + plateau2[8][2] + " " + plateau2[8][3] + " " + plateau2[8][4] + " " + plateau2[8][5] + " " + plateau2[8][6] + " " + plateau2[8][7] + " " + plateau2[8][8] + " " + plateau2[8][9];
        String chaine10 = plateau2[9][0] + " " + plateau2[9][1] + " " + plateau2[9][2] + " " + plateau2[9][3] + " " + plateau2[9][4] + " " + plateau2[9][5] + " " + plateau2[9][6] + " " + plateau2[9][7] + " " + plateau2[9][8] + " " + plateau2[9][9];
        String chaine = System.lineSeparator();
        chaine += chaine1;
        chaine += System.lineSeparator();
        chaine += chaine2;
        chaine += System.lineSeparator();
        chaine += chaine3;
        chaine += System.lineSeparator();
        chaine += chaine4;
        chaine += System.lineSeparator();
        chaine += chaine5;
        chaine += System.lineSeparator();
        chaine += chaine6;
        chaine += System.lineSeparator();
        chaine += chaine7;
        chaine += System.lineSeparator();
        chaine += chaine8;
        chaine += System.lineSeparator();
        chaine += chaine9;
        chaine += System.lineSeparator();
        chaine += chaine10;
        chaine += System.lineSeparator();
        return chaine;

    }
}