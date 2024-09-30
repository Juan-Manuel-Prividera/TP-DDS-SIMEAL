package ar.edu.utn.frba.dds.db.repositories;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.simeal.models.entities.suscripciones.notificacion.HayMuchasViandas;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SuscripcionesRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuscripcionRepositoryTest implements SimplePersistenceTest {
  SuscripcionesRepository suscripcionesRepository = (SuscripcionesRepository) ServiceLocator.getRepository(SuscripcionesRepository.class);
  Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);

  @Test @DisplayName("Se ecuentra una suscrpcion por la heladera que tenga asginada")
  public void buscarPorHeladeraTest() {
    Heladera heladera = new Heladera();
    Colaborador colaborador = new Colaborador();
    Suscripcion suscripcion = new Suscripcion(heladera, colaborador, 2,new HayMuchasViandas());

    repositorio.guardar(heladera);
    repositorio.guardar(colaborador);
    suscripcionesRepository.guardar(suscripcion);

    List<Suscripcion> suscripciones = suscripcionesRepository.buscarPor(heladera);
    assertEquals(suscripcion.getId(), suscripciones.get(0).getId());
  }
}
