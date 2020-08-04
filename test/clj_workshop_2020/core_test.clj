(ns clj-workshop-2020.core-test
  (:require [clj-workshop-2020.core :refer :all]
            [clojure.test :refer :all]))

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
