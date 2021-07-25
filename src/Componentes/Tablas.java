package Componentes;

import Componentes.Renderizado.ButtonEditorClientesEliminar;
import Componentes.Renderizado.ButtonEditorClientesModificar;
import Componentes.Renderizado.ButtonRenderer;
import Consulta.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class Tablas {
    public void Table(DefaultTableModel model,int[] array, JPanel panel,String identificador)
    {
        JTable jTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jTable);
        model = new DefaultTableModel();
        scrollPane.setBounds(array[0],array[1],array[2],array[3]);
        jTable.setBounds(array[0],array[1],array[2],array[3]);
        ResultSet resultSet;
        Consulta consulta = new Consulta();
        jTable.setModel(model);
        jTable.setAutoCreateColumnsFromModel(true);
        panel.add(scrollPane);
        if (identificador.equals("Clientes"))
        {
            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.clientes");
            model.addColumn("Id");
            model.addColumn("Nombre");
            model.addColumn("Direccion");
            model.addColumn("Teléfono");
            model.addColumn("Correo electrónico");
            model.addColumn("Accion");
            model.addColumn("Accion2");
            jTable.getColumn("Accion").setCellRenderer(new ButtonRenderer());
            jTable.getColumn("Accion").setCellEditor(new ButtonEditorClientesModificar(new JCheckBox()));
            jTable.getColumn("Accion2").setCellRenderer(new ButtonRenderer());
            jTable.getColumn("Accion2").setCellEditor(new ButtonEditorClientesEliminar(new JCheckBox()));
            consulta.Consulta(resultSet,model,"Clientes");
        }
        if (identificador.equals("Productos"))
        {
            resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.productos");
            model.addColumn("Id");
            model.addColumn("Nombre");
            model.addColumn("Clave");
            model.addColumn("Cantidad");
            model.addColumn("Estatus");
            consulta.Consulta(resultSet,model,"Productos");
        }
    }
}
