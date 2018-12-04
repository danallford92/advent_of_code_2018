(ns problem3.core
  (:require
    [problem1.core :as problem1]))

(defn parse-input [line]
  (
    ->> line
        (re-matches #"#(.*?) @ (\d+),(\d+): (\d+)x(\d+)")
        rest
        (map #(Integer/parseInt %))
        (into [])
        )
  )

(defn to-squares [[id x y width height]]
  (for [x-coord (set (range x (+ x width))), y-coord (set (range y (+ y height)))] (list x-coord y-coord))
  )


(defn contentious-squares [squares]
  (->>
    squares
    (mapcat to-squares)
    problem1/find-duplicates
    )
  )

(defn run1 [input]
  (->>
    input
    (map parse-input)
    contentious-squares
    count
    )
  )

(defn run2 [input]
  (time
    (let [squares (->>
                    input
                    (map parse-input)) contended
          (contentious-squares squares)]
      (filter #(not-any? (fn [s] (contains? contended s)) (to-squares %)) squares)))
  )

