/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import util.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ObtenerReservasServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        
        try {
            String pasajeroId = request.getParameter("pasajero_id");
            
            if (pasajeroId == null || pasajeroId.isEmpty()) {
                out.print("{\"success\":false,\"mensaje\":\"ID de pasajero requerido\"}");
                return;
            }
            
            String sql = "SELECT r.id, r.viaje_id, r.asientos_reservados, r.monto_total as monto, " +
                        "v.origen, v.destino, v.fecha, v.precio, " +
                        "u.nombre as conductor_nombre " +
                        "FROM reservas r " +
                        "JOIN viajes v ON r.viaje_id = v.id " +
                        "JOIN usuarios u ON v.conductor_id = u.id " +
                        "WHERE r.pasajero_id = ? " +
                        "ORDER BY r.id DESC";
            
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"reservas\":[");
            
            try (Connection conn = Conexion.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, Integer.parseInt(pasajeroId));
                ResultSet rs = pstmt.executeQuery();
                
                boolean first = true;
                while (rs.next()) {
                    if (!first) json.append(",");
                    first = false;
                    
                    json.append("{");
                    json.append("\"id\":").append(rs.getInt("id")).append(",");
                    json.append("\"viaje_id\":").append(rs.getInt("viaje_id")).append(",");
                    json.append("\"origen\":\"").append(rs.getString("origen")).append("\",");
                    json.append("\"destino\":\"").append(rs.getString("destino")).append("\",");
                    json.append("\"fecha\":\"").append(rs.getString("fecha")).append("\",");
                    json.append("\"asientos\":").append(rs.getInt("asientos_reservados")).append(",");
                    json.append("\"monto\":").append(rs.getDouble("monto")).append(",");
                    json.append("\"conductor_nombre\":\"").append(rs.getString("conductor_nombre")).append("\"");
                    json.append("}");
                }
            }
            
            json.append("]}");
            out.print(json.toString());
            
        } catch (Exception e) {
            out.print("{\"success\":false,\"mensaje\":\"Error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}


