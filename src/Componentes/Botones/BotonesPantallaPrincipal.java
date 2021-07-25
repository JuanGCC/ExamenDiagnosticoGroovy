package Componentes.Botones;

import Clientes.Clientes;
import Productos.Productos;
import Ventas.Ventas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotonesPantallaPrincipal {
    public void botones(String Titulo, int [] bounds, JPanel panel, String identificador, JFrame frame)
    {
        JButton button = new JButton(Titulo);
        button.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
        panel.add(button);
        if (identificador.equals("Salir"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        if (identificador.equals("Clientes"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Clientes clientes = new Clientes();
                    clientes.setVisible(true);
                }
            });
        }
        if (identificador.equals("Productos"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Productos productos = new Productos();
                    productos.setVisible(true);
                }
            });
        }
        if (identificador.equals("Ventas"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Ventas ventas = new Ventas();
                    ventas.setVisible(true);
                }
            });
        }
    }
}
