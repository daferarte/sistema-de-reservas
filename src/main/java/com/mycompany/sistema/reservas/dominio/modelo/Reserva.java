/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

import comportamentales.observer.GestorEventosReserva;
import comportamentales.state.EstadoPendiente;
import comportamentales.state.EstadoReserva;
import comportamentales.strategy.EstrategiaCancelacion;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 *
 * @author daferarte
 */
public class Reserva {
    private final UUID id;             
    private final Cliente cliente;     
    private final Habitacion habitacion;
    private RangoFechas periodo;       
    
    // 1. Patrón State: El estado es una interfaz, no un Enum
    private EstadoReserva estado;

    // 2. Patrones inyectados para manejo de reglas dinámicas y eventos
    private EstrategiaCancelacion estrategiaCancelacion;
    private GestorEventosReserva gestorEventos;
    
    public Reserva(Cliente cliente, Habitacion habitacion, RangoFechas periodo) {
        if (cliente == null) throw new IllegalArgumentException("El cliente es obligatorio");
        if (!cliente.puedeRealizarReservas()) throw new IllegalStateException("El cliente no está habilitado");
        if (habitacion == null) throw new IllegalArgumentException("La habitación es obligatoria");
        if (habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) throw new IllegalStateException("Habitación en mantenimiento");
        if (periodo == null) throw new IllegalArgumentException("El periodo es obligatorio");

        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.periodo = periodo;
        
        // La reserva nace en estado pendiente
        this.estado = new EstadoPendiente(); 
    }
    
    // --- MÉTODOS DE DELEGACIÓN (Cero IFs) ---
    
    public void confirmar() {
        this.estado.confirmar(this);
    }
    
    public void cancelar(int diasRestantes) {
        this.estado.cancelar(this, diasRestantes);
    }

    public void cancelar() {
        int diasRestantes = (int) ChronoUnit.DAYS.between(LocalDateTime.now(), periodo.fechaInicio());
        cancelar(Math.max(0, diasRestantes));
    }
    
    public void setEstado(EstadoReserva nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // --- GETTERS Y SETTERS ---

    public void setEstrategiaCancelacion(EstrategiaCancelacion estrategia) {
        this.estrategiaCancelacion = estrategia;
    }

    public EstrategiaCancelacion getEstrategiaCancelacion() {
        return estrategiaCancelacion;
    }

    public void setGestorEventos(GestorEventosReserva gestor) {
        this.gestorEventos = gestor;
    }

    public GestorEventosReserva getGestorEventos() {
        return gestorEventos;
    }
    
    public UUID getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public RangoFechas getPeriodo() { return periodo; }
    public EstadoReserva getEstado() { return estado; }
    
    // Método auxiliar necesario para Strategy
    public double getTotal() {
        return habitacion.getPrecioPorNoche() * periodo.getDias();
    }
}