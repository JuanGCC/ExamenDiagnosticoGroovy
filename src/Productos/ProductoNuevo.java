package Productos;

import Componentes.*;
import Componentes.Botones.BotonesProductos;

import javax.swing.*;
import java.awt.*;

public class ProductoNuevo extends JFrame {
    JPanel panel;
    jFrame frame = new jFrame();
    public static  int id;
    public ProductoNuevo()
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
        labels.Label("Generar Producto",new int[]{400,20,200,20},panel,"Titulo");
        labels.Label("Nombre",new int[]{60,100,100,20},panel,"Datos");
        labels.Label("Clave",new int[]{295, 100,100,20},panel,"Datos");
        labels.Label("Cantidad",new int[]{525,100,100,20},panel,"Datos");
        labels.Label("Estatus",new int[]{730,100,140,20},panel,"Datos");
    }
    public void CajasTexto()
    {
        CajaTexto cajaTexto = new CajaTexto();
        CajaTexto.Nombre.setBounds(40,130,150,25);
        CajaTexto.claveProducto.setBounds(270,130,150,25);
        CajaTexto.cantidadProducto.setBounds(500,130,150,25);
        CajaTexto.estatus.setBounds(730,130,150,25);
        panel.add(CajaTexto.Nombre);
        panel.add(CajaTexto.claveProducto);
        panel.add(CajaTexto.cantidadProducto);
        panel.add(CajaTexto.estatus);
    }
    public void Button()
    {
        BotonesProductos botonesProductos = new BotonesProductos();
        botonesProductos.botones("Cancelar",new int[]{350,200,130,40},panel,"Cancelar",this);
        botonesProductos.botones("Insertar",new int[]{500,200,130,40},panel,"ProductosAlta",this);
    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        ProductoNuevo clienteNuevo = new ProductoNuevo();
        clienteNuevo.setVisible(true);
    }
}
