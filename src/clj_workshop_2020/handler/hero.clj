(ns clj-workshop-2020.handler.hero
  (:require [clj-workshop-2020.db.datascript.hero :as db.hero]))

(defn respond-hello
  [{:keys [db] :as request}]
  {:status 400 :body (db.hero/data db)})
