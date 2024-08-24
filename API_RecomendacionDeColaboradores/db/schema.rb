# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[7.2].define(version: 0) do
  create_table "colaboradores", id: :integer, charset: "utf8mb3", force: :cascade do |t|
    t.string "nombre"
    t.string "apellido"
    t.integer "contacto_preferido_id"
    t.float "puntos"
    t.index ["contacto_preferido_id"], name: "contacto_preferido_id"
  end

  create_table "contactos", id: :integer, charset: "utf8mb3", force: :cascade do |t|
    t.string "contacto"
    t.string "medio"
  end

  create_table "donacion_viandas", id: false, charset: "utf8mb3", force: :cascade do |t|
    t.integer "colaborador_id"
    t.string "fecha"
    t.index ["colaborador_id"], name: "colaborador_id"
  end

  add_foreign_key "colaboradores", "contactos", column: "contacto_preferido_id", name: "colaboradores_ibfk_1"
  add_foreign_key "donacion_viandas", "colaboradores", column: "colaborador_id", name: "donacion_viandas_ibfk_1"
end
