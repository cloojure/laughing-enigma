(ns tst.clj.ttt
  (:use clj.ttt
        clojure.test
        tupelo.core))

(deftest t-basic
  (is (= (row b1 0) [1 2 3] ))
  (is (= (row b1 1) [4 5 6] ))
  (is (= (row b1 2) [7 8 9] ))

  (is (= (col b1 0) [1 4 7] ))
  (is (= (col b1 1) [2 5 8] ))
  (is (= (col b1 2) [3 6 9] ))

  (is (= (transpose b1) [ [1 4 7]
                          [2 5 8]
                          [3 6 9] ] ))

  (is (= (diag-main b1) [1 5 9] ))
  (is (= (diag-anti b1) [3 5 7] ))

  (is (= true  (x? :x)))
  (is (= false (x? :o)))
  (is (= true  (o? :o)))
  (is (= false (o? :x)))

  (is (= :x  (triple-winner [:x :x :x])))
  (is (= :o  (triple-winner [:o :o :o])))
  (is (= nil (triple-winner [:o :x :o])))
)

(def g1 [[:x :o :x]
         [:x :o :o]
         [:x :x :o]] )

(def g2 [[:o :x :x]
         [:x :o :x]
         [:x :o :o]] )

(def g3 [[:x :o :x]
         [:x :o :x]
         [:o :x :o]] )

(deftest t-game
  (assert (= :x (game-winner g1)))
  (assert (= :o (game-winner g2)))
  (assert (nil? (game-winner g3)))
)
