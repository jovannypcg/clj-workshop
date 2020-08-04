(ns clj-workshop-2020.concurrency.composing-transactions)

(defn- remove-once [pred coll]
  ((fn inner [coll]
     (lazy-seq
      (when-let [[x & xs] (seq coll)]
        (if (pred x)
          xs
          (cons x (inner xs))))))
   coll))

(def left  (ref [:bob]))
(def right (ref []))

(defn contains? [ref elem]
  (dosync
   (->> @ref (filter #(= elem %)) empty? not)))

(defn insert! [ref elem]
  (dosync
   (alter ref conj elem)))

(defn remove! [ref elem]
  (dosync
   (if (contains? ref elem)
     (do (alter ref (partial remove-once #{elem}))
         true)
     false)))

(defn move! [from to elem]
  (dosync
   (if (contains? from elem)
     (do (insert! to elem)
         (remove! from  elem)
         true)
     false)))

[@left
 @right]

(contains? left :bob)

(contains? right :bob)

(move! left right :bob)

(move! right left :bob)









;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(def bucket-init (ref (range 30)))
(def bucket-odd  (ref []))
(def bucket-even (ref []))
(def bucket-five (ref []))

(defn fivey? [n]
  (zero? (mod n 5)))

(defn add-to-bucket! [predicate from to n]
  (when (predicate n)
    (future
      (dosync
       (if  (contains? from n)
         (do (remove! from n)
             (insert! to n)
             true)
         false)))))

{:odd @bucket-odd
 :even @bucket-even
 :fivey @bucket-five
 :init @bucket-init}

(def ant-1 (partial add-to-bucket! odd?   bucket-init bucket-odd))
(def ant-2 (partial add-to-bucket! even?  bucket-init bucket-even))
(def ant-5 (partial add-to-bucket! fivey? bucket-init bucket-five))

(comment

  (dosync
   (pmap #(pmap % @bucket-init) [ant-1 ant-2 ant-5])))















