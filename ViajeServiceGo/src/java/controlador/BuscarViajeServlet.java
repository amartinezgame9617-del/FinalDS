/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import dao.DAOFactory;
import dao.IViajeDAO;
import modelo.Viaje;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuscarViajeServlet extends HttpServlet {
    
    private IViajeDAO viajeDAO;
    
    @Override
    public void init() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        viajeDAO = factory.getViajeDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        
        try {
            String origen = request.getParameter("origen");
            String destino = request.getParameter("destino");
            
            List<Viaje> viajes = viajeDAO.buscarViajes(origen, destino);
            
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"viajes\":[");
            
            for (int i = 0; i < viajes.size(); i++) {
                Viaje v = viajes.get(i);
                json.append("{");
                json.append("\"id\":").append(v.getId()).append(",");
                json.append("\"conductor_id\":").append(v.getConductorId()).append(",");
                json.append("\"conductor_nombre\":\"").append(v.getConductorNombre() != null ? v.getConductorNombre() : "Conductor").append("\",");
                json.append("\"origen\":\"").append(v.getOrigen()).append("\",");
                json.append("\"destino\":\"").append(v.getDestino()).append("\",");
                json.append("\"fecha\":\"").append(v.getFecha()).append("\",");
                json.append("\"asientos\":").append(v.getAsientosDisponibles()).append(",");
                json.append("\"precio\":").append(v.getPrecio());
                json.append("}");
                
                if (i < viajes.size() - 1) {
                    json.append(",");
                }
            }
            
            json.append("]}");
            out.print(json.toString());
            
        } catch (Exception e) {
            String json = "{\"success\":false,\"mensaje\":\"Error: " + e.getMessage() + "\"}";
            out.print(json);
            e.printStackTrace();
        }
    }
}



