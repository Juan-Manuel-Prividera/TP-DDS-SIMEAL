package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.OperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SolicitudOperacionRepository;
import ar.edu.utn.frba.dds.simeal.utils.DDMetricsUtils;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OperacionHeladeraController {
  private final Repositorio repositorio;
  private final SolicitudOperacionRepository solicitudOperacionRepository;

  public OperacionHeladeraController(Repositorio repositorio, SolicitudOperacionRepository solicitudOperacionRepository) {
    this.repositorio = repositorio;
    this.solicitudOperacionRepository = solicitudOperacionRepository;
  }

  public void create(Long heladeraId, TipoOperacion tipoOperacion, Long tarjetaColabId) {
    List<SolicitudOperacionHeladera> solicitudes = solicitudOperacionRepository.getPorTarjetaColaborador(tarjetaColabId);
    SolicitudOperacionHeladera solicitud = null;

    for (SolicitudOperacionHeladera s : solicitudes) {
      if (s.getActivo() && s.getTipoOperacion().equals(tipoOperacion)) {
        solicitud = s;
        break;
      }
    }

    if (solicitud != null) {
      solicitudOperacionRepository.desactivar(solicitud);
      OperacionHeladera operacion = new OperacionHeladera(solicitud, LocalDateTime.now());
      repositorio.guardar(operacion);
      Logger.debug("Operacion persistida");
    } else {
      Logger.error("No se pudo persistir la operacion");
    }

    DDMetricsUtils.getInstance().getAccesoHeladera().incrementAndGet();
  }
}
