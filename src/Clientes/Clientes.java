package Clientes;

import Componentes.Botones.BotonesClientes;
import Componentes.Renderizado.*;
import Componentes.jFrame;
import Componentes.Labels;
import Consulta.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Clientes extends JFrame {
    public static JTable jTable;
    public static DefaultTableModel model;
    ResultSet resultSet;
    Consulta consulta = new Consulta();
    jFrame frame = new jFrame();
    JPanel panel;
    Connection conexion;
    public Clientes()
    {
        frame.jframe(this);
        Componentes();

    }
    public void Componentes()
    {
        panel();
        etiquetas();
        Tabla();
        Button();
    }
    public void etiquetas()
    {
        Labels labels = new Labels();
        labels.Label("Clientes",new int[]{450,20,100,20},panel,"Titulo");

    }
    public void Button()
    {
        BotonesClientes botonesClientes = new BotonesClientes();
        botonesClientes.botones("Nuevo Cliente",new int[]{80,100,150,30},panel,"ClientesNuevo",this);
    }
    public void Tabla()
    {
        /*Tablas tablas = new Tablas();
        tablas.Table(model,new int[]{80,200,800,300},panel,"Clientes");*/
        jTable = new JTable();
        model = new DefaultTableModel();
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(80,200,800,300);
        jTable.setBounds(80,200,800,300);
        jTable.setModel(model);
        jTable.setAutoCreateColumnsFromModel(true);
        panel.add(scrollPane);
        resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Direccion");
        model.addColumn("Teléfono");
        model.addColumn("Correo electrónico");
        model.addColumn("Accion");
        model.addColumn("Accion2");
        jTable.setDefaultRenderer(Object.class, new Render());
        jTable.getColumn("Accion").setCellRenderer(new ButtonRenderer());
        jTable.getColumn("Accion").setCellEditor(new ButtonEditorClientesModificar(new JCheckBox()));

        /*jTable.getColumn("Accion2").setCellRenderer(new ButtonRenderer());
        jTable.getColumn("Accion2").setCellEditor(new ButtonEditorClientesEliminar(new JCheckBox()));*/
        consulta.Consulta(resultSet,model,"Clientes");
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
                                    int row = Clientes.jTable.getSelectedRow();
                                    PreparedStatement preparedStatement;
                                    ResultSet resultSet;
                                    int resultado;
                                    Consulta consulta = new Consulta();
                                    try {
                                        consulta();
                                        long id = Long.parseLong(Clientes.jTable.getValueAt(row,0).toString());
                                        String sql= "DELETE FROM clientes WHERE id LIKE ?";
                                        preparedStatement = conexion.prepareStatement(sql);
                                        preparedStatement.setLong(1,id);
                                        resultado = preparedStatement.executeUpdate();
                                        if (resultado== 1)
                                        {
                                            System.out.println("Record deleted");
                                            Clientes.model.setRowCount(0);
                                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");
                                            consulta.Consulta(resultSet,Clientes.model,"Clientes");
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
        Clientes clientes  = new Clientes();
        clientes.setVisible(true);
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
