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
#_{:request  {:path-params {:id :name}}
	 :response {}}

(mount/start)

(def path->name {:name :path->name
								 :enter (fn [{{:keys [path-params]} :request :as context}]
													(if-let [name (:name path-params)]
														(update context :request assoc :name name)
														context))})

(def db-interceptor {:name :db-interceptor
										 :enter (fn [context]
															(update context :request assoc :db (ds/db datascript/connection)))})

(defn to->json
	[response]
	(-> response
		(update :body json/write-str)
		(assoc-in [:headers "Content-Type"] "application/json")))

(def body->json {:name :body->json
								 :leave (fn [context]
													(update context :response to->json))})

;:stage = enter or leave
;:exception-type
;:exception
;:interceptor
;:process-id

(defn get-response-from-error
	[error]
	(let [type (get (ex-data error) :type)]
		(case type
			:not-found {:status 404 :body  "Custom Not found"}
			:else {:status 500 :body "Custom Internal server error"})))

(def error-interceptor
	(error-int/error-dispatch [context ex]
		[{:exception-type :clojure.lang.ExceptionInfo}]
		(assoc context :response (get-response-from-error ex))
		:else
		(assoc context :io.pedestal.impl.interceptor/error ex)))

(def routes
	(route/expand-routes
		#{["/greet/:id" :get [handler.hero/greet-get] :route-name :greet-get :constraints {:id #"[0-9]+"}]
			["/heroes/:name" :get [error-interceptor body->json db-interceptor path->name handler.hero/heroes-get] :route-name :heroes-get]
			["/heroes/:name/occupations" :get [body->json db-interceptor path->name handler.hero/occupation-get] :route-name :occupation.get]}))

#_["/path" :get [handler] :route-name :name :constraints {:id ""}]

#_(route/try-routing-for routes :prefix-tree "/greet/1" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/p001/rewew" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/1" :post)

#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :post)
