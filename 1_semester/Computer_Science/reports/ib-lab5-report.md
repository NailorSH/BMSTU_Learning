% Лабораторная работа № 5. Интерпретатор конкатенативного языка программирования
% 20 декабря 2022 г.
% Наиль Шиятов, ИУ9-11Б

# Цель работы
Реализовать интерпретатор стекового языка программирования по его описанию. 

# Реализация
```scheme                                                        
;; ~~~~~~~~~~~~~~ interpret ~~~~~~~~~~~~~~
(define (interpret program stack)
  (define (start-information)
    (display "interpret running... program: ")
    (display program)
    (display " stack: ")
    (display stack))

  ;(start-information)
  
  #t
  
  (define (ERROR word)
    (begin
      (display "ERROR|command not found: ")
      (write word)))
  
  ;; ~~~~~~~~~~~~~~ stack commands ~~~~~~~~~~~~~~  
  (define (displaying stack)
    (newline) (display stack) stack)
  
  (define (plus stack)
    (cons (+ (cadr stack) (car stack)) (cddr stack)))

  (define (minus stack)
    (cons (- (cadr stack) (car stack)) (cddr stack)))

  (define (multiplication stack)
    (cons (* (cadr stack) (car stack)) (cddr stack)))

  (define (int-division stack)
    (cons (quotient (cadr stack) (car stack)) (cddr stack)))

  (define (remainder-of-division stack)
    (cons (remainder (cadr stack) (car stack)) (cddr stack)))

  (define (negative stack)
    (cons (- (car stack)) (cdr stack)))

  (define (equaling? stack)
    (cons (if (= (cadr stack) (car stack)) -1 0) (cddr stack)))

  (define (bigger? stack)
    (cons (if (> (cadr stack) (car stack)) -1 0) (cddr stack)))

  (define (smaller? stack)
    (cons (if (< (cadr stack) (car stack)) -1 0) (cddr stack)))

  (define (logical-not stack)
    (cons (if (= (car stack) 0) -1 0) (cdr stack)))

  (define (logical-and stack)
    (cons (if (or (= (car stack) 0) (= (cadr stack) 0)) 0 -1) (cddr stack)))

  (define (logical-or stack)
    (cons (if (and (= (car stack) 0) (= (cadr stack) 0)) 0 -1) (cddr stack)))

  (define (drop stack)
    (cdr stack))

  (define (swap stack)
    (cons (cadr stack) (cons (car stack) (cddr stack))))

  (define (dup stack)
    (cons (car stack) stack))

  (define (over stack)
    (cons (cadr stack) stack))

  (define (rot stack)
    (cons (caddr stack) (cons (cadr stack) (cons (car stack) (cdddr stack)))))

  (define (depth stack)
    (cons (length stack) stack))
  
  ;; ~~~~~~~~~~~~~~ control commands ~~~~~~~~~~~~~~
  (define (defining words-vector word-index data-stack return-stack glossary)
    (let loop ((n (+ word-index 2)))
      (if (equal? (vector-ref words-vector n) 'end)
          (list words-vector (+ n 1) data-stack return-stack
                (cons
                 (cons (vector-ref words-vector (+ word-index 1)) (+ word-index 2)) glossary))
          (loop (+ n 1)))))

  (define (end words-vector word-index data-stack return-stack glossary)
    (list words-vector (car return-stack) data-stack (cdr return-stack) glossary))
  
  (define (exit words-vector word-index data-stack return-stack glossary)
    (list words-vector (car return-stack) data-stack (cdr return-stack) glossary))
  
  (define (start-of-if words-vector word-index data-stack return-stack glossary)
    (if (= (car data-stack) 0)
        (let loop ((n (+ word-index 1)))
          (if (equal? (vector-ref words-vector n) 'endif)
              (list words-vector (+ n 1) (cdr data-stack) return-stack glossary)
              (loop (+ n 1))))
        (list words-vector (+ word-index 1) (cdr data-stack) return-stack glossary)))

  (define (end-of-if stack) stack)
  
  ;; ~~~~~~~~~~~~~~ commands ~~~~~~~~~~~~~~  
  
  (define start-glossary
    (list (cons 'dsp displaying)
          (cons '+ plus)
          (cons '- minus)
          (cons '* multiplication)
          (cons '/ int-division)
          (cons 'mod remainder-of-division)
          (cons 'neg negative)
          (cons '= equaling?)
          (cons '> bigger?)
          (cons '< smaller?)
          (cons 'not logical-not)
          (cons 'and logical-and)
          (cons 'or logical-or)
          (cons 'drop drop)
          (cons 'swap swap)
          (cons 'dup dup)
          (cons 'over over)
          (cons 'rot rot)
          (cons 'depth depth)
          
          (cons 'endif end-of-if) 
          (cons 'define defining)
          (cons 'end end)
          (cons 'exit exit)
          (cons 'if start-of-if)))
  
  (define control-structures (cddddr (cddddr (cddddr (cddddr (cddddr start-glossary))))))
  
  (let ((program-length (vector-length program)))
    (define (processing words-vector word-index data-stack return-stack glossary)
      (if (= word-index program-length)
          data-stack 
          (let* ((word (vector-ref words-vector word-index))
                 (command-pair (assoc word glossary)) 
                 (command (if command-pair (cdr command-pair) #f)))
            
            (if command 
                (if (number? command)
                    (processing words-vector command data-stack
                                (cons (+ word-index 1) return-stack) glossary) 
                    (if (assoc word control-structures) 
                        (let* ((response (command words-vector
                                                  word-index data-stack
                                                  return-stack glossary)) 
                               (words-vector (car response)) 
                               (word-index (cadr response))
                               (data-stack (caddr response))
                               (return-stack (cadddr response))
                               (glossary (car (cddddr response))))
                          (processing words-vector word-index data-stack
                                      return-stack glossary))
                          
                        (processing words-vector (+ word-index 1)
                                    (command data-stack) return-stack glossary))) 
                                                                                                             
                (if (number? word) 
                    (processing words-vector (+ word-index 1)
                                (cons word data-stack) return-stack glossary) 
                    (ERROR word))))))

    (processing program 0 stack '() start-glossary)))



  ;; ~~~~~~~~~~~~~~ Unit-testing ~~~~~~~~~~~~~~
  (define-syntax test
    (syntax-rules()
      ((test expr true-res)
       (list 'expr (lambda () expr) true-res))))

  (define (run-test the-test)
    (let* ((expr (car the-test))
           (_ (write expr))
           (expr-res (force ((car (cdr the-test)))))
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

  (define the-tests (list
                     (test (delay (interpret #(7 + 2) '(1 2 3))) '(2 8 2 3))
                     (test (delay (interpret #(+ + +) '(2 2 2 8))) '(14))
                     (test (delay (interpret #(* + +) '(2 3 2 8))) '(16))
                     ))

  (define (run-tests the-tests)
    (or (null? the-tests)
        (if (run-test (car the-tests))
            (run-tests (cdr the-tests))
            (begin
              (run-tests (cdr the-tests))
              #f))))
```
# Тестирование
```
Welcome to DrRacket, version 8.6 [cs].
Language: R5RS; memory limit: 128 MB.
> (run-tests the-tests)
(delay (interpret #(9 + 3) '(1 2 3))) ok
(delay (interpret #(+ + +) '(2 2 2 8))) ok
(delay (interpret #(* + +) '(2 3 2 8))) ok
#t
> (interpret #(   define abs
                    dup 0 <
                    if neg endif
                end
                 9 abs
                -9 abs      ) (quote ()))
(9 9)
> (interpret #(   define =0? dup 0 = end
                define <0? dup 0 < end
                define signum
                    =0? if exit endif
                    <0? if drop -1 exit endif
                    drop
                    1
                end
                 0 signum
                -5 signum
                10 signum       ) (quote ()))
(1 -1 0)
> (interpret #(   define -- 1 - end
                define =0? dup 0 = end
                define =1? dup 1 = end
                define factorial
                    =0? if drop 1 exit endif
                    =1? if drop 1 exit endif
                    dup --
                    factorial
                    *
                end
                0 factorial
                1 factorial
                2 factorial
                3 factorial
                4 factorial     ) (quote ()))
(24 6 2 1 1)
> (interpret #(   define =0? dup 0 = end
                define =1? dup 1 = end
                define -- 1 - end
                define fib
                    =0? if drop 0 exit endif
                    =1? if drop 1 exit endif
                    -- dup
                    -- fib
                    swap fib
                    +
                end
                define make-fib
                    dup 0 < if drop exit endif
                    dup fib
                    swap --
                    make-fib
                end
                10 make-fib     ) (quote ()))
(0 1 1 2 3 5 8 13 21 34 55)
> (interpret #(   define =0? dup 0 = end
                define gcd
                    =0? if drop exit endif
                    swap over mod
                    gcd
                end
                90 99 gcd
                234 8100 gcd    ) '())
(18 9)
```
# Вывод
Я смог реализовать интерпретатор стекового языка программирования Forth по
описанию его оснывных команд и конструкций. Разобрался, что такое стек вызова 
и стек возврата. Узнал, как выполняются вызовы функций в стековом языке программирования. 
Научился реализовывать конкатенативное программирование.