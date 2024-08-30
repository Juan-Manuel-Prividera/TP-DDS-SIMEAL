package ar.edu.utn.frba.dds.simeal.service.cronjobs;

import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.reporte.Reporte;
import ar.edu.utn.frba.dds.simeal.models.repositories.DistribucionDeViandasRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;

import java.security.Provider;

// Crontab para ejecutar una vez por semana, todos los lunes a medianoche.
// Este cronjob genera un reporte basado en datos de diferentes repositorios.
// 0 0 * * 1 "java -jar /path/to/GenerarReporteCronjob.jar"

public class GenerarReporte {

  public static void main(String[] args) {

    Reporte reporte = new Reporte((HeladeraRepository) ServiceLocator.getRepository("heladera"),
      (IncidenteRepository) ServiceLocator.getRepository("incidente"),
      (ViandaRepository) ServiceLocator.getRepository("vianda"),
      (DistribucionDeViandasRepository) ServiceLocator.getRepository("distribucion_vianda"));
    reporte.generarReporte();

    System.out.println("Reporte generado exitosamente");
  }
}
