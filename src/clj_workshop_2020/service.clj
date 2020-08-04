(ns clj-workshop-2020.service
  (:require [io.pedestal.http.route :as route]
            [clj-workshop-2020.mounts.datascript :as datascript]
            [datascript.core :as ds]
            [mount.core :as mount]))

(mount/start)

(def db
  (ds/db datascript/connection))

(def query
  '[:find ?name
    :in $
    :where
    [_ :name ?name]])

(def data
  (ds/q query db))

(defn respond-hello
  [request]
  {:status 400 :body data})

(def routes
  (route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet-view]}))
