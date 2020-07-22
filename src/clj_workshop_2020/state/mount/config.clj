(ns clj-workshop-2020.state.mount.config
  (:require [mount.core :refer [defstate]]
            [clojure.edn :as edn]))

(def config-path "resources/config.edn")

(defn load-config
  "Loads a file given the incoming path and transforms it to a
  traditional Clojure map."
  [path]
  (-> path
      slurp
      edn/read-string))

;; The `config` state is declared here.
