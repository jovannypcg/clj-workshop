(ns clj-workshop-2020.db.datascript.util.scratch
  (:require [datascript.core :as ds]
            [clj-workshop-2020.db.datascript.util.seed :as util.seed]))

(def conn (ds/create-conn util.seed/db-schema))
(util.seed/seed! conn)
(ds/pull @conn '[*] [:name "Spider-Man"])

(ds/q '[:find [?name ...]
        :in $ [?occupation]
        :where
        [?hero :occupation ?occupation]
        [?hero :name ?name]]
      @conn ["Student"])

;; Get all races
(def races-query
  '[:find ?race
    :in $
    :where
    [_ :race ?race]])
(ds/q races-query @conn)

;; All heroes given a race
(def heroes-by-race-query
  '[:find [?name ...]
    :in $ ?race
    :where
    [?hero :race ?race]
    [?hero :name ?name]])
(ds/q heroes-by-race-query @conn :saiyan)

;; Get hero occupations
(ds/q
  '[:find [?occupation ...]
    :in $ ?hero-name
    :where
    [?hero :name ?hero-name]
    [?hero :occupation ?occupation]]
  @conn
  "Spider-Man")

(ds/pull @conn '[:occupation] [:name "Spider-Man"])

(comment
  (:tx-meta (ds/transact! conn [{:name "Spider-Man" :occupation ["Amateur developer"]}])))

;; Get heroes by powers using OR
(def heroes-by-powers-query
  '[:find ?name
    :in $ [?power ...]
    :where
    [?hero :powers ?power]
    [?hero :name ?name]])
(ds/q heroes-by-powers-query @conn [:flight :time-travel])

;; Get heroes by powers using AND
(def heroes-by-powers-query*
  '[:find ?name
    :in $ [?power-a ?power-b]
    :where
    [?hero :powers ?power-a]
    [?hero :powers ?power-b]
    [?hero :weight ?weight]
    [(>= ?weight 87.0)]
    [?hero :name ?name]])
(ds/q heroes-by-powers-query* @conn [:flight :time-travel])

(ds/transact-async conn [{:name   "Spider-Man"
                          :powers [:saying-yes-to-everyting-2]}])

;; Get hero details
(def hero-details-selector
  '[:name :aliases :height :full-name :first-appearance
    {(:stats :as "Stats" :limit 2) [[:stat/name :as :name] [:stat/value :as value]]}
    {(:publisher :as "Pub") [:name]}])
(ds/pull @conn hero-details-selector [:name "Goku"])

(defn my-count
  [x]
  (count x))

(ds/q '[:find ?name-count
        :in $
        :where
        [?hero :stats ?stats]
        [?hero :name "Zoom"]
        [?hero :name ?name]
        [(count ?name) ?name-count]]
      @conn)
(ds/pull @conn '[{:stats [[:stat/name :as :name]]}] [:name "Goku"])
