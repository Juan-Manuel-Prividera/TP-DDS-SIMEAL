// Cuando el html termina de cargar ejecuta cargarMapa()
let map;

class Heladera {
    constructor(id, nombre, latitud, longitud,activa,altura,nombreCalle) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.activa = activa;
        this.altura = altura;
        this.nombreCalle = nombreCalle;

    }
}

$(document).ready(function() {
    cargarMapa();
    buscarHeladeraMapa()
});


// La L es un objeto de leaflet
function cargarMapa(){
    const puntoCentral ={
		lat : -34.656032172926906, 
		lng : -58.426227983358935,
	}

  	var heladeraIcon = L.icon({
  	  iconUrl: '/img/heladera.png',
      iconSize: [20,40]
 	});


    // Inicializar el mapa y establecer su punto central y el nivel de zoom
    map = L.map('mapid').setView([puntoCentral.lat,puntoCentral.lng], 13);
    
    // Cargar mapa
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Grupo 11 Diseño de Sistemas'
    }).addTo(map);

    map.doubleClickZoom.disable();

    // Añadir puntos en el mapa
    obtenerHeladeras().then(heladeras => {
        console.log(heladeras)
            heladeras.forEach(heladera => {
                agregarHeladera(heladera,map,heladeraIcon)
            })
    })



   //   agregarHeladera(-34.598516864145495, -58.420117003006,"HELADERA MEDRANO",map,heladeraIcon);
  //  agregarHeladera(-34.659069603084916, -58.467244833730525, "HELADERA CAMPUS",map,heladeraIcon);

    // Añadir heladeras al mapa haciendo doble click
    // Salta un cuadro de dialogo para que el usuario ingrese el nombre de la heladera
    map.on('dblclick', function(event) {
        var nombreHeladera = prompt("Ingrese el nombre de la heladera", "Heladera1")
        if(nombreHeladera != null){
            var punto = event.latlng;
            agregarHeladera(punto.lat, punto.lng, nombreHeladera,map,heladeraIcon);
        }
    });

}

function agregarHeladera(heladera,map, heladeraIcon){
    var marker = L.marker([heladera.latitud, heladera.longitud], {icon: heladeraIcon}).addTo(map);
    marker.bindPopup("<b>"+heladera.nombre+"</b>").openPopup();
    marker.on('click', function(){
        document.getElementById("nombreHeladera").value = heladera.nombre
        document.getElementById("ubicacionHeladera").value = heladera.nombreCalle + " " + heladera.altura
    })
}

export function buscarDireccion(direccion){
    const geocoder = new L.Control.Geocoder.Nominatim();

    geocoder.geocode(direccion,function(result){
        if(result && result.length > 0){
            const latlng = result[0].center;

            map.setView(latlng,13)
        } else
            alert("Direccion no encontrada");
    });
}

function obtenerHeladeras() {
    return fetch("http://localhost:8080/heladeras")
        .then(response => {
            if (!response.ok) {
                throw new Error("No se pudieron obtener las heladeras: " + response.statusText)
            }
            return response.json()
        });

}

function buscarHeladeraMapa() {
    document.getElementById("inputHeladera").addEventListener('click', function () {
        const nombreHeladera = document.getElementById("nombreHeladera").value;
        const ubicacionHeladera = document.getElementById("ubicacionHeladera").value;
        obtenerHeladeras().then(heladeras => {
            heladeras.forEach(heladera => {
                if (heladera.nombre == nombreHeladera) {
                    map.flyTo([heladera.latitud, heladera.longitud], 13);
                    fetch("http://localhost:8080/heladera/" + heladera.id).then(response =>{
                        if (!response.ok) {
                            throw new Error("Error al hacer get sobre heladera seleccionada")
                        }
                    })
                }
            })
        })


    })

}