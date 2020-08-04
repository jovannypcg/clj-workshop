(ns clj-workshop-2020.data-modeling.x03-queries
  (:require [datascript.core :as ds]
            [clj-workshop-2020.data-modeling.x02-schemas-solution :refer [hero-schema]]))

;; ## Queries and Data Access
;; In this section we discuss the key ways to get data from a db.
(def db
  (ds/db-with
    (ds/empty-db
      hero-schema)
    [{:name       "Batman"
      :alias      "Bruce Wayne"
      :powers     #{"Rich"}
      :weapons    #{"Utility Belt" "Kryptonite Spear"}
      :hair-color :black
      :alignment  "Chaotic Good"
      :nemesis    [{:name "Joker"}
                   {:name "Penguin"}]}
     {:name  "Batman"
      :alias "Bruce"}]))

;; ## Exercise: Try the following in a REPL to understand the query methods.

;; ### The Pull API
;; This allows you to 'pull' facts straight from a db given an identity.
(ds/pull db '[*] 1)
;; You can use 'lookup refs' for any unique identity (entity or value).
(ds/pull db '[*] [:name "Batman"])
;; You can specify certain keys as well as do more complex attribute specs.
(ds/pull db '[:name {:nemesis [:name]}] [:name "Batman"])

;; ### The Entity API
;; When you get an entity it's like situating yourself at a node in a graph db.
(:name (ds/entity db [:name "Batman"]))
;; You can navigate any way you want. This is a forward reference.
(->> (ds/entity db [:name "Batman"])
     :nemesis)
;; You can also do 'backrefs' to walk the link backwards.
(->> (ds/entity db [:name "Joker"])
     :_nemesis)

;; ### The Query API
;; This is the most powerful and most commonly used API.
;;
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
(ds/q nemeses-query db "Batman")

;; Exercise - Write a query to list the name and alignment of all individuals
;; in the database.

(ds/q '[:find [?name ?alignment]
        :in $
        :where
        [?e :name ?name]
        [?e :alignment ?alignment]]
      db)
