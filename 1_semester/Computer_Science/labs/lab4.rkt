;;ЛР 4

;; ~~~~~~~~~~~~~~ 1 ~~~~~~~~~~~~~~
(define call/cc call-with-current-continuation)
(define the-void (if #f #f))
(define (print expr)
  (if (not (equal? expr the-void))
      (begin
        (write expr)
        (newline))))
(define r #f)

(print (call/cc
        (lambda (cc)
          (set! r cc)
          "Assertions included")))

(define-syntax assert
  (syntax-rules ()
    ((assert expr) (if expr #t (begin (display "FAILED: ")
                                      (r 'expr))))))

;(define (use-assertions)
;  (load "D:/Files/Онлайн-образование/Универ/Учёба/1 семестр/Основы информатики/ЛР/lab4.rkt"))


;; ~~~~~~~~~~~~~~ 2 ~~~~~~~~~~~~~~
(define (save-data data file-path)
  (call-with-output-file file-path
    (lambda(port)
      (write data port))))

(define (load-data file-path)
  (call-with-input-file file-path
    (lambda(port)
      (read port))))

(define (counting_lines file-path)
  (define number 0)

  (define (loop_counting_lines port)
    (if (not(eof-object? (peek-char port)))
        (begin
          (if (and  (equal? (read-char port) #\newline)
                    (not (equal? (read-char port) #\newline)))
              (set! number (+ number 1)))
          (loop_counting_lines port))))
  
  (call-with-input-file file-path
    (lambda(port)
      (begin
        (set! number 1)
        (loop_counting_lines port)
        number))))


;; ~~~~~~~~~~~~~~ 3 ~~~~~~~~~~~~~~
(define trib
  (let ((known-results '((2 1) (1 0) (0 0))))
    (lambda (n)
      (let ((res (assoc n known-results)))
        (if res
            (cadr res)
            (let ((trib-n (+ (trib (- n 1)) (trib (- n 2)) (trib (- n 3)))))
              (begin
                (set! known-results (cons (list n trib-n) known-results))
                trib-n)))))))


;; ~~~~~~~~~~~~~~ 4 ~~~~~~~~~~~~~~
(define-syntax my-if
  (syntax-rules ()
    ((my-if cond? true false)
     (force (or (and cond? (delay true)) (delay false))))))


(define-syntax my-if-cc
  (syntax-rules ()
    ((my-if-cc cond true false)
     (force (call/cc 
             (lambda (return) 
               (or (and cond (return (delay true)))
                   (return (delay false)))))))))


;; ~~~~~~~~~~~~~~ 5 ~~~~~~~~~~~~~~
(define-syntax my-let
  (syntax-rules ()
    ((my-let ((name1 def1)
              (name2 def2) ...)
             expr1 expr2 ...)
     ((lambda (name1 name2 ...) expr1 expr2 ...) def1 def2 ...))))

(define-syntax my-let*
  (syntax-rules ()
    ((my-let* ((name def)) expr1 ...) (my-let ((name def)) expr1 ...))
    ((my-let* ((name1 def1) . defs) expr1 ...)
     (my-let ((name1 def1)) (my-let* defs expr1 ...)))))


;; ~~~~~~~~~~~~~~ 6A ~~~~~~~~~~~~~~
(define-syntax when
  (syntax-rules ()
    ((when cond? expr1 ...) (if cond? (begin expr1 ...)))))
(define-syntax unless
  (syntax-rules ()
    ((unless cond? expr1 ...) (if (not cond?) (begin expr1 ...)))))

;; ~~~~~~~~~~~~~~ 6Б ~~~~~~~~~~~~~~
(define-syntax for
  (syntax-rules (in as)
    ((for x in xs expr1 expr2 ...) (for-each (lambda (x) (begin expr1 expr2 ...)) xs))
    ((for xs as x expr1 expr2 ...) (for-each (lambda (x) (begin expr1 expr2 ...)) xs))))


;; ~~~~~~~~~~~~~~ 6В ~~~~~~~~~~~~~~   
(define-syntax while
  (syntax-rules ()
    ((while cond? expr1 expr2 ...)
     (let loop () (if cond? (begin expr1 expr2 ... (loop)))))))


;; ~~~~~~~~~~~~~~ 6Г ~~~~~~~~~~~~~~
(define-syntax repeat
  (syntax-rules (until)
    ((repeat (expr1 expr2 ...) until cond?)
     (let loop () (begin expr1 expr2 ... (if (not cond?) (loop)))))))

;; Как видно, repeat со скобочками особо не отличается

(define-syntax repeat
  (syntax-rules (until)
    ((repeat expr1 expr2 ... until cond?)
     (let loop () (begin expr1 expr2 ... (if (not cond?) (loop)))))))


;; ~~~~~~~~~~~~~~ 6Д ~~~~~~~~~~~~~~
(define endl newline)
(define-syntax cout
  (syntax-rules (<< endl)
    ((cout << expr) (begin
                      (if (equal? expr endl)
                          (newline)
                          (display expr))))
    ((cout << endl << expr1 ...) (begin
                                   (newline)
                                   (cout << expr1 ...)))
    ((cout << expr << expr1 ...) (begin
                                   (if (equal? expr endl)
                                       (newline)
                                       (display expr))
                                   (cout << expr1 ...)))))


