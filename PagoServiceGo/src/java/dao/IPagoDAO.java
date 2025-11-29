/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import modelo.Pago;

public interface IPagoDAO {
    Pago procesarPago(Pago pago);
    Pago obtenerPagoPorId(String id);
    Pago obtenerPagoPorReserva(int reservaId);
}

