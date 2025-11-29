/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

public abstract class DAOFactory {
    
    public static final int POSTGRESQL = 1;
    public static final int MYSQL = 2;
    
    public static DAOFactory getDAOFactory(int tipo) {
        switch(tipo) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            default:
                return null;
        }
    }
    
    public abstract IViajeDAO getViajeDAO();
}

class PostgreSQLDAOFactory extends DAOFactory {
    @Override
    public IViajeDAO getViajeDAO() {
        return new ViajeDAOImpl();
    }
}

