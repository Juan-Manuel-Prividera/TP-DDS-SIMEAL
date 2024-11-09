package ar.edu.utn.frba.dds.simeal.controllers;


import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.OfertaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.AdherirHeladera;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Producto;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.OfertaRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import org.apache.maven.model.Model;
import org.eclipse.aether.transfer.RepositoryOfflineException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.*;

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
    setSelfOffers(model,app);

    model.put("agregarOferta", "true");
    app.render("ofertas/oferta_self.hbs",model);
  }

  public void selfOffersSelected(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setUpperBox(model,app);
    setSelfOffers(model,app);
    setOferta(model,app);

    model.put("agregarOferta", "true");
    model.put("directorio", "../");

    app.render("ofertas/oferta_self_selected.hbs", model);
  }

  public void publicar(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    model.put("publicar_modificar", "publicar");
    model.put("agregarOferta", "true");
    app.render("ofertas/oferta_publicar_modificar.hbs", model);
  }

  public void persistOferta(Context app){
    HashMap<String, Object> model = new HashMap<>();
    Producto producto = new Producto(app.formParam("productoNombre"), app.formParam("descripcion"));
    Colaborador colaborador = (Colaborador)  repositorio.buscarPorId(app.sessionAttribute("colaborador_id"), Colaborador.class);
    Rubro newRubro = giveRubroNotRepeted(app.formParam("rubro"));
    repositorio.guardar(producto);

    Oferta oferta = Oferta.create(
        colaborador,
        app.formParam("nombre"),
        LocalDate.now(),
        Double.parseDouble(app.formParam("puntos")),
        newRubro,
        app.formParam("imagen"),
        producto
    );
    Logger.info("Info formulario: %s", oferta.toString());
    repositorio.guardar(oferta);

    setNavBar(model,app);
    model.put("publicar_modificar", "publicar");
    model.put("agregarOferta", "true");
    model.put("required", "required");
    app.render("ofertas/oferta_publicar_modificar.hbs", model);
  }

  public void modificar(Context app){
    HashMap<String, Object> model = new HashMap<>();

    model.put("ofertaId", app.pathParam("oferta_id"));
    app.render("ofertas/oferta_publicar_modificar.hbs", model);
  }


  public void updateOferta(Context app) {
    HashMap<String, Object> model = new HashMap<>();

    Oferta oferta = (Oferta) ServiceLocator.getRepository(OfertaRepository.class).buscarPorId(Long.valueOf(app.pathParam("oferta_id")), Oferta.class);

    if (app.formParam("nombre") != null && !app.formParam("nombre").isEmpty()) {
      oferta.setNombre(app.formParam("nombre"));
    }
    if (app.formParam("puntos") != null && !app.formParam("puntos").isEmpty()) {
      oferta.setPuntosNecesarios(Double.parseDouble(app.formParam("puntos")));
    }
    if (app.formParam("rubro") != null && !app.formParam("rubro").isEmpty()) {
      oferta.setRubro(giveRubroNotRepeted(app.formParam("rubro")));
    }
    if (app.formParam("producto") != null && !app.formParam("producto").isEmpty()) {
      oferta.setProducto(giveProductoNotRepeted(app.formParam("producto"), oferta.getProducto().getDescripcion()));
    }
    if (app.formParam("descripcion") != null && !app.formParam("descripcion").isEmpty()) {
      oferta.setProducto(giveProductoNotRepeted(oferta.getProducto().getNombre(), app.formParam("descripcion")));
    }
    if (app.formParam("imagen") != null && !app.formParam("imagen").isEmpty()) {
      oferta.setImagen(app.formParam("imagen"));
    }


    repositorio.actualizar(oferta);

    model.put("ofertaId", app.pathParam("oferta_id"));

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
    List<Producto> productos = (List<Producto>) repositorio.obtenerTodos(Producto.class);
    List<Oferta> ofertas = (List<Oferta>) ofertaRepository.obtenerTodos(Oferta.class);

    model.put("ofertas", converToDTO(ofertas));
  }

  //Setea la oferta que seleccionó el usuario
  private void setOferta(HashMap<String, Object> model, Context ctx) {
    //TODO: NO USAR PATHPARAMS
    Oferta oferta = (Oferta) ofertaRepository.buscarPorId(Long.valueOf(ctx.pathParam("oferta_id")), Oferta.class);
    OfertaDTO ofertaDTO = new OfertaDTO(oferta);
    model.put("oferta", ofertaDTO);
  }

  //Setea la parte superior de la pág ofertas
  private void setUpperBox(HashMap<String, Object> model, Context ctx) {
    setRubros(model, ctx);
    //Logger.debug("user_type = %s", (String) ctx.sessionAttribute("user_type"));
    if(Objects.equals(ctx.sessionAttribute("user_type"), "HUMANO")){
      setSelfPuntos(model, ctx);
    }
  }

  //Setea los puntos que tiene el usuario
  private void setSelfPuntos(HashMap<String, Object> model, Context ctx) {
    double ptsColab = calcularPts(ctx);
    Logger.debug("Puntos del colaborador: " + ptsColab);
    model.put("ptsColab", ptsColab);
  }

  //Setea los rubros almacenados en bd
  private void setRubros(HashMap<String, Object> model, Context ctx) {
    List<Rubro> rubros = (List<Rubro>) repositorio.obtenerTodos(Rubro.class);
    model.put("rubros", rubros);
  }

  //Setea las ofertas que pertenecen al usuario
  private void setSelfOffers(HashMap<String, Object> model, Context ctx) {
    Long colabId = ctx.sessionAttribute("colaborador_id");
    List<Oferta> ofertas = ofertaRepository.getPorColaborador(colabId);
    model.put("ofertas", converToDTO(ofertas));
  }




// ************************************************************
// ****************** HELPERS de los HELPERS ******************
// ************************************************************


  private List<OfertaDTO> converToDTO(List<Oferta> ofertas){
    List<OfertaDTO> ofertasDTO = new ArrayList<>();
    for(Oferta oferta : ofertas){
      OfertaDTO ofertaDTO = new OfertaDTO(oferta);
      ofertasDTO.add(ofertaDTO);
    }
    return ofertasDTO;
  }

  private double calcularPts(Context ctx) {
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    double ptsColab = CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador);
    return ptsColab;
  }

  private Rubro giveRubroNotRepeted (String nombre) {
    List<Rubro> rubros = (List<Rubro>) repositorio.obtenerTodos(Rubro.class);
    Rubro newRubro = null;
    for (Rubro rubro : rubros) {
      if (Objects.equals(rubro.getNombre(), nombre)) {
        newRubro = rubro;
      }
    }
    if (Objects.isNull(newRubro)) {
      repositorio.guardar(newRubro);
      newRubro = new Rubro(nombre);
    }
    return newRubro;
  }

  private Producto giveProductoNotRepeted (String nombre, String descripcion) {
    List<Producto> productos = (List<Producto>) repositorio.obtenerTodos(Producto.class);
    Producto newProducto = null;
    for (Producto producto: productos) {
      if (Objects.equals(producto.getNombre(), nombre) && Objects.equals(producto.getDescripcion(), descripcion)) {
        newProducto = producto;
      }
    }
    if (Objects.isNull(newProducto)) {
      repositorio.guardar(newProducto);
      newProducto = new Producto(nombre, descripcion);
    }
    return newProducto;
  }

}
