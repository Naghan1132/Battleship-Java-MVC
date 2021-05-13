package src.Vue;

import src.Modele.BatailleNavale;
import src.Modele.Joueur.*;
import src.Modele.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class HomeGUI extends JFrame {

    public HomeGUI() {
        super();

        Scanner scanner = new Scanner(System.in);

        GridLayout layout = new GridLayout(3, 3,150 , 150);

        JButton easyButton = new JButton("Facile");
        JButton mediumButton = new JButton("Moyen");
        JButton hardButton = new JButton("Impossible");



        this.add(easyButton, BorderLayout.NORTH);
        this.add(mediumButton,BorderLayout.CENTER);
        this.add(hardButton,BorderLayout.SOUTH);


        this.setLayout(layout);

        easyButton.setPreferredSize(new Dimension(20,20));

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAlls();
                JoueurRandom joueurRandom = new JoueurRandom("Bot Random");
                JoueurHumain joueurHumain = new JoueurHumain("Joueur Humain", scanner);
                Plateau plateau = new Plateau(joueurHumain,joueurRandom); // OK

                BatailleNavale grilleGUI = new BatailleNavale(plateau);

                new BatailleGUI(grilleGUI);
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAlls();
                JoueurIntelligent joueurIntelligent = new JoueurIntelligent("Bot Intelligent");
                JoueurHumain joueurHumain = new JoueurHumain("Joueur humain", scanner);

                Plateau plateau = new Plateau(joueurHumain,joueurIntelligent); // OK

                BatailleNavale grilleGUI = new BatailleNavale(plateau);
                new BatailleGUI(grilleGUI);
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAlls();
                JoueurImpossible joueurImpossible = new JoueurImpossible("Bot Impossible");
                JoueurHumain joueurHumain = new JoueurHumain("Joueur humain", scanner);
                Plateau plateau = new Plateau(joueurHumain,joueurImpossible); // OK

                BatailleNavale grilleGUI = new BatailleNavale(plateau);

                new BatailleGUI(grilleGUI);
            }
        });


        this.setSize(new Dimension(1200,700));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void removeAlls(){
        this.removeAll();
        this.repaint();
        //this.setVisible(false);
        this.dispose();
    }

}
