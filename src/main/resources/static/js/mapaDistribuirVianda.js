let map;
// class Heladera {
//     constructor(id, nombre, latitud, longitud,activa,altura,nombreCalle) {
//         this.id = id;
//         this.nombre = nombre;
//         this.latitud = latitud;
//         this.longitud = longitud;
//         this.activa = activa;
//         this.altura = altura;
//         this.nombreCalle = nombreCalle;
//
//     }
// }

// Cuando el html termina de cargar ejecuta cargarMapa()
$(document).ready(function() {
    cargarMapa();
    buscarHeladeraMapa();
});


function cargarMapa(){
    const puntoCentral ={
		lat : -34.656032172926906, 
		lng : -58.426227983358935,
	}

    // La L es un objeto de leaflet
    // Seteamos el icono de la heladera


    // Inicializar el mapa y establecer su punto central y el nivel de zoom
    map = L.map('mapid').setView([puntoCentral.lat,puntoCentral.lng], 13);
    
    // Cargar mapa
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Grupo 11 Diseño de Sistemas'
    }).addTo(map);

    // Si se hace doble click en el mapa no hace zoom
    map.doubleClickZoom.disable();

    // Añadir las heladeras al mapa
    obtenerHeladeras().then(heladeras => {
        heladeras.forEach(heladera => {
            agregarHeladera(heladera,map);
        })
    })
}

function agregarHeladera(heladera,map){
    // Creamos el punto con los datos del back y lo ponemos
    let marker
    let heladeraIcon
    if (heladera.esElEncargado === "true") {
        console.log("En el if esElEncargado= "+ heladera.esElEncargado)
        heladeraIcon = L.icon({
            iconUrl: '/img/heladeraMapa.png',
            iconSize: [25, 45]
        });
        marker = L.marker([heladera.latitud, heladera.longitud], {icon: heladeraIcon}).addTo(map);
        marker.bindPopup("<b>"+heladera.nombre+"</b>").openPopup();
    } else {
        console.log("En el else esElEncargado= "+ heladera.esElEncargado)
        heladeraIcon = L.icon({
            iconUrl: '/img/heladera.png',
            iconSize: [20, 40]
        });
        marker = L.marker([heladera.latitud, heladera.longitud], {icon: heladeraIcon}).addTo(map);
        marker.bindPopup("<b>"+heladera.nombre+"</b>").openPopup();
    }



    // Evento para que cuando se clickee sobre una heladera muestre sus datos en el formulario de seleccion
    marker.on('click', function(){
        let origenNombre = document.getElementById("origen_nombre");
        let origenId = document.getElementById("origen_id");
        let origenDireccion = document.getElementById("origen_direccion");

        let destinoNombre = document.getElementById("destino_nombre");
        let destinoId = document.getElementById("destino_id");
        let destinoDireccion = document.getElementById("destino_direccion");

        if (!origenNombre.textContent || !origenId.value || !origenDireccion.textContent){
            origenNombre.textContent = heladera.nombre;
            origenDireccion.textContent = heladera.nombreCalle + " " + heladera.altura;
            origenId.value = heladera.id;
        } else {
            destinoNombre.textContent = heladera.nombre;
            destinoDireccion.textContent = heladera.nombreCalle + " " + heladera.altura;
            destinoId.value = heladera.id;
        }
    })

}

function vaciarCampos() {
    let origenNombre = document.getElementById("origen_nombre");
    let origenId = document.getElementById("origen_id");
    let origenDireccion = document.getElementById("origen_direccion");

    let destinoNombre = document.getElementById("destino_nombre");
    let destinoId = document.getElementById("destino_id");
    let destinoDireccion = document.getElementById("destino_direccion");


    origenNombre.textContent = "";
    origenDireccion.textContent = "";
    origenId.textContent = "";
    destinoNombre.textContent = "";
    destinoDireccion.textContent = "";
    destinoId.textContent = "";
}

// Request al Back para traer todas las heladeras
function obtenerHeladeras() {
    return fetch("http://localhost:80/heladeras")
        .then(response => {
            if (!response.ok) {
                throw new Error("No se pudieron obtener las heladeras: " + response.statusText)
            }
            return response.json()
        });
}
