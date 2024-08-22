package ar.edu.utn.frba.dds.simeal.service.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
  private String username;
  private String password;
  private List<Rol> roles;
}
