(ns clj-workshop-2020.data-modeling.util.hero-data
  (:require [clj-workshop-2020.data-modeling.utility.misc :as misc]))

;;; Process heroes_information.csv to get basic superhero data
(defn normalize [m]
  (let [dbl-fields [:height :weight]
        kw-fields [:gender :alignment :hair-color :skin-color :eye-color :race]]
    (-> m
        (misc/maybe-bulk-update dbl-fields #(Double/parseDouble %))
        (misc/maybe-bulk-update kw-fields misc/kwize))))

(defn heroes-data []
  (let [filename "resources/heroes_information.csv"]
    (->> filename
         misc/csv-file->maps
         (map normalize))))
