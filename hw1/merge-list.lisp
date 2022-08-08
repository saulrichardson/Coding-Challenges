;; merge two lists
(defun merge-list (x y)
  (cond ((eq x nil) y) ;;x is empty
	((eq y nil) x) ;;y is empty 
	(t (cons (car x) (merge-list y (cdr x)))))) ;;recursive call

(merge-list '(1 2 3) nil)
(merge-list '(1 2 3 ('a 'b 'c)) '(A B C))
(merge-list nil '(1 2 3))
(merge-list '(1 2 3) '(A B C))
(merge-list '(1 2) '(A B C D))
(print (merge-list '((1 2) (3 4)) '(A B)))

(quit)
