;; Define a variable holding the data:
* (defvar grades '((Riley (90.0 33.3))
                    (Jessie (100.0 85.0 97.0))
                    (Quinn (70.0 100.0))))
;; Get the student name
(defun name (student)
    (car student)
)

;; Get the student grades
(defun grades (student)
    (car (cdr student))
)

;; Return the grades of a given student
(defun lookup (name students)
    (cond ((eq students nil) nil)
          ((eq (name (car students)) name) (grades (car students)))
          (t (lookup name (cdr students)))
    )
)

;; Get the sum of a list
(defun sum (gradelist)
    (cond ((eq gradelist nil) 0)
          (t (+ (car gradelist) (sum (cdr gradelist))))
    )
)
;; Get the average of a list of grades
(defun avg (gradelist)
    (/ (sum gradelist) (length gradelist))
)

;; Get the average grades of a student
(defun student-avg (student)
    (cons (name student) (cons (avg (grades student)) nil))
)

;; Get the list of students and their average grade
(defun averages (grades)
    (mapcar #'student-avg grades)
)

;; Compare the average grades of two students
(defun compare-students (student1 student2)
    (cond ((< (grades student1) (grades student2)) t)
          (t nil)
    )
)

(print (lookup 'Riley grades))
(print (averages grades))
(print (sort (averages grades) #'compare-students))

(quit)