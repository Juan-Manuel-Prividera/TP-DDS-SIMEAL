package ar.edu.utn.frba.dds.simeal.models.entities.usuario;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rol {
  String rol;
  List<Permisos> permisos;
}
