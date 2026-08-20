package com.mycompany.sistema.reservas.dominio;

import com.mycompany.sistema.reservas.dominio.modelo.Cliente;
import com.mycompany.sistema.reservas.dominio.modelo.Email;
import com.mycompany.sistema.reservas.dominio.modelo.RangoFechas;
import com.mycompany.sistema.reservas.dominio.modelo.Reserva;
import java.time.LocalDateTime;
import notificacion.EmailNotificadorService;
import notificacion.NotificadorService;
import politicas.DescuentoCorporativo;
import politicas.PoliticaDescuento;
import repositorio.ReservaMemoriaRepository;
import repositorio.ReservaRepository;
import servicio.ConfirmacionReservaService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daferarte
 */
public class mainsemana2 {
    public static void main(String[] args) {
        // 1. Instanciar Entidades y Value Objects de Semana 1
        Cliente cliente = new Cliente("Beatriz Morales", new Email("beatriz@empresa.com"));
        RangoFechas periodo = new RangoFechas(
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(5)
        );
        Reserva reserva = new Reserva(cliente, periodo);

        // 2. Configurar la infraestructura deseada (Fácilmente intercambiable)
        ReservaRepository repositorio = new ReservaMemoriaRepository();
        NotificadorService notificador = new EmailNotificadorService(); // Podría ser SmsNotificadorService

        // 3. Crear el servicio inyectando las dependencias
        ConfirmacionReservaService servicio = new ConfirmacionReservaService(repositorio, notificador);

        // 4. Ejecutar el caso de uso aplicando descuento Corporativo
        PoliticaDescuento descuentoCorporativo = new DescuentoCorporativo();
        double precioFinal = servicio.procesar(reserva, descuentoCorporativo, 300.0);

        System.out.println("Proceso finalizado. Total pagado: $" + precioFinal);
    }
}
