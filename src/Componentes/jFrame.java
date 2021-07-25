package Componentes;

import javax.swing.*;
import java.awt.*;

public class jFrame {
    public void jframe (JFrame frame)
    {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setMinimumSize(new Dimension(1000,800));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    public void Catalogo (JFrame frame)
    {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setMinimumSize(new Dimension(1000,300));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    public void PantallaPrincipal(JFrame frame)
    {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setMinimumSize(new Dimension(210,400));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
