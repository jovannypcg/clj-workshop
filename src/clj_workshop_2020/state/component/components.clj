(ns clj-workshop-2020.state.component.components
  (:require [clj-workshop-2020.state.component.config :as config]
            [clj-workshop-2020.state.component.database :as database]
            [com.stuartsierra.component :as component]))

(def config-path "resources/config.edn")

;; System is declared here.

(defn create-system
  []
  ())

;; Start system here.

(def system
  (-> config-path
      ;; ...
      ))
