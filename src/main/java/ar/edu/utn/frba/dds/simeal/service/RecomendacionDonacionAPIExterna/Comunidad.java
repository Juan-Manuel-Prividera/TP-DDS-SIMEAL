package ar.edu.utn.frba.dds.simeal.service.RecomendacionDonacionAPIExterna;

import lombok.Getter;

@Getter
public class Comunidad {
    Long id;
    String nombre;
    Double lat;
    Double lon;
    Double distanciaEnKM;
}
