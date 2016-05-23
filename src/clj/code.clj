(ns clj.code
  (:use tupelo.core)
  (:require
    [tupelo.schema :as ts]
    [schema.core :as s] )
)

(def digits   [[1 2 3]
               [4 5 6]
               [7 8 9] ])

(def jumps
  "Maps from end points of a jump to the jumped point"
  ;  end   jumped
  ; points  point
  { #{1 3}    2
    #{3 9}    6
    #{7 9}    8
    #{1 7}    4
    #{1 9}    5
    #{3 7}    5 } )

(s/defn all-valid-digits? :- s/Bool
  "Indicates if a digit is valid"
  [digits :- [s/Int]]
  (every? #(<= 1 % 9) digits))

(s/defn each-used-once? :- s/Bool
  "Indicates if each digit is used exactly once"
  [digits :- [s/Int]]
  (=  (count digits)
      (count (set digits))))

(defn jumped-point [d1 d2]
  (get jumps #{d1 d2} ))

(defn is-jump? [d1 d2]
  (not-nil? (jumped-point d1 d2)))

(defn no-invalid-jumps?
  [digits]
  (not-any? truthy?
    ; loop over each sequential pair
    (forv [ii (range (dec (count digits))) ]    ; 1st of pair
      (let [jj           (inc ii)               ; 2nd of pair
            d1           (nth digits ii)
            d2           (nth digits jj)
            used-digits  (into #{} (subvec digits 0 ii))
            illegal-jump (and (is-jump? d1 d2)
                           (not (contains? used-digits (jumped-point d1 d2)))) ]
        illegal-jump))))

(s/defn valid-path :- s/Bool
  [digits :- [s/Int]]
  (and
    (all-valid-digits?  digits)
    (each-used-once?    digits)
    (no-invalid-jumps?  digits)))

(defn -main []
  (println "-main"))
