package ar.edu.utn.frba.dds.simeal.models.entities.usuario;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
  String username;
  String password;
  List<Rol> roles;
}
