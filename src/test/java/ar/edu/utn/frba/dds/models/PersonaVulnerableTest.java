package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.simeal.models.entities.personas.personaVulnerable.PersonaVulnerable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaVulnerableTest {
  PersonaVulnerable personaVulnerable;
  PersonaVulnerable hijoMenor1;
  PersonaVulnerable hijoMenor2;
  PersonaVulnerable hijoMayor;
  List<PersonaVulnerable> hijos;

  @BeforeEach
  public void init(){
    hijos = new ArrayList<>();
    hijoMenor1 = new PersonaVulnerable(null, LocalDate.of(2010,3,21), null,null, null, null);
    hijoMenor2 = new PersonaVulnerable(null, LocalDate.of(2019,3,21), null,null, null, null);
    hijoMayor = new PersonaVulnerable(null, LocalDate.of(2000,3,21), null,null, null, null);
    hijos.add(hijoMenor1);
    hijos.add(hijoMenor2);
    hijos.add(hijoMayor);
  }

  @Test @DisplayName("Cantidad de hijos menores de una persona vulnerable")
  public void cantidadHijosMenoresTest(){
    personaVulnerable = new PersonaVulnerable(null, LocalDate.of(1990,3,21), null,null, hijos, null);
    Assertions.assertEquals(2,personaVulnerable.cantHijosMenores());
  }
}
