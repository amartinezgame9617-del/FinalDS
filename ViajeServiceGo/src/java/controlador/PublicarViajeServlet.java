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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PublicarViajeServlet extends HttpServlet {
    
    private IViajeDAO viajeDAO;
    
    @Override
    public void init() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        viajeDAO = factory.getViajeDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        
        try {
            int conductorId = Integer.parseInt(request.getParameter("conductor_id"));
            String origen = request.getParameter("origen");
            String destino = request.getParameter("destino");
            String fecha = request.getParameter("fecha");
            int asientos = Integer.parseInt(request.getParameter("asientos"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            
            Viaje viaje = new Viaje(conductorId, origen, destino, fecha, asientos, precio);
            boolean publicado = viajeDAO.publicarViaje(viaje);
            
            if (publicado) {
                String json = "{\"success\":true,\"mensaje\":\"Viaje publicado exitosamente\"}";
                out.print(json);
            } else {
                String json = "{\"success\":false,\"mensaje\":\"Error al publicar viaje\"}";
                out.print(json);
            }
            
        } catch (Exception e) {
            String json = "{\"success\":false,\"mensaje\":\"Error: " + e.getMessage() + "\"}";
            out.print(json);
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}

