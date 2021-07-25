package Componentes.Botones;

import Clientes.Clientes;
import Componentes.CajaTexto;
import Consulta.*;
import Ventas.VentaNuevo;
import Ventas.Ventas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BotonesVentas {
    Connection conexion;
    Statement statement ;
    //PreparedStatement preparedStatement;
    ResultSet resultset ;
    ResultSet resultSet;
    PreparedStatement preparedStatementVentas, preparedStatementDetalleVenta,preparedStatementProductos;
    Consulta consulta = new Consulta();

    int resultado,resultado2,resultado3;
    public void botones(String Titulo, int [] bounds, JPanel panel, String identificador, JFrame frame)
    {
        JButton button = new JButton(Titulo);
        button.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
        panel.add(button);
        if(identificador.equals("VentasAlta"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try
                    {
                        consulta();
                        statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        String sql = "insert into ventas values (?,?,?)";
                        String sql2 = "insert into detalle_ventas values(?,?,?,?)";
                        preparedStatementVentas = conexion.prepareStatement(sql);
                        preparedStatementDetalleVenta = conexion.prepareStatement(sql2);
                        java.util.Date fecha = new java.util.Date();
                        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                        for(int i = 0; i < VentaNuevo.model.getRowCount(); i++)
                        {
                            System.out.println(VentaNuevo.model.getValueAt(i,1));
                            ResultSet resultSetClientes = ConexionConsulta.getTabla("SELECT id FROM clientes WHERE nombre LIKE'"+VentaNuevo.model.getValueAt(i,0).toString()+"'");


                            while (resultSetClientes.next())
                            {
                                preparedStatementVentas.setString(1,VentaNuevo.id.getText());
                                preparedStatementVentas.setLong(2, resultSetClientes.getLong("id"));
                                preparedStatementVentas.setDate(3, new java.sql.Date(fecha.getTime()));
                            }
                            preparedStatementDetalleVenta.setString(1,null);
                            String sql4= "update productos set cantidad = ? WHERE id =?";
                            preparedStatementProductos= conexion.prepareStatement(sql4);
                            resultset = ConexionConsulta.getTabla("SELECT id, cantidad FROM productos WHERE nombre LIKE '"+VentaNuevo.model.getValueAt(i,1).toString()+"'");
                            //resultSet = statement.executeQuery("SELECT id FROM clientes WHERE nombre LIKE '"+VentaNuevo.model.getValueAt(i,0).toString()+"'");

                            while (resultset.next() )
                            {
                                Long productoid = resultset.getLong("id");
                                System.out.println(productoid);

                                preparedStatementDetalleVenta.setLong(2,Long.parseLong(VentaNuevo.id.getText()));
                                preparedStatementDetalleVenta.setLong(3,resultset.getLong("id"));
                                preparedStatementDetalleVenta.setInt(4,Integer.parseInt(VentaNuevo.model.getValueAt(i,2).toString()));
                                preparedStatementProductos.setLong(1,VentaNuevo.resta);
                                preparedStatementProductos.setLong(2,productoid);

                                System.out.println("la resta es "+VentaNuevo.resta);
                            }

                            resultado = preparedStatementVentas.executeUpdate();
                            resultado2 = preparedStatementDetalleVenta.executeUpdate();
                            resultado3 = preparedStatementProductos.executeUpdate();
                            if (resultado2== 1 && resultado == 1){
                                System.out.println("record inserted");
                                VentaNuevo.id.setText("");
                                CajaTexto.cantidad.setText("");
                                VentaNuevo.model.setRowCount(0);
                                resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.ventas");
                                Ventas.model.setRowCount(0);
                                consulta.Consulta(resultSet,Ventas.model,"Ventas");


                                conexion.close();
                                frame.dispose();
                            }

                        }
                    }
                    catch (Exception exception)
                    {
                        JOptionPane.showMessageDialog(null,exception.getMessage());
                    }
                }
            });
        }
        if (identificador.equals("VentasNueva"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VentaNuevo ventaNuevo = new VentaNuevo();
                    ventaNuevo.setVisible(true);
                }
            });
        }
        if (identificador.equals("Cancelar"))
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    VentaNuevo.id.setText("");
                    CajaTexto.cantidad.setText("");
                }
            });
        }
    }
    public  void consulta() throws SQLException {
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendiagnostico?serverTimezone=UTC",
                    "root","Soyuncacahuate_1");
            System.out.println("Conectado!");
        }catch(SQLException e){
            e.printStackTrace();
        }      }
}
