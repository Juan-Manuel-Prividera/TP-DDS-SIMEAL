# frozen_string_literal: true

require "faraday"
require "json"
require 'logger'

class ActualizadorColaboradores
  def recibir_colaboradores
    token = autenticar
    logger = Logger.new("log/recibir_colaboradores.log")
    # Se conecta y hace la solicitud, si hay un error termina
    conn = Faraday.new(url: "http://localhost:8080/simeal", headers: { "Authorization" => "Bearer #{token}" })
    response = conn.get("/colaboradores")
    if response.status != 200 || response.body.empty?
      logger.error("Error al recibir los colaboradores: #{response.body}")
      exit 1
    end

    # Borro mi bd de colaboradores
    Colaborador.delete_all

    colabs_json = JSON.parse(json.body.read)
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
    config = YAML.load 'config/api_config.yml'
    apikey = config["api"]["key"]
    clientId = config["api"]["clientId"]

    conn = Faraday.new(url: "http://localhost:8080/simeal")
    params = {
      clientId: clientId,
      apikey: apikey,
    }
    response = conn.post('/auth', params)

    if response.status == 200
      body = JSON.parse(response.body)
      body["token"] # Retorna el token
    else
      logger.error("Error al autenticar en el servidor: #{response.body}")
    end
  end

end