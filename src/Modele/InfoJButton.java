package src.Modele;

import javax.swing.*;
import java.awt.*;

public class InfoJButton extends JButton
{
    private int typeCase;
    private String id;
    private int cpt;

    public InfoJButton()
    {
        this.typeCase = 0;
        this.id = "99";
        this.cpt = 99;
        this.setPreferredSize(new Dimension(100,100));
    }

    public int getTypeCase()
    {
        return typeCase;
    }

    public void setTypeCase(int typeCase) {
        this.typeCase = typeCase;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getCpt(){
        return cpt;
    }

    public void setCpt(Integer cpt){
        this.cpt = cpt;
    }
}
