package Componentes.Botones;

import Clientes.*;
import Clientes.ClienteNuevo;
import Componentes.CajaTexto;
import Consulta.ConexionConsulta;
import Consulta.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BotonesClientes {
    Connection conexion;
    Statement statement ;
    //PreparedStatement preparedStatement;
    ResultSet resultset ;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    Consulta consulta = new Consulta();
    int resultado;
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
        if (identificador.equals("ClientesEliminacion"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = Clientes.jTable.getSelectedRow();
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
                            CajaTexto.Nombre.setText(null);
                            CajaTexto.telefono.setText(null);
                            CajaTexto.correoElectronico.setText(null);
                            CajaTexto.direccion.setText(null);
                            //Clientes.jTable.setModel(Clientes.model);
                            Clientes.model.setRowCount(0);
                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");
                            consulta.Consulta(resultSet,Clientes.model,"Clientes");
                            //frame.dispose();
                            conexion.close();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        }
        if (identificador.equals("ClientesModificar"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(ClienteModificacion.id);

                    try {
                        consulta();
                        String sql= "update clientes set nombre=?, direccion=?,telefono=?,correo_electronico=? WHERE id =?";
                        preparedStatement= conexion.prepareStatement(sql);
                        preparedStatement.setInt(5,ClienteModificacion.id);
                        preparedStatement.setString(1,CajaTexto.Nombre.getText());
                        preparedStatement.setString(2,CajaTexto.direccion.getText());
                        preparedStatement.setLong(3,Long.parseLong(CajaTexto.telefono.getText()));
                        preparedStatement.setString(4,CajaTexto.correoElectronico.getText());
                        resultado = preparedStatement.executeUpdate();
                        if (resultado== 1)
                        {
                            frame.dispose();
                            CajaTexto.Nombre.setText(null);
                            CajaTexto.telefono.setText(null);
                            CajaTexto.correoElectronico.setText(null);
                            CajaTexto.direccion.setText(null);
                            Clientes.jTable.setModel(Clientes.model);
                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");
                            Clientes.model.setRowCount(0);
                            consulta.Consulta(resultSet,Clientes.model,"Clientes");
                            conexion.close();
                        }
                    } catch (Exception es) {
                        es.printStackTrace();
                    }

                }
            });
        }
        if (identificador.equals("ClientesModificacion"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = Clientes.jTable.getSelectedRow();
                    System.out.println(Clientes.jTable.getValueAt(row,0));
                    ClienteModificacion clienteModificacion = new ClienteModificacion();

                    try {
                        consulta();
                        statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        resultset = statement.executeQuery("SELECT * FROM clientes WHERE id LIKE '"+Clientes.jTable.getValueAt(row,0)+"'");
                        if (resultset.next())
                        {
                            ClienteModificacion.id = resultset.getInt("id");
                            CajaTexto.Nombre.setText(resultset.getString("nombre"));
                            CajaTexto.direccion.setText(resultset.getString("direccion"));
                            CajaTexto.telefono.setText(String.valueOf(resultset.getLong("telefono")));
                            CajaTexto.correoElectronico.setText(resultset.getString("correo_electronico"));
                            conexion.close();
                        }
                        clienteModificacion.setVisible(true);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        }
        if (identificador.equals("ClientesNuevo"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClienteNuevo clienteNuevo = new ClienteNuevo();
                    clienteNuevo.setVisible(true);
                }
            });
        }

        if (identificador.equals("ClientesAlta"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (CajaTexto.Nombre.getText().equals("") || CajaTexto.direccion.getText().equals("") || CajaTexto.telefono.getText().equals("") || CajaTexto.correoElectronico.getText().equals(""))
                    JOptionPane.showMessageDialog(null,"No debe haber un campo vacio");
                    if (!CajaTexto.correoElectronico.getText().endsWith(".com") && !CajaTexto.correoElectronico.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Ingrese correo Valido: @ \'compañia\' terminacion \" .com \" ");
                    }else{

                    try {
                        consulta();
                        statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        String sql = "insert into clientes values (?,?,?,?,?)";
                        preparedStatement = conexion.prepareStatement(sql);
                        preparedStatement.setString(1,null);
                        preparedStatement.setString(2,CajaTexto.Nombre.getText().toUpperCase());
                        preparedStatement.setString(3,CajaTexto.direccion.getText().toUpperCase());
                        preparedStatement.setLong(4,Long.parseLong(CajaTexto.telefono.getText()));
                        preparedStatement.setString(5,CajaTexto.correoElectronico.getText());
                        resultado = preparedStatement.executeUpdate();
                        if (resultado==1)
                        {
                            System.out.println("Record Inserted");
                            //Clientes.jTable.setModel(Clientes.model);
                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");

                            Clientes.model.setRowCount(0);
                            consulta.Consulta(resultSet,Clientes.model,"Clientes");
                            CajaTexto.Nombre.setText(null);
                            CajaTexto.direccion.setText(null);
                            CajaTexto.telefono.setText(null);
                            CajaTexto.correoElectronico.setText(null);
                            frame.dispose();

                        }else{
                            System.out.println("no se insertaron los datos");

                        }
                        conexion.close();
                    } catch (Exception a) {
                        System.out.printf(a.getMessage());
                    }}
                }
            });
        }
        if (identificador.equals("ClientesEliminar"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }
        if (identificador.equals("Cancelar"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    CajaTexto.Nombre.setText(null);
                    CajaTexto.telefono.setText(null);
                    CajaTexto.correoElectronico.setText(null);
                    CajaTexto.direccion.setText(null);
                    //ClienteModificacion.id= 0;
                }
            });
        }
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
