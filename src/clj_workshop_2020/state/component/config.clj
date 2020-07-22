(ns clj-workshop-2020.state.component.config
  (:require [clojure.edn :as edn]
            [com.stuartsierra.component :as component]))

(defn load-config
  "Loads a file given the incoming path and transforms it to a
  traditional Clojure map."
  [path]
  (-> path
      slurp
      edn/read-string))

;; Config component is declared here.

;; ...

;; Constructor for the Config component is declared here.

;; ...
