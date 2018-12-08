(ns problem7.core-test
  (:require [clojure.test :refer :all]
            [problem7.core :as problem7]
            [runner.core :as runner]
            [clojure.string :as str]))



(deftest run1
  (is (= "BKCJMSDVGHQRXFYZOAULPIEWTN"
         (->>
           [7]
           runner/get-problem-input
           str/split-lines
           problem7/run1
           )
         )))


(deftest run2
  (is (= 1040
         (->>
           [7]
           runner/get-problem-input
           str/split-lines
           problem7/run2
           )
         )))