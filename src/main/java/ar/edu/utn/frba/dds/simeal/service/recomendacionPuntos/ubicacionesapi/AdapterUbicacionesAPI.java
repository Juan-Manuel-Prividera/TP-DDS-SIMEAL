package ar.edu.utn.frba.dds.simeal.service.recomendacionPuntos.ubicacionesapi;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.PuntosRecomendados;

public interface AdapterUbicacionesAPI {
    // Esta seria la interfaz que conoce nuestro sistema
    PuntosRecomendados getUbicaciones(double latitud, double longitud, double radio);
}
