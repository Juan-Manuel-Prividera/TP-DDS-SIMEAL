class ColabRecomendacionController < ApplicationController

  def obtener_recomendacion
    # Recibo los query params y los convierto en numeros
    puntos = params[:puntos].to_f
    cant_donaciones = params[:donaciones].to_i
    max = params[:max].to_i

    unless validar_parametros(puntos, cant_donaciones, max)
      render json: {
        error: "Parámetros inválidos. Asegúrate de que 'puntos', 'donaciones' y 'max' sean numéricos."
        },
        status: :bad_request
      return
    end

    Rails.logger.debug("Solicitud de recomendacion recibida con parametros validos:
      #{puntos}, cant_donaciones: #{cant_donaciones}, max: #{max}")

    recomendador = RecomendadorColaboradores.new(puntos, cant_donaciones, max)

    # Levanto todos los colabs
    colaboradores = Colaborador.all

    colabs_recomendados = recomendador.recomendar(colaboradores)

    # Convierte en json y responde a la solicitud
    render json: {
      "colaboradores": colabs_recomendados
    }
  end


  def validar_parametros(puntos, cant_donaciones, max)
    if puntos >= 0 && cant_donaciones >= 0 && max > 0 &&
      puntos.is_a?(Float) && cant_donaciones.is_a?(Integer) && max.is_a?(Integer)
      true
    else
      false
    end
  end
end