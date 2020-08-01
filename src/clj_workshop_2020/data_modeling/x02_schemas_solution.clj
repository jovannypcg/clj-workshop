(ns clj-workshop-2020.data-modeling.x02-schemas-solution
  (:require [datascript.core :as ds]))

(def schema
  {:name    {:db/unique :db.unique/identity}
   :alias   {:db/unique      :db.unique/identity
             :db/cardinality :db.cardinality/many}
   :powers  {:db/cardinality :db.cardinality/many}
   :weapons {:db/cardinality :db.cardinality/many}
   :nemesis {:db/valueType   :db.type/ref
             :db/cardinality :db.cardinality/many}})

(comment
  (def datomic-schema
    [{:db/ident       :name
      :db/valueType   :db.type/string
      :db/unique      :db.unique/identity
      :db/cardinality :db.cardinality/one}

     {:db/ident       :alias
      :db/valueType   :db.type/string
      :db/unique      :db.unique/identity
      :db/cardinality :db.cardinality/many}

     {:db/ident       :powers
      :db/valueType   :db.type/string
      :db/cardinality :db.cardinality/many}

     {:db/ident       :weapons
      :db/valueType   :db.type/string
      :db/cardinality :db.cardinality/many}

     {:db/ident       :nemesis
      :db/valueType   :db.type/ref
      :db/cardinality :db.cardinality/many}]))

;; Play around with entity, entid, entity-db, pull, pull-many
(def db
  (ds/db-with
    (ds/empty-db schema)
    [{:name       "Batman"
      :alias      "Bruce Wayne"
      :powers     #{"Rich"}
      :weapons    #{"Utility Belt" "Kryptonite Spear"}
      :hair-color :black
      :alignment  "Chaotic Good"
      :nemesis    [{:name "Joker"}
                   {:name "Penguin"}]}
     {:name  "Batman"
      :alias "Bruce"}
     {:name    "Penguin"
      :nemesis [{:alias "Bruce"}]}]))

(def ddb
  (ds/db-with
    (ds/empty-db {:name {:db/unique :db.unique/identity
                         :db/valueType :db.type/string}})
    [{:name "Jovanny Pablo"}]))
