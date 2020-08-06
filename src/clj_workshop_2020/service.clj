(ns clj-workshop-2020.service
  (:require [clj-workshop-2020.handler.hero :as handler.hero]
            [clj-workshop-2020.mounts.datascript :as datascript]
            [datascript.core :as ds]
            [io.pedestal.http.route :as route]
            [mount.core :as mount]
            [clojure.data.json :as json]
            [io.pedestal.interceptor.error :as error-int]))



#_(route/try-routing-for routes :prefix-tree "/greet/p001" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/p001" :get)
#_(route/try-routing-for routes :prefix-tree "/greet/1" :get)


#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :get)
#_(route/try-routing-for routes :prefix-tree "/heroes/Spider-Man/occupations" :post)
