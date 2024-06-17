package ar.edu.utn.frba.dds.models;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones.AdministradorDeEventos;
import ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones.QuedanPocasViandas;
import ar.edu.utn.frba.dds.simeal.models.entities.Suscripciones.Suscriptor;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuscripcionPorPocasViandasTest {
  QuedanPocasViandas suscripcion;
  List<Vianda> viandas;
  Vianda vianda1;
  Vianda vianda2;
  Vianda vianda3;
  Heladera heladera;
  List<Suscriptor> suscriptores;
  Suscriptor suscriptor1;
  Suscriptor suscriptor2;
  Suscriptor suscriptor3;

  @BeforeEach
  public void setUp() {
    heladera = new Heladera();
    vianda1 = new Vianda(heladera);
    vianda2 = new Vianda(heladera);
    vianda3 = new Vianda(heladera);

    viandas = new ArrayList<>();
    viandas.add(vianda2);
    viandas.add(vianda3);

    suscriptor1 = new Colaborador(1);
    suscriptor2 = new Colaborador(1);
    suscriptor3 = new Colaborador(4);

    suscriptores = new ArrayList<>();
    suscriptores.add(suscriptor1);
    suscriptores.add(suscriptor2);
    suscriptores.add(suscriptor3);

    suscripcion = new QuedanPocasViandas(heladera, suscriptores);
  }

  @Test
  public void seEnviaLaNotificacion() {
    ViandaRepository viandaRepository = mock(ViandaRepository.class);
    when(viandaRepository.buscarPorHeladera(heladera)).thenReturn(viandas);

    AdministradorDeEventos admin = new AdministradorDeEventos();
    admin.setSuscripcion(suscripcion);
    admin.setViandaRepository(viandaRepository);

    vianda1.agregarOyente(admin);


    vianda1.retirar();
  }


}
