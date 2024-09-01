class Colaborador < ApplicationRecord
  attr_accessor :puntos, :cantDonaciones
  self.table_name = :colaboradores

end