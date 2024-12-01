let map;

// Cuando el html termina de cargar ejecuta cargarMapa()
$(document).ready(function() {
    cargarMapa();
    buscarHeladeraMapa()
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
    if (!heladera.estaActiva === true) {
        heladeraIcon = L.icon({
            iconUrl: '/img/heladeraTriste.png',
            iconSize: [20, 40]
        });
        marker = L.marker([heladera.latitud, heladera.longitud], {icon: heladeraIcon}).addTo(map);
        marker.bindPopup("<b>"+heladera.nombre+"</b>").openPopup();

    } else if (heladera.esElEncargado === "true") {
        console.log("En el if esElEncargado= "+ heladera.esElEncargado)
        heladeraIcon = L.icon({
            iconUrl: '/img/heladeraVioleta.png',
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
        document.getElementById("nombre_heladera").value = heladera.nombre
        if (document.getElementById("id_heladera")) {
            document.getElementById("id_heladera").value = heladera.id
        }
        document.getElementById("ubicacion_heladera").value = heladera.nombreCalle + " " + heladera.altura
    })

}

// Request al Back para traer todas las heladeras
function obtenerHeladeras() {
    return fetch(`${window.origin}:80/heladeras`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No se pudieron obtener las heladeras: " + response.statusText)
            }
            return response.json()
        });

}
// Cuando se selecciona una heladera por el formulario la centramos en el mapa
function buscarHeladeraMapa() {
    document.getElementById("inputHeladera").addEventListener('click', function () {
        // Leemos los inputs del formulario
        const nombreHeladera = document.getElementById("nombre_heladera").value;
        const ubicacionHeladera = document.getElementById("ubicacion_heladera").value;
        obtenerHeladeras().then(heladeras => {
            var encontrada = false
            heladeras.forEach(heladera => {
                // Nos fijamos que coincidan nombre y ubicación de la heladera
                // No es una identificacion univoca pero son los datos que tenemos y seria raro mismo nombre y misma ubi
                if (heladera.nombre == nombreHeladera && (heladera.nombreCalle + " " + heladera.altura) == ubicacionHeladera) {
                    encontrada = true
                    map.flyTo([heladera.latitud, heladera.longitud], 13);
                    setTimeout(() => {
                        // Hacemos la request que nos lleva a ver el estado de esa heladera
                        // Si no entra al if la heladera que pidio no existe asi que no hacemos la request
                        fetch(`${window.origin}:80/heladera/` + heladera.id)
                            .then(response => {
                                console.log("ejecuto la request: /heladera/" + heladera.id)
                                console.log(response.statusText)
                                if (!response.ok)
                                    throw new Error("Error al hacer get sobre heladera seleccionada")

                                window.location.href = `${window.origin}:80/heladera/` + heladera.id;
                            })
                            .catch(error => {
                                console.error(error)
                            })
                    }, 1)
                }
            })
            if (!encontrada) {
                alert("No existe heladera con ese nombre y ubicación")
            }
        })

    })
}
