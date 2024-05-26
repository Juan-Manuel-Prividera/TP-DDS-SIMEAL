package ar.edu.utn.frba.dds.simeal.models.entities.ubicacion;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PuntosRecomendados {
  List<Ubicacion> locations;
}
