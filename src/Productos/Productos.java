package Productos;

import Clientes.Clientes;
import Componentes.*;
import Componentes.Botones.BotonesProductos;
import Componentes.Renderizado.ButtonEditorProductosEliminar;
import Componentes.Renderizado.ButtonEditorProductosModificar;
import Componentes.Renderizado.ButtonRenderer;
import Componentes.Renderizado.Render;
import Consulta.*;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Productos extends JFrame {
    public static JTable jTable;
    public static DefaultTableModel model;
    ResultSet resultSet;
    Consulta consulta = new Consulta();
    jFrame frame = new jFrame();
    JPanel panel;
    Connection conexion;
    public Productos()
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
        labels.Label("Productos",new int[]{450,20,100,20},panel,"Titulo");

    }
    public void Button()
    {
        BotonesProductos botonesProductos = new BotonesProductos();
        botonesProductos.botones("Nuevo Producto",new int[]{80,100,150,30},panel,"ProductosNuevo",this);
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
        resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.productos");
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Clave");
        model.addColumn("Cantidad");
        model.addColumn("Estatus");
        model.addColumn("Accion");
        model.addColumn("Accion2");
        jTable.setDefaultRenderer(Object.class, new Render());
        jTable.getColumn("Accion").setCellRenderer(new ButtonRenderer());
        jTable.getColumn("Accion").setCellEditor(new ButtonEditorProductosModificar(new JCheckBox()));
        //jTable.getColumn("Accion2").setCellRenderer(new ButtonRenderer());
        //jTable.getColumn("Accion2").setCellEditor(new ButtonEditorProductosEliminar(new JCheckBox()));
        consulta.Consulta(resultSet,model,"Productos");
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
                                    int row = Productos.jTable.getSelectedRow();
                                    PreparedStatement preparedStatement;
                                    ResultSet resultSet;
                                    int resultado;
                                    Consulta consulta = new Consulta();
                                    try {
                                        consulta();
                                        long id = Long.parseLong(Productos.jTable.getValueAt(row,0).toString());
                                        String sql= "DELETE FROM productos WHERE id LIKE ?";
                                        preparedStatement = conexion.prepareStatement(sql);
                                        preparedStatement.setLong(1,id);
                                        resultado = preparedStatement.executeUpdate();
                                        if (resultado== 1)
                                        {
                                            System.out.println("Record deleted");
                                            CajaTexto.Nombre.setText(null);
                                            CajaTexto.cantidadProducto.setText(null);
                                            CajaTexto.claveProducto.setText(null);
                                            CajaTexto.estatus.setText(null);
                                            Productos.jTable.setModel(Productos.model);
                                            Productos.model.setRowCount(0);
                                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.productos");
                                            consulta.Consulta(resultSet,Productos.model,"Productos");
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
        Productos clientes  = new Productos();
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
