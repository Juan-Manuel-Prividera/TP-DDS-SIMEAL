package ar.edu.utn.frba.dds.db.repositories;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColaboracionRepositoryTest implements SimplePersistenceTest {
  ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator.getRepository(TipoRepo.COLABORACION);
  Repositorio repositorio = ServiceLocator.getRepository(TipoRepo.COLABORADOR);

  @Test @DisplayName("Se encuentran colaboraciones por el colaborador que las hizo")
  public void buscarColaboracionPorIdTest() {
    Colaborador colaborador1 = new Colaborador();
    Colaborador colaborador2 = new Colaborador();
    repositorio.persist(colaborador1);
    repositorio.persist(colaborador2);

    DonarVianda donarVianda1 = new DonarVianda();
    donarVianda1.setColaborador(colaborador1);
    DonarVianda donarVianda2 = new DonarVianda();
    donarVianda2.setColaborador(colaborador1);
    colaboracionRepository.persist(donarVianda1);
    colaboracionRepository.persist(donarVianda2);

    List<DonarVianda> donaciones = (List<DonarVianda>) colaboracionRepository.getPorColaborador(colaborador1.getId(),DonarVianda.class);

    assertEquals(colaborador1.getId(), donaciones.get(0).getColaborador().getId());
  }

}
