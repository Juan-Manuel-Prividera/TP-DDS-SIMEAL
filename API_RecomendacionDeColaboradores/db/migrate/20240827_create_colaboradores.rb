# frozen_string_literal: true

class CreateColaboradores < ActiveRecord::Migration[6.0]
  def change
    create_table :colaboradores do |t|
      t.string :nombre
      t.string :apellido
      t.string :contacto
      t.float :puntos
      t.integer :cantDonaciones
    end
  end
end
