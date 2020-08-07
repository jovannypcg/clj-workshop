(ns clj-workshop-2020.handler.hero
	(:require [clj-workshop-2020.db.datascript.hero :as db.hero]))


;;REQUEST MAP
#_{:request {:path-params {:id :name}
						 :json-body   {}
						 :name        ""
						 :db          {}
						 :headers     {}}}

;;RESPONSE MAP
#_{:response {:headers {}
							:status  0
							:body    {}}}

(defn greet-get
	[{:keys [path-params]}]
	{:status 200 :body (str "Greeting Id:" (:id path-params))})

(defn occupation-get
	[{:keys [db name]}]
	{:status 200 :body (db.hero/occupations! name db)})

(defn occupation-post
	[{:keys []}]
	{:status :body})

(defn heroes-get
	[{:keys [db name]}]
	(try
		{:status 200 :body (db.hero/details! name db)}
		(catch Exception e
			(throw (ex-info "Not found" {:type :not-found})))))
