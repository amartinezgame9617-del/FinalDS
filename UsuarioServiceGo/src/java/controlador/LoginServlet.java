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
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    
    private IUsuarioDAO usuarioDAO;
    
    @Override
    public void init() {
        //patrón Factory
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
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            Usuario usuario = usuarioDAO.autenticarUsuario(email, password);
            
            if (usuario != null) {
                //Crear sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                
                
                String json = "{"
                    + "\"success\":true,"
                    + "\"mensaje\":\"Login exitoso\","
                    + "\"usuario\":{"
                        + "\"id\":" + usuario.getId() + ","
                        + "\"nombre\":\"" + usuario.getNombre() + "\","
                        + "\"email\":\"" + usuario.getEmail() + "\","
                        + "\"tipo\":\"" + usuario.getTipo() + "\""
                    + "}"
                + "}";
                out.print(json);
                
            } else {
                // Credenciales inválidas
                String json = "{\"success\":false,\"mensaje\":\"Credenciales inválidas\"}";
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
