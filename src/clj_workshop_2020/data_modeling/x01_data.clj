(ns clj-workshop-2020.data-modeling.x01-data)

;; Exercise: Define data for the Parker family according
;; to the following facts.
;;
;; * Petter Parker is son of Richard and Mary Parker, who are maried
;; * Petter aliases are “Spidey” and “Spider-Man”
;; * Richard’s brother is Ben Parker
;; * Ben Parker is maried to May Parker
;;
;; Datascript schemas require these attributes: :db/unique, :db/cardinality, :db/valueType

(def family-data
  [{}
   {}])

;; Exercise: Model at least 2 superheros as data.
;;
;; For example, Batman may have the
;; following characteristics:
;; * His name is "Batman"
;; * His alias is "Bruce Wayne"
;; * His powers are that he is rich
;; * His weapons are his utility belt and a kryptonite spear
;; * His nemeses include the Joker and Penguin
;;
;; How might you model referential relations, such as nemeses, alliances, or
;; familial relationships?

(def hero-data
  [{}
   {}])
