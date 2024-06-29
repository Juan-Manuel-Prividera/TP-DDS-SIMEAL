package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PuntosRecomendados {
  List<Coordenada> locations;
}
