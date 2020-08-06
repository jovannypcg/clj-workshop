(ns clj-workshop-2020.service
  (:require [clj-workshop-2020.handler.hero :as handler.hero]
            [clj-workshop-2020.mounts.datascript :as datascript]
            [datascript.core :as ds]
            [io.pedestal.http.route :as route]
            [mount.core :as mount]
            [clojure.data.json :as json]
            [io.pedestal.interceptor.error :as error-int]))

