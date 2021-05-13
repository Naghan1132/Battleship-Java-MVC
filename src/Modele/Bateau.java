package src.Modele;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Bateau {
    public int[] boat;
    public int taille;
    public String id;
    private static AtomicLong idCounter = new AtomicLong(); // pour un id unique pour chaque bateau

    public Bateau(int taille) {
        this.taille = taille;
        this.boat = new int[taille];
        this.id = createID();
    }

    public int getTaille() {
        return taille;
    }

    public String getId(){
        return id;
    }

    public static String createID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    @Override
    public String toString() {
        return "Bateau{" +
                "boat=" + Arrays.toString(boat) +
                ", taille=" + taille +
                ", id='" + id + '\'' +
                '}';
    }
}