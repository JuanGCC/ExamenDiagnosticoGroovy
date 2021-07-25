package Consulta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionConsulta {
    public static ResultSet getTabla(String Consulta)
    {
        ResultSet datos= null;
        Statement statement;
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendiagnostico?serverTimezone=UTC",
                    "root","Tu_Contraseña");
            statement = connection.createStatement();
            datos= statement.executeQuery(Consulta);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return datos;
    }
}
