(ns clj-workshop-2020.core
  (:gen-class)
  (:require [mount.core :as mount]))

(mount/start)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
