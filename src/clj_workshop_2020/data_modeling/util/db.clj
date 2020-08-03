(ns clj-workshop-2020.data-modeling.util.db
  (:require [datascript.core :as ds]
            [clj-workshop-2020.data-modeling.util.hero-data :as hero-data]
            [clj-workshop-2020.data-modeling.util.hero-powers-data :as hero-powers-data]
            [clj-workshop-2020.data-modeling.util.supplemental-hero-data :as supplemental-hero-data]
            [clj-workshop-2020.data-modeling.util.misc :as misc]))

(def hero-schema (misc/read-edn "schema/hero-schema.edn"))
(def hero-powers-schema (misc/read-edn "schema/hero-powers-schema.edn"))
(def supplemental-hero-schema (misc/read-edn "schema/supplemental-hero-data-schema.edn"))

(defn seed
  [conn]
  (do
    (ds/transact! conn (mapv hero-data/hero->datascript-format (hero-data/heroes-data)))
    (ds/transact! conn (vec (hero-powers-data/powers-data)))
    (ds/transact! conn (mapv supplemental-hero-data/hero->datascript-format (supplemental-hero-data/supplemental-hero-data)))))

(comment
  (ds/pull @conn '[*] [:name "Spider-Man"]))
