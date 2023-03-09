(define-syntax test
  (syntax-rules()
    ((test expr true-res)
     (list 'expr (lambda () expr) true-res))))

(define (run-test the-test)
  (let* ((expr (car the-test))
         (_ (write expr))
         (expr-res ((car (cdr the-test))))
         (true-res (car (cdr (cdr the-test)))))
    (if (equal? expr-res true-res)
        (begin
          (display " ok")
          (newline)
          #t)
        (begin
          (display " FAIL")
          (newline)
          (display "  Expected: ")
          (write true-res)
          (newline)
          (display "  Returned: ")
          (write expr-res)
          (newline)
          #f))))

(define (run-tests the-tests)
  (or (null? the-tests)
      (if (run-test (car the-tests))
          (run-tests (cdr the-tests))
          (begin
            (run-tests (cdr the-tests))
            #f))))