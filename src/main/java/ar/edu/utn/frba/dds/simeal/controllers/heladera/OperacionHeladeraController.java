package ar.edu.utn.frba.dds.simeal.controllers.heladera;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.distribuirvianda.DistribuirVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.OperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.SolicitudOperacionHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.heladera.operacionHeladera.TipoOperacion;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.TarjetaColaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.repositories.SolicitudOperacionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.simeal.utils.DDMetricsUtils;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OperacionHeladeraController {
  private final Repositorio repositorio;
  private final SolicitudOperacionRepository solicitudOperacionRepository;
  private final ColaboracionRepository colaboracionRepository;

  public OperacionHeladeraController(Repositorio repositorio, SolicitudOperacionRepository solicitudOperacionRepository, ColaboracionRepository colaboracionRepository) {
    this.repositorio = repositorio;
    this.solicitudOperacionRepository = solicitudOperacionRepository;
    this.colaboracionRepository = colaboracionRepository;
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

      TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) repositorio
        .buscarPorId(tarjetaColabId, TarjetaColaborador.class);


      chequarDonacionPendiente(tarjetaColaborador.getColaborador(), solicitud.getTipoOperacion());
      chequarDistribucionPendiente(tarjetaColaborador.getColaborador(),solicitud.getHeladera(),solicitud.getTipoOperacion());

      repositorio.guardar(operacion);

      Logger.debug("Operacion persistida");
    } else {
      Logger.error("No se pudo persistir la operacion");
    }

    DDMetricsUtils.getInstance().getAccesoHeladera().incrementAndGet();
  }

  private void chequarDonacionPendiente(Colaborador colab, TipoOperacion tipoOperacion) {
    if (!tipoOperacion.equals(TipoOperacion.INGRESO))
      return;

    List<DonarVianda> colaboraciones = (List<DonarVianda>) colaboracionRepository
      .getPorColaborador(colab.getId(), DonarVianda.class);

    for (DonarVianda donarVianda : colaboraciones) {
      if (!donarVianda.getVianda().getEntregada()) {
        donarVianda.getVianda().setEntregada(true);
        repositorio.actualizar(donarVianda);
        break;
      }
    }
  }

  private void chequarDistribucionPendiente(Colaborador colab, Heladera heladera, TipoOperacion tipoOperacion) {
    List<DistribuirVianda> colaboraciones = (List<DistribuirVianda>) colaboracionRepository
      .getPorColaborador(colab.getId(), DistribuirVianda.class);
    for (DistribuirVianda distribuirVianda : colaboraciones) {
      if (!distribuirVianda.isRealizada()) {
        if (tipoOperacion.equals(TipoOperacion.INGRESO) && distribuirVianda.getDestino().getId().equals(heladera.getId()) ||
          (tipoOperacion.equals(TipoOperacion.RETIRO) && distribuirVianda.getOrigen().getId().equals(heladera.getId()))) {
          distribuirVianda.setRealizada(true);
          repositorio.actualizar(distribuirVianda);
          break;
        }
      }
    }

  }
}