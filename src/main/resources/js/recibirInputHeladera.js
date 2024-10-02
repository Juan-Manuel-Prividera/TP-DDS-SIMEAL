import {buscarDireccion} from 'js/mapa.js'

function recibirInputHeladera(){
    const btn = document.getElementById('inputHeladera')
    btn.addEventListener('click',function(){
        const nombreHeladera = document.getElementById("nombreHeladera");
        const ubicacionHeladera = document.getElementById("ubicacionHeladera");

        buscarDireccion(nombreHeladera);
    })


}