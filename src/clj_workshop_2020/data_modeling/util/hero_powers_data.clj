(ns clj-workshop-2020.data-modeling.util.hero-powers-data
  (:require [clj-workshop-2020.data-modeling.util.misc :as misc]))

;; ## Get Powers Data
;;
;;; We'll process super_hero_powers.csv to get powers data.
;;
;; Convert map of {:hero-name "name" :powerx "True|False"} to map of
;; {:name "name" :powers #{:set :of :powers}}.
(defn normalize [raw-hero-map]
  (letfn [(f [m [k v]]
            (cond
              (= k :hero-names) (assoc m :name v)
              (= v "True") (update m :powers (comp set conj) k)
              :else m))]
    (reduce f {} raw-hero-map)))

;; Note that the dataset is a seq of maps with the key :hero-names (the name)
;; and a boolean (as string) key for each power. Write a function that
;; normalizes our data into a map with the hero's name as :name and powers as a
;; set of keywords (e.g. #{:super-strength :flight}).
(defn powers-data []
  (let [filename "resources/super_hero_powers.csv"]
    (->> filename
         misc/csv-file->maps
         (map normalize))))
