package Componentes.Renderizado;

import Clientes.Clientes;
import Componentes.CajaTexto;
import Consulta.*;
import Ventas.Ventas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonEditorVentasProductosVendidosEliminar extends DefaultCellEditor {
    Connection conexion;
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditorVentasProductosVendidosEliminar(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                //fireEditingCanceled();
            }
        });
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {

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
                    Ventas.model.setRowCount(0);
                    resultSet = ConexionConsulta.getTabla("SELECT * FROM examendiagnostico.ventas");
                    consulta.Consulta(resultSet,Ventas.model,"Ventas");
                    conexion.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {


            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
    public  void consulta() throws SQLException {
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendiagnostico?serverTimezone=UTC",
                    "root","Tu_Contrase??a");
            System.out.println("Conectado!");
        }catch(SQLException e){
            e.printStackTrace();
        }      }
}
