package Componentes.Renderizado;

import Clientes.Clientes;
import Componentes.CajaTexto;
import Consulta.*;
import Ventas.VentaNuevo;
import Ventas.VentasProductos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonEditorVentasProductosVendidos extends DefaultCellEditor{
    Connection conexion;
    protected JButton button;
    private String label;
    private boolean isPushed;
    ResultSet resultSet;
    public ButtonEditorVentasProductosVendidos(JCheckBox checkBox) {
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
            VentasProductos productos = new Ventas.VentasProductos();
            productos.setVisible(true);
        //JOptionPane.showMessageDialog(null,"ouch");
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
                    "root","Tu_Contraseña");
            System.out.println("Conectado!");
        }catch(SQLException e){
            e.printStackTrace();
        }      }

}
