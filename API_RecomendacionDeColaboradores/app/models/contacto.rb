class Contacto < ApplicationRecord
  validates :contacto, presence: true
  validates :medio, presence: true
end
