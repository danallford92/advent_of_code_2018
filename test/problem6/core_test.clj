(ns problem6.core-test
  (:require [clojure.test :refer :all]
            [problem6.core :as problem6]))


(deftest equidistant-intersections
  (is (= #{{:type :finite :points #{[-5 0] [0 -5]}}} (problem6/equidistant-intersections [0 0] [-5 -5])))
  (is (= #{{:type :finite :points #{[5 0] [0 5]}}} (problem6/equidistant-intersections [0 0] [5 5])))
  (is (= #{{:type :finite :points #{[3 0] [1 2]}}} (problem6/equidistant-intersections [3 2] [1 0])))
  (is (= #{{:type :finite :points #{[3/2 1] [5/2 0]}}} (problem6/equidistant-intersections [3 1] [1 0])))
  (is (= #{{:type :finite :points #{[5/2 1] [3/2 0]}}} (problem6/equidistant-intersections [1 1] [3 0])))
  (is (= #{{:type :vertical :x 3/2}} (problem6/equidistant-intersections [1 2] [2 2])))
  (is (= #{{:type :horizontal :y 5/2}} (problem6/equidistant-intersections [1 2] [1 3])))
  (is (= #{{:type :finite :points #{[1/2 3] [3/2 2]}}} (problem6/equidistant-intersections [0 2] [2 3])))
  (is (= #{{:type :finite :points #{[3 1/2] [2 3/2]}}} (problem6/equidistant-intersections [2 0] [3 2])))
  )

;(deftest equidistant
;  (is (= #{{:type :finite :points #{[-5 0] [0 -5]}}
;           #{{:type :infinite :orientation :horizontal :point [-5 0]}}
;           #{{:type :infinite :orientation :vertical :point [0 -5]}}
;           }
;         (problem6/equidistant [0 0] [-5 -5])))
;  )

