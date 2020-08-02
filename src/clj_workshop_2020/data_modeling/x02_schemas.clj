(ns clj-workshop-2020.data-modeling.x02-schemas
  (:require [datascript.core :as ds]
            [clj-workshop-2020.data-modeling.util.misc :as misc]))

;; Exercise: Define the schema for superheroes.
;;
;; A schema is a map whose keys are the fields of the entity
;; and its values are the attributes describing the purpose of the field.
;;
;; For example, Wonder Woman may have the
;; following characteristics for his schema:
;;
;; * Her name is unique
;; * Her alias is unique but may have multiple aliases
;; * She may have many superpowers
;; * She may have attack with many weapons
;; * She may have many nemeses with similar characteristics.
;;
;; Datascript schemas require these attributes: :db/unique, :db/cardinality, :db/valueType

(comment
  (def schema-attributes
    "Make sure to grab only one value from the set."
    {:db/unique      #{:db.unique/identity :db.unique/value}
     :db/cardinality #{:db.cardinality/one :db.cardinality/many}
     :db/valueType   #{:db.type/ref}}))

(def superhero-schema
  (misc/read-edn "schema/hero-schema.edn"))

(def db
  (ds/db-with
    (ds/empty-db superhero-schema)
    []))
