(ns tst.clj.code
  (:use clj.code
        clojure.test 
        tupelo.core)
  (:require [tupelo.misc :as tm]))

(deftest t-each-used-once?
  (is (each-used-once? [1 2 3]))
  (is (not (each-used-once? [1 2 3 1]))))

(deftest t-all-valid-digits?
  (is (all-valid-digits? [1 2 3 4 5 6 7 8 9]))
  (is (not (all-valid-digits? [0 1 2 3 4 5 6 7 8 9])))
  (is (not (all-valid-digits? [1 2 3 4 5 6 7 8 9 10]))))

(deftest t-no-invalid-jumps?
  (is (no-invalid-jumps? [1 2]))
  (is (no-invalid-jumps? [2 1]))
  (is (no-invalid-jumps? [1 4]))
  (is (no-invalid-jumps? [4 1]))
  (is (no-invalid-jumps? [1 5]))
  (is (no-invalid-jumps? [2 5]))

  (is (not (no-invalid-jumps? [1 3])))
  (is (not (no-invalid-jumps? [3 1])))
  (is (not (no-invalid-jumps? [7 9])))
  (is (not (no-invalid-jumps? [9 7])))
  (is (not (no-invalid-jumps? [1 7])))
  (is (not (no-invalid-jumps? [7 1])))
  (is (not (no-invalid-jumps? [1 9])))
  (is (not (no-invalid-jumps? [9 1])))

  (is      (no-invalid-jumps? [1 2 3]))
  (is      (no-invalid-jumps? [3 2 1]))
  (is      (no-invalid-jumps? [2 1 3]))
  (is      (no-invalid-jumps? [2 3 1]))
  (is (not (no-invalid-jumps? [1 3 2])))
  (is (not (no-invalid-jumps? [3 1 2])))

  (is      (no-invalid-jumps? [1 5 9]))
  (is      (no-invalid-jumps? [9 5 1]))
  (is      (no-invalid-jumps? [5 1 9]))
  (is      (no-invalid-jumps? [5 9 1]))
  (is (not (no-invalid-jumps? [1 9 5])))
  (is (not (no-invalid-jumps? [9 1 5])))
)

(deftest t-valid-path
  (is (true?  (valid-path [1 6 7 4])))                      ;; knights jump is valid
  (is (true?  (valid-path [2 1 3])))                        ;; 2 is already used, so we can cross it
  (is (false? (valid-path [1 3 2])))                        ;; can't get from 1 to 3 without using 2 first
  (is (false? (valid-path [1 9])))                          ;; can't cross 5 without using
  (is (false? (valid-path [1 2 3 2 1])))                    ;; can't use dots more than once
  (is (false? (valid-path [0 1 2 3])))                      ;; there's no dot 0
)

(spyx (count
        (filter truthy?
          (map valid-path (tm/permute (range 1 10))))))

(deftest t-count-perms
)

