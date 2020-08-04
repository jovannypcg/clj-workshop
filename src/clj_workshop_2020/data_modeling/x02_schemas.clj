(ns clj-workshop-2020.data-modeling.x02-schemas
  (:require [datascript.core :as ds]
            [clj-workshop-2020.data-modeling.util.misc :as misc]))

;; Exercise: Define the schema for the Parker family according
;; to the following facts.
;;
;; * Petter Parker is son of Richard and Mary Parker, who are maried
;; * Petter aliases are “Spidey” and “Spider-Man”
;; * Richard’s brother is Ben Parker
;; * Ben Parker is maried to May Parker
;;
;; Datascript schemas require these attributes: :db/unique, :db/cardinality, :db/valueType

(comment
  (def schema-attributes
    "Make sure to grab only one value from the set."
    {:db/unique      #{:db.unique/identity :db.unique/value}
     :db/cardinality #{:db.cardinality/one :db.cardinality/many}
     :db/valueType   #{:db.type/ref}}))

(def family-schema
  {})

;; Exercise: Define the schema for superheroes.
;;
;; A schema is a map whose keys are the fields of the entity
;; and its values are the attributes describing the purpose of the field.
;;
;; For example, Wonder Woman may have the
;; following characteristics for his schema:
;;
;; * Her name is unique and her identifier
;; * She has many unique aliases
;; * She has many superpowers
;; * She attacks with many weapons
;; * She may have many nemeses
;;
;; Datascript schemas require these attributes: :db/unique, :db/cardinality, :db/valueType

(def hero-schema
  {})
