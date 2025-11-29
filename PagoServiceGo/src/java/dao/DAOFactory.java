/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

public abstract class DAOFactory {
    
    // Constantes para tipos de BD
    public static final int MONGODB = 1;
    public static final int POSTGRESQL = 2;
    
    // Método Factory principal
    public static DAOFactory getDAOFactory(int tipo) {
        switch(tipo) {
            case MONGODB:
                return new MongoDBDAOFactory();
            case POSTGRESQL:
                // Si en el futuro quisieras usar PostgreSQL para pagos
                return null;
            default:
                return null;
        }
    }
    
    // Método abstracto
    public abstract IPagoDAO getPagoDAO();
}

// Implementación para MongoDB (NoSQL)
class MongoDBDAOFactory extends DAOFactory {
    @Override
    public IPagoDAO getPagoDAO() {
        return new PagoDAOMongoImpl();
    }
}
