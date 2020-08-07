(ns clj-workshop-2020.core-test
  (:require [clojure.test :refer :all]
            [clj-workshop-2020.core :as core]
            [clj-workshop-2020.service :as service]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]))


(def service-fn
  (::http/service-fn (http/create-servlet core/service)))

(def url-for
  (route/url-for-routes service/routes))

(deftest greet
  (let [response {}
        {:keys [status body]} response]
    (is (= 200 status))
    (is (= "Greeting Id:1"
          body))))
