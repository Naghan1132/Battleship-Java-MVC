package src.Modele;

import src.Control.*;


public class BatailleNavale extends AbstractModeleEcoutable {

    protected Plateau p;
    protected Boolean allBoatPlaces;

    public BatailleNavale(Plateau p) {
        super();
        this.p = p;
        this.allBoatPlaces = false;
    }

    public Plateau getPlateau() {
        return p;
    }

    public void next()
    {
        notifierLesEcouteurs();
    }

    public Boolean getAllBoatPlaces(){
        return allBoatPlaces;
    }

    public void setAllBoatPlaces(Boolean b){
        this.allBoatPlaces = b;
    }
}

