(ns problem8.core-test
  (:require [clojure.test :refer :all]
            [problem8.core :as problem8]
            ))

(deftest read-leaf
  (is (= {:node {:children [] :meta [99]} :rest [1 2 3]} (problem8/read-node [0 1 99 1 2 3])))
  (is (= {:node {:children [] :meta [1 2]} :rest [1 2 3]} (problem8/read-node [0 2 1 2 1 2 3])))
  )

(deftest read-node-depth-1
  (is (=
        {
         :node
         {
          :children
          [
          {:children [] :meta [1 2]} {:children [] :meta [1]}
           ]
          :meta [0]} :rest [1 99 2 1 0 0 7]
         }
        (problem8/read-node [2 1 0 2 1 2 0 1 1 0 1 99 2 1 0 0 7])))
  )

(deftest read-node-at
  (is (=
        {
         :node {
                :children
                      [
                       {:children [] :meta [10 11 12]}
                       {
                        :children
                              [
                               {:children [] :meta [99]}
                               ]
                        :meta [2]
                        }
                       ]
                :meta [1 1 2]
                }
         :rest []}

        (problem8/read-node [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2]))
      )
  )

(deftest sum-meta
  (is (= 138 (problem8/sum-meta (:node (problem8/read-node [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])))))
  )

(deftest sum-meta2
  (is (= 66 (problem8/sum-meta2 (:node (problem8/read-node [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])))))
  )