# frozen_string_literal: true

require "faraday"
require "json"
class ActualizadorColaboradores
  def recibir_colaboradores
    # Se conecta y hace la solicitud, si hay un error termina
    @conn = Faraday.new(url: "http://localhost:3000/simeal")
    response = @conn.get("/colaboradores")
    if response.status != 200 || response.body.empty?
      return
    end

    # Me llega la bd de colabs entera entonces borro la mia y pongo la que llega
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
end
