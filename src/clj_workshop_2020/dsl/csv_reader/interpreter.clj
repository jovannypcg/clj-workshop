(ns clj-workshop-2020.dsl.csv-reader.interpreter
  (:require [clojure.string :as string]))

(def ^:private column-parsers
  {:integer     #(Integer/parseInt %)
   :double      #(Double/parseDouble %)
   :big-integer #(new BigInteger %)
   :big-decimal #(new BigDecimal %)
   :string      identity})

(defn ^:private get-column-parser
  [column-type]
  (get column-parsers column-type))

(defn ^:private parse-column
  [[column column-spec]]
  (let [column-parser (get-column-parser (:type column-spec))]
    (column-parser column)))

(defn ^:private make-columns-parser
  [column-specs]
  (fn [columns]
    (map parse-column (zipmap columns column-specs))))

(defn ^:private make-line-splitter
  [separator]
  (let [pattern (re-pattern separator)]
    (fn [line]
      (string/split line pattern))))

(defn make-reader
  [{:keys [separator has-header? column-specs]}]
  (fn [reader]
    (let [parse-columns (make-columns-parser column-specs)
          split-lines   (make-line-splitter separator)]
      (loop [columns-of-all-lines (->> reader
                                       line-seq
                                       (map split-lines))
             parsed-csv           {}]
        (if (empty? columns-of-all-lines)
          parsed-csv
          (let [columns-of-current-line (first columns-of-all-lines)]
            (if (and has-header?
                     (-> parsed-csv :header not))
              (assoc parsed-csv :header columns-of-current-line)
              (recur (rest columns-of-all-lines)
                     (update parsed-csv
                             :lines
                             conj (parse-columns columns-of-current-line))))))))))
