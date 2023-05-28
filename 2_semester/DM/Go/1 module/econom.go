package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func removeSpaces(str string) string {
	var tokens []rune

	for _, char := range str {
		if char != ' ' {
			tokens = append(tokens, char)
		}
	}

	result := string(tokens)

	return result
}

func polishEconom(str string) int {
	subexpressionsSet := map[string]struct{}{} // множество подвыражений

	expr := removeSpaces(str) // удаляем все пробелы

	var openBrackets []int // срез из индексов на открывающие скобки
	for i, token := range expr {
		switch token {
		case '(':
			openBrackets = append(openBrackets, i) // добавляем индекс открывающей скобки в срез
		case ')':
			lastOpenBracket := len(openBrackets) - 1 // получаем индекс последней открывающей скобки

			// выделяем подвыражение
			subexprStart := openBrackets[lastOpenBracket]
			subexprEnd := i + 1
			currentSubexpr := expr[subexprStart:subexprEnd]

			subexpressionsSet[currentSubexpr] = struct{}{}

			openBrackets = openBrackets[:lastOpenBracket] // удаляем последний индекс
		}
	}
	return len(subexpressionsSet)
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)
	if scanner.Scan() {
		expr := strings.TrimSpace(scanner.Text())

		fmt.Println(polishEconom(expr))
	}
}
