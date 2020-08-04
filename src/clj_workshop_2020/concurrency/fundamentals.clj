(ns clj-workshop-2020.concurrency.fundamentals)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Future
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; This will run on a different thread


(comment
  (future 1)

  ;; This will block and wait till it's complete
  (deref (future 1))

  ;; A short hand for deref
  @(future 1)

  (def f (future (println "Running thread")
                 1))

  ;; Guess the output of second deref
  @f
  @f)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Promise
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Different futures can pass on values with futures as well.


(comment
  (def p (promise))

  ;; This will block and execute
  (future (Thread/sleep 8000) (deliver p 1))

  @p)



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Atoms
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; A symbol in clojure is just an identity which points to a value / state


(def a 1)

;; It can point to an atom

(def a (atom 1))

;; Since atom's value can change we need to deref it to access it

(deref a)

;; A short hand to deref is @

@a

(swap! a (constantly 5))



;; Let's try this with an atom


(comment
  (do
    (def b (atom []))
    (doseq [n (range 2000)]
      (future (swap! b conj n)))
    (println (count @b))))

(comment
  (do
    (def b (atom []))

    (doseq [n (range 2000)]
      (future (swap! b
                     conj
                     (do (when (= n 5)
                           (println (count @b))
                           (Thread/sleep 100))
                         n))))
    (Thread/sleep 100)
    (println (count @b))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; refs
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Refs ensure safe mutation of multiple shared states using STM

;; dosync is macro to start transaction

;; ref-set sets it to a value

;; alter runs a function on the value

;; Values held by refs must be immutable preferably clojure persistent
;; structures


(def a-ref-num (ref 0))
(def b-ref-num (ref 0))

(comment
  (doseq [n (range 10)]
    (future
      (dosync
       (println "Transaction - " n)
       (ref-set a-ref-num n)
       (ref-set b-ref-num n)))))

[@a-ref-num @b-ref-num]

(do
  (def a-ref (ref 0))
  (def b-ref (ref 0))
  (def a-atom (atom 0))
  (def b-atom (atom 0)))

(comment
  (do
    (doseq [n (range 100)]
      (future

        (dosync
         (ref-set a-ref n)
         (Thread/sleep (rand-int 200))
         (ref-set b-ref n))

        (reset! a-atom n)
        (Thread/sleep (rand-int 200))
        (reset! b-atom n)))

    (doseq [n (range 5)]
      (Thread/sleep 1000)
      (println (format "Ref values A - %s,  B - %s\nAtom values A - %s,  B - %s\n"
                       @a-ref
                       @b-ref
                       @a-atom
                       @b-atom)))))

(def a-alter (ref []))
(def b-alter (ref []))

(comment
  (doseq [n (range 10)]
    (future
      (dosync
       (println "Transaction - " n)
       (alter a-alter conj n)
       (Thread/sleep (rand-int 20))
       (alter b-alter conj n)))))

[@a-alter @b-alter]

(def a-commute (ref 0))
(def b-commute (ref 0))

(comment
  (doseq [n (range 10)]
    (future
      (dosync
       (println "Transaction - " n)
       (commute a-commute (partial + n))
       (Thread/sleep (rand-int 20))
       (commute b-commute #(str n %))))))

[@a-commute @b-commute]