(ns problem9.core)

(defn insert-after [list id value]
  (let
    [
     node-before (get list id)
     node-after-id (:next node-before)
     new-id (rand)
     to-insert {:value value :prev id :next node-after-id}
     node-after (get list node-after-id)
     new-node-after (assoc node-after :prev new-id)
     new-node-before (assoc node-before :next new-id)
     new-list (assoc list id new-node-before new-id to-insert node-after-id new-node-after)
     ]
    [new-id new-list]
    )
  )

(defn remove-at [list id]
  (let [
        to-remove (get list id)
        next-id (:next to-remove)
        prev-id (:prev to-remove)
        next (get list next-id)
        prev (get list next-id)
        next-replacement (assoc next :prev prev-id)
        prev-replacement (assoc prev :next next-id)
        ]
    (assoc list id nil next-id next-replacement prev-id prev-replacement)
    )
  )

(defn find-node-n-before [list id n]
  (reduce
    (fn [acc n] (:prev (get list acc)))
    id
    (range 7))
  ; (loop [l list current-id id num-left n]
  ;   (if (= 0 num-left)
  ;     current-id
  ;     (recur l (:prev (get l current-id)) (dec num-left))
  ;     )
  ;   )
  )

(defn next-game-state [current-state turn-number num-players]
  (let
    [
     {current-index :current-index marbles :marbles scores :scores} current-state
     ]
    (if (= 0 (mod turn-number 23))
      (let [
            ;<remove>
            index-to-remove (find-node-n-before marbles current-index 7)
            to-remove (get marbles index-to-remove)
            new-marbles (remove-at marbles index-to-remove)
            new-current-index (:next to-remove)
            ;</remove>

            ;<increaseScore>
            current-player (mod turn-number num-players)
            score-increment (+ (:value to-remove) turn-number)
            new-player-score (+ score-increment (get scores current-player 0))

            new-scores (assoc scores current-player new-player-score)
            ;</increaseScore>
            ]
        {:current-index new-current-index :marbles new-marbles :scores new-scores}
        )
      (let [
            next-index (:next (get marbles current-index))
            [new-current-index new-marbles] (insert-after marbles next-index turn-number)
            ]
        {:current-index new-current-index :marbles new-marbles :scores scores}
        )
      )
    )
  )


(defn generate [num-players num-marbles]
  (reduce (fn [acc turn-number] (next-game-state acc turn-number num-players)) 
  {:current-index "4321" :marbles {"1234" {:value 0, :prev "4321", :next "4321"} "4321" {:value 1, :prev "1234", :next "1234"}} :scores {}} 
  (range 2 num-marbles)
  )
  )

(defn run1 [in]
  (time (apply max (vals (:scores (generate 470 (* 1 72170))))))
  )

(defn run2 [in]
  (time (apply max (vals (:scores (generate 470 (* 100 72170))))))
  )


