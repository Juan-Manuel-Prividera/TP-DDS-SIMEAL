package ar.edu.utn.frba.dds.db.repositories;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViandaRepositoryTest implements SimplePersistenceTest {
    ViandaRepository viandaRepository = (ViandaRepository) ServiceLocator.getRepository(ViandaRepository.class);
    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);

    @Test @DisplayName("Se encuentra vianda por la heladera en la que este")
    public void buscarPorHeladera() {
      Heladera heladera = new Heladera();
      Vianda vianda = new Vianda(heladera);
      repositorio.guardar(heladera);
      viandaRepository.guardar(vianda);

      List<Vianda> viandaBuscada = viandaRepository.buscarPorHeladera(heladera);
      assertEquals(vianda.getId(), viandaBuscada.get(0).getId());
    }

}
