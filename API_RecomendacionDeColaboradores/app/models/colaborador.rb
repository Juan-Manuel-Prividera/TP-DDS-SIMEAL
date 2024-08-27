class Colaborador < ApplicationRecord
  attr_accessor :puntos, :cantDonaciones
  self.table_name = :colaboradores

  def create_from_json(json)

  end

end