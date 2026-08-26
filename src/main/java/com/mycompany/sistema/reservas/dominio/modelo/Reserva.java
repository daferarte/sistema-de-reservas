/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

import java.util.UUID;

/**
 *
 * @author daferarte
 */
public class Reserva {
    private final UUID id;            // Inmutable 
    private final Cliente cliente;    // Asociación directa con la entidad Cliente
    private final Habitacion habitacion;
    private RangoFechas periodo;      // Value Object
    private EstadoReserva estado;
    
    public Reserva(Cliente cliente, Habitacion habitacion, RangoFechas periodo) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (!cliente.puedeRealizarReservas()) {
            throw new IllegalStateException("El cliente no está habilitado para realizar reservas");
        }
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación es obligatoria");
        }
        if (habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
            throw new IllegalStateException("No se puede reservar una habitación en mantenimiento");
        }
        if (periodo == null) {
            throw new IllegalArgumentException("El periodo es obligatorio");
        }

        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.periodo = periodo;
        this.estado = EstadoReserva.PENDIENTE;
    }
    
    public void confirmar() {
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se puede confirmar una reserva cancelada");
        }
        this.habitacion.asignarAReserva();
        this.estado = EstadoReserva.CONFIRMADA;
    }
    
    public void cancelar() {
        if (this.estado == EstadoReserva.CONFIRMADA) {
            // reglas cancelar
        }
        this.estado = EstadoReserva.CANCELADA;
        this.habitacion.habilitar();
    }
    
    public UUID getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public RangoFechas getPeriodo() { return periodo; }
    public EstadoReserva getEstado() { return estado; }
}
