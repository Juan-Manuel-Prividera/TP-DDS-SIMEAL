package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.middleware.AuthenticatedMiddleware;
import ar.edu.utn.frba.dds.simeal.middleware.AuthorizedMiddleware;
import ar.edu.utn.frba.dds.simeal.server.exception_handlers.AppHandlers;
import ar.edu.utn.frba.dds.simeal.utils.*;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import io.javalin.micrometer.MicrometerPlugin;

import java.io.IOException;
import java.rmi.MarshalException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Server {
  private static Javalin app = null;
  private static final ConfigReader configReader = new ConfigReader();

  public static Javalin app() {
    if (app == null)
      throw new RuntimeException("App no inicializada");
    return app;
  }

  public static void init() {
    if (app == null) {
      Integer port = Integer.parseInt(configReader.getProperty("server_port"));
      app = Javalin.create(config()).start(port);

      AuthenticatedMiddleware.apply(app);
      AuthorizedMiddleware.apply(app);



      // Apply our custom interruption handlers
      AppHandlers.applyHandlers(app);
      Router.init(app);

      if (Boolean.parseBoolean(configReader.getProperty("dev_mode"))) {
        Initializer.init();
      }
    }
    Logger.info("*** Iniciando aplicacion ***");
  }

  private static Consumer<JavalinConfig> config() {
    return config -> {
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "/static";
      });

      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "src/main/resources/dinamic";
        staticFiles.location = Location.EXTERNAL;
      });

      final var metricsUtils = DDMetricsUtils.getInstance();
      final var registry = metricsUtils.getRegistry();

      // Config
      final var micrometerPlugin = new MicrometerPlugin(cfg -> {cfg.registry = registry;});
      config.registerPlugin(micrometerPlugin);

      // Metricas
      final var acceso = registry.gauge("simeal.acceso", new AtomicInteger(0));
      final var dineroDonado = registry.gauge("simeal.dineroDonado", new AtomicInteger(0));
      final var accesoHeladera = registry.gauge("simeal.accesoHeladera", new AtomicInteger(0));
      final var viandasDonadas = registry.gauge("simeal.viandasDonadas", new AtomicInteger(0));
      final var incidenteOcurrido = registry.gauge("simeal.incidentes", new AtomicInteger(0));
      final var ofertasCanjeadas = registry.gauge("simeal.ofertasCanjeadas", new AtomicInteger(0));

      metricsUtils.setOfertasCanjeadas(ofertasCanjeadas);
      metricsUtils.setIncidentesReportados(incidenteOcurrido);
      metricsUtils.setViandasDonadas(viandasDonadas);
      metricsUtils.setAccesoHeladera(accesoHeladera);
      metricsUtils.setDineroDonado(dineroDonado);
      metricsUtils.setAcceso(acceso);

      config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
        Handlebars handlebars = new Handlebars();
        Template template = null;
        try {
          template = handlebars.compile(
            "templates/" + path.replace(".hbs", ""));
          return template.apply(model);
        } catch (IOException e) {
          e.printStackTrace();
          context.status(HttpStatus.NOT_FOUND);
          return "No se encuentra la página indicada...";
        }
      }));


    };
  }


}
