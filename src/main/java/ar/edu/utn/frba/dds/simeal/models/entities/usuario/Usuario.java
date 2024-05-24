package ar.edu.utn.frba.dds.simeal.models.entities.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
  String username;
  String password;
  List<Rol> roles;
}
