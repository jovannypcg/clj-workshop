(ns clj-workshop-2020.mounts.datascript
  (:require [clj-workshop-2020.db.datascript.util.seed :as util.db]
            [datascript.core :as ds]
            [mount.core :refer [defstate]]))

(def schema util.db/db-schema)

(defn create-connection
  []
  (let [conn (ds/create-conn schema)]
    (do
      (util.db/seed! conn)
      (println "Datascript connection has been created")
      (println "Seeding Datascript database...")
      conn)))

(defstate connection :start (create-connection))
