(ns clj-workshop-2020.core-test
  (:require [clojure.test :refer :all]
            [clj-workshop-2020.core :as core]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]))


#_(def service-fn
  (::http/service-fn (http/create-servlet core/service)))


#_(def url-for
    (route/url-for-routes host/routes))

#_(deftest greet
    (let [response (test/response-for service-fn :get (url-for :greet-get :path-params {:id 1}))
          {:keys [status body]} response]
      (is (= 200 status))
      (is (= "{:message \"Greetings!!! 1\", :conn {:conn \"localhost\"}}"
            body))))





#_(comment
(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest addition-tests
  (is (= 5 (+ 3 2)))
  (is (= 10 (+ 5 5))))

(deftest pr-str-read-string-round-trip
  (is (= [1 2 3] (read-string (pr-str [1 2 3]))))
  (is (= {:a :b :c :d} (read-string (pr-str {:a :b :c :d})))))

(defn add
  [x y]
  (+ x y))

(deftest add-x-to-y
  (is (= 5 (add 2 3))))

(deftest add-x-to-y-a-using-are
  (are [x y] (= 5
                (add x y))
    2 3
    1 4
    3 2))
)
