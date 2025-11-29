// URLS de los microservicios
const API_USUARIO = 'http://localhost:8080/UsuarioServiceGo';
const API_VIAJE = 'http://localhost:8080/ViajeServiceGo';
const API_PAGO = 'http://localhost:8080/PagoServiceGo';

// Función Login
async function login(email, password) {
    const res = await fetch(`${API_USUARIO}/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `email=${email}&password=${password}`
    });
    return await res.json();
}

// Función Registro
async function registrarUsuario(data) {
    const res = await fetch(`${API_USUARIO}/register`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `nombre=${encodeURIComponent(data.nombre)}&email=${encodeURIComponent(data.email)}&telefono=${encodeURIComponent(data.telefono)}&password=${encodeURIComponent(data.password)}&tipo=${encodeURIComponent(data.tipo)}`
    });
    return await res.json();
}

// Publicar viaje
async function publicarViaje(data) {
    const res = await fetch(`${API_VIAJE}/publicar-viaje`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `conductor_id=${data.conductor_id}&origen=${encodeURIComponent(data.origen)}&destino=${encodeURIComponent(data.destino)}&fecha=${encodeURIComponent(data.fecha)}&asientos=${data.asientos}&precio=${data.precio}`
    });
    return await res.json();
}

// Buscar viajes
async function buscarViajes(origen, destino) {
    let url = `${API_VIAJE}/buscar-viajes?`;
    if (origen) url += `origen=${encodeURIComponent(origen)}&`;
    if (destino) url += `destino=${encodeURIComponent(destino)}&`;
    const res = await fetch(url.slice(0,-1));
    return await res.json();
}

// Reservar viaje
async function reservarViaje(viajeId, pasajeroId, asientos, monto) {
    const res = await fetch(`${API_VIAJE}/reservar-viaje`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `viaje_id=${viajeId}&pasajero_id=${pasajeroId}&asientos=${asientos}&monto=${monto}`
    });
    return await res.json();
}

// Procesar pago
async function procesarPago(reservaId, usuarioId, monto, metodoPago) {
    const res = await fetch(`${API_PAGO}/procesar`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `reserva_id=${reservaId}&usuario_id=${usuarioId}&monto=${monto}&metodo_pago=${encodeURIComponent(metodoPago)}`
    });
    return await res.json();
}

// Obtener reservas del pasajero
async function obtenerReservas(pasajeroId) {
    const res = await fetch(`${API_VIAJE}/obtener-reservas?pasajero_id=${pasajeroId}`);
    return await res.json();
}


