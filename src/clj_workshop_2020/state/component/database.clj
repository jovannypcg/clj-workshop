(ns clj-workshop-2020.state.component.database
  (:require [com.stuartsierra.component :as component]))

(defn start-connection
  [host port]
  (do
    (println "Starting database connection...")
    (Thread/sleep 2000)
    (atom {:host host :port port :data []})))

;; Database component is declared here.

;; ...

;; Constructor for the Database component is declared here.

;; ...
