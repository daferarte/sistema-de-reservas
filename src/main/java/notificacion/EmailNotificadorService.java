/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notificacion;

import com.mycompany.sistema.reservas.dominio.modelo.Cliente;

/**
 *
 * @author daferarte
 */
public class EmailNotificadorService implements NotificadorService {
    @Override
    public void notificar(Cliente cliente, String mensaje) {
        System.out.println("[Correo enviado a " + cliente.getEmail().valor() + "]: " + mensaje);
    }
}
