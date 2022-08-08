;; The list of bad words
* (defvar badwords '(extension algorithms graphics AI midterm))

;; figure out if a word is a badword and censor it
(defun censor-word (word)
    (cond ((member word badwords) 'XXXX)
          (t word)
    )
)

;; censor all the bad words in teh list
(defun censor (list)
    (mapcar #'censor-word list)
)

(print (censor '(I NEED AN EXTENSION BECAUSE I HAD A AI MIDTERM)))

(quit)