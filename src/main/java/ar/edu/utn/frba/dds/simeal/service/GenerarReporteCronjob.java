package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.simeal.models.repositories.DistribucionDeViandasRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;

public class GenerarReporteCronjob {

  public static void main(String[] args) {
    HeladeraRepository heladeraRepository = new HeladeraRepository();
    ViandaRepository viandaRepository = new ViandaRepository();
    DistribucionDeViandasRepository distribucionDeViandasRepository = new DistribucionDeViandasRepository();
    // agregar get instance a los repositorios x ser singletons
    Reporte reporte = new Reporte(heladeraRepository, viandaRepository, distribucionDeViandasRepository);
    reporte.generarReporte();

    System.out.println("Reporte generado exitosamente");
  }
}
