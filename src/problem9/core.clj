(ns problem9.core)


;(defn insert-at [coll n item]
;  (let [[before after] (split-at n coll)]
;    (vec (concat before [item] after))
;    1
;    )
;  )
;
;(defn remove-at [coll n]
;  (let [[before after] (split-at n coll)]
;    (vec (concat before (rest after)))
;    )
;  )
;
;(defn next-game-state [current-state]
;  (let
;    [
;     {current-index :current-index marbles :marbles prev-turn-number :turn-number scores :scores num-players :num-players} current-state
;     num-marbles (count marbles)
;     turn-number (inc prev-turn-number)
;     ]
;    (if (= 0 (mod turn-number 23))
;      (let [
;            index-to-remove (mod (- current-index 7) num-marbles)
;            value-of-removed (nth marbles index-to-remove)
;            score-increment (+ value-of-removed turn-number)
;
;            current-player (mod turn-number num-players)
;            new-player-score (+ score-increment (get scores current-player 0))
;
;            new-marbles (remove-at marbles index-to-remove)
;            new-num-marbles (- num-marbles 1)
;            new-current-index (mod index-to-remove new-num-marbles)
;
;
;            new-scores (assoc scores current-player new-player-score)
;            ]
;        {:current-index new-current-index :marbles new-marbles :turn-number turn-number :scores new-scores :num-players num-players}
;        )
;      (let [
;            new-current-index (mod (+ 2 current-index) num-marbles)
;            new-marbles (insert-at marbles new-current-index turn-number)
;            ]
;        {:current-index new-current-index :marbles new-marbles :turn-number turn-number :scores scores :num-players num-players}
;        )
;      )
;    )
;  )

(defn insert-after [list id value]
  (let
    [
     node-before (get list id)
     node-after (get list (:next node-before))
     new-id (str (rand) (rand) (rand))
     to-insert {:value value :prev id :next (:id node-after) :id new-id}
     new-node-after (assoc node-after :prev new-id)
     new-node-before (assoc node-before :next new-id)
     new-list (assoc list (:id node-before) new-node-before new-id to-insert (:id node-after) new-node-after)
     ]
    (if (= node-before node-after)
      (let [new-node (assoc node-before :prev new-id :next new-id)]
        (assoc list id new-node new-id to-insert)
        )
      new-list
      )
    )
  )

(defn remove-at [list id]
  (let [
        to-remove (get list id)
        next (get list (:next to-remove))
        prev (get list (:prev to-remove))
        next-replacement (assoc next :prev (:id prev))
        prev-replacement (assoc prev :next (:id next))
        ]
    (assoc list id nil (:id next-replacement) next-replacement (:id prev-replacement) prev-replacement)
    )
  )

(defn find-node-n-before [list id n]
  (if (= 0 n)
    id
    (find-node-n-before list (:prev (get list id)) (dec n))
    )
  )

(defn next-game-state [current-state]
  (let
    [
     {current-index :current-index marbles :marbles prev-turn-number :turn-number scores :scores num-players :num-players} current-state
     turn-number (inc prev-turn-number)
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
        {:current-index new-current-index :marbles new-marbles :turn-number turn-number :scores new-scores :num-players num-players}
        )
      (let [
            next-index (:next (get marbles current-index))
            new-marbles (insert-after marbles next-index turn-number)
            new-current-index (:next (get new-marbles next-index))
            ]
        {:current-index new-current-index :marbles new-marbles :turn-number turn-number :scores scores :num-players num-players}
        )
      )
    )
  )


(defn generate [num-players max-val]
  ;(reduce (fn [acc _] (next-game-state acc)) {:current-index 1 :marbles [0 1] :turn-number 1 :scores {} :num-players num-players} (range 2 max-val))
  (iterate next-game-state {:current-index 1 :marbles [0 1] :turn-number 1 :scores {} :num-players num-players})
  )


