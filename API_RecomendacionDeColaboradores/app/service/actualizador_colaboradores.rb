# frozen_string_literal: true

require "faraday"
require "json"
require 'logger'

class ActualizadorColaboradores
  def recibir_colaboradores
    token = autenticar
    # Se conecta y hace la solicitud, si hay un error termina
    conn = Faraday.new(url: "http://localhost:8080")
    response = conn.get("/simeal/colaboradores") do |request|
      request.headers["Authorization"] = "Bearer #{token}"
    end
    Rails.logger.debug("Solicito colaboradores con el token: #{token}")
    if response.status != 200 || response.body.empty?
      Rails.logger.error("Error al recibir los colaboradores: #{response.body}")
      exit 3
    end

    # Borro mi bd de colaboradores
    Colaborador.delete_all

    colabs_json = JSON.parse(response.body)
    colabs_json.each do |colab|
      # Crea un obj colaborador y lo persiste
      Colaborador.create(nombre: colab["nombre"],
                         apellido: colab["apellido"],
                         contacto: colab["contacto"],
                         puntos: colab["puntos"],
                         cantDonaciones: colab["cantDonaciones"])

    end
  end

  def autenticar
    config = YAML.load_file(Rails.root.join('config', 'api_config.yml'))
    if config == nil
      Rails.logger.error("No se pudo cargar la configuracion")
      exit 4
    end
    apikey = config["api"]["key"]
    client_id = config["api"]["clientId"]

    conn = Faraday.new(url: "http://localhost:8080")
    params = {
      apikey: apikey,
      client_id: client_id,
    }
    response = conn.get("/simeal/auth", params)

    Rails.logger.debug("Solicitud de autenticacion enviada con parametros: #{params}")
    if response.status == 200
      Rails.logger.info("Autenticado exitosamente en el servidor")
      body = JSON.parse(response.body)
      body["token"] # Retorna el token
    else
      Rails.logger.error("Error al autenticar en el servidor: #{response.body}")
      exit 5
    end
  end

end