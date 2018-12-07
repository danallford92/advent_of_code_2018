(ns problem7.core
  (:require
    [clojure.set :as s]))


(defn to-dep [line]
  (let [
        [_ constraint step] (re-matches #"Step (.) must be finished before step (.) can begin." line)
        ]
    [step constraint]
    )
  )

(defn leaves [deps]
  (map (fn [x] [x #{}])
       (filter #(not (contains? (into #{} (map first deps)) %))
               (into #{} (mapcat second deps))
               )
       )
  )

(defn to-deps [lines]
  (let [
        deps (->> lines
                  (map to-dep)
                  (group-by first)
                  (map (fn [[step steps-with-deps]] [step (into #{} (map second steps-with-deps))]))
                  )]
    (concat deps (leaves deps))
    )
  )

(defn round-n [[done to-do n in-progress _]]
  (let [
        started-this-round (take n
              (sort
                (map first
                     (filter
                       (fn [[_ constraints]]
                         (s/subset? constraints done))
                       to-do)))
              )
        time (apply min (keys in-progress))

        completed-this-round (get in-progress time)

        new-in-progress {(+ 1 time) started-this-round}

        still-to-do (filter (fn [[step _]] (not (contains? (set started-this-round) step))) to-do)
        ]
    [(set (concat started-this-round done)) still-to-do n new-in-progress started-this-round]
    )
  )

(defn solve [deps]
  (take-while #(not (empty? (mapcat second (nth % 3))))
              (rest (iterate round-n [#{} deps 1 {0 []} []])))
  )



(defn run1 [in]
  (time (->>
          in
          to-deps
          solve
          (mapcat #(nth % 4))
          (apply str)
          )
        )
  )

(defn run2 [in]
  (time (->>
          in
          to-deps
          solve
          (map #(nth % 2))
          (apply str)
          )
        )
  )

(def deps

  (to-deps
    [
     "Step C must be finished before step A can begin."
     "Step C must be finished before step F can begin."
     "Step A must be finished before step B can begin."
     "Step A must be finished before step D can begin."
     "Step B must be finished before step E can begin."
     "Step D must be finished before step E can begin."
     "Step F must be finished before step E can begin."
     ]))
