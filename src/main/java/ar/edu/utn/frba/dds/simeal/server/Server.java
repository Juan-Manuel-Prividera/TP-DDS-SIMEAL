package ar.edu.utn.frba.dds.simeal.server;

import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.middleware.AuthenticatedMiddleware;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.models.usuario.Usuario;
import ar.edu.utn.frba.dds.simeal.server.handlers.AppHandlers;
import ar.edu.utn.frba.dds.simeal.utils.ConfigReader;
import ar.edu.utn.frba.dds.simeal.utils.Initializer;
import ar.edu.utn.frba.dds.simeal.utils.JavalinRenderer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.util.function.Consumer;

public class Server {
  private static Javalin app = null;
  private static ConfigReader configReader = new ConfigReader();

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

      // Apply our custom interruption handlers
      AppHandlers.applyHandlers(app);
      Router.init(app);

      if (Boolean.parseBoolean(configReader.getProperty("dev_mode"))) {
        Initializer.init();
      }
    }
  }

  private static Consumer<JavalinConfig> config() {
    return config -> {
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "/static";
      });

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
