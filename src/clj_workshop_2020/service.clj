(ns clj-workshop-2020.service
  (:require [io.pedestal.http.route :as route]))

(defn respond-hello
  [request]
  {:status 400 :body "Hello"})

(def routes
  (route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet-view]}))
