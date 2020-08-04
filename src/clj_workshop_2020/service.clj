(ns clj-workshop-2020.service
  (:require [clj-workshop-2020.handler.hero :as handler.hero]
            [clj-workshop-2020.mounts.datascript :as datascript]
            [datascript.core :as ds]
            [io.pedestal.http.route :as route]
            [mount.core :as mount]))

(mount/start)

(def db-interceptor
  {:name  :db-interceptor
   :enter (fn [context]
            (update context :request assoc :db (ds/db datascript/connection)))})

(def routes
  (route/expand-routes
   #{["/greet" :get [db-interceptor handler.hero/respond-hello] :route-name :greet-view]}))
