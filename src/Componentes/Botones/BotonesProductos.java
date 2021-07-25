package Componentes.Botones;


import Clientes.ClienteModificacion;
import Clientes.Clientes;
import Componentes.CajaTexto;
import Consulta.*;
import Productos.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BotonesProductos {
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
        if (identificador.equals("ProductosNuevo"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProductoNuevo productoNuevo = new ProductoNuevo();
                    productoNuevo.setVisible(true);
                }
            });
        }
        if (identificador.equals("ProductosModificar"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(ClienteModificacion.id);

                    try {
                        consulta();
                        String sql= "update productos set nombre=?, clave=?,cantidad=?,estatus=? WHERE id =?";
                        preparedStatement= conexion.prepareStatement(sql);
                        preparedStatement.setInt(5, ProductoModificacion.id);
                        preparedStatement.setString(1,CajaTexto.Nombre.getText().toUpperCase());
                        preparedStatement.setString(2,CajaTexto.claveProducto.getText().toUpperCase());
                        preparedStatement.setLong(3,Long.parseLong(CajaTexto.cantidadProducto.getText()));
                        preparedStatement.setString(4,CajaTexto.estatus.getText().toUpperCase());
                        resultado = preparedStatement.executeUpdate();
                        if (resultado== 1)
                        {
                            frame.dispose();
                            CajaTexto.Nombre.setText(null);
                            CajaTexto.claveProducto.setText(null);
                            CajaTexto.cantidadProducto.setText(null);
                            CajaTexto.estatus.setText(null);
                            //Productos.jTable.setModel(Clientes.model);
                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.productos");
                            Productos.model.setRowCount(0);
                            consulta.Consulta(resultSet,Productos.model,"Productos");
                            conexion.close();
                        }
                    } catch (Exception es) {
                        es.printStackTrace();
                    }

                }
            });
        }

        if (identificador.equals("ProductosAlta"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        if (CajaTexto.estatus.getText().equals("") || CajaTexto.Nombre.getText().equals("") || CajaTexto.claveProducto.getText().equals("") || CajaTexto.cantidadProducto.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "No debe haber ni un campo vacio");
                        }else
                        {


                        if (CajaTexto.estatus.getText().equals("ACTIVO") || CajaTexto.estatus.getText().equals("activo") || CajaTexto.estatus.getText().equals("INACTIVO") || CajaTexto.estatus.getText().equals("inactivo"))
                        {


                        consulta();
                        statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        String sql = "insert into productos values (?,?,?,?,?)";
                        preparedStatement = conexion.prepareStatement(sql);
                        preparedStatement.setString(1,null);
                        preparedStatement.setString(2, CajaTexto.Nombre.getText().toUpperCase());
                        preparedStatement.setString(3,CajaTexto.claveProducto.getText().toUpperCase());
                        preparedStatement.setLong(4,Long.parseLong(CajaTexto.cantidadProducto.getText()));
                        preparedStatement.setString(5,CajaTexto.estatus.getText().toUpperCase());
                        resultado = preparedStatement.executeUpdate();
                        if (resultado==1)
                        {
                            System.out.println("Record Inserted");
                            Productos.jTable.setModel(Productos.model);
                            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.productos");

                            Productos.model.setRowCount(0);
                            consulta.Consulta(resultSet,Productos.model,"Productos");
                            CajaTexto.Nombre.setText(null);
                            CajaTexto.claveProducto.setText(null);
                            CajaTexto.cantidadProducto.setText(null);
                            CajaTexto.estatus.setText(null);
                            frame.dispose();

                        }else{
                            System.out.println("no se insertaron los datos");

                        }
                        conexion.close();

                        }else
                        {
                            JOptionPane.showMessageDialog(null,"El estatus debe contener solo: ACTIVO o inactivo (mayuscula o minuscula)");
                        }

                        }
                    } catch (Exception a) {
                        System.out.printf(a.getMessage());
                    }
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
                    CajaTexto.claveProducto.setText(null);
                    CajaTexto.cantidadProducto.setText(null);
                    CajaTexto.estatus.setText(null);
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
