;; reverse the items in the list
(defun rev (x)
  (cond ((atom x) x)
	((eq (cdr x) nil) x)  ;;only one element in list
	(t (append (rev (cdr x)) (cons (car x) nil)))))  ;;append reverse of cdr to first element of list

(print (rev '(1)))
(rev '(1 2))
(rev nil)
(rev 'A)
(rev '(A (B C) D))
(rev '((A B) (C D)))
(rev '(A (1 2) jk l))
(print (rev '(A B C D)))

(quit)
