(ns clj.ttt
  (:use tupelo.core)
  (:require
    [tupelo.schema :as ts]
    [schema.core :as s]
    )
)

(def b1 [[1 2 3]
         [4 5 6]
         [7 8 9]])
(s/defn row :- ts/List
  "Returns row with index ii (zero-based)."
  [board  :- ts/TupleList
   ii     :- s/Int]
  (board ii))

(s/defn col :- ts/List
  "Returns row with index jj (zero-based)."
  [board  :- ts/TupleList
   jj     :- s/Int]
  (forv [ii (range 3)]
        (get-in board [ii jj] )))

(s/defn transpose :- ts/List
  "Returns the transpose of rows/cols."
  [board :- ts/List]
  (forv [jj (range 3)]
        (col board jj )))

(s/defn diag-main :- ts/List
  "Returns the main diagonal of board."
  [board :- ts/List]
  (forv [ii (range 3)]
        (get-in board [ii ii] )))

(s/defn diag-anti :- ts/List
  "Returns the anti-diagonal of board."
  [board :- ts/List]
  (forv [ii (range 3)]
    (get-in board [ii (- 2 ii)] )))

(s/defn x?
  "Returns true if for keyword :x"
  [arg]
  (= :x arg))
(s/defn o?
  "Returns true if for keyword :o"
  [arg]
  (= :o arg))

(s/defn triple-winner
  "Given a Vec3, returns:
    :x      if all elements are :x
    :o      if all elements are :o
    nil     otherwise "
  [triple :- ts/Vec3]
  (cond
    (every? x? triple) :x
    (every? o? triple) :o
    :else nil))

(s/defn game-winner :- s/Any
  "Given a Tic-Tac-Toe board, returns:
    :x      if :x has won
    :o      if :o has won
    nil     otherwise
  Simply returns the first winner found. Does not diagnose nonsensical
  boards with more than one winner. "
  [board :- ts/TupleList]
  (let [triples (s/validate [ts/Vec3] (glue board
                                            (transpose board)
                                            [(diag-main board)
                                             (diag-anti board)]))
        winners       (mapv triple-winner triples)
        first-winner  (first (keep-if not-nil? winners)) ]
    first-winner))

(defn -main []
  (println "-main"))
