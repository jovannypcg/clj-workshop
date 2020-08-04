(ns clj-workshop-2020.db.datascript.util.misc
  (:require [clojure-csv.core :as csv]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as cs]
            [cuerdas.core :as cc]))

;; ### Keywordize strings by:
;;  1. replacing all sequences of nonword characters with a space
;;  1. Removing all single quotes
;;  1. Turning the string into a keyword using the
;;  [Cuerdas](https://cljdoc.org/d/funcool/cuerdas/2020.03.26-3/doc/user-guide) library.
(defn kwize [s]
  (-> s (cs/replace #"\W+" " ") (cs/replace #"'" "") cc/keyword))

;;Update key k in map m with function f if there is a value in m for k.
(defn maybe-update [m k f]
  (cond-> m (some? (m k)) (update k f)))

;;Bulk update several keys ks in map m with function f
(defn maybe-bulk-update [m ks f]
  (reduce (fn [m k] (maybe-update m k f)) m ks))

;; Remove entries in a seq of pairs for which any of the following are true:
;; * The first item (key) is nil
;; * The second item (value) is nil, "", or "-"
(defn remove-bad-entries [m]
  (into (empty m)
        (remove (fn [[k v]] (or (nil? k) (contains? #{"-" "" nil} v))) m)))

;; Convert a sequence of vectors into a sequence of maps, assuming the first row
;; of the vectors is a header row
(defn table->maps [[headers & cols]]
  (let [h (map cc/keyword headers)]
    (->> cols
         (map (fn [col] (zipmap h (map #(cond-> % (string? %) cs/trim) col))))
         (map remove-bad-entries))))

(defn csv-file->maps [f]
  (-> f
      slurp
      csv/parse-csv
      ;Once you get your solution in place, remove the external solution
      table->maps))

(defn read-edn [resource]
  (->> (io/resource resource) slurp edn/read-string))
