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

; [["C", "D"], ["C", "E"]] -> {"C": [["C", "D"], ["C", "E"]]} -> {"C": #{D, E}}

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

(defn round-n [finish-times [done to-do n in-progress _]]
  (let [
        time (apply min (keys in-progress))

        completed-this-round (get in-progress time)

        still-in-progress (filter (fn [[k v]] (< time k)) in-progress)

        idle-count (- n (count still-in-progress))

        new-done (set (map str (concat completed-this-round done)))

        started-this-round (take idle-count
                                 (sort
                                   (map first
                                        (filter
                                          (fn [[_ constraints]]
                                            (every? #(contains? new-done %) constraints))
                                          to-do)))
                                 )

        started-this-round-to-time (finish-times started-this-round time)

        new-in-progress (merge-with concat started-this-round-to-time still-in-progress)

        still-to-do (filter (fn [[step _]] (not (contains? (set started-this-round) step))) to-do)
        ]
    [new-done still-to-do n new-in-progress completed-this-round]
    )
  )


(defn solve [deps]
  (take-while
        #(not (empty? (nth % 4)))
        (rest (rest
                (iterate
                  #(round-n (fn [started-this-round time] {(+ 1 time) started-this-round}) %)
                  [#{} deps 1 {0 []} []]))))
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


(defn solve2 [deps]
  (take-while

        #(not (empty? (nth % 3)))
        (iterate
          #(round-n (fn [started-this-round time] (into {} (map (fn [s] [(+ 61 (int (get s 0)) time (- (int \A))) s]) started-this-round))) %)
          [#{} deps 5 {0 []} []]))
  )


(defn run2 [in]
  (time (->>
          in
          to-deps
          solve2
          (map #(nth % 3))
          last
          keys
          first
          )
        )
  )

