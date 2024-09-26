package ar.edu.utn.frba.dds.simeal.utils;

import io.javalin.http.Context;

// Interface para que implementen los controllers que lo requieran
public interface ICrudViewsHandler {
  void index(Context context);
  void show(Context context);
  void create(Context context);
  void save(Context context);
  void edit(Context context);
  void update(Context context);
  void delete(Context context);
}
