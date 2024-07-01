package ar.edu.utn.frba.dds.simeal.service;

import ar.edu.utn.frba.dds.simeal.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.simeal.models.repositories.DistribucionDeViandasRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ViandaRepository;

public class GenerarReporteCronjob {

  public static void main(String[] args) {

    Reporte reporte = new Reporte(HeladeraRepository.getInstance(), IncidenteRepository.getInstance(), ViandaRepository.getInstance(), DistribucionDeViandasRepository.getInstance());
    reporte.generarReporte();

    System.out.println("Reporte generado exitosamente");
  }
}
