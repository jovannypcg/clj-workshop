(ns clj-workshop-2020.state.mount.database
  (:require [mount.core :refer [defstate]]
            [clj-workshop-2020.state.mount.config :refer [config]]))

(defn start-connection
  [config]
  (do
    (println "Starting database connection...")
    (Thread/sleep 2000)
    (atom {:host   (get-in config [:database :host])
           :port   (get-in config [:database :port])
           :status :up
           :data   []})))

(defn stop-connection
  [connection]
  (do
    (println "Stopping database connection...")
    (Thread/sleep 2000)
    (swap! connection :status :down)))

;; The `connection` state is declared here.
