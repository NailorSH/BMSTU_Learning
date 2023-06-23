package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func polish(expr string) int {
	tokens := tokenize(expr)

	result, _ := parse(tokens)
	return result
}

func parse(tokens []string) (int, int) {
	var stack []int
	var operation string

	i := 0

	for ; i < len(tokens) && tokens[i] != ")"; i++ {
		token := tokens[i]

		switch token {
		case "+":
			operation = "+"
		case "-":
			operation = "-"
		case "*":
			operation = "*"
		case "(":
			var subresult, skip int

			subresult, skip = parse(tokens[i+1:])
			stack = append(stack, subresult)

			i += skip
		default:
			num, _ := strconv.Atoi(token)
			stack = append(stack, num)
		}
	}

	subtokens_size := i + 1

	if len(stack) == 1 {
		return stack[0], subtokens_size
	} else {
		oper1 := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		oper2 := stack[len(stack)-1]
		stack = stack[:len(stack)-1]

		op_result := calculate(operation, oper2, oper1)

		stack = append(stack, op_result)

		return stack[0], subtokens_size
	}
}

func calculate(operation string, operand1, operand2 int) int {
	result := 0

	switch operation {
	case "+":
		result = operand1 + operand2
	case "-":
		result = operand1 - operand2
	case "*":
		result = operand1 * operand2
	}
	return result
}

func tokenize(str string) []string {
	var tokens []string

	for i := 0; i < len(str); i++ {
		switch str[i] {

		case '(':
			tokens = append(tokens, "(")
		case ')':
			tokens = append(tokens, ")")
		case '+':
			tokens = append(tokens, "+")
		case '-':
			tokens = append(tokens, "-")
		case '*':
			tokens = append(tokens, "*")
		case ' ':
			continue

		default:
			num := string(str[i])
			tokens = append(tokens, num)
		}
	}

	return tokens
}

func isDigit(c byte) bool {
	return c >= '0' && c <= '9'
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	if scanner.Scan() {
		expr := strings.TrimSpace(scanner.Text())

		fmt.Println(polish(expr))
	}
}