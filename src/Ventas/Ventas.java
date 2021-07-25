package Ventas;

import Componentes.Botones.BotonesProductos;
import Componentes.Botones.BotonesVentas;
import Componentes.CajaTexto;
import Componentes.Labels;
import Componentes.Renderizado.*;
import Componentes.Tablas;
import Componentes.jFrame;
import Consulta.ConexionConsulta;
import Consulta.Consulta;
import Productos.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Ventas extends JFrame {
    public static JTable jTable;
    public static DefaultTableModel model;
    ResultSet resultSet;
    Consulta consulta = new Consulta();
    jFrame frame = new jFrame();
    JPanel panel;
    Connection conexion;
    public Ventas()
    {
        frame.jframe(this);
        panel();
        etiquetas();
        Button();
        Tabla();
    }
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
        resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.ventas");
        model.addColumn("Id");
        model.addColumn("Cliente");
        model.addColumn("Fecha Venta");
        model.addColumn("Accion");
        model.addColumn("Accion2");
        jTable.setDefaultRenderer(Object.class, new Render());
        jTable.getColumn("Accion").setCellRenderer(new ButtonRenderer());
        jTable.getColumn("Accion").setCellEditor(new ButtonEditorVentasProductosVendidos(new JCheckBox()));

        consulta.Consulta(resultSet,model,"Ventas");
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY()/jTable.getRowHeight();
                if (row < jTable.getRowCount() && row >=0 && column< jTable.getColumnCount() && column >=0)
                {
                    Object value = jTable.getValueAt(row,column);
                    if (value instanceof  JButton)
                    {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;
                        if (boton.getName().equals("Eliminar"))
                        {
                            boton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int row = Ventas.jTable.getSelectedRow();
                                    PreparedStatement preparedStatement;
                                    ResultSet resultSet;
                                    int resultado;
                                    Consulta consulta = new Consulta();
                                    try {
                                        consulta();
                                        long id = Long.parseLong(Ventas.jTable.getValueAt(row,0).toString());
                                        String sql= "DELETE FROM ventas WHERE id LIKE ?";
                                        preparedStatement = conexion.prepareStatement(sql);
                                        preparedStatement.setLong(1,id);
                                        resultado = preparedStatement.executeUpdate();
                                        if (resultado== 1)
                                        {
                                            System.out.println("Record deleted");

                                           // Ventas.jTable.setModel(Productos.model);
                                            Ventas.model.setRowCount(0);
                                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.ventas");
                                            consulta.Consulta(resultSet,Ventas.model,"Ventas");
                                            //frame.dispose();
                                            conexion.close();
                                        }
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

    }
    public void panel()
    {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }
    public static void main(String[] args) {
        Ventas ventas = new Ventas();
        ventas.setVisible(true);
    }
    public  void consulta() throws SQLException {
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendiagnostico?serverTimezone=UTC",
                    "root","Tu_Contraseña");
            System.out.println("Conectado!");
        }catch(SQLException e){
            e.printStackTrace();
        }      }
}
