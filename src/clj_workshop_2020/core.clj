(ns clj-workshop-2020.core
  (:gen-class)
  (:require [clj-workshop-2020.service :as service]
            [io.pedestal.http :as http]))

(def port 8890)

(def service )

(defn create-server
  []
  )

(defn start-server []
  (do
    (println (str "Server has started in port " port))
    ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  )
