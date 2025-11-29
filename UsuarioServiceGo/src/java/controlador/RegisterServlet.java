/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import dao.DAOFactory;
import dao.IUsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    
    private IUsuarioDAO usuarioDAO;
    
    @Override
    public void init() {
        //Patrón Factory
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        usuarioDAO = factory.getUsuarioDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        
        try {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String password = request.getParameter("password");
            String tipo = request.getParameter("tipo");
            
            //VALIDACIÓN 1: Verificar si email ya existe
            Usuario existente = usuarioDAO.obtenerPorEmail(email);
            if (existente != null) {
                String json = "{\"success\":false,\"mensaje\":\"El email ya está registrado\"}";
                out.print(json);
                return;
            }
            
            //VALIDACIÓN 2: Verificar si teléfono ya existe
            existente = usuarioDAO.obtenerPorTelefono(telefono);
            if (existente != null) {
                String json = "{\"success\":false,\"mensaje\":\"El teléfono ya está registrado\"}";
                out.print(json);
                return;
            }
            
            //Con el registro normal
            Usuario usuario = new Usuario(nombre, email, telefono, password, tipo);
            boolean registrado = usuarioDAO.registrarUsuario(usuario);
            
            if (registrado) {
                String json = "{\"success\":true,\"mensaje\":\"Usuario registrado exitosamente\"}";
                out.print(json);
            } else {
                String json = "{\"success\":false,\"mensaje\":\"Error al registrar usuario\"}";
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


