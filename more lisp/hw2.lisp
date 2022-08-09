;;;;
;;;; CS334 -- HW 2
;;;;
;;;; Please include solutions to all parts in this file.
;;;;


;;;; (a)

(defun filter (p l)
  (cond ((eq l nil) nil)
	((funcall p (car l)) (cons (car l) (filter p (cdr l))))
	((not (funcall p (car l))) (filter p (cdr l)))))

;;tests
(defun even (x) (eq (mod x 2) 0))
(filter #'even '(6 4 3 5 2))
(filter #'(lambda (x) (>= x 0)) '(-1 1 2 -3 4 -5))

;;;; (b)

(defun set-union (a b)
  (append a (filter #'(lambda (x) (not (member x a))) b)))

(set-union '(1 2 3) '(2 3 4))

(defun set-intersect (y z)
  (filter #'(lambda (x) (member x z)) y))

(set-intersect '(1 2 3) '(2 3 4))


;;;; (c)

(defun exists (p l)
  (not (eq (filter #'(lambda (x) (funcall p x)) l) nil)))

(exists #'(lambda (x) (eq x 0)) '(-1 0 1))
(exists #'(lambda (x) (eq x 2)) '(-1 0 1))

(defun all (p l)
  (not (exists #'(lambda (x) (not (funcall p x))) l)))

(all #'(lambda (x) (> x -2)) '(-1 0 1))
(all #'(lambda (x) (> x 0)) '(-1 0 1))
