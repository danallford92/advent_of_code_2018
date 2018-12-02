(ns problem2.core-test
  (:require [clojure.test :refer :all]
            [problem2.core :as problem2]))


(deftest letter-count-frequencies-test
  (is (= #{1} (problem2/letter-counts "abcdef")))
  (is (= #{1} (problem2/letter-counts "a")))
  (is (= #{1 2} (problem2/letter-counts "12233")))
  (is (= #{1 2 3} (problem2/letter-counts "122333")))
  )

(deftest run1
  (is (= 0 (problem2/run1 `("abcdef"))))
  (is (= 1 (problem2/run1 `("122333"))))
  (is (= 2 (problem2/run1 `("122333" "12233"))))
  (is (= 4 (problem2/run1 `("122333" "122333"))))
  (is (= 1 (problem2/run1 `("12233344555"))))
  )

(deftest run1-at
  (is (= 12
         (problem2/run1
           `("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))))
  )

(deftest remove-nth
  (is (= "bc" (problem2/remove-nth "abc" 0)))
  (is (= "ac" (problem2/remove-nth "abc" 1)))
  (is (= "ab" (problem2/remove-nth "abc" 2)))
  )

(deftest stubs
  (is (= #{"bc" "ac" "ab" } (problem2/stubs "abc")))
  )

(deftest run2-at
  (is (= "fgij"
        (problem2/run2 `("abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"))
        )))


(deftest run2-at2
  (is (= "fgij"
         (problem2/run2 `("aaaaa" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"))
         )))