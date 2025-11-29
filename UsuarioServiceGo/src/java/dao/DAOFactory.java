/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

public abstract class DAOFactory {
    
    // Constantes para tipos de BD
    public static final int POSTGRESQL = 1;
    public static final int MYSQL = 2;
    
    // Método Factory principal
    public static DAOFactory getDAOFactory(int tipo) {
        switch(tipo) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            case MYSQL:
                return new MySQLDAOFactory();
            default:
                return null;
        }
    }
    
    // Método abstracto que cada factory implementará
    public abstract IUsuarioDAO getUsuarioDAO();
}

// Implementación concreta para PostgreSQL
class PostgreSQLDAOFactory extends DAOFactory {
    @Override
    public IUsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}

// Implementación alternativa para MySQL 
class MySQLDAOFactory extends DAOFactory {
    @Override
    public IUsuarioDAO getUsuarioDAO() {
        
        return new UsuarioDAOImpl(); 
    }
}
