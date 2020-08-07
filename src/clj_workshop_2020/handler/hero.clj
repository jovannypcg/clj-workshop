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
	[{:keys []}]
	{:status :body})

(defn occupation-get
	[{:keys []}]
	{:status :body})

(defn occupation-post
	[{:keys []}]
	{:status :body})

(defn heroes-get
	[{:keys []}]
	{:status :body})
