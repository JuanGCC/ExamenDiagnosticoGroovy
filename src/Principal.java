import Componentes.Botones.BotonesPantallaPrincipal;
import Componentes.jFrame;

import javax.swing.*;

public class Principal extends JFrame {
    jFrame frame = new jFrame();
    JPanel panel;
    public Principal()
    {
        frame.PantallaPrincipal(this);
        panel();
        Botones();
    }
    public void Botones()
    {
        BotonesPantallaPrincipal botonesPantallaPrincipal = new BotonesPantallaPrincipal();
        botonesPantallaPrincipal.botones("Clientes",new int[]{20,40,150,30},panel,"Clientes",this);
        botonesPantallaPrincipal.botones("Productos",new int[]{20,100,150,30},panel,"Productos",this);
        botonesPantallaPrincipal.botones("Ventas",new int[]{20,160,150,30},panel,"Ventas",this);
    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }
}
