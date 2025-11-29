/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import dao.DAOFactory;
import dao.IPagoDAO;
import modelo.Pago;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcesarPagoServlet extends HttpServlet {
    
    private IPagoDAO pagoDAO;
    
    @Override
    public void init() {
        // Usar el patrón Factory para MongoDB
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MONGODB);
        pagoDAO = factory.getPagoDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        
        try {
            int reservaId = Integer.parseInt(request.getParameter("reserva_id"));
            int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
            double monto = Double.parseDouble(request.getParameter("monto"));
            String metodoPago = request.getParameter("metodo_pago");
            
            Pago pago = new Pago(reservaId, usuarioId, monto, metodoPago);
            Pago resultado = pagoDAO.procesarPago(pago);
            
            if ("exitoso".equals(resultado.getEstado())) {
                String json = "{"
                    + "\"success\":true,"
                    + "\"mensaje\":\"Pago procesado exitosamente\","
                    + "\"pago\":{"
                        + "\"id\":\"" + resultado.getId() + "\","
                        + "\"codigo_transaccion\":\"" + resultado.getCodigoTransaccion() + "\","
                        + "\"estado\":\"" + resultado.getEstado() + "\","
                        + "\"monto\":" + resultado.getMonto()
                    + "}"
                + "}";
                out.print(json);
                
            } else if ("fallido".equals(resultado.getEstado())) {
                String json = "{"
                    + "\"success\":false,"
                    + "\"mensaje\":\"Pago rechazado. Intente nuevamente\","
                    + "\"codigo_transaccion\":\"" + resultado.getCodigoTransaccion() + "\""
                + "}";
                out.print(json);
                
            } else {
                String json = "{\"success\":false,\"mensaje\":\"Error al procesar el pago\"}";
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

