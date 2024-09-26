package ar.edu.utn.frba.dds.simeal.service.enviarDatosColaboradores;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.TipoRepo;
import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import ar.edu.utn.frba.dds.simeal.utils.logger.LoggerType;
import io.javalin.Javalin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.util.*;

public class EnviarDatosColabs {
    private static final ConfigReader configReader = new ConfigReader();
    private static final String miApiKey = configReader.getProperty("api.key");
    private static final Logger logger = Logger.getInstance("envioDatos.log");

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("simeal/auth", ctx -> {
            logger.log(LoggerType.DEBUG,"Solicitud de autenticacion recibida: " + ctx.userAgent());
            String apikey = ctx.queryParam("apikey");
            String clientID = ctx.queryParam("clientID");

            if (apikey.equals(miApiKey)) {
                String token = generateToken(clientID);
                ctx.json(Map.of("token", token));
                logger.log(LoggerType.DEBUG, "Usuario autenticado, se envia el token: " + token);
            } else {
                logger.log(LoggerType.DEBUG, "Solicitud recibida de usuario no autorizado");
                ctx.status(400).result("Usuario no autorizado");
            }
        });

        app.get("simeal/colaboradores", ctx -> {
            String token = ctx.header("Authorization");
            logger.log(LoggerType.DEBUG, "Solicitud de colaboradores recibida: " + ctx.headerMap() + " " + ctx.header("Authorization"));

            if (token != null && validateToken(token.replace("Bearer ", ""))) {
                logger.log(LoggerType.DEBUG, "Usuario validado correctamente");

                List<Colaborador> colaboradores = obtenerColaboradores();
                List<DonarVianda> donacionesViandas = obtenerDonaciones();

                if (colaboradores.isEmpty() || donacionesViandas.isEmpty()) {
                    logger.log(LoggerType.ERROR, "No hay colaboradores ni donaciones dispoibles para enviar");
                    throw new RuntimeException("No hay colaboradores ni donaciones dispoibles");
                } else
                    logger.log(LoggerType.DEBUG, "Se obtuvieron correctamente colaboradores y donaciones");

                List<ColaboradorEnviado> response = prepararRespuesta(colaboradores, donacionesViandas);
                ctx.json(response);
                logger.log(LoggerType.DEBUG, "Respuesta enviada correctamente: " + response);
            } else {
                logger.log(LoggerType.DEBUG, "Usuario no autenticado se retorna un 400, token recibido: " + token);
                ctx.status(400).result("Usuario no autorizado");
            }
        });
    }

    private static boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
              .setSigningKey(miApiKey)
              .build()
              .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Genera un Token JWT
    private static String generateToken(String clientID) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("clienteID", clientID);
        return Jwts.builder()
          .setClaims(claims)
          .setSubject(clientID)
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
          .signWith(SignatureAlgorithm.HS256, miApiKey)
          .compact();
    }


    private static List<ColaboradorEnviado> prepararRespuesta(List<Colaborador> colaboradores, List<DonarVianda> donacionesViandas) {
        List<ColaboradorEnviado> colaboradoresEnviados = new ArrayList<>();
        for (Colaborador colaborador : colaboradores) {
            int cantDonaciones = 0;
            for (DonarVianda donacion : donacionesViandas) {
                // Solo cuentan las donaciones del mes actual
                if (colaborador.getId() == donacion.getColaborador().getId() &&
                  donacion.getFechaDeRealizacion().getMonth() == LocalDate.now().getMonth())
                    cantDonaciones++;
            }
            logger.log(LoggerType.DEBUG, "El colaborador: " + colaborador.getId() + " tiene: " + cantDonaciones +" donaciones de vianda");
            List<AdherirHeladera> colabsExtra = obtenerColbasExtra(colaborador);
            double puntos = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador,colabsExtra);

            ColaboradorEnviado colabEnviar = new ColaboradorEnviado(colaborador, cantDonaciones, puntos);
            colaboradoresEnviados.add(colabEnviar);
        }
        return colaboradoresEnviados;
    }

    private static List<Colaborador> obtenerColaboradores() {
       return (List<Colaborador>) ServiceLocator
         .getRepository(TipoRepo.COLABORADOR).obtenerTodos(Colaborador.class);
    }
    private static List<DonarVianda> obtenerDonaciones() {
        return  (List<DonarVianda>) ServiceLocator
          .getRepository(TipoRepo.COLABORACION).obtenerTodos(DonarVianda.class);
    }

    private static List<AdherirHeladera> obtenerColbasExtra(Colaborador colaborador) {
        ColaboracionRepository colaboracionRepository = (ColaboracionRepository) ServiceLocator.getRepository(TipoRepo.COLABORACION);
        return (List<AdherirHeladera>) colaboracionRepository
          .getPorColaborador(colaborador.getId(), AdherirHeladera.class);
    }

}
