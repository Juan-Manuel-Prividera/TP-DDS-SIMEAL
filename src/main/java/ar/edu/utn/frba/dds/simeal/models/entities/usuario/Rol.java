package ar.edu.utn.frba.dds.simeal.models.entities.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Rol {
  private String rol;
  private List<Permisos> permisos;
}
