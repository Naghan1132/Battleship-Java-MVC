package src.Control;

import java.util.ArrayList;
import java.util.List;

public class AbstractModeleEcoutable implements ModeleEcoutable{

    protected List<EcouteurModele> ecouteurs;

    public AbstractModeleEcoutable(List<EcouteurModele> ecouteurs){
        this.ecouteurs=ecouteurs;
    }

    public AbstractModeleEcoutable(){
        this(new ArrayList<>());
    }

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
        this.ecouteurs.add(e);
        e.modeleMisAJour(this);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        this.ecouteurs.remove(e);
    }

    protected void notifierLesEcouteurs(){//fireChangement dans le CM
        for(EcouteurModele e :ecouteurs){
            e.modeleMisAJour(this);
        }
    }
}
