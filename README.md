# ExamenDiagnosticoGroovy
Este Repositorio es un Sistema de Ventas
Instrucciones para correr el programa sin problemas:
Hay clases que no se les fue implementada la clase consulta de forma general y la tienen en su respectiva clase, lo que tiene que hacer simplemente es cambiar en el metodo consulta() es poner su usuario y contraseña y por consiguiente generar la base de datos, las clases que tienen dicho método son: 
Todas las que están alojadas en el directorio Botones
Todas las que están alojadas en el directorio  Renderizado (a excepción de los archivos: ButtonRenderer y Render)
En el directorio Ventas solo su clase principal (Ventas.java) y lo mismo con Clientes y Productos
Todas las que están alojadas en el directorio Consultas
La clase/medoto del que hablo es este:
public  void consulta() throws SQLException {
    try{
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendiagnostico?serverTimezone=UTC",
                "root","Tu_Contraseña");
        System.out.println("Conectado!");
    }catch(SQLException e){
        e.printStackTrace();
    }      }
    
    
