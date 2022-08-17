(setf (get 'p1 'pattern) '(st ha gr st))
(setf (get 'p2 'pattern) '(bx st ha bx))
(setf (get 'p3 'pattern) '(st ha bx gr))
(setf (get 'p4 'pattern) '(gr gr ha bx))

; To run the program, we invoke with
; (solve_squares nil pieces_avail) or (test)

(setq pieces_avail '(p1 p2 p3 p4))
(setq box_width 2)
(setq box_length 2)

(defun all_but_last (l)
  (cond ((null (cdr l)) nil)
	(t (cons (car l)(all_but_last (cdr l))))))

(defun rotate_list (l n)
  (cond ((zerop n) l)
	(t (rotate_list
	     (append (last l)
		   (all_but_last l)) 
	     (- n 1) ))))

(defun orient (piece orientation)
  (rotate_list (get piece 'pattern) (- orientation 1)))

(defun sidesok (new_piece orientation cur_state)
  (cond ((null cur_state) T)	; no pieces previously placed
	(T (prog (trial len)	; some pieces previously placed
		 (setq trial (orient new_piece orientation))
		 (setq len (length cur_state))
		 (cond
		   ;case of leftmost column:
		   ((= 0 (mod len 2)) ; box_width
		    (return (matchnorth trial cur_state )))
		   ;case of top row
		   ((< len 2) ; box_width
		    (return (matchwest trial cur_state )))
		   ;general case:
		   (T (return
			(and (matchnorth trial cur_state )
			     (matchwest trial cur_state )))
		      ))))))

(defun matchnorth (trial state )
  (eq (caddr trial)  ;north side of rotated new piece
      (car (orient (car (nth 1 state)) ; 2 is box_width
		   (cadr (nth 1 state))))))		   

;      (car (apply 'orient
;		  (nth box_width state) ))
      ; south side of square to the north
;      ))

(defun matchwest (trial state )
  (eq (cadddr trial)  ;west side of rotated new piece
	(cadr (orient (caar state) (cadar state)))))

;      (cadr (apply 'orient
;		   (car state) ))
      ;east side of square to the west
;      ))

; The above code produces warnings about box_length and
; box width being undefined variables but that may simply be
; a bug in SBCL.

(defun solve_squares (cur_state unused_pieces)
  (cond ((null unused_pieces) (show cur_state)) ;solution found
	(T (mapcar (lambda (piece)
		     (mapcar (lambda (orientation)
			(cond ((sidesok piece orientation cur_state)
			       (solve_squares
				 (cons (list piece orientation)
				       cur_state)
				 (delete piece unused_pieces)))
			      (T nil))) '(1 2 3 4))) unused_pieces)
	   nil)))

	;(T (mapcar 'trypiece unused_pieces)
	;   nil) ))

(defun show (soln)
  (prog () 
	(print "solution found")
	(print soln)))

(defun test ()
  (prog (count)
	(setq count 0)
	(solve_squares nil pieces_avail)))

; The 2 functions below need to be rewritten since
; tryorientation does not recognize "piece".  It might
; be possible to pass "piece" to the tryorientation function
; and use a map function that would accomodate the 2 variable
; function.
;(defun trypiece (piece)
;  (mapcar 'tryorientation '(1 2 3 4)))
;   (mapcar (lambda (orientation) (tryorientation orientation							 piece))
	   ;'(1 2 3 4)))

;(defun tryorientation (orientation piece)
;  (cond ((sidesok piece orientation cur_state)
;	 (solve_squares
;	   (cons (list piece orientation) cur_state)
;	   (delete piece unused_pieces)))
;	(t nil)))

