
require "test_helper"
require_relative "../../app/models/colaborador"
require_relative "../../app/models/contacto"
require_relative "../../app/models/donacion_vianda"
require_relative "../../app/service/recomendador_colaboradores"


class RecomendadorTest < ActiveSupport::TestCase

  def setup
    @colaboradores = []
    contacto1 = Contacto.new(contacto: "jperez@gmail.com", medio: "mail", id: 1)
    @colaborador1 = Colaborador.new(id: 1, nombre: "Juan", apellido: "Perez", contacto_preferido_id: contacto1.id, puntos: 250)
    @colaboradores.push(@colaborador1)

    contacto3 = Contacto.new(contacto: "pdomingo@gmail.com", medio: "mail", id: 2)
    @colaborador3 = Colaborador.new(id: 2, nombre: "Pablo", apellido: "Domingo", contacto_preferido_id: contacto3.id, puntos: 100)
    @colaboradores.push(@colaborador3)

    contacto2 = Contacto.new(contacto: "jdomingo@gmail.com", medio: "mail", id: 3)
    @colaborador2 = Colaborador.new(id: 3, nombre: "Juan", apellido: "Domingo", contacto_preferido_id: contacto2.id, puntos: 170)
    @colaboradores.push(@colaborador2)


    @donaciones = []
    donacion1 = DonacionVianda.new(colaborador_id: @colaborador1.id, fecha: Date.new(2022, 3, 20))
    @donaciones.push(donacion1)
    donacion2 = DonacionVianda.new(colaborador_id: @colaborador1.id, fecha: Date.new(2022, 4, 20))
    @donaciones.push(donacion2)
    donacion3 = DonacionVianda.new(colaborador_id: @colaborador1.id, fecha: Date.new(2022, 5, 20))
    @donaciones.push(donacion3)

    donacion4 = DonacionVianda.new(colaborador_id: @colaborador2.id, fecha: Date.new(2022, 3, 20))
    @donaciones.push(donacion4)
    donacion5 = DonacionVianda.new(colaborador_id: @colaborador3.id, fecha: Date.new(2022, 4, 20))
    @donaciones.push(donacion5)
    donacion6 = DonacionVianda.new(colaborador_id: @colaborador3.id, fecha: Date.new(2022, 5, 20))
    @donaciones.push(donacion6)
    donacion7 = DonacionVianda.new(colaborador_id: @colaborador3.id, fecha: Date.new(2022, 7, 20))
    @donaciones.push(donacion7)

  end

  def teardown
    # Do nothing
  end

  test "Descarte por puntos y donaciones" do
    recomendador = RecomendadorColaboradores.new(150, 2, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores, @donaciones)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_not(colabs_recomendados.include?(@colaborador2))
    assert_not(colabs_recomendados.include?(@colaborador3))

  end

  test "Descarte por puntos" do
    recomendador = RecomendadorColaboradores.new(150, 1, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores, @donaciones)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_includes(colabs_recomendados, @colaborador2)
    assert_not(colabs_recomendados.include?(@colaborador3))
  end

  test "Descarte por donaciones" do
    recomendador = RecomendadorColaboradores.new(50, 2, 10)

    colabs_recomendados = recomendador.recomendar(@colaboradores, @donaciones)

    assert_includes(colabs_recomendados, @colaborador1)
    assert_not(colabs_recomendados.include?(@colaborador2))
    assert_includes(colabs_recomendados, @colaborador3)
  end

  test "Test de metodo ordenar" do
    contacto4 = Contacto.new(contacto: "pdomingo@gmail.com", medio: "mail", id: 4)
    @colaborador4 = Colaborador.new(nombre: "Pablo", apellido: "Domingo", contacto_preferido_id: contacto4.id, puntos: 250)
    @colaboradores.push(@colaborador4)
    contacto5 = Contacto.new(contacto: "pdomingo@gmail.com", medio: "mail", id: 5)
    @colaborador5 = Colaborador.new(nombre: "Pablo", apellido: "Domingo", contacto_preferido_id: contacto5.id, puntos: 100)
    @colaboradores.push(@colaborador5)

    recomendador = RecomendadorColaboradores.new(50, 2, 10)
    colaboradores_ordenados = recomendador.ordenar_colaboradores(@colaboradores, @donaciones)

    # Orden esperado: 1, 4, 2, 3, 5
    assert(colaboradores_ordenados[0] == @colaborador1)
    assert(colaboradores_ordenados[1] == @colaborador4)
    assert(colaboradores_ordenados[2] == @colaborador2)
    assert(colaboradores_ordenados[3] == @colaborador3)
    assert(colaboradores_ordenados[4] == @colaborador5)

  end

  test "es_recomendable => True" do
    recomendador = RecomendadorColaboradores.new(150, 2, 10)
    # Esto es 3
    cant_donaciones = @donaciones.count { |donacion| donacion.colaborador_id == @colaborador1.id }

    assert(recomendador.es_recomendable(@colaborador1, cant_donaciones))
  end

  test "es_recomendable => False" do
    recomendador = RecomendadorColaboradores.new(150, 5, 10)
    # Esto es 3
    cant_donaciones = @donaciones.count { |donacion| donacion.colaborador_id == @colaborador1.id }

    assert_not(recomendador.es_recomendable(@colaborador1, cant_donaciones))
  end
end
