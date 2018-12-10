(ns problem8.core
  (:require [clojure.string :as str]))

(defn parse-inputs [rows]
  (->>
    (str/split rows #" ")
    (map #(Integer/parseInt %))
    )
  )

(declare read-node)

(defn read-n-nodes [n rest]
  (if (= 0 n)
    {:nodes [] :rest rest}
    (let [
          {first-node :node after-first :rest} (read-node rest)
          {other-nodes :nodes after-all :rest} (read-n-nodes (- n 1) after-first)
          ]
      {:nodes (cons first-node other-nodes) :rest after-all}
      )
    )
  )
;[1 1 0 1 10 20 1 1 0 1 2 15]

(defn read-node [[child-count meta-count & rest]]
  (let [{children :nodes after-children :rest} (read-n-nodes child-count rest)]
    {:node {:children children :meta (take meta-count after-children)} :rest (drop meta-count after-children)}
    )
  )

(defn sum-meta [node]
  (let [
        node-contrib (reduce + (:meta node))
        children-contrib (reduce + (map sum-meta (:children node)))
        ]
    (+ node-contrib children-contrib)
    )
  )

(defn run1 [rows]
  (let [tree (:node (read-node (parse-inputs (get rows 0))))]
    (sum-meta tree)
    )

  )

(defn sum-meta2 [node]
  (let [children (:children node) meta (:meta node)
        num-children (count children)]
    (if (empty? children)
      (reduce + meta)
      (->>
        meta
        (map #(- % 1))
        (filter #(> num-children %))
        (filter #(<= 0 %))
        (map #(nth children %))
        (map sum-meta2)
        (reduce +)
        )
      )
    )
  )

(defn run2 [rows]
  (time
    (let [tree (:node (read-node (parse-inputs (get rows 0))))]
      (sum-meta2 tree)
      ))

  )
