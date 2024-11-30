package ar.edu.utn.frba.dds.simeal.service.cronjobs;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Alerta;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.incidentes.Incidente;
import ar.edu.utn.frba.dds.simeal.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.reporte.Reporte;

import java.util.ArrayList;
import java.util.List;

// Crontab para ejecutar una vez por semana, todos los lunes a medianoche.
// Este cronjob genera un reporte basado en datos de diferentes repositorios.
// 0 0 * * 1 "java -jar /path/to/GenerarReporteCronjob.jar"

public class GenerarReporte {

  public static void main(String[] args) {
    Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
    List<Heladera> heladeras = (List<Heladera>) repositorio.obtenerTodos(Heladera.class);
    List<FallaTecnica> fallaTecnicas = (List<FallaTecnica>) repositorio.obtenerTodos(FallaTecnica.class);
    List<Alerta> alertas = (List<Alerta>) repositorio.obtenerTodos(Alerta.class);
    List<Vianda> viandas = (List<Vianda>) repositorio.obtenerTodos(Vianda.class);
    List<DistribuirVianda> distribuciones = (List<DistribuirVianda>) repositorio.obtenerTodos(DistribuirVianda.class);

    List<Incidente> incidentes = new ArrayList<>();
    incidentes.addAll(alertas);
    incidentes.addAll(fallaTecnicas);

    Reporte reporte = new Reporte(heladeras, incidentes, viandas, distribuciones);
    reporte.generarReporte();
    Logger.info("Reporte generado exitosamente");
  }
}
