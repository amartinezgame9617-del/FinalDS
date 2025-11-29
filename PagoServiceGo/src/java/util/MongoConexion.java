/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {
    
    private static final String HOST = "localhost";
    private static final int PUERTO = 27017;
    private static final String BASE_DATOS = "rurago_pagos";
    
    private static MongoClient mongoClient = null;
    
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(HOST, PUERTO);
            System.out.println("✓ Conexión a MongoDB establecida");
        }
        return mongoClient.getDatabase(BASE_DATOS);
    }
    
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("✓ Conexión a MongoDB cerrada");
        }
    }
    
    // Método de prueba
    public static void main(String[] args) {
        try {
            MongoDatabase db = MongoConexion.getDatabase();
            System.out.println("✓ Conectado a base de datos: " + db.getName());
            
            // Listar colecciones
            System.out.println("Colecciones disponibles:");
            for (String nombre : db.listCollectionNames()) {
                System.out.println("  - " + nombre);
            }
            
            MongoConexion.closeConnection();
            
        } catch (Exception e) {
            System.out.println("✗ Error de conexión:");
            e.printStackTrace();
        }
    }
}
