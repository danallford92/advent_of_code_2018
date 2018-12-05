(ns problem5.core-test
  (:require [clojure.test :refer :all]
            [problem5.core :as problem5]))


(deftest are-opposite
  (is (problem5/are-opposite "a" "A"))
  (is (problem5/are-opposite "A" "a"))
  (is (not (problem5/are-opposite "A" "b")))
  (is (not (problem5/are-opposite "a" "b")))
  )

(deftest anhilate
  (is (= "" (problem5/anhilate "aA")))
  (is (= "aa" (problem5/anhilate "aa")))
  (is (= "a" (problem5/anhilate "a")))
  (is (= "b" (problem5/anhilate "aAb")))
  (is (= "ab" (problem5/anhilate "ab")))
  (is (= "b" (problem5/anhilate "aAb")))
  (is (= "dabCBAcaDA" (problem5/anhilate "dabAcCaCBAcCcaDA")))
  )