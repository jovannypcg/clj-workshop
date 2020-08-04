(ns clj-workshop-2020.core
  (:gen-class)
  (:require [clj-workshop-2020.service :as service]
            [io.pedestal.http :as http]))

(def port 8890)

(def service
  {::http/routes service/routes
   ::http/type   :jetty
   ::http/port   port})

(defn create-server
  []
  (http/create-server service))

(defn start-server []
  (do
    (println (str "Server has started in port " port))
    (http/start (create-server))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  #_(start-server))
