class ColabRecomendacionController < ApplicationController

  def obtener_recomendacion
    # Recibo los query params y los convierto en numeros
    puntos = params[:puntos].to_i
    cant_donaciones = params[:donaciones].to_f
    max = params[:max].to_i

    unless validar_parametros(puntos, cant_donaciones, max)
      render json: {
        error: "Parámetros inválidos. Asegúrate de que 'puntos', 'donaciones' y 'max' sean numéricos."
        },
        status: :bad_request
      return
    end

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
    if puntos >= 0 && cant_donaciones >= 0 && max > 0
      true
    else
      false
    end
  end
end
