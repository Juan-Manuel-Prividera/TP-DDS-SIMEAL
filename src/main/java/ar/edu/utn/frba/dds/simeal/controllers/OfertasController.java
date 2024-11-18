package ar.edu.utn.frba.dds.simeal.controllers;


import ar.edu.utn.frba.dds.simeal.config.ServiceLocator;
import ar.edu.utn.frba.dds.simeal.models.dtos.CanjeDTO;
import ar.edu.utn.frba.dds.simeal.models.dtos.OfertaDTO;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Canje;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Oferta;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Producto;
import ar.edu.utn.frba.dds.simeal.models.entities.colaboraciones.oferta.Rubro;
import ar.edu.utn.frba.dds.simeal.models.entities.personas.colaborador.Colaborador;
import ar.edu.utn.frba.dds.simeal.models.repositories.CanjeRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.OfertaRepository;
import ar.edu.utn.frba.dds.simeal.models.repositories.Repositorio;
import ar.edu.utn.frba.dds.simeal.utils.CalculadorDeReconocimientos;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OfertasController {
  //TODO: VER DE NO USAR ESTE MÉTODO PARA CONSEGUIR LOS REPOSITORY
  private final OfertaRepository ofertaRepository = (OfertaRepository) ServiceLocator.getRepository(OfertaRepository.class);
  private final Repositorio repositorio = ServiceLocator.getRepository(Repositorio.class);
  private final CanjeRepository canjeRepository = (CanjeRepository) ServiceLocator.getRepository(CanjeRepository.class);

  public void index(Context app){
    HashMap<String, Object> model = new HashMap<>();

    setNavBar(model,app);
    setOfertas(model,app);
    setUpperBox(model,app);

    //Para mostrar confirmación/error al comprar una oferta
    if(app.sessionAttribute("resultado_compra") != null){
      if(app.sessionAttribute(("resultado_compra")).equals("confirmado")){
        model.put("popup_title", "Compra confirmada");
        model.put("popup_message", "La compra se ha concretado satisfactoriamente");
        app.sessionAttribute("resultado_compra", null);
        Logger.info("Se confirmó la compra");
      }
      else if(app.sessionAttribute(("resultado_compra")).equals("rechazado")){
        model.put("popup_title", "Compra rechazada");
        model.put("popup_message", "No se ha podido concretar la compra");
        app.sessionAttribute("resultado_compra", null);
        Logger.info("Se rechazó la compra");
      }
    }

    app.render("ofertas/oferta_all.hbs", model);
  }

  public void comprar(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    Logger.info("Comprar oferta controller");

    long ofertaId = Long.parseLong(app.body());
    Long colaboradorId = app.sessionAttribute("colaborador_id");

    Logger.info("El colaboradorId: %s, pretende comprar la ofertaId: %s", colaboradorId.toString(), Long.toString(ofertaId));

    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(colaboradorId, Colaborador.class);
    Oferta oferta = (Oferta) repositorio.buscarPorId(ofertaId, Oferta.class);

    //Checkeo que la oferta exista y que tenga los puntos suficientes
    if (oferta != null && colaborador.puedeCanjear(oferta)) {
      oferta.canjear(colaborador);
      Canje canje = new Canje(oferta, colaborador, LocalDateTime.now(), oferta.getPuntosNecesarios());
      repositorio.guardar(canje);
      repositorio.actualizar(colaborador); //Actualizo los puntos del colaborador
      app.sessionAttribute("resultado_compra", "confirmado");
    }
    else
        app.sessionAttribute("resultado_compra", "rechazado");
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

    if(app.queryParam("error") != null){
    if(app.queryParam("error") == "true"){
     model.put("popup_title", "Publicación Confirmada");
     model.put("popup_message", "bueno nada, eso ^^^. Que más queres que te diga?");
   }
    else if(app.queryParam("error") == "false"){
     model.put("popup_title", "ERROR");
     model.put("popup_message", "No se pudo realizan la publicación");
    }
    }

    setNavBar(model,app);
    model.put("publicar_modificar", "publicar"); //para mostrar bien el titulo del form
    model.put("agregarOferta", "true"); //Para realizar bien el form (que mande bien el post
    model.put("required", "required"); //Settea que los campos del form son obligatorios
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
        saveImage(app),
        producto
    );

    try{
      repositorio.guardar(oferta);
    }catch(Exception e){
      Logger.error("Error al persistir una nuevo oferta - %s", e);
      app.redirect("/ofertas/misOfertas/publicar?error=true");
    }
    Logger.info("Nueva oferta persistida - Nombre: %s, Costo: %s", oferta.getNombre(), oferta.getPuntosNecesarios());
    app.redirect("/ofertas/misOfertas/publicar?error=false");
  }

  public void modificar(Context app){
    HashMap<String, Object> model = new HashMap<>();

    if(app.queryParam("error") != null){
    if(app.queryParam("error") == "true"){
      model.put("popup_title", "Cambios Realizados");
      model.put("popup_message", "bueno nada, eso ^^^. Que más queres que te diga?");
    }
    else if(app.queryParam("error") == "false"){
      model.put("popup_title", "ERROR");
      model.put("popup_message", "No se pudo actualizar la publicación");
    }
    }
    model.put("ofertaId", app.pathParam("oferta_id")); //Para setter la url del post del form
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
      oferta.setImagen(saveImage(app));
    }

    String url = "/ofertas/misOfertas" + oferta.getId() + "/modificar";
    try{
      repositorio.actualizar(oferta);
    }catch(Exception e){
      Logger.error("Error al actualizar la oferta - %s", e);
      url += "?error=true";
      app.redirect(url);
    }
    url += "?error=false";
    app.redirect(url);
  }

  public void canjes(Context app) {
    HashMap<String, Object> model = new HashMap<>();
    setNavBar(model,app);
    model.put("Ofertas", null);
    List<Canje> canjes = canjeRepository.getPorColaborador(app.sessionAttribute("colaborador_id"));
    model.put("canjes", convertToCanjeDTO(canjes));
    app.render("ofertas/oferta_canjes.hbs", model);
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

  private String saveImage(Context ctx){
    UploadedFile uploadedFile = ctx.uploadedFile("imagen");


    if (uploadedFile == null) {
      Logger.error("No se recibió ningún archivo");
      return null;
    }

    // Validar el tipo de archivo (opcional)
    if (!uploadedFile.contentType().startsWith("image/")) {
      Logger.error("El archivo no es una imagen válida");
      return null;
    }

    // Crear el directorio de destino si no existe
    String uploadDir = "src/main/resources/static/img/ofertas/";
    File directory = new File(uploadDir);
    if (!directory.exists()) {
      directory.mkdirs();
    }


    // Guardar el archivo
    File file = new File(uploadDir + uploadedFile.filename());
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(uploadedFile.content().readAllBytes());
    } catch (IOException e) {
      Logger.error("Error al guardar el archivo - %s", e);
      return null;
    }

    String path = "/img/ofertas/" + uploadedFile.filename();
    Logger.info("Se guardo el archivo correctamente");
    return path;
  }

// ************************************************************
// ****************** HELPERS de los HELPERS ******************
// ************************************************************


  //TODO: HACER TODO EN UN MISMO MÉTODO
  private List<OfertaDTO> converToDTO(List<Oferta> ofertas){
    List<OfertaDTO> ofertasDTO = new ArrayList<>();
    for(Oferta oferta : ofertas){
      OfertaDTO ofertaDTO = new OfertaDTO(oferta);
      ofertasDTO.add(ofertaDTO);
    }
    return ofertasDTO;
  }

  private List<CanjeDTO> convertToCanjeDTO(List<Canje> canjes){
    List<CanjeDTO> canjesDTO = new ArrayList<>();
    for(Canje canje : canjes){
      CanjeDTO canjeDTO = new CanjeDTO(canje);
      canjesDTO.add(canjeDTO);
    }
    return canjesDTO;
  }

  private double calcularPts(Context ctx) {
    Colaborador colaborador = (Colaborador) repositorio.buscarPorId(ctx.sessionAttribute("colaborador_id"), Colaborador.class);
    return CalculadorDeReconocimientos.calcularReconocimientoTotal(colaborador);
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
      newRubro = new Rubro(nombre);
      repositorio.guardar(newRubro);
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
