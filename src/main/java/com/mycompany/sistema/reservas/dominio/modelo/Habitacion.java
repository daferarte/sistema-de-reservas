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
public class Habitacion {
    private final UUID id;
    private final NumeroHabitacion numero;
    private final int capacidadMaxima;
    private EstadoHabitacion estado;

    public Habitacion(NumeroHabitacion numero, int capacidadMaxima) {
        if (numero == null) {
            throw new IllegalArgumentException("El número de habitación es obligatorio");
        }
        if (capacidadMaxima < 1) {
            throw new IllegalArgumentException("La capacidad máxima debe ser de al menos 1 persona");
        }
        this.id = UUID.randomUUID();
        this.numero = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = EstadoHabitacion.DISPONIBLE;
    }

    public void marcarEnMantenimiento() {
        this.estado = EstadoHabitacion.MANTENIMIENTO;
    }

    public void habilitar() {
        this.estado = EstadoHabitacion.DISPONIBLE;
    }

    public void asignarAReserva() {
        if (this.estado == EstadoHabitacion.MANTENIMIENTO) {
            throw new IllegalStateException("No se puede asignar una habitación en mantenimiento");
        }
        this.estado = EstadoHabitacion.OCUPADA;
    }

    public UUID getId() { return id; }
    public NumeroHabitacion getNumero() { return numero; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public EstadoHabitacion getEstado() { return estado; }
}
