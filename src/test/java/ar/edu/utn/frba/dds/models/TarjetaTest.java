package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.Retiro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Documento;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.Tarjeta;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.documentacion.TipoDocumento;
import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarjetaTest {
  List<Retiro> retiros = new ArrayList<>();
  List<PersonaVulnerable> hijos = new ArrayList<>();
  PersonaVulnerable personaVulnerable;
  Tarjeta tarjeta;

  @BeforeEach
  public void init(){
    hijos.add(
        new PersonaVulnerable(
          "pedro",
          LocalDate.of(2016, 5, 23),
          LocalDate.of(2020, 7, 20),
          new Ubicacion("Calle",123),
          null,
          new Documento(TipoDocumento.DNI, "47221334")
        )
    );

    personaVulnerable = new PersonaVulnerable(
        "rodrigo",
        LocalDate.of(1995, 3, 11),
        LocalDate.of(2020, 7, 20),
        new Ubicacion("Calle",123),
        hijos,
        new Documento(TipoDocumento.DNI, "26231329")
    );

    tarjeta = new Tarjeta("123456789", personaVulnerable);

    Retiro retiro = new Retiro(null, tarjeta);

    retiros.add(retiro);
    retiros.add(retiro);
    retiros.add(retiro);
  }

  @Test @DisplayName("Se intenta retirar vianda con tarjeta con limite de retiros alcanzado")
  public void AlcanzoElLimiteNoPuedeRetirar (){
    Retiro retiro = new Retiro(null, tarjeta);
    retiros.add(retiro);
    retiros.add(retiro);
    retiros.add(retiro);

    Assertions.assertFalse(tarjeta.puedeRetirar(retiros));
  }

  @Test @DisplayName("Se intenta retirar vianda con tarjeta con retiros todavia disponibles")
  public void NoAlcanzoElLimitePuedeRetirar (){
    Assertions.assertTrue(tarjeta.puedeRetirar(retiros));
  }

  @Test @DisplayName("Se verifica el limite de usos para una persona con un hijo")
  public void limiteDeUsoConUnHijo(){
    Assertions.assertEquals(5,tarjeta.calcularLimiteDeUso());
  }

}
