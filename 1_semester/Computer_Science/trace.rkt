;; tracing

(define-syntax trace-ex
  (syntax-rules()
    ((trace expr)
     (begin
       (write 'expr)
       (display " => ")
       (let* ((expr-res expr))
         (write expr-res)
         (newline)
         expr-res)))))