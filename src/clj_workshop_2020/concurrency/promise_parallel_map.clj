(ns clj-workshop-2020.concurrency.promise-parallel-map)

(defn fn-to-map [n]
  (println (str n ": Started"))
  (Thread/sleep 5000)
  (println (str n ": Finished")))

(defn parallel-map!
  [promise f values]
  (let [runner! (fn [f x]
                  (future (f x)))
        running (doall (map (partial runner! f) values))
        results (doall (map deref running))]
    (deliver promise results)
    (println "parallel-map delivered results to promise")
    (println "parallel-map continues working")
    (Thread/sleep 15000)
    (println "parallel-map finishes and returns a value")
    (count results)))

(defn do-stuff! []
  (let [results (promise)
        values  (range 36)
        mapped  (future (parallel-map! results fn-to-map values))]
    (println "Map is running while this is printed")
    (println (str "Let's check if map has finished: " (realized? mapped)))
    (println "We can continue with other work")
    (Thread/sleep 3000)
    (println "Now we need to use the value of the promise")
    (println "We will be blocked until a value is available")
    @results
    (println "we got unblocked because parallel-map delivered results")
    (println (str "Has parallel-map stopped running? " (realized? mapped)))
    (println "Let's wait for it")
    (println (str "parallel-map returned: " @mapped))))


(do-stuff!)
