(ns clj-workshop-2020.db.datascript.hero
  (:require [datascript.core :as ds]))

(def ^:private occupations-query
  '[:find [?occupation ...]
    :in $ ?hero-name
    :where
    [?hero :name ?hero-name]
    [?hero :occupation ?occupation]])

(def ^:private details-query
  '[*
    {:stats [[:stat/name :as :name]
             [:stat/value :as :value]]}
    {:team-affiliation [[:team/name :as :name]
                        [:hero.team/id :as :hero-id]
                        [:team/leader? :as :leader?]
                        [:team/former? :as :former?]]
     :relatives        [{:relative [:name]}]
     :publisher        [:name]
     :creator          [:name]}])

(defn occupations!
  [name db]
  (ds/q occupations-query db name))

(defn details!
  [name db]
  (ds/pull db details-query [:name name]))

(defn insert-occupation!
  [hero-name occupation db]
  (let [conn (ds/conn-from-db db)]
    (:tx-meta (ds/transact! conn [{:name hero-name :occupation [occupation]}]))))

(defn data
  [db]
  (details! "Spider-Man" db))
