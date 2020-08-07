(ns clj-workshop-2020.data-modeling.x03-queries
  (:require [clj-workshop-2020.data-modeling.x01-data-solution :refer [parker-family-data]]
            [clj-workshop-2020.data-modeling.x02-schemas-solution :refer [hero-schema parker-family-schema]]
            [datascript.core :as ds]))

;; ## Queries and Data Access
;; In this section we discuss the key ways to get data from a db.

(def parker-family-db
  (ds/db-with
   (ds/empty-db parker-family-schema)
   parker-family-data))

(def hero-db
  (ds/db-with
   (ds/empty-db
    hero-schema)
   [{:name       "Batman"
     :alias      "Bruce Wayne"
     :powers     #{"Rich" "Strong"}
     :weapons    #{"Utility Belt" "Kryptonite Spear"}
     :hair-color :black
     :alignment  "Chaotic Good"
     :nemesis    [{:name "Joker"}
                  {:name "Penguin"}]}
    {:name  "Batman"
     :alias "Bruce"}]))

;; ## Exercise: Try the following in a REPL to understand the query methods.

;; ### The Entity API
;; You can get an entity by providing its id.
(->> 1
     (ds/entity hero-db))

;; Entities are lazy, but you can get its attributes as follows:
(->> 1
     (ds/entity hero-db)
     :weapons)

;; You can even inspect the whole entity with `touch`.
(->> 1
     (ds/entity hero-db)
     ds/touch)

;; Or inspect nested entities.
(->> 1
     (ds/entity hero-db)
     :nemesis
     last
     ds/touch)

;; When you get an entity it's like situating yourself at a node in a graph db.
(:name (ds/entity hero-db [:name "Batman"]))

;; You can navigate any way you want. This is a forward reference.
(->> (ds/entity hero-db [:name "Batman"])
     :nemesis)

;; You can also do 'backrefs' to walk the link backwards.
(->> (ds/entity hero-db [:name "Joker"])
     :_nemesis)

;; ### The Pull API
;; This allows you to 'pull' facts straight from a db given an identity.
;; Tell me everything about entity 1
(ds/pull hero-db '[*] 1)
;; You can use 'lookup refs' for any unique identity (entity or value).
(ds/pull hero-db '[*] [:name "Batman"])
;; You can also do 'backrefs' to walk the link backwards.
(ds/pull hero-db '[{:_nemesis [*]}] [:name "Joker"])

;; ### The Query API
;; This is the most powerful and most commonly used API.

;; Get powers by hero name
(def basic-query
  '[])

(comment
  (ds/q basic-query hero-db))

;; Exercise: Get the uncle's name of a certain person

(def uncle-query
  '[:find ?uncle-name
    :in $ ,,,
    :where
    []
    []
    []])

(comment
  (ds/q uncle-query parker-family-db [:name "Peter Parker"]))

;; Queries have a powerful datalog syntax. Note that queries are data. It is
;; common to inline them with the q function, but they can be stored up as
;; standalone items in a file, db, etc.

(def nemeses-query
  '[:find [?enemy-name ...]
    :in $ ?name
    :where
    [?e :name ?name]
    [?e :nemesis ?n]
    [?n :name ?enemy-name]])

;;Get the nemeses of Batman
(ds/q nemeses-query hero-db "Batman")

;; Exercise - Write a query to list the name and alignment of all individuals
;; in the database.

(ds/q '[:find [?name ?alignment]
        :in $
        :where
        [?e :name ?name]
        [?e :alignment ?alignment]]
      hero-db)
