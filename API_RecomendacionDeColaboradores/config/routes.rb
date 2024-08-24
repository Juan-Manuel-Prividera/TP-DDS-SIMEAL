Rails.application.routes.draw do
  get "simeal/api/colabs/recomendacion", to: "colab_recomendacion#obtener_recomendacion"
end