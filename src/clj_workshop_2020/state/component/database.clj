(ns clj-workshop-2020.state.component.database
  (:require [com.stuartsierra.component :as component]))

(defn start-connection
  [host port]
  (do
    (println "Starting database connection...")
    (Thread/sleep 2000)
    (atom {:host host :port port :status :up :data []})))

(defn stop-connection
  [connection]
  (do
    (println "Stopping database connection...")
    (Thread/sleep 2000)
    (assoc connection :status :down)))

;; Database component is declared here.

;; ...

;; Constructor for the Database component is declared here.

;; ...
