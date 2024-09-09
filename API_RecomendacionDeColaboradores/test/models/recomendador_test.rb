
require "test_helper"
require_relative "../../app/models/colaborador"
require_relative "../../app/service/recomendador_colaboradores"


class RecomendadorTest < ActiveSupport::TestCase

  def setup
    @colaboradores = []
    @colaborador1 = Colaborador.new(id: 1, nombre: "Juan", apellido: "Perez", puntos: 250, cantDonaciones: 3)
    @colaboradores.push(@colaborador1)

    @colaborador3 = Colaborador.new(id: 2, nombre: "Pablo", apellido: "Domingo", puntos: 100, cantDonaciones: 3)
    @colaboradores.push(@colaborador3)

    @colaborador2 = Colaborador.new(id: 3, nombre: "Hernan", apellido: "Domingo", puntos: 170, cantDonaciones: 1)
    @colaboradores.push(@colaborador2)
  end

  def teardown
    # Do nothing
  end

  test "Descarte por puntos y donaciones" do
    recomendador = RecomendadorColaboradores.new(150, 2, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_not(colabs_recomendados.include?(@colaborador2))
    assert_not(colabs_recomendados.include?(@colaborador3))

  end

  test "Descarte por puntos" do
    recomendador = RecomendadorColaboradores.new(150, 1, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_includes(colabs_recomendados, @colaborador2)
    assert_not(colabs_recomendados.include?(@colaborador3))
  end

  test "Descarte por donaciones" do
    recomendador = RecomendadorColaboradores.new(50, 2, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_not(colabs_recomendados.include?(@colaborador2))
    assert_includes(colabs_recomendados, @colaborador3)
  end

  test "Test de metodo ordenar" do
    @colaborador4 = Colaborador.new(nombre: "Pablo", apellido: "Domingo", puntos: 250, cantDonaciones: 0)
    @colaboradores.push(@colaborador4)
    @colaborador5 = Colaborador.new(nombre: "Pablo", apellido: "Domingo", puntos: 100, cantDonaciones: 1)
    @colaboradores.push(@colaborador5)

    recomendador = RecomendadorColaboradores.new(50, 2, 10)
    colaboradores_ordenados = recomendador.ordenar_colaboradores(@colaboradores)

    # Orden esperado: 1, 4, 2, 3, 5
    assert(colaboradores_ordenados[0] == @colaborador1)
    assert(colaboradores_ordenados[1] == @colaborador4)
    assert(colaboradores_ordenados[2] == @colaborador2)
    assert(colaboradores_ordenados[3] == @colaborador3)
    assert(colaboradores_ordenados[4] == @colaborador5)

  end

  test "es_recomendable => True" do
    recomendador = RecomendadorColaboradores.new(150, 2, 10)

    assert(recomendador.es_recomendable(@colaborador1))
  end

  test "es_recomendable => False" do
    recomendador = RecomendadorColaboradores.new(150, 5, 10)

    assert_not(recomendador.es_recomendable(@colaborador1))
  end
end