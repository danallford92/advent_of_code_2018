(ns problem4.core_test
  (:require [clojure.test :refer :all]
            [problem4.core :as problem4]))


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

