package Consulta;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Consulta {
    JButton eliminar;
    Connection conexion;
    public void Consulta(ResultSet resultSet, DefaultTableModel Model, String Identificador)
    {
        eliminar= new JButton("Eliminar");
        eliminar.setName("Eliminar");
        try {
            while (resultSet.next()) {
                if (Identificador.equals("Clientes"))
                {
                    Model.addRow(new Object[]{
                            resultSet.getString("id"),resultSet.getString("nombre"),
                            resultSet.getString("direccion"),resultSet.getString("telefono"),
                            resultSet.getString("correo_electronico"),"Modificar",eliminar
                    });

                }
                if (Identificador.equals("Productos"))
                {
                    Model.addRow(new Object[]{
                             resultSet.getString("id"), resultSet.getString("nombre"),
                            resultSet.getString("clave"),resultSet.getLong("cantidad"),
                            resultSet.getString("estatus"),"Modificar",eliminar
                    });


                }
                if(Identificador.equals("Ventas"))
                {
                    ResultSet resultSetClientes;
                    resultSetClientes = ConexionConsulta.getTabla("SELECT nombre FROM examendiagnostico.clientes WHERE id LIKE '"+ resultSet.getLong("cliente_id")+"'");
                    while(resultSetClientes.next())
                    {
                        Model.addRow(new Object[]{resultSet.getString("id"), resultSetClientes.getString("nombre"),resultSet.getString("fecha_venta"),"Producto Vendido",eliminar
                        });
                    }
                }
                if (Identificador.equals("DetallesVentas"))
                {
                    ResultSet resultSetClientes;
                    resultSetClientes = ConexionConsulta.getTabla("SELECT nombre FROM examendiagnostico.productos WHERE id LIKE '"+ resultSet.getLong("id_producto")+"'");
                    while(resultSetClientes.next())
                    {
                        Model.addRow(new Object[]{resultSet.getString("cantidad"), resultSetClientes.getString("nombre")
                        });
                    }
                }





            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
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
