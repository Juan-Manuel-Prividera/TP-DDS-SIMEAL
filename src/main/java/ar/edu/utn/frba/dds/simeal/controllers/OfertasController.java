package ar.edu.utn.frba.dds.simeal.controllers;


import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Producto;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.OfertaRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import org.apache.maven.model.Model;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfertasController {
  //TODO: VER DE NO USAR ESTE MÉTODO PARA CONSEGUIR LOS REPOSITORY
  private OfertaRepository ofertaRepository = (OfertaRepository) ServiceLocator.getRepository(OfertaRepository.class);
  private Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);

  public void index(Context app){
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setOfertas(model,app);
    setUpperBox(model,app);

    app.render("ofertas/oferta_all.hbs", model);
  }

  public void show(Context app){
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setOfertas(model,app);
    setUpperBox(model,app);

    setOferta(model,app);

    model.put("directorio", "../");

    app.render("ofertas/oferta_selected.hbs", model);
  }

  public void selfOffers(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setUpperBox(model,app);
    //TODO: ASOCIAR LAS OFERTAS A UN USUARIO VÁLIDO
    //setSelfOffers(model,app);

    model.put("agregarOferta", "true");
    app.render("ofertas/oferta_self.hbs",model);
  }

  public void selfOffersSelected(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setUpperBox(model,app);
    //TODO: ASOCIAR LAS OFERTAS A UN USUARIO VÁLIDO
    //setSelfOffers(model,app);

    setOferta(model,app);

    app.render("ofertas/oferta_self_selected.hbs", model);
  }

  public void publicar(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    model.put("publicar_modificar", "Publicar");
    model.put("agregarOferta", "true");
    app.render("ofertas/oferta_publicar_modificar.hbs", model);
  }

  public void modificar(Context app){
    HashMap<String, Object> model = new HashMap<>();

    app.render("ofertas/oferta_publicar_modificar.hbs", model);
  }

// *********************************************
// ****************** HELPERS ******************
// *********************************************

  //Setea la barra de navegación
  private void setNavBar(HashMap<String, Object> model, Context ctx) {
    if(ctx.sessionAttribute("user_type").equals("HUMANO"))
      model.put("esHumano", "true");
    //TODO: Esta bien esto? O tambien puede ser admin?
    else
      model.put("esJuridico", "true");

    model.put("username", ctx.sessionAttribute("user_name"));
    model.put("Ofertas", "true");
    model.put("titulo", "Simeal - Lista de ofertas");
  }

  //Setea todas las ofertas de la bd
  private void setOfertas(HashMap<String, Object> model, Context ctx) {
    List<Oferta> ofertas = (List<Oferta>) ofertaRepository.obtenerTodos(Oferta.class);
    model.put("ofertas", ofertas);
  }

  //Setea la oferta que seleccionó el usuario
  private void setOferta(HashMap<String, Object> model, Context ctx) {
    //TODO: NO USAR PATHPARAMS
    Oferta oferta = (Oferta) ofertaRepository.buscarPorId(Long.valueOf(ctx.pathParam("oferta_id")), Oferta.class);
    model.put("oferta", oferta);
    Producto producto = (Producto) repositorio.buscarPorId(Long.valueOf(ctx.pathParam("producto_id")), Producto.class);
    model.put("producto", producto);
  }

  //Setea la parte superior de la pág ofertas
  private void setUpperBox(HashMap<String, Object> model, Context ctx) {
    setRubros(model, ctx);
    //Logger.debug("user_type = %s", ctx.sessionAttribute("user_type").toString());
    /*
    if(ctx.sessionAttribute("user_type").equals("HUMANO") && false){
      setSelfPuntos(model, ctx);
    }
     */
  }

  //Setea los puntos que tiene el usuario
  private void setSelfPuntos(HashMap<String, Object> model, Context ctx) {
    //TODO: MOVER ESTO AL LOGIN
    if(ctx.sessionAttribute("puntos_colab") == null){
      Colaborador colaborador = (Colaborador) repositorio.buscarPorId(Long.valueOf(ctx.sessionAttribute("colaborador_id")), Colaborador.class);
      double ptsColab = colaborador.getPuntosDeReconocimientoParcial() + ptsPorHeladeras(colaborador);
      ctx.sessionAttribute("puntos_colab", ptsColab);
      Logger.debug("Puntos del colaborador: " + ptsColab);
    }
    Logger.debug("Puntos del colaborador: " + ctx.sessionAttribute("puntos_colab"));
    model.put("puntosDisponibles", ctx.sessionAttribute("puntos_colab"));
  }
  private double ptsPorHeladeras(Colaborador colaborador){
    //TODO
    return 0;
  }

  //Setea los rubros almacenados en bd
  private void setRubros(HashMap<String, Object> model, Context ctx) {
    List<Rubro> rubros = (List<Rubro>) repositorio.obtenerTodos(Rubro.class);
    model.put("rubros", rubros);
  }

  //Setea las ofertas que pertenecen al usuario
  private void setSelfOffers(HashMap<String, Object> model, Context ctx) {
    Logger.debug("Colaborador id:" + ctx.sessionAttribute("colaborador_id").toString());
    Long colabId = Long.valueOf(ctx.sessionAttribute("colaborador_id"));
    List<Oferta> ofertas = (List<Oferta>) ofertaRepository.getPorColaborador(colabId);
    model.put("ofertas", ofertas);
  }
}
