package Ventas;

import Clientes.ClienteNuevo;
import Componentes.Botones.BotonesClientes;
import Componentes.Botones.BotonesVentas;
import Componentes.CajaTexto;
import Componentes.Labels;
import Componentes.jFrame;
import Consulta.ConexionConsulta;
import Consulta.Consulta;
import Productos.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaNuevo extends JFrame {
    jFrame frame = new jFrame();
    JPanel panel;
    ResultSet resultSet, resultSet2;
    public static List<Integer> cantidades = new ArrayList<Integer>();
    JButton agregar;
    public static JTable jTable;
    public static DefaultTableModel model;
    public static JComboBox clientes;
    public static JComboBox productos;
    public static JTextField id = new JTextField();
    public static int resta;
    public VentaNuevo()
    {
        frame.jframe(this);
        panel();
        Etiquetas();
        CajasTexto();
        Button();
        Tabla();
    }
    public void Etiquetas()
    {
        Labels labels = new Labels();
        labels.Label("Generar Venta",new int[]{440,20,150,20},panel,"Titulo");
        labels.Label("Cliente",new int[]{60,100,100,20},panel,"Datos");
        labels.Label("Productos",new int[]{295, 100,100,20},panel,"Datos");
        labels.Label("Cantidad",new int[]{525,100,100,20},panel,"Datos");
        labels.Label("ID", new int[]{65,30,100,25},panel,"Datos");

    }
    public void CajasTexto() {
        try
        {

            clientes = new JComboBox();
            productos = new JComboBox();

            resultSet = ConexionConsulta.getTabla("SELECT id, nombre FROM examendiagnostico.clientes");
            resultSet2= ConexionConsulta.getTabla("SELECT id,nombre FROM examendiagnostico.productos WHERE estatus LIKE 'ACTIVO'");
            while (resultSet.next()){
                //clientesid= new long[]{resultSet.getLong("id")};
                clientes.addItem(resultSet.getString("nombre"));

            }
            while (resultSet2.next())
            {
                productos.addItem(resultSet2.getString("nombre"));
            }
            id.setBounds(40,50,150,25);
            clientes.setBounds(40,130,150,25);
            productos.setBounds(270,130,150,25);
            CajaTexto.cantidad.setBounds(500,130,150,25);
            panel.add(clientes);
            panel.add(productos);
            panel.add(CajaTexto.cantidad);
            panel.add(id);

        }catch(Exception e)
        {

        }

    }
    public void Tabla()
    {
        agregar = new JButton("Agregar");
        agregar.setBounds(700,130,150,25);
        panel.add(agregar);
        jTable = new JTable();
        model = new DefaultTableModel();
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(80,200,800,300);
        jTable.setBounds(80,200,800,300);
        jTable.setModel(model);
        jTable.setAutoCreateColumnsFromModel(true);
        panel.add(scrollPane);
        model.addColumn("Clientes");
        model.addColumn("Productos");
        model.addColumn("Cantidad");

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad;

                System.out.println(productos.getSelectedItem());
                try
                {

                    resultSet = ConexionConsulta.getTabla("SELECT nombre, cantidad FROM examendiagnostico.productos WHERE estatus = 'ACTIVO'AND nombre LIKE '"+ productos.getSelectedItem()+"'");
                    while(resultSet.next())
                    {

                        cantidad= resultSet.getInt("cantidad");
                        resta =cantidad   - Integer.parseInt(CajaTexto.cantidad.getText());
                        cantidades.add(resultSet.getInt("cantidad"));
                        System.out.println(productos.getSelectedItem());
                        int suma;
                        boolean igualdad = false;
                        int fila = 0;
                        if (resta >=0){
                        for (int i = 0; i < model.getRowCount(); i++)
                        {
                            if (productos.getSelectedItem().equals(model.getValueAt(i,1)))
                                igualdad = true;
                                fila= i;

                        }
                        if(igualdad== false)
                        {
                            model.addRow(new Object[]{
                                    clientes.getSelectedItem(), productos.getSelectedItem(), CajaTexto.cantidad.getText()
                            });
                        }else{
                            suma = Integer.parseInt(model.getValueAt(fila,2).toString()) +Integer.parseInt(CajaTexto.cantidad.getText());
                            if (suma > cantidad){JOptionPane.showMessageDialog(null,"Ha exedido la cantidad: "+ cantidad);
                                igualdad = false;
                            }else
                            {
                                model.setValueAt(suma,fila,2);
                                igualdad= false;
                            }
                        }



                        }else {JOptionPane.showMessageDialog(null,"Ingrese menos de: " +cantidad+" productos" );}
                    }

                }
                catch (Exception exception)
                {
                    System.out.println(exception.toString());
                }

            }
        });

    }
    public void Button()
    {

        BotonesVentas botonesVentas = new BotonesVentas ();
        botonesVentas.botones("Cancelar",new int[]{350,600,130,40},panel,"Cancelar",this);
        botonesVentas.botones("Insertar",new int[]{500,600,130,40},panel,"VentasAlta",this);

    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        VentaNuevo clienteNuevo = new VentaNuevo();
        clienteNuevo.setVisible(true);
    }

}
