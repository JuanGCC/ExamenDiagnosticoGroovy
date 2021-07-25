package Clientes;

import Componentes.Botones.BotonesClientes;
import Componentes.CajaTexto;
import Componentes.Labels;
import Componentes.jFrame;

import javax.swing.*;
import java.awt.*;

public class ClienteModificacion extends JFrame {
    JPanel panel;
    jFrame frame = new jFrame();
    public static  int id;
    public ClienteModificacion()
    {
        frame.Catalogo(this);
        panel();
        Etiquetas();
        CajasTexto();
        Button();
    }
    public void Etiquetas()
    {
        Labels labels = new Labels();
        labels.Label("Modificar Cliente",new int[]{440,20,200,20},panel,"Titulo");
        labels.Label("Nombre",new int[]{60,100,100,20},panel,"Datos");
        labels.Label("Dirección",new int[]{295, 100,100,20},panel,"Datos");
        labels.Label("Telefono",new int[]{525,100,100,20},panel,"Datos");
        labels.Label("Correo Electronico",new int[]{730,100,140,20},panel,"Datos");
    }
    public void CajasTexto()
    {
        CajaTexto cajaTexto = new CajaTexto();
        CajaTexto.Nombre.setBounds(40,130,150,25);
        CajaTexto.direccion.setBounds(270,130,150,25);
        CajaTexto.telefono.setBounds(500,130,150,25);
        CajaTexto.correoElectronico.setBounds(730,130,150,25);
        panel.add(CajaTexto.Nombre);
        panel.add(CajaTexto.direccion);
        panel.add(CajaTexto.telefono);
        panel.add(CajaTexto.correoElectronico);
    }
    public void Button()
    {
        BotonesClientes botonesClientes = new BotonesClientes();
        botonesClientes.botones("Cancelar",new int[]{350,200,130,40},panel,"Cancelar",this);
        botonesClientes.botones("Editar",new int[]{500,200,130,40},panel,"ClientesModificar",this);
    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        ClienteModificacion clienteNuevo = new ClienteModificacion();
        clienteNuevo.setVisible(true);
    }
}
