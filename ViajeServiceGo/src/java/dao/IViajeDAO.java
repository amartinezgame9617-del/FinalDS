/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import modelo.Viaje;
import java.util.List;

public interface IViajeDAO {
    boolean publicarViaje(Viaje viaje);
    List<Viaje> buscarViajes(String origen, String destino);
    List<Viaje> listarTodosViajes();
    boolean reservarViaje(int viajeId, int pasajeroId, int asientos, double monto);
    Viaje obtenerViajePorId(int id);
}

