package ar.edu.utn.frba.dds.simeal.service.enviarDatosColaboradores;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.AdherirHeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboradorRespository;
import ar.edu.utn.frba.dds.simeal.models.repositories.DonacionViandaRepository;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import io.javalin.Javalin;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

public class EnviarDatosColabs {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("simeal/colaboradores", ctx -> {
            List<Colaborador> colaboradores = ColaboradorRespository.getInstance().getAllColaboradores();
            List<DonarVianda> donacionesViandas = DonacionViandaRepository.getInstance().getAllDonaciones();

            ctx.json(prepararRespuesta(colaboradores,donacionesViandas));
        });
    }


    private static List<ColaboradorEnviado> prepararRespuesta(List<Colaborador> colaboradores, List<DonarVianda> donacionesViandas) {
        List<ColaboradorEnviado> colaboradoresEnviados = new ArrayList<>();
        for (Colaborador colaborador : colaboradores) {
            int cantDonaciones = 0;
            for (DonarVianda donacion : donacionesViandas) {
                if (colaborador.getDocumento().getNroDocumento().equals(
                  donacion.getColaborador().getDocumento().getNroDocumento()))
                    cantDonaciones ++;
            }

            List<AdherirHeladera> colabsExtra = AdherirHeladeraRepository.getInstance().getPorColaborador(colaborador.id);
            double puntos = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador,colabsExtra);

            ColaboradorEnviado colabEnviar = new ColaboradorEnviado(colaborador, cantDonaciones, puntos);
            colaboradoresEnviados.add(colabEnviar);
        }
        return colaboradoresEnviados;
    }
}
