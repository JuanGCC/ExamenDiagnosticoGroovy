package Componentes;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CajaTexto {
    public static JTextField Nombre = new JTextField(), direccion = new JTextField(),
    telefono = new JTextField(), correoElectronico = new JTextField(),
    claveProducto = new JTextField(), cantidadProducto = new JTextField(),
    estatus = new JTextField(), cantidad = new JTextField();
    public CajaTexto()
    {
        Caracteres(Nombre);
        Combinado(direccion);
        CorreoElectronico(correoElectronico);
        Numeros(telefono);
        Caracteres(estatus);
        Numeros(cantidadProducto);
        Combinado(claveProducto);
        Numeros(cantidad);
    }
    public void Caracteres(JTextField textField)
    {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char car = keyEvent.getKeyChar();
                if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car < ' ' || car>' ' ))keyEvent.consume();
                if (textField.getText().length() == 50) keyEvent.consume();
            }
        });
    }
    public void Combinado(JTextField textField)
    {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char car = keyEvent.getKeyChar();
                if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car < '0' || car > '9'))keyEvent.consume();
                if (textField.getText().length() == 50) keyEvent.consume();
            }
        });
    }
    public void CorreoElectronico(JTextField textField)
    {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char car = keyEvent.getKeyChar();
                if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z') && (car < '0' || car > '9') && (car < '@' || car > '@') && (car < '.' || car > '.'))keyEvent.consume();
                if (textField.getText().length() == 50) keyEvent.consume();
            }
        });
    }
    public void Numeros(JTextField textField)
    {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char car = keyEvent.getKeyChar();
                if ((car < '0' || car > '9'))keyEvent.consume();
                if (textField.getText().length() == 11) keyEvent.consume();
            }
        });
    }
}
