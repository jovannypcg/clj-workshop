(ns clj-workshop-2020.db.datascript.hero
  (:require [datascript.core :as ds]))

(def query
  '[:find ?name
    :in $
    :where
    [_ :name ?name]])

(defn data
  [db]
  (ds/q query db))
