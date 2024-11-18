package ar.edu.utn.frba.dds.simeal.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class GuardadorDeImagenes {
  private static final String STORAGE_PATH = "/ruta/a/carpeta/imagenes/";

  /*
  public String guardarImagen(FileUpload archivo) throws IOException {
    String fileName = UUID.randomUUID().toString() + "-" + archivo.getFilename();
    Path filePath = Paths.get(STORAGE_PATH, fileName);
    Files.copy(archivo.getContent(), filePath);
    return "/imagenes/" + fileName; // Devuelve la URL relativa
  }

   */
}
