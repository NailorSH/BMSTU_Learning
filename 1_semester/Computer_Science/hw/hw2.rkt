(load "D:/Files/Education/Univer/Learning/1_semester/Computer_Science/trace.rkt")

; HW 2

;; 1. List processing

; (my-range a b d) => O(n), n = b-a
(define (my-range a b d) ;1 2 1
  (if (>= a b)
      '()
      (append (list a) (my-range (+ a d) b d))))

;  (my-flatten lst) => O(n^2) или O(n*m),
; где n - кол-во элементов, а m - макс. глубина вложенности
(define (my-flatten lst)
  (define (my-flatten-helper lst acc stk)
    (cond ((null? lst) 
           (if (null? stk) (reverse acc)
               (my-flatten-helper (car stk) acc (cdr stk))))
          ((pair? (car lst))
           (my-flatten-helper (car lst) acc (if (null? (cdr lst)) 
                                                stk 
                                                (cons (cdr lst) stk))))
          (else 
           (my-flatten-helper (cdr lst) (cons (car lst) acc) stk))))
  (my-flatten-helper lst '() '()))

; (my-element? x xs) => O(n)
(define (my-element? x xs)
  (and (not (null? xs))
       (or (equal? x (car xs))
           (my-element? x (cdr xs)))))

; (my-filter pred? xs) => O(n)
(define (my-filter pred? xs)
  (if (pair? xs)
      (append (if (pred? (car xs))
                  (list (car xs))
                  '())
              (my-filter pred? (cdr xs)))
      '()))

; (my-fold-left op xs) => O(n)
(define (my-fold-left op xs)
  (if (<= (length xs) 1)
      (car xs)
      (my-fold-left op (cons (op (car xs) (cadr xs)) (cddr xs)))))

; (my-fold-right op xs) => O(n)
(define (my-fold-right op xs)
  (if (<= (length xs) 1)
      (car xs)
      (op (car xs) (my-fold-right op (cdr xs)))))


;; 2. Sets

; n = (length xs)
; (list->set xs) => O(n)
(define (list->set xs)
  (if (null? xs)
      '()
      (if (my-element? (car xs) (cdr xs))
          (list->set (cdr xs))
          (cons (car xs) (list->set (cdr xs))))))

; (set? xs) => O(n)
(define (set? xs)
  (or (null? xs) (equal? xs (list->set xs))))

; (union xs ys) => O(n)
(define (union xs ys)
  (list->set (append xs ys)))

; (intersection xs ys) => O(n)
(define (intersection xs ys)
  (if (or (null? xs) (null? ys))
      '()
      (if (my-element? (car xs) ys)
          (list->set (append (list (car xs))
                             (intersection (cdr xs) ys)))
          (intersection (cdr xs) ys))))

; (difference xs ys) => O(n)
(define (difference xs ys)
  (cond ((null? xs) '())
        ((null? ys) xs)
        ((my-element? (car xs) ys) (difference (cdr xs) ys))
        (else
         (list->set
          (append (list (car xs))
                  (difference (cdr xs) ys))))))

; (symmetric-difference xs ys) => O(n)
(define (symmetric-difference xs ys)
  (difference (union xs ys) (intersection xs ys)))

; (set-eq? xs ys) => O(n^2)
(define (set-eq? xs ys)
  (equal?
   (difference (union xs ys) (intersection xs ys))
   '()))

#|
(list->set '(1 1 2 3))                       
(set? '(1 2 3))                              
(set? '(1 2 3 3))                            
(set? '())                                  
(union '(1 2 3) '(2 3 4))                    
(intersection '(1 2 3) '(2 3 4))             
(difference '(1 2 3 4 5) '(2 3))             
(symmetric-difference '(1 2 3 4) '(3 4 5 6))
(set-eq? '(1 2 3) '(3 2 1))                  
|#


;; 3. Working with strings

(define (whitespace? x)
  (or (equal? x '#\newline)
      (equal? x '#\space)
      (equal? x '#\tab)
      (equal? x '#\return)))

(define (list-trim-left xs)
  (if (or (null? xs)
          (not (whitespace? (car xs))))
      xs
      (if (whitespace? (car xs))
          (list-trim-left (cdr xs))
          (cons (car xs) (list-trim-left (cdr xs))))))

(define (list-trim-right xs)
  (reverse (list-trim-left (reverse xs))))

(define (list-trim xs)
  (list-trim-left (list-trim-right xs)))

; ((string-trim-left xs) => O(n),
; n - количество пробельных символов в начале строки
(define (string-trim-left str)
  (list->string (list-trim-left (string->list str))))

; (string-trim-right xs) => O(n),
; n - количество пробельных символов в конце строки
(define (string-trim-right str)
  (list->string (list-trim-right (string->list str))))

; (string-trim xs) => O(n), 
; n - количество пробельных символов в начале и в конце строки
(define (string-trim str)
  (list->string (list-trim (string->list str))))

(define (list-prefix? a b)
  (or (not (pair? a))
      (and (pair? b)
           (and (equal? (car a) (car b))
                (list-prefix? (cdr a) (cdr b))))))

(define (list-suffix? a b)
  (list-prefix? (reverse a) (reverse b)))

(define (list-infix? a b)
  (and (pair? b)
       (or (list-prefix? a b)
           (list-infix? a (cdr b)))))

; (string-prefix? a b) => O(len(a))
(define (string-prefix? a b)
  (list-prefix? (string->list a) (string->list b)))

; (string-suffix? a b) => O(len(a))
(define (string-suffix? a b)
  (list-suffix? (string->list a) (string->list b)))

; (string-infix? a b) => O(len(a)*len(b))
(define (string-infix? a b)
  (list-infix? (string->list a) (string->list b)))

(define (add-substr str sep)
  (if (or (equal? (string-length str) 0)
          (string-prefix? sep str))
      ""
      (string-append
       (make-string 1 (string-ref str 0))
       (add-substr (substring str 1) sep))))

; (string-split a b) => O(len(str)^2)
(define (string-split str sep)
  (if (equal? (string-length str) 0)
      '()
      (if (string-prefix? sep str)
          (string-split (substring str (string-length sep)) sep)
          (let ((substr (add-substr str sep)))
            (cons substr (string-split
                          (substring str (string-length substr))
                          sep))))))


;; 4. Multidimensional vectors
(define (compos xs)
  (if (null? xs)
       1
       (* (car xs) (compos (cdr xs)))))

(define (pos-vec sizes indices)
  (if (null? (cdr sizes))
       (car sizes)
       (+ (* (car indices) (compos (cdr sizes)))
          (pos-vec (cdr sizes) (cdr indices)))))

(define (make-multi-vector sizes . fill) 
  (cons sizes
        (list (if (not (eqv? (length fill) 1)) 
                  (make-vector (compos sizes))
                  (make-vector (compos sizes) (car fill))))))

(define (multi-vector? m)
  (and (list? m)
       (list? (car m))
       (vector? (cadr m))))

(define (multi-vector-ref m indices)
  (vector-ref (cadr m) (pos-vec (car m) indices)))

(define (multi-vector-set! m indices x)
  (vector-set! (cadr m) (pos-vec (car m) indices) x))



;; 5. Function composition
(define (build_op f x)
  (f x))

(define (o . xs)
  (lambda (x)
    (my-fold-right build_op (append xs (list x)))))

;; achievement
#|
(define (vector->string v)
  (list->string (vector->list v)))

(define (string->vector s)
  (list->vector (string->list s)))

(define (vec-len v)
  (length (vector->list v)))

(define (list-trim-right xs)
  (define (loop v)
    (if (whitespace?
         (vector-ref v (- (vec-len v) 1)))
        (let ((s (vector->string v)))
          (loop (string->vector
                 (substring s 0 (- (vec-len v) 1)))))
        v))
  (vector->list (loop (list->vector xs))))
|#