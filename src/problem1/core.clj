(ns problem1.core
  (:require [clojure.string :as str]))

(defn run [input]
  (->> input str/split-lines
       (map (fn [x] (Integer/parseInt x)))
       (reduce +))
  )
