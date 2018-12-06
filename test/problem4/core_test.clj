(ns problem4.core_test
  (:require [clojure.test :refer :all]
            [runner.core :as runner]
            [problem4.core :as problem4]
            [clojure.string :as str]))


(deftest guard-on-duty
  (is (= [1 2 3 1 1 1 2] (problem4/guard-on-duty `({:guard-id 1} {:guard-id 2} {:guard-id 3} {:guard-id 1} {} {} {:guard-id 2})))))

(deftest with-guard-and-previous
  (is (= [[1 {:guard-id 2} {:guard-id 1}]
          [2 {:guard-id 3} {:guard-id 2}]
          [3 {:guard-id 1} {:guard-id 3}]
          [1 {} {:guard-id 1}]
          [1 {} {}]
          [1 {:guard-id 2} {}]]
         (problem4/with-guard-and-previous `({:guard-id 1} {:guard-id 2} {:guard-id 3} {:guard-id 1} {} {} {:guard-id 2})))))

(deftest run1
  (is (= 84834
         (->>
           [4]
           runner/get-problem-input
           str/split-lines
           problem4/run1
           )
         )))

(deftest run2
  (is (= 53427
         (->>
           [4]
           runner/get-problem-input
           str/split-lines
           problem4/run2
           )
         )))