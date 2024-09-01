package ar.edu.utn.frba.dds.simeal.service.enviarDatosColaboradores;

import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.service.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
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

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.post("simeal/auth", ctx -> {
            String apikey = ctx.queryParam("apikey");
            String clientID = ctx.queryParam("clientID");
             if (apikey.equals(miApiKey)) {
                String token = generateToken(clientID);
                ctx.json(Map.of("token", token));
             } else
                ctx.status(400).result("Usuario no autorizado");

        });

        app.get("simeal/colaboradores", ctx -> {
            String token = ctx.header("Authorization");
            if (token != null && validateToken(token.replace("Bearer ", ""))) {
                List<Colaborador> colaboradores = ServiceLocator.getRepository("colaboradores").obtenerTodos();
                List<DonarVianda> donacionesViandas = ServiceLocator.getRepository("donacion_vianda").obtenerTodos();

                ctx.json(prepararRespuesta(colaboradores,donacionesViandas));     
            } else 
                ctx.status(400).result("Usuario no autorizado");
           
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
                if (colaborador.equals(donacion.getColaborador()) &&
                  donacion.getFechaDeRealizacion().getMonth().equals(LocalDate.now().getMonth()))
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
