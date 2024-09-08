# frozen_string_literal: true

class RecomendadorColaboradores

  def initialize(puntos_requeridos, cant_donaciones_requeridas, cant_max_colaboradores)
    @puntos_requeridos = puntos_requeridos
    @cant_donaciones_requeridas = cant_donaciones_requeridas
    @cant_max_colaboradores = cant_max_colaboradores
  end

  def recomendar(colaboradores)
    Rails.logger.info("Colaboradores a recomendar: #{colaboradores}")
    colabs_recomendados = []
    colabs_ordenados = ordenar_colaboradores(colaboradores)

    colabs_ordenados.each do |colaborador|
      if colabs_recomendados.length < @cant_max_colaboradores
        if es_recomendable(colaborador)
          colabs_recomendados << colaborador
        end
      else
        break
      end
    end
    colabs_recomendados
  end

  def es_recomendable(colaborador)
    colaborador.puntos >= @puntos_requeridos &&
    colaborador.cantDonaciones  >= @cant_donaciones_requeridas

  end

  def ordenar_colaboradores(colaboradores)
    colaboradores.sort do |colab1, colab2|
      comparacion_puntos = colab2.puntos <=> colab1.puntos
      if comparacion_puntos.zero?
        colab2.cantDonaciones <=> colab1.cantDonaciones
      else
        comparacion_puntos
      end
    end
  end
end