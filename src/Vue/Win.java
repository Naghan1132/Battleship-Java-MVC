package src.Vue;

import org.w3c.dom.Text;
import src.Modele.Joueur.Joueur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Win extends JPanel {

    private Joueur winner;

    public Win(Joueur winner){
        super();

        JLabel win = new JLabel("Bravo "+winner.getName()+" tu as gagné");
        win.setBorder(new EmptyBorder(0,10,0,10));
        win.setFont(new Font("Verdana", Font.PLAIN, 18));
        JButton exitButton = new JButton("EXIT");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(win);
        this.add(exitButton);
    }

}
