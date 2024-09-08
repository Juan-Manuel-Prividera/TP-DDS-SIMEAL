# lib/tasks/update_bd.rake

# Config de CronTask para que se ejecute una vez al mes
# 0 0 1 * * bundle exec rake db:update RAILS_ENV=production
namespace :db do
  desc "Actualizar la base de datos"
  task update: :environment do
    actualizador = ActualizadorColaboradores.new
    actualizador.recibir_colaboradores
  end
end