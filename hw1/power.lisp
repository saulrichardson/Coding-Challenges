;;helper for computing square
(defun sq (x) (* x x))

;;exp calcualtor --> y is base and z is exp
(defun fastexp (y z)
  (cond ((eq z 1) y)  ;;exp is one --> return base
	((or (eq y 0) (eq y 1)) y)  ;;eliminate unnecessary recursive calls
	((eq (mod z 2) 0) (sq (fastexp y (/ z 2))))  ;;exp is even
	((not (eq (mod z 2) 0)) (* y (fastexp y (- z 1))))))   ;;exp must be odd

;;(trace fastexp) ;;if you want to see recursive calls
(fastexp 2 2) ;;4
(fastexp 0 58373464) ;;0
(fastexp 1 10000000) ;;1
(fastexp 5 10) ;;9765625
(fastexp 10 9) ;;1000000000

#|proof:
Claim: A call to (fastexp b e) takes at most 2log(e) multiplications.

Base Case: e = 1. (fastexp b 1) hits the first conditional statement since the exponent equals 1 and
just returns the base b. No multiplication steps are performed. 2log(1) = 0 and 0 <= 0. 

Induction hypothesis: For all k < e, (fastexp b e) takes at most 2log(e) multiplications.

Prove for e: 
Case e is even: (fastexp b e) goes to the third condition, where we square the result of (fastexp b e/2).
Because of our inductive hypothesis we know (fastexp b e/2) takes at most 2log(e/2) operations, and together
with our squaring will be 2log(e/2) + 1 operations. 2log(e/2) + 1 = 2log(e) - 2log(2) + 1 = 2log(e) - 1 which
is less than or equal to 2log(e).

Case e is odd. (fastexp b e) goes to the fourth condition, where we run (fastexp b e-1). Since e is odd, e-1 is even,
and thus we would run (fastexp b (e-1)/2) and square that result before multiplying by the base, which would give us two operations, and using our inductive hypothesis
we know that (fastexp b (e-1)/2) will take at most 2log((e-1)/2), giving a total of at most 2log((e-1)/2) + 2 = 2log(e-1) - 2log(2) + 2 = 
2log(e - 1) which is less than or equal to 2log(e), and thus our inductive hypothesis holds in all cases.
|#

(quit)