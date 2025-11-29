/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import modelo.Usuario;

public interface IUsuarioDAO {
    boolean registrarUsuario(Usuario usuario);
    Usuario autenticarUsuario(String email, String password);
    Usuario obtenerUsuarioPorId(int id);
    Usuario obtenerPorEmail(String email);      
    Usuario obtenerPorTelefono(String telefono); 
}


