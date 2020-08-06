(ns clj-workshop-2020.service
	(:require [clj-workshop-2020.handler.hero :as handler.hero]
						[clj-workshop-2020.mounts.datascript :as datascript]
						[datascript.core :as ds]
						[io.pedestal.http.route :as route]
						[mount.core :as mount]
						[io.pedestal.http.body-params :as body-params]
						[clojure.data.json :as json]
						[io.pedestal.interceptor.error :as error-int]))

;;CONTEXT MAP
#_{:request  {:path-params {}}
	 :response {}}

(def path->name {:name
								 :enter})

(def db-interceptor {:name
										 :enter})

(def body->json {:name
								 :leave})

(def error-interceptor {})

(def routes
	(route/expand-routes
		#{}))

#_["/path" :get [handler] :route-name :name :constraints {:id ""}]

#_(route/try-routing-for routes :prefix-tree "/greet/p001" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/p001" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/1" :get)

#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :post)
