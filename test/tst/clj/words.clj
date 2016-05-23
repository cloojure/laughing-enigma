(ns tst.clj.words
  (:use clj.words
        clojure.test 
        tupelo.core)
  (:require
    [clojure.string :as str]
    [tupelo.misc :as tm] ))

(deftest t-get-all-search-strings
  (let [
        t-base-str        " a b c
                            d e f
                            x y z "

        t-row-mat        [[\a \b \c]
                          [\d \e \f]
                          [\x \y \z]]

        t-row-mat-rev    [[\c \b \a]
                          [\f \e \d]
                          [\z \y \x]]

        t-row-mat-tx     [[\a \d \x]
                          [\b \e \y]
                          [\c \f \z]]

        t-row-mat-tx-rev [[\x \d \a]
                          [\y \e \b]
                          [\z \f \c]]

        row-mat           (baseline-chars t-base-str)
        row-mat-rev       (mapv reverse row-mat)
        row-mat-tx        (transpose row-mat)
        row-mat-tx-rev    (mapv reverse row-mat-tx)
  ]
    (is (= row-mat          t-row-mat))
    (is (= row-mat-rev      t-row-mat-rev))
    (is (= row-mat-tx       t-row-mat-tx))
    (is (= row-mat-tx-rev   t-row-mat-tx-rev))
    (is (= (set (get-all-search-strings t-base-str ))
           #{ "abc" "cba"
              "def" "fed"
              "xyz" "zyx"
              "adx" "xda"
              "bey" "yeb"
              "cfz" "zfc" } ))
  ))

(deftest t-word-in-str?
  (is (word-in-str? "abcdefxyz" "abc"))
  (is (word-in-str? "abcdefxyz" "def"))
  (is (word-in-str? "abcdefxyz" "xyz"))
  (is (not (word-in-str? "abcdefxyz" "kyz")))
)


(def m1 [[1 2 3]
         [4 5 6]])
(def m1t [[1 4]
          [2 5]
          [3 6]] )

(deftest t-basic
  (is (= 2 (num-rows m1)))
  (is (= 3 (num-cols m1)))
  (is (= [4 5 6]  (get-row m1 1)))
  (is (= [2 5]    (get-col m1 1)))
  (is (= m1t      (transpose m1)))
)

(def words-1
  " A O T D L R O W
    L C B M U M L U
    D R U J D B L J
    P A Z H Z Z E F
    B C Z E L F H W
    R K U L V P P G
    A L B L P O P Q
    B E M O P P J Y	" )

(deftest t-count-words-in-matrix
  (is (= 2 (count-words-in-matrix words-1 "HELLO")))
  (is (= 1 (count-words-in-matrix words-1 "WORLD")))
  (is (= 2 (count-words-in-matrix words-1 "BUZZ")))
  (is (= 0 (count-words-in-matrix words-1 "CLOJURE")))
  (is (= 0 (count-words-in-matrix words-1 "COWABUNGA")))
)

(deftest t-samples
  (is (= \b (get "abc" 1)))
  (is (= [\a \b \c] (vec "abc")))
  (is (= 5 (get-in [[1 2 3]
                    [4 5 6]] [1 1])))
  (is (= "abc" (str/join [\a \b \c])))
  (is (= "cba" (str/join (reverse [\a \b \c]))))
  (is (= #{0 1 2} (set (range 3))))
  (is (truthy? (re-find (re-pattern "def") "abcdefxyz")))
  )

