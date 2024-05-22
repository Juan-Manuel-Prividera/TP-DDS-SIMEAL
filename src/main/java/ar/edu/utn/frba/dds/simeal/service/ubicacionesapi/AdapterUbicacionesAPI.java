package ar.edu.utn.frba.dds.simeal.service.ubicacionesapi;

public interface AdapterUbicacionesAPI {
    PuntosRecomendados getUbicaciones(double latitud,double longitud, double radio);
}
