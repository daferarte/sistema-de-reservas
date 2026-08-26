/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.mycompany.sistema.reservas.dominio.modelo;

import java.util.regex.Pattern;

/**
 *
 * @author daferarte
 */
public record NumeroHabitacion(String valor) {
    private static final Pattern PATRON = Pattern.compile("^P\\d{2}-\\d{3}$");

    public NumeroHabitacion {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El número de habitación no puede estar vacío");
        }
        if (!PATRON.matcher(valor).matches()) {
            throw new IllegalArgumentException("Formato inválido. Debe seguir el patrón 'PXX-XXX' (Ej: P03-302)");
        }
    }
}
