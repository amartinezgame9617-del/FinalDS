/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modelo.Pago;
import org.bson.Document;
import org.bson.types.ObjectId;
import util.MongoConexion;
import java.util.Date;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class PagoDAOMongoImpl implements IPagoDAO {
    
    private MongoCollection<Document> coleccionPagos;
    
    public PagoDAOMongoImpl() {
        MongoDatabase db = MongoConexion.getDatabase();
        coleccionPagos = db.getCollection("pagos");
    }
    
    @Override
    public Pago procesarPago(Pago pago) {
        try {
            
            Random random = new Random();
            boolean exitoso = random.nextInt(100) < 80;
            
            //Generar código de transacción
            String codigoTransaccion = "TRX-" + System.currentTimeMillis() + "-" + random.nextInt(9999);
            
            pago.setCodigoTransaccion(codigoTransaccion);
            pago.setEstado(exitoso ? "exitoso" : "fallido");
            pago.setFechaPago(new Date());
            
            //Guardar en MongoDB
            Document doc = new Document()
                    .append("reserva_id", pago.getReservaId())
                    .append("usuario_id", pago.getUsuarioId())
                    .append("monto", pago.getMonto())
                    .append("metodo_pago", pago.getMetodoPago())
                    .append("estado", pago.getEstado())
                    .append("codigo_transaccion", pago.getCodigoTransaccion())
                    .append("fecha_pago", pago.getFechaPago());
            
            coleccionPagos.insertOne(doc);
            
            //Obtener el ID generado por MongoDB
            pago.setId(doc.getObjectId("_id").toString());
            
            return pago;
            
        } catch (Exception e) {
            e.printStackTrace();
            pago.setEstado("error");
            return pago;
        }
    }
    
    @Override
    public Pago obtenerPagoPorId(String id) {
        try {
            Document doc = coleccionPagos.find(eq("_id", new ObjectId(id))).first();
            
            if (doc != null) {
                return documentToPago(doc);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Pago obtenerPagoPorReserva(int reservaId) {
        try {
            Document doc = coleccionPagos.find(eq("reserva_id", reservaId)).first();
            
            if (doc != null) {
                return documentToPago(doc);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Pago documentToPago(Document doc) {
        Pago pago = new Pago();
        pago.setId(doc.getObjectId("_id").toString());
        pago.setReservaId(doc.getInteger("reserva_id"));
        pago.setUsuarioId(doc.getInteger("usuario_id"));
        pago.setMonto(doc.getDouble("monto"));
        pago.setMetodoPago(doc.getString("metodo_pago"));
        pago.setEstado(doc.getString("estado"));
        pago.setCodigoTransaccion(doc.getString("codigo_transaccion"));
        pago.setFechaPago(doc.getDate("fecha_pago"));
        return pago;
    }
}

