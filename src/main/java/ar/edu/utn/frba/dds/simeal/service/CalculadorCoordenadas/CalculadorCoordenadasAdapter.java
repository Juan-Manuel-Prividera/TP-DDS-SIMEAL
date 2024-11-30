package ar.edu.utn.frba.dds.simeal.service.CalculadorCoordenadas;

import ar.edu.utn.frba.dds.simeal.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.simeal.utils.logger.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CalculadorCoordenadasAdapter {
  public Coordenada calcularCoordenadas(String calle, int altura, int cp, String provincia, String localidad) {
    Coordenada coordenada = null;
    try {
      String direccion = "Argentina," + provincia + "," + localidad + ","+ altura + " "+ calle + "," + cp;

      String direccionCodificada = URLEncoder.encode(direccion, StandardCharsets.UTF_8);
      String url = "https://nominatim.openstreetmap.org/search?q=" + direccionCodificada + "&format=json&limit=1";

      HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();
      conexion.setRequestMethod("GET");
      conexion.setRequestProperty("User-Agent", "Java Client");

      int codigoRespuesta = conexion.getResponseCode();
      if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        StringBuilder respuesta = new StringBuilder();
        String linea;

        while ((linea = entrada.readLine()) != null) {
          respuesta.append(linea);
        }
        entrada.close();

        // Parsear la respuesta JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodoRaiz = mapper.readTree(respuesta.toString());

        if (nodoRaiz.isArray() && nodoRaiz.size() > 0) {
          JsonNode coordenadas = nodoRaiz.get(0);
          String latitud = coordenadas.get("lat").asText();
          String longitud = coordenadas.get("lon").asText();
          coordenada = new Coordenada(Double.parseDouble(latitud), Double.parseDouble(longitud));
        } else {
          Logger.warn("No se encontraron coordenadas para la dirección ingresada.");
        }
      } else {
        Logger.warn("Error en la solicitud: " + codigoRespuesta);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return coordenada;
  }
}

