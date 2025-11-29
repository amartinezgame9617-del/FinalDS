/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Viaje {
    private int id;
    private int conductorId;
    private String conductorNombre;  // ✅ NUEVO
    private String origen;
    private String destino;
    private String fecha;
    private int asientosDisponibles;
    private double precio;
    private String estado;
    
    // Constructor vacío
    public Viaje() {
    }
    
    // Constructor completo
    public Viaje(int conductorId, String origen, String destino, String fecha, 
                 int asientosDisponibles, double precio) {
        this.conductorId = conductorId;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.asientosDisponibles = asientosDisponibles;
        this.precio = precio;
        this.estado = "disponible";
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConductorId() {
        return conductorId;
    }

    public void setConductorId(int conductorId) {
        this.conductorId = conductorId;
    }

    //NUEVO: Getter y Setter para conductorNombre
    public String getConductorNombre() {
        return conductorNombre;
    }

    public void setConductorNombre(String conductorNombre) {
        this.conductorNombre = conductorNombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


