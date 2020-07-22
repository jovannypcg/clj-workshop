(ns clj-workshop-2020.state.mount.database
  (:require [mount.core :refer [defstate]]
            [clj-workshop-2020.state.mount.config :as config]))

(defn create-connection
  [config]
  (do
    (println "Starting database connection...")
    (Thread/sleep 3000)
    (atom {:host (get-in config [:database :host])
           :port (get-in config [:database :port])})))

;; The `connection` state is declared here.
