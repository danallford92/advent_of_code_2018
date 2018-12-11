(ns problem11.core-test
  (:require [clojure.test :refer :all]
            [problem11.core :as problem11]
            ))


(deftest compute-square-power
  (is (= 4 (problem11/compute-square [3 5] 8)))
  (is (= -5 (problem11/compute-square [122,79] 57)))
  (is (= 0 (problem11/compute-square [217,196] 39)))
  (is (= 4 (problem11/compute-square [101,153] 71)))
  )