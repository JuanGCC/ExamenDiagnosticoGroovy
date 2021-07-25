package Ventas;

import Componentes.Botones.BotonesVentas;
import Componentes.Labels;
import Componentes.Renderizado.ButtonEditorProductosEliminar;
import Componentes.Renderizado.ButtonEditorVentasProductosVendidos;
import Componentes.Renderizado.ButtonRenderer;
import Componentes.jFrame;
import Consulta.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class VentasProductos extends JFrame {
    public VentasProductos()
    {
        frame.jframe(this);
        panel();
        etiquetas();
        Button();
        Tabla();
    }

    public static JTable jTable;
    public static DefaultTableModel model;
    ResultSet resultSet;
    Consulta consulta = new Consulta();
    jFrame frame = new jFrame();
    JPanel panel;

    public void etiquetas()
    {
        Labels labels = new Labels();
        labels.Label("Ventas",new int[]{450,20,100,20},panel,"Titulo");

    }
    public void Button()
    {
        BotonesVentas botonesVentas = new BotonesVentas();
        botonesVentas.botones("Nueva Venta",new int[]{80,100,150,30},panel,"VentasNueva",this);
    }
    public  void Tabla()
    {
        //Tablas tablas = new Tablas();
        //tablas.Table(new int[]{80,200,800,300},panel,"Clientes");
        jTable = new JTable();
        model = new DefaultTableModel();
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(80,200,800,300);
        jTable.setBounds(80,200,800,300);
        jTable.setModel(model);
        jTable.setAutoCreateColumnsFromModel(true);
        panel.add(scrollPane);
        resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.detalle_ventas");
        model.addColumn("Nombre Producto");
        model.addColumn("Cantidad");

        //Productos.jTable.getColumn("Update").setCellRenderer(new BotonRenderizado());
        consulta.Consulta(resultSet,model,"DetallesVentas");

    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }
    public static void main(String[] args) {
        VentasProductos productos = new VentasProductos();
        productos.setVisible(true);
    }
}
