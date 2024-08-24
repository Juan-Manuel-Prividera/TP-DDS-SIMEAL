# frozen_string_literal: true

require 'test_helper'
class ColabRecomendacionControllerTest < ActionDispatch::IntegrationTest
  test "Responde correctamente con 200 OK a una solicitud correcta" do
    get "/simeal/api/colabs/recomendacion", params: { puntos: 20, donaciones: 0, max: 5 }
    assert_response :success
  end

  test "Solcitud invalidad" do
    get "/simeal/api/colabs/recomendacion", params: { puntos: nil, donaciones: nil, max: -5 }
    assert_response :bad_request
    json_response = JSON.parse(response.body)

    assert json_response["error"] != nil
  end
end
