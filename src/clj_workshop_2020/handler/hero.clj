(ns clj-workshop-2020.handler.hero
  (:require [clj-workshop-2020.db.datascript.hero :as db.hero]))


;;REQUEST MAP
#_{:request {:path-params {}
             :json-body {}
             :name ""
             :db {}}}

(defn greet-get
  [{:keys []}]
  {:status :body})

(defn occupation-get
  [{:keys []}]
  {:status :body})

(defn occupation-post
  [{:keys []}]
  {:status :body})

(defn heores-get
  [{:keys []}]
  {:status :body})
