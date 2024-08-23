class DonacionVianda < ApplicationRecord
  belongs_to :colaborador, class_name: "Colaborador", optional: false
end
