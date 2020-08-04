(ns clj-workshop-2020.db.datascript.util.hero-data
  (:require [clj-workshop-2020.db.datascript.util.misc :as misc]))

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

;; It is common to have a function that transforms the data into a schema
;; compliant format. This can also be achieved with database functions, which
;; are beyond the scope of this workshop.
(defn hero->datascript-format [{:keys [publisher] :as hero}]
  (cond-> hero publisher (update :publisher (fn [p] {:name p}))))
