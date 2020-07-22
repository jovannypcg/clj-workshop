(ns clj-workshop-2020.state.component.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component])
  (:import (java.io PushbackReader)))

(defn load-config
  "Loads a file given the incoming path and transforms it to a
  traditional Clojure map."
  [path]
  (-> path
      slurp
      .getBytes
      io/reader
      PushbackReader.
      edn/read))

;; Config component is declared here.

;; ...

;; Constructor for the Config component is declared here.

;; ...
