package src.Modele.Joueur;

import src.Modele.InfoJButton;
import src.Modele.Plateau;

import java.util.ArrayList;

public class JoueurIntelligent implements Joueur {

    protected String nomBot;
    protected String current_id;
    protected InfoJButton current_portion_bateau; // la 1ère portion touché du bateau actuel
    protected InfoJButton portionBateauRecherche; // la dernière portion touché du bateau actuel
    protected Boolean changerCible;
    protected Boolean gauche;
    protected Boolean droite;
    protected Boolean haut;
    protected Boolean bas;

    public JoueurIntelligent(String nomBot) { // moins fort que JoueurImpossible(), comportement plus 'humain'
        this.nomBot = nomBot;                // recherche les lignes/colonnes quand il touche un bateaux
        current_id = null;
        changerCible = true;
        current_portion_bateau = null;
        gauche = false;
        droite = false;
        haut = false;
        bas = false;
    }
    // joue random mais quand touche un bateau recherche autour !
    //tester dans les 4 positions, gauche/droite/haut/bas quand on touche un bateau
    //donc retenir les coups déjà jouer avant
    // si on touche 2 fois sur une meme ligne jouer encore sur la ligne et pas en colonne (logique)

    public Boolean estTouche(InfoJButton[][] p, int coup) {
        Integer cpt = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (cpt == coup) {
                    if (p[i][j].getTypeCase() == 1) {
                        return true;
                    } else if (p[i][j].getTypeCase() == 0) {
                        return false;
                    }
                }
                cpt += 1;
            }
        }
        return false;
    }

    public ArrayList getBateau(InfoJButton[][] p, String id) {
        ArrayList<InfoJButton> listB = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (p[i][j].getId() == id) {
                    listB.add(p[i][j]);
                }
            }
        }
        return listB;
    }

    public String getId(InfoJButton[][] p, int coup) {
        Integer cpt = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (cpt == coup) {
                    return p[i][j].getId();
                }
                cpt += 1;
            }
        }
        return null;
    }

    public InfoJButton getPortionBateau(InfoJButton[][] p, int coup) {
        Integer cpt = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                if (cpt == coup) {
                    return p[i][j];
                }
                cpt += 1;
            }
        }
        return null;
    }

    public Boolean estCouler(InfoJButton[][] p, String id) {
        ArrayList<InfoJButton> test = getBateau(p, id);
        for (InfoJButton a : test) {
            if (a.getTypeCase() != 4) { // si un des élément n'est pas égal à '2' alors le bateau n'est pas entièrement coulé
                return false;
            }
        }
        return true;
    }

    public Boolean estCoulerAvecCoup(InfoJButton[][] p, String id, Integer coup) {
        ArrayList<InfoJButton> test = new ArrayList<>();
        for (int k = 0; k < p.length; k++) {
            for (int l = 0; l < p[k].length; l++) {
                if (p[k][l].getId() == id) {
                    test.add(p[k][l]);
                }
            }
        }
        Integer nbB = test.size();
        Integer nbBtouche = 0;
        Integer type = 0;

        for (InfoJButton n : test) {
            if (n.getTypeCase() == 2) {
                nbBtouche += 1;
            }
            if (n.getCpt() == coup) {
                type = n.getTypeCase();
            }
        }
        if (nbBtouche == nbB - 1 && type == 1) { // le nombre de portions touché est égal à la taille -1 et le bateau dernier est pas encore coulé
            return true;
        }

        return false;
    }

    public int rechercheDansSens(Plateau jeu, InfoJButton[][] p, String sens) {
        //2EME COUP TOUCHÉ :
        // On est sensé connaitre le sens du bateau (ligne ou colonne)
        // donc on peut juste finir de cliquer sur le bateau
        // exemple sur un bateau en ligne,
        // quand il n'y a plus de bateau à gauche
        // on revient au premier coup touché et on essaye à droite

        if (portionBateauRecherche == null) {
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[i].length; j++) {
                    if (p[i][j] == current_portion_bateau && sens == "col") {
                        if (i > 0 && jeu.isValid(p, p[i - 1][j].getCpt())) {
                            if (p[i - 1][j].getTypeCase() == 1) {
                                portionBateauRecherche = p[i - 1][j];
                            } else { //TEST
                                portionBateauRecherche = current_portion_bateau;
                            }
                            return p[i - 1][j].getCpt();
                        } else if (i < 8 && jeu.isValid(p, p[i + 1][j].getCpt())) {
                            if (p[i + 1][j].getTypeCase() == 1) {
                                portionBateauRecherche = p[i - 1][j];
                            } else {
                                portionBateauRecherche = current_portion_bateau;
                            }
                            return p[i + 1][j].getCpt();
                        }
                    } else if (p[i][j] == current_portion_bateau && sens == "ligne") {
                        if (j > 0 && jeu.isValid(p, p[i][j - 1].getCpt())) {
                            if (p[i][j - 1].getTypeCase() == 1) {
                                portionBateauRecherche = p[i][j - 1];
                            } else {
                                portionBateauRecherche = current_portion_bateau;
                            }
                            return p[i][j - 1].getCpt();
                        } else if (j < 8 && jeu.isValid(p, p[i][j + 1].getCpt())) {
                            if (p[i][j + 1].getTypeCase() == 1) {
                                portionBateauRecherche = p[i][j + 1];
                            } else {
                                portionBateauRecherche = current_portion_bateau;
                            }
                            return p[i][j + 1].getCpt();
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[i].length; j++) {
                    if (p[i][j] == portionBateauRecherche && sens == "col") {
                        if (i > 0 && jeu.isValid(p, p[i - 1][j].getCpt())) {
                            if (p[i - 1][j].getTypeCase() == 1) {
                                portionBateauRecherche = p[i - 1][j];
                                return p[i - 1][j].getCpt();
                            } else {
                                portionBateauRecherche = null;
                                return p[i - 1][j].getCpt();
                            }
                        } else if (i < 8 && jeu.isValid(p, p[i + 1][j].getCpt())) {
                            if (p[i + 1][j].getTypeCase() == 1) {
                                portionBateauRecherche = p[i + 1][j];
                                return p[i + 1][j].getCpt();
                            } else {
                                portionBateauRecherche = null;
                                return p[i + 1][j].getCpt();
                            }
                        }//si ya ni a gauche ni à droite alors revenir au bateau de base et essayer dans les 2 sens
                        else {
                            portionBateauRecherche = null;
                            return rechercheDansSens(jeu, p, "col");
                        }
                    }
                    if (p[i][j] == portionBateauRecherche && sens == "ligne") { //curent Portion bateau
                        if (j > 0 && jeu.isValid(p, p[i][j - 1].getCpt())) {
                            if (p[i][j - 1].getTypeCase() == 1) {
                                portionBateauRecherche = p[i][j - 1];
                                return p[i][j - 1].getCpt();
                            } else {
                                portionBateauRecherche = null;
                                return p[i][j - 1].getCpt();
                            }
                        } else if (j < 8 && jeu.isValid(p, p[i][j + 1].getCpt())) {
                            if (p[i][j + 1].getTypeCase() == 1) {
                                portionBateauRecherche = p[i][j + 1];
                                return p[i][j + 1].getCpt();
                            } else {
                                portionBateauRecherche = null;
                                return p[i][j + 1].getCpt();
                            }
                        } else {
                            portionBateauRecherche = null;
                            return rechercheDansSens(jeu, p, "ligne");
                        }
                    }
                }
            }
        }
        return 99;
    }

    public int autourBateau(Plateau jeu, InfoJButton[][] p) { // OK
        Integer cpt = 0;
        // 1ER COUP :
        // TESTER 4 POSSIBILITÉES puis déduire la direction et finir
        if (gauche == false && droite == false && haut == false && bas == false) { // 4 POSSIBILITÉES, alors on n'a touché se bateau que 1 fois, on joue alors en COL ou en LIGNE pour connaitre le sens
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[i].length; j++) {
                    if (p[i][j] == current_portion_bateau) { // la portion de bateau autour duquel on veux jouer

                        int choix = (int) Math.round(Math.random() * 3) + 1;

                        if (choix == 1) { // i = ligne , j = col, et on test si le coup est valide
                            if (!jeu.isValid(p, cpt + 1) || j >= 8) {
                                return autourBateau(jeu, p);
                            }
                            if (p[i][j + 1].getId() == current_portion_bateau.getId() && p[i][j + 1].getTypeCase() == 1) {
                                droite = true;
                                portionBateauRecherche = p[i][j + 1];
                                return cpt + 1; // peut importe si ya un bateau ou pas à droite on return le coup

                            } else {
                                droite = false;
                                return cpt + 1; // peut importe si ya un bateau ou pas à droite on return le coup

                            }


                        } else if (choix == 2) {
                            if (!jeu.isValid(p, cpt - 1) || j <= 0) {
                                return autourBateau(jeu, p);
                            }
                            if (p[i][j - 1].getId() == current_portion_bateau.getId() && p[i][j - 1].getTypeCase() == 1) {
                                gauche = true;
                                portionBateauRecherche = p[i][j - 1];
                                return cpt - 1;
                            } else {
                                gauche = false;
                                return cpt - 1;
                            }

                        } else if (choix == 3) {
                            if (!jeu.isValid(p, cpt - 9) || i <= 0) {
                                return autourBateau(jeu, p);
                            }
                            if (p[i - 1][j].getId() == current_portion_bateau.getId() && p[i - 1][j].getTypeCase() == 1) {
                                haut = true;
                                portionBateauRecherche = p[i - 1][j];
                                return cpt - 9;
                            } else {
                                haut = false;
                                return cpt - 9;
                            }
                        } else if (choix == 4) { // OK
                            if (!jeu.isValid(p, cpt + 9) || i >= 8) {
                                return autourBateau(jeu, p);
                            }
                            if (p[i + 1][j].getId() == current_portion_bateau.getId() && p[i + 1][j].getTypeCase() == 1) {
                                bas = true;
                                portionBateauRecherche = p[i + 1][j];
                                return cpt + 9;
                            } else {
                                bas = false;
                                return cpt + 9;
                            }

                        }
                    }
                    cpt += 1;
                }
            }
        }
        if (gauche || droite) { // le bateau est en ligne

            Integer coup = rechercheDansSens(jeu, p, "ligne");
            return coup;

        } else if (haut || bas) { // le bateau est en colonne

            Integer coup = rechercheDansSens(jeu, p, "col");
            return coup;
        }

        return 99;
    }

    public int chooseMove(Plateau jeu) {
        InfoJButton[][] p = jeu.getPlateauAdversaire(jeu.getJoueurCourant());

        if (changerCible) { // OK , jamais touché de bateaux, ou si au coup d'avant on a coulé un bateau

            int coup = (int) Math.round(Math.random() * 80);
            while (!jeu.isValid(p, coup)) {
                coup = (int) Math.round(Math.random() * 80);
            }

            if (estTouche(p, coup)) { // TOUCHÉ

                if (estCoulerAvecCoup(p, getId(p, coup), coup)) { // le bateau est coulé alors on change de cible, on remets tous a null
                    current_portion_bateau = null;
                    current_id = null;
                    changerCible = true;
                    return coup;
                } else { // Touché mais pas coulé
                    current_id = getId(p, coup);
                    current_portion_bateau = getPortionBateau(p, coup); // BUG QUAND UN BATEAU DE TAILLE 1 EST COULÉ
                    changerCible = false;
                    return coup;
                }
            } else { // PAS TOUCHÉ on change de cible/retire au hasard
                changerCible = true;
                current_portion_bateau = null;
                current_id = null;
                return coup;
            }

        } else if (estCouler(p, current_id)) {
            changerCible = true;
            current_portion_bateau = null;
            portionBateauRecherche = null;
            current_id = null;
            gauche = false;
            droite = false;
            haut = false;
            bas = false;
            return chooseMove(jeu);
        } else if (!changerCible) { // on a déjà une cible qui n'est pas encore coulé
            Integer choix = autourBateau(jeu, p);
            return choix;
        }
        return 99;
    }

    public void doExecute(Plateau jeu) { // Ok
        Integer coup = chooseMove(jeu);
        jeu.execute(coup, jeu.getPlateauAdversaire(jeu.getJoueurCourant()));
        jeu.changeJoueur_courant();
    }


    @Override
    public Boolean isBot() {
        return true;
    }

    @Override
    public String getName() {
        return nomBot;
    }
}
