/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Viaje;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAOImpl implements IViajeDAO {
    
    @Override
    public boolean publicarViaje(Viaje viaje) {
        String sql = "INSERT INTO viajes (conductor_id, origen, destino, fecha, asientos_disponibles, precio, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, viaje.getConductorId());
            pstmt.setString(2, viaje.getOrigen());
            pstmt.setString(3, viaje.getDestino());
            pstmt.setDate(4, Date.valueOf(viaje.getFecha()));
            pstmt.setInt(5, viaje.getAsientosDisponibles());
            pstmt.setDouble(6, viaje.getPrecio());
            pstmt.setString(7, "disponible");
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
        @Override
    public List<Viaje> buscarViajes(String origen, String destino) {
        List<Viaje> viajes = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT v.*, u.nombre as conductor_nombre " +
            "FROM viajes v " +
            "JOIN usuarios u ON v.conductor_id = u.id " +
            "WHERE v.asientos_disponibles > 0"
        );
        
        if (origen != null && !origen.isEmpty()) {
            sql.append(" AND LOWER(v.origen) LIKE LOWER(?)");
        }
        if (destino != null && !destino.isEmpty()) {
            sql.append(" AND LOWER(v.destino) LIKE LOWER(?)");
        }
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int index = 1;
            if (origen != null && !origen.isEmpty()) {
                pstmt.setString(index++, "%" + origen + "%");
            }
            if (destino != null && !destino.isEmpty()) {
                pstmt.setString(index++, "%" + destino + "%");
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setId(rs.getInt("id"));
                viaje.setConductorId(rs.getInt("conductor_id"));
                viaje.setOrigen(rs.getString("origen"));
                viaje.setDestino(rs.getString("destino"));
                viaje.setFecha(rs.getString("fecha"));
                viaje.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
                viaje.setPrecio(rs.getDouble("precio"));
                viaje.setConductorNombre(rs.getString("conductor_nombre"));
                viajes.add(viaje);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return viajes;
    }


    
    @Override
    public List<Viaje> listarTodosViajes() {
        return buscarViajes(null, null);
    }
    
    @Override
    public boolean reservarViaje(int viajeId, int pasajeroId, int asientos, double monto) {
        String sqlReserva = "INSERT INTO reservas (viaje_id, pasajero_id, asientos_reservados, monto_total, estado) "
                          + "VALUES (?, ?, ?, ?, 'pendiente')";
        String sqlActualizarAsientos = "UPDATE viajes SET asientos_disponibles = asientos_disponibles - ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            
            // Insertar reserva
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlReserva)) {
                pstmt1.setInt(1, viajeId);
                pstmt1.setInt(2, pasajeroId);
                pstmt1.setInt(3, asientos);
                pstmt1.setDouble(4, monto);
                pstmt1.executeUpdate();
            }
            
            // Actualizar asientos disponibles
            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlActualizarAsientos)) {
                pstmt2.setInt(1, asientos);
                pstmt2.setInt(2, viajeId);
                pstmt2.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            Conexion.closeConnection(conn);
        }
    }
    
    @Override
    public Viaje obtenerViajePorId(int id) {
        String sql = "SELECT * FROM viajes WHERE id = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setId(rs.getInt("id"));
                viaje.setConductorId(rs.getInt("conductor_id"));
                viaje.setOrigen(rs.getString("origen"));
                viaje.setDestino(rs.getString("destino"));
                viaje.setFecha(rs.getDate("fecha").toString());
                viaje.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
                viaje.setPrecio(rs.getDouble("precio"));
                viaje.setEstado(rs.getString("estado"));
                return viaje;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}

