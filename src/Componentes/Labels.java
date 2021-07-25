package Componentes;

import javax.swing.*;
import java.awt.*;

public class Labels {
    public void Label(String titulo, int[] bounds, JPanel panel,String identificador)
    {
        if (identificador.equals("Titulo"))
        {
            JLabel label = new JLabel(titulo, SwingConstants.CENTER);
            label.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
            label.setFont(new Font("arial", Font.BOLD,20));
            panel.add(label);
        }
        if (identificador.equals("Datos"))
        {
            JLabel label = new JLabel(titulo, SwingConstants.CENTER);
            label.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
            label.setFont(new Font("arial", Font.BOLD,12));
            panel.add(label);
        }
    }
}
