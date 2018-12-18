(ns problem13.core-test
  (:require [clojure.test :refer :all]
            [problem13.core :as problem13]))


(deftest parse-track
  (is (= (problem13/parse-track "/----\\") {0 :corner-right-down 1 :horizontal 2 :horizontal 3 :horizontal 4 :horizontal 5 :corner-left-down}))
  (is (= (problem13/parse-track "   /----\\") {3 :corner-right-down 4 :horizontal 5 :horizontal 6 :horizontal 7 :horizontal 8 :corner-left-down}))
  (is (= (problem13/parse-track "   /--+-\\") {3 :corner-right-down 4 :horizontal 5 :horizontal 6 :junction 7 :horizontal 8 :corner-left-down}))
  (is (= (problem13/parse-track "   \\--+-/") {3 :corner-right-up 4 :horizontal 5 :horizontal 6 :junction 7 :horizontal 8 :corner-left-up}))
  (is (= (problem13/parse-track "   \\++/") {3 :corner-right-up 4 :junction 5 :junction 6 :corner-left-up}))
  (is (= (problem13/parse-track "   | \\-/ | ") {3 :vertical 5 :corner-right-up 6 :horizontal 7 :corner-left-up 9 :vertical}))
  (is (= (problem13/parse-track "   V \\-/ ^ ") {3 :vertical 5 :corner-right-up 6 :horizontal 7 :corner-left-up 9 :vertical}))
  (is (= (problem13/parse-track "   V \\</ ^ ") {3 :vertical 5 :corner-right-up 6 :horizontal 7 :corner-left-up 9 :vertical}))
  (is (= (problem13/parse-track "   V \\>/ ^ ") {3 :vertical 5 :corner-right-up 6 :horizontal 7 :corner-left-up 9 :vertical}))
  )

(deftest parse-all
  (is (= (problem13/parse-all ["/----\\"
                               "|    |"
                               "\\----/"])
         {[0 0] :corner-right-down [1 0] :horizontal [2 0] :horizontal [3 0] :horizontal [4 0] :horizontal [5 0] :corner-left-down
          [0 1] :vertical [5 1] :vertical
          [0 2] :corner-right-up [1 2] :horizontal [2 2] :horizontal [3 2] :horizontal [4 2] :horizontal [5 2] :corner-left-up
          }))

  )