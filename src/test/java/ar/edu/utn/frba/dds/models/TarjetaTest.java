package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.Retiro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TarjetaTest {
  List<LocalDate> fechasRetiros = new ArrayList<>();
  List<PersonaVulnerable> hijos = new ArrayList<>();
  PersonaVulnerable personaVulnerable;
  Tarjeta tarjeta;
  Retiro retiro;



  @BeforeEach
  public void init(){
    hijos.add(new PersonaVulnerable(
        "pedro",
        LocalDate.of(2016, 5, 23),
        LocalDate.of(2020, 7, 20),
        new Ubicacion("Calle",123),
        null,
        new Documento(TipoDocumento.DNI, "47221334")));
    personaVulnerable = new PersonaVulnerable(
        "rodrigo",
        LocalDate.of(1995, 3, 11),
        LocalDate.of(2020, 7, 20),
        new Ubicacion("Calle",123),
        hijos,
        new Documento(TipoDocumento.DNI, "26231329"));
    tarjeta = new Tarjeta("123456789", personaVulnerable, fechasRetiros);
    retiro = new Retiro(null, tarjeta);
  }

  @Test @DisplayName("Se intenta retirar vianda con tarjeta con limite de retiros alcanzado")
  public void AlcanzoElLimiteNoPuedeRetirar (){
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    Assertions.assertFalse(tarjeta.puedeRetirar());
  }

  @Test @DisplayName("Se intenta retirar vianda con tarjeta con retiros todavia disponibles")
  public void NoAlcanzoElLimitePuedeRetirar (){
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    tarjeta.agregarRetiro(retiro);
    Assertions.assertTrue(tarjeta.puedeRetirar());
  }

  @Test @DisplayName("Se verifica el limite de usos para una persona con un hijo")
  public void limiteDeUsoConUnHijo(){
        Assertions.assertEquals(5,tarjeta.calcularLimiteDeUso());
  }

  @Test @DisplayName("Se retiro una vianda de la heladera, instanciando el retiro y agregando la fecha a la tarjeta")
  public void agregarUnaNuevaFechaDeRetiro(){
      tarjeta.agregarRetiro(retiro);
      Assertions.assertTrue(tarjeta.getFechasRetiros().stream().anyMatch(fecha -> retiro.getFechaRetiro().equals(fecha)));
  }
}
