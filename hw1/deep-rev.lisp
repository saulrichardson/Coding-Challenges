;; Reverses all items in the list, and then applies the function to those items
(defun deep-rev (x)
    (cond ((atom x) x)
          ;; If item is list, reverse it
          ((eq (cdr x) nil) (cons (deep-rev (car x)) nil))
          ;; reverse all the items within the list
          (t (append (deep-rev (cdr x)) (cons (deep-rev (car x)) nil)))
    )
)

(print (deep-rev '((A B) (C D))))
(print (deep-rev '(A B C D)))
(print (deep-rev '(A (B C) D)))
(print (deep-rev '(1 2 ((3 4) 5))))

(quit)