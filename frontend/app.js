const API = "http://localhost:8080/carritos";

const PRODUCTOS = {
    1: "Auriculares inalámbricos",
    2: "Teclado mecánico",
    3: "Smartwatch deportivo"
};

// -------- CREAR CARRITO --------
async function crearCarrito() {
    const res = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idUsuario: 1,
            correoUsuario: "test@icai.com"
        })
    });

    const data = await res.json();
    localStorage.setItem("carritoId", data.idCarrito);
    return data.idCarrito;
}

// -------- OBTENER CARRITO ID --------
async function getCarritoId() {
    let id = localStorage.getItem("carritoId");

    try {
        if (id) {
            const res = await fetch(`${API}/${id}`);

            if (!res.ok) {
                localStorage.removeItem("carritoId");
                id = null;
            }
        }
    } catch (e) {
        localStorage.removeItem("carritoId");
        id = null;
    }

    if (!id) {
        id = await crearCarrito();
    }

    return id;
}

// -------- AÑADIR PRODUCTO --------
async function addProducto(idArticulo, precio) {
    const idCarrito = await getCarritoId();

    const res = await fetch(`${API}/${idCarrito}/lineas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idArticulo: idArticulo,
            precioUnitario: precio,
            numeroUnidades: 1
        })
    });

    if (!res.ok) {
        alert("Error al añadir producto");
        return;
    }

    const mensaje = document.getElementById("mensaje-carrito");

    if (mensaje) {
        mensaje.innerText = "Producto añadido al carrito";
    } else {
        alert("Producto añadido al carrito");
    }}

// -------- CARGAR CARRITO --------
async function cargarCarrito() {
    const idCarrito = await getCarritoId();

    const res = await fetch(`${API}/${idCarrito}`);
    const carrito = await res.json();

    const tbody = document.getElementById("tbody-carrito");
    tbody.innerHTML = "";

    carrito.lineas.forEach(l => {
        const nombre = PRODUCTOS[l.idArticulo] || "Producto " + l.idArticulo;

        const fila = `
            <tr>
                <td>${nombre}</td>
                <td>${l.precioUnitario} €</td>
                <td>${l.numeroUnidades}</td>
                <td>${l.costeLineaArticulo} €</td>
                <td>
                    <button onclick="eliminar(${l.idArticulo})">❌</button>
                </td>
            </tr>
        `;

        tbody.innerHTML += fila;
    });

    document.getElementById("total").innerText = carrito.totalPrecio + " €";
}

// -------- ELIMINAR --------
async function eliminar(idArticulo) {
    const idCarrito = await getCarritoId();

    await fetch(`${API}/${idCarrito}/lineas/${idArticulo}`, {
        method: "DELETE"
    });

    cargarCarrito();
}