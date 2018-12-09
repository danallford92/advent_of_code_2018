(ns problem9.core-test
  (:require [clojure.test :refer :all]
            [problem9.core :as problem9]
            ))

;(deftest next-game-state
;  (is (= {:current-index 3, :marbles [0 8 4 9 2 5 1 6 7], :turn-number 9, :scores {} :num-players 9}
;         (problem9/next-game-state {:current-index 1 :marbles [0 8 4 2 5 1 6 7] :turn-number 8 :scores {} :num-players 9})))
;  (is (= {:current-index 1 :marbles [0 2 1] :turn-number 2 :scores {} :num-players 5}
;         (problem9/next-game-state {:current-index 1 :marbles [0 1] :turn-number 1 :scores {} :num-players 5})))
;  )
;
;(deftest next-game-state-23
;  (is (= {:current-index 6, :marbles [0 16 8 17 4 18 19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15], :turn-number 23, :scores {5 32} :num-players 9}
;         (problem9/next-game-state {:current-index 13 :marbles [0 16 8 17 4 18 9 19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15] :turn-number 22 :scores {} :num-players 9})))
;  )

(deftest generate-at
  (is (= 32 (time (apply max (vals (:scores (problem9/generate 9 25)))))))
  ;(is (= 8317 (apply max (vals (last (take 1618 (map :scores (problem9/generate 10))))))))
  ;(is (= 146373 (time (apply max (vals (:scores (problem9/generate 13 7999)))))))
  (is (= 146373 (time (apply max (vals (:scores (problem9/generate 13 7999)))))))
  ;(is (= 2764 (apply max (vals (last (take 1104 (map :scores (problem9/generate 17))))))))
  ;(is (= 54718 (time (apply max (vals (last (take 6111 (map :scores (problem9/generate 21)))))))))
  ;(is (= 37305 (time (apply max (vals (last (take 5807 (map :scores (problem9/generate 30)))))))))
  (is (= 388024 (time (apply max (vals (:scores (problem9/generate 470 72170)))))))
  ;(is (= 388024 (time (apply max (vals (:scores (problem9/generate 470 7217000)))))))
  )
