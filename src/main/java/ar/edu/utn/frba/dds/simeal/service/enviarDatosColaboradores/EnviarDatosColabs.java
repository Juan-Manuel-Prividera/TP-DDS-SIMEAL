package ar.edu.utn.frba.dds.simeal.service.enviarDatosColaboradores;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.AdherirHeladeraRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboradorRespository;
import ar.edu.utn.frba.dds.simeal.models.repositories.DonacionViandaRepository;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
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
            List<Colaborador> colaboradores = ServiceLocator.getRepository("colaboradores").obtenerTodos();
            List<DonarVianda> donacionesViandas = ServiceLocator.getRepository("donacion_vianda").obtenerTodos()

            ctx.json(prepararRespuesta(colaboradores,donacionesViandas));
        });
    }


    private static List<ColaboradorEnviado> prepararRespuesta(List<Colaborador> colaboradores, List<DonarVianda> donacionesViandas) {
        List<ColaboradorEnviado> colaboradoresEnviados = new ArrayList<>();
        for (Colaborador colaborador : colaboradores) {
            int cantDonaciones = 0;
            for (DonarVianda donacion : donacionesViandas) {
                if (colaborador.equals(donacion.getColaborador()))
                    cantDonaciones ++;
            }

            List<AdherirHeladera> colabsExtra = ServiceLocator.getRepository("adherir_heladera").obtenerTodos();
            double puntos = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador,colabsExtra);

            ColaboradorEnviado colabEnviar = new ColaboradorEnviado(colaborador, cantDonaciones, puntos);
            colaboradoresEnviados.add(colabEnviar);
        }
        return colaboradoresEnviados;
    }
}
