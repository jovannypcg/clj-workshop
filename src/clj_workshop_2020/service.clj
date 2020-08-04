(ns clj-workshop-2020.service
	(:require [io.pedestal.http.route :as route]
						[clj-workshop-2020.mounts.datascript :as datascript]
						[datascript.core :as ds]
						[mount.core :as mount]))

(mount/start)

;(def db
;	(ds/db datascript/connection))

(def query
	'[:find ?name
		:in $
		:where
		[_ :name ?name]])

(defn data
	[db]
	(ds/q query db))

(defn respond-hello
	[{:keys [db] :as request}]
	{:status 400 :body (data db)})

(def db-interceptor
	{:name  :db-interceptor
	 :enter (fn [context]
						(update context :request assoc :db (ds/db datascript/connection)))})

(def routes
	(route/expand-routes
		#{["/greet" :get [db-interceptor respond-hello] :route-name :greet-view]}))
