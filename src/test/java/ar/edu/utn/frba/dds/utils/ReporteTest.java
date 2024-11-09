package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.reporte.Reporte;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReporteTest {

  @Test
  public void generarReporte() {
    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<Incidente> incidentes = (List<Incidente>) repositorio.obtenerTodos(Incidente.class);
    List<Vianda> viandas = (List<Vianda>) repositorio.obtenerTodos(Vianda.class);
    List<DistribuirVianda> distribuirViandas = (List<DistribuirVianda>) repositorio.obtenerTodos(DistribuirVianda.class);
    Reporte reporte = new Reporte(heladeras,incidentes,viandas,distribuirViandas);
    reporte.generarReporte();
  }
}
