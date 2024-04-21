// Cuando el html termina de cargar ejecuta cargarMapa()
$(document).ready(function() {
    cargarMapa();
});


// La L es un objeto de leaflet 
function cargarMapa(){
	var map;
    const puntoCentral ={
		lat : -34.656032172926906, 
		lng : -58.426227983358935,
	}
  		
    // Inicializar el mapa y establecer su punto central y el nivel de zoom
    map = L.map('mapid').setView([puntoCentral.lat,puntoCentral.lng], 13);
    
    // Cargar mapa
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Grupo 11 de Diseño'
    }).addTo(map);

    map.doubleClickZoom.disable();

    // Añadir puntos en el mapa
    agregarHeladera(-34.598516864145495, -58.420117003006,"HELADERA MEDRANO",map);
    agregarHeladera(-34.659069603084916, -58.467244833730525, "HELADERA CAMPUS",map);

    // Añadir heladeras al mapa haciendo doble click
    // Salta un cuadro de dialogo para que el usuario ingrese el nombre de la heladera
    map.on('dblclick', function(event) {
        var nombreHeladera = prompt("Ingrese el nombre de la heladera", "Heladera1")
        if(nombreHeladera != null){
            var punto = event.latlng;
            agregarHeladera(punto.lat, punto.lng, nombreHeladera,map);
        }
    });
    
}

function agregarHeladera(latitud,longitud,nombreHeladera,map){
    var marker = L.marker([latitud, longitud]).addTo(map);
    marker.bindPopup("<b>"+nombreHeladera+"</b>").openPopup();
    
}

