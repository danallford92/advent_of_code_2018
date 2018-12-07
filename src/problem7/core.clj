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

(defn round-n [[done to-do a]]
  (let [
        done-this-round (first
                          (sort
                            (map first
                                 (filter
                                   (fn [[_ constraints]]
                                     (s/subset? constraints done))
                                   to-do)))
                          )

        still-to-do (filter (fn [[step _]] (not (= step done-this-round))) to-do)
        ]
    [(set (cons done-this-round done)) still-to-do done-this-round]
    )
  )

(defn solve [deps]
  (take-while #(not (nil? (nth % 2)))
              (iterate round-n [#{} deps ""]))
  )



(defn run1 [in]
  (time (->>
          in
          to-deps
          solve
          (map #(nth % 2))
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
