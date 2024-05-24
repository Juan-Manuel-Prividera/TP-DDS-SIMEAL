package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;
import ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi.AdapterUbicacionesAPI;

public class CalculadorPuntosRecomendados {
    AdapterUbicacionesAPI adapterUbicacionesAPI;

    public CalculadorPuntosRecomendados(AdapterUbicacionesAPI adapterUbicacionesAPI) {
        this.adapterUbicacionesAPI = adapterUbicacionesAPI;
    }
    public PuntosRecomendados getPuntosRecomendados(double lat, double lon, double radio){
        return adapterUbicacionesAPI.getUbicaciones(lat,lon,radio);
    }
}
