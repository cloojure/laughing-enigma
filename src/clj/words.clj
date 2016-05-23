(ns clj.words
  (:use tupelo.core)
  (:require
    [clojure.string :as str]
    [tupelo.misc :as tm]
    [schema.core :as s] )
)

(def Vec [s/Any] )
(def Mat2D [[s/Any]] )

(s/defn baseline-chars :- Mat2D
  "Accept a multi-line string, & reformat into a seq of Strings with no whitespace."
  [multi-line-str :- s/Str]
  (mapv #(vec (str/replace % " " ""))
    (str/split-lines multi-line-str)))

(s/defn num-rows :- s/Int
  [char-mat :- Mat2D]
  (count char-mat))

(s/defn num-cols :- s/Int
  [char-mat :- Mat2D]
  (count (first char-mat)))

(s/defn get-char :- s/Any
  [char-mat  :- Mat2D
   row :- s/Int
   col :- s/Int]
  (get-in char-mat [row col]))

(s/defn get-row :- Vec
  [char-mat :- Mat2D
   row :- s/Int]
  (vec (get-in char-mat [row])))

(s/defn get-col :- Vec
  [char-mat  :- Mat2D
   col :- s/Int]
  (glue
    (forv [ii (range (num-rows char-mat)) ]
      (get-char char-mat ii col))))

(defn transpose
  [char-mat]
  (forv [icol (range (num-cols char-mat)) ]
    (get-col char-mat icol)))

(s/defn get-all-search-strings :- [s/Str]
  [multi-line-str :- s/Str]
  (let [row-mat           (baseline-chars multi-line-str)
        row-mat-rev       (mapv reverse row-mat)
        row-mat-tx        (transpose row-mat)
        row-mat-tx-rev    (mapv reverse row-mat-tx)

        all-rows-mat      (glue row-mat
                                row-mat-rev
                                row-mat-tx
                                row-mat-tx-rev)
        all-search-strings (mapv str/join all-rows-mat) ]
    all-search-strings ))

(defn word-in-str?
  [search-str word]
  (truthy? (re-find (re-pattern word) search-str)))

(defn count-words-in-matrix
  [multi-line-str word]
  (let [all-search-strings (s/validate [s/Str]
                             (get-all-search-strings multi-line-str))
        str-matches        (mapv #(word-in-str? % word) all-search-strings)
        num-found          (count (keep-if truthy? str-matches))
        ]
    num-found ))
