class Colaborador < ApplicationRecord
  self.table_name = :colaboradores
  belongs_to :contacto_preferido, class_name: "Contacto", optional: true
end
