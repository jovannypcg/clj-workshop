(ns clj-workshop-2020.data-modeling.util.supplemental-hero-data
  (:require [clojure.string :as cs]
            [clj-workshop-2020.data-modeling.util.misc :as misc]))

;; ## Supplemental Hero Data
;;
;; Process SuperheroDataset.csv to get additional source data. This is a fairly
;; ugly file and requires extra processing.
;;
;; We are not going to do any exercises here, but it is worthwhile to scroll to
;; the bottom of the file and view the rich comments.
(defn remove-trash-fields [m]
  (let [trash-values #{"No team connections added yet." "No alter egos found."}]
    (into {} (remove (fn [[_ v]] (trash-values v)) m))))

(defn process-team-affiliations [s]
  (let [teams (map cs/trim (cs/split s #","))
        parser (partial re-matches #"(Formerly:)?([^\(]+)(?:\(([^\)]+)\))?")]
    (loop [[team & r] teams former? false res []]
      (if team
        (let [[_ f n l] (parser team)
              former? (or former? (some? f))
              team-data {:team/name    (cs/trim n)
                         :team/leader? (some? l)
                         :team/former? former?}]
          (recur r former? (conj res team-data)))
        res))))

(defn captrim-all [v] (map (comp cs/capitalize cs/trim) v))
(defn process-occupations [s] (captrim-all (cs/split s #"[,;]")))
(defn process-bases [s] (captrim-all (cs/split s #";")))
(defn process-alter-egos [s] (captrim-all (cs/split s #",")))

(defn process-units-field [s]
  (let [multipliers {"ton" 1000 "meter" 100}
        [mag units] (-> s (cs/split #"//") last cs/trim (cs/split #"\s+"))]
    (* (Double/parseDouble (cs/replace mag #"," ""))
       (multipliers units 1))))

(defn process-aliases [s]
  (map cs/trim (cs/split s #",")))

(defn process-relatives [s]
  (for [[_ names relation] (re-seq #";?([^;\(]*)\(([^\)]+)\)" s)
        name (->> (cs/split names #",") (map cs/trim) (filter seq))]
    {:relative     {:name (cs/trim name)}
     :relationship (misc/kwize (cs/trim relation))}))

(defn gather-stats [m]
  (let [attrs [:combat :durability :intelligence :power :speed :strength :total-power :unnamed-0]
        stats (select-keys m attrs)]
    (assoc
      (apply dissoc m attrs)
      :stats (map (fn [[k v]] {:stat/name k :stat/value v}) stats))))

(defn normalize [m]
  (let [dbl-fields [:speed :intelligence :unnamed-0 :power :durability :strength
                    :total-power :combat]
        kw-fields [:alignment :hair-color :eye-color :gender :skin-color :race]
        unit-fields [:weight :height]]
    (-> m
        (misc/maybe-bulk-update dbl-fields #(Double/parseDouble %))
        (misc/maybe-bulk-update kw-fields misc/kwize)
        (misc/maybe-bulk-update unit-fields process-units-field)
        remove-trash-fields
        (misc/maybe-update :team-affiliation process-team-affiliations)
        (misc/maybe-update :aliases process-aliases)
        (misc/maybe-update :alter-egos process-alter-egos)
        (misc/maybe-update :occupation process-occupations)
        (misc/maybe-update :relatives process-relatives)
        (misc/maybe-update :base process-bases)
        gather-stats)))

(defn supplemental-hero-data []
  (let [filename "resources/SuperheroDataset.csv"]
    (->> filename misc/csv-file->maps (map normalize))))

(defn add-stat-ids [{hero-name :name :as hero}]
  (letfn [(add-stat-id [stats]
            (map (fn [{stat-name :stat/name :as stat}]
                   (assoc stat :hero.stat/id (str hero-name "/" (name stat-name))))
                 stats))]
    (update hero :stats add-stat-id)))

(defn add-relative-ids [{hero-name :name :as hero}]
  (letfn [(add-relative-id [relatives]
            (map (fn [{:keys [relative] :as r}]
                   (assoc r :hero.relative/id (str hero-name "/" (:name relative))))
                 relatives))]
    (update hero :relatives add-relative-id)))

(defn add-team-affiliations [{hero-name :name :as hero}]
  (letfn [(add-team-affiliation [team-affiliation]
            (map (fn [{tn :team/name :as r}]
                   (assoc r :hero.team/id (str hero-name "/" tn)))
                 team-affiliation))]
    (update hero :team-affiliation add-team-affiliation)))

(defn creator-name->creator-ref [{:keys [creator] :as hero}]
  (cond-> hero creator (update :creator (fn [p] {:name p}))))

(defn hero->datascript-format [hero]
  (-> hero
      add-stat-ids
      ;; add-relative-ids
      ;; add-team-affiliations
      ;; creator-name->creator-ref
      ))
