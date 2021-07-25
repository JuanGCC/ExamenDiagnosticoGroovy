package Componentes.Renderizado;

import Clientes.ClienteModificacion;
import Componentes.CajaTexto;
import Productos.Productos;
import Productos.ProductoModificacion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonEditorProductosModificar extends DefaultCellEditor {
    Connection conexion;
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditorProductosModificar(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
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
            //int row = Prueba.table.getSelectedRow();
            int row = Productos.jTable.getSelectedRow();
            //System.out.println(Clientes.jTable.getValueAt(row,0));
            ProductoModificacion clienteModificacion = new ProductoModificacion();
            Statement statement;
            ResultSet resultset;
            try {
                consulta();
                statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                //resultset = statement.executeQuery("SELECT * FROM clientes WHERE id LIKE '"+Prueba.table.getValueAt(row,0)+"'");
                resultset = statement.executeQuery("SELECT * FROM productos WHERE id LIKE '"+Productos.jTable.getValueAt(row,0)+"'");
                if (resultset.next())
                {
                    ProductoModificacion.id = resultset.getInt("id");
                    CajaTexto.Nombre.setText(resultset.getString("nombre"));
                    CajaTexto.claveProducto.setText(resultset.getString("clave"));
                    CajaTexto.cantidadProducto.setText(String.valueOf(resultset.getLong("cantidad")));
                    CajaTexto.estatus.setText(resultset.getString("estatus"));
                    conexion.close();
                }
                clienteModificacion.setVisible(true);

            } catch (SQLException throwables) {
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
                    "root","Tu_Contraseña");
            System.out.println("Conectado!");
        }catch(SQLException e){
            e.printStackTrace();
        }      }
}
