package main

import (
	"fmt"      // пакет для форматированного ввода вывода
	"log"      // пакет для логирования
	"net/http" // пакет для поддержки HTTP протокола
	"strings"  // пакет для работы с UTF-8 строками
	"github.com/mmcdole/gofeed" // пакет для разбора rss 
)

func HomeRouterHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()       //анализ аргументов
	name := r.Form.Get("name") // получаем значение параметров из запроса
	text := r.Form.Get("text")

	// Генерируем HTML-код с гиперссылками
	html := `
	<html>
	<head>
	<title>Простейший сервер</title>
	</head>
	<body>
	<h1>Главная</h1>
	<h2>GET-запрос: /?name=Имя&text=текст</h1>
	<h3>Привет, ` + name + `</h1>
	<p>Ваш текст: ` + text + `</p>

	<h3>Меню:</h1>
	<ul>
	<li><a href="/">Главная</a></li>
	<li><a href="/rss">RSS</a></li>
	<li><a href="/about">About</a></li>
	</ul>
	</body>
	</html>
	`

	fmt.Println(r.Form) // ввод информации о форме на стороне сервера
	fmt.Println("path", r.URL.Path)
	fmt.Println("scheme", r.URL.Scheme)
	fmt.Println(r.Form["url_long"])
	for k, v := range r.Form {
		fmt.Println("key:", k)
		fmt.Println("val:", strings.Join(v, ""))
	}

	fmt.Fprintf(w, html) // отправляем данные на клиентскую сторону
}

func RSSRouterHandler(w http.ResponseWriter, r *http.Request) {
	// Создаем объект для парсинга RSS-фида
	fp := gofeed.NewParser()
	
	rssURL := "http://www.kommersant.ru/RSS/news.xml"

	feed, err := fp.ParseURL(rssURL)
	if err != nil {
		http.Error(w, "Не удалось получить RSS-фид", http.StatusInternalServerError)
		return
	}

	fmt.Println(feed.Title)

	// Генерируем HTML-код для отображения RSS-фида
	html := `
	<html>
	<head>
	<title>` + feed.Title + `</title>
	</head>
	<body>
	<h1>` + feed.Title + `</h1>
	`

	for _, item := range feed.Items {
		html += fmt.Sprintf("<h2>%s</h2>", item.Title)
		html += fmt.Sprintf("<p>%s</p>", item.Description)
		html += fmt.Sprintf("<p>Дата: %s</p>", item.Published)
		html += fmt.Sprintf("<p><a href=\"%s\">Ссылка</a></p>", item.Link)
	}

	html += "</body></html>"

	fmt.Fprintf(w, html)
}

func AboutRouterHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()       

	html := `
	<html>
	<head>
	<title>Простейший сервер</title>
	</head>
	<body>
	<h1>About</h1>
	<h2>Добро, пожаловать!</h2>
	</body>
	</html>
	`
	fmt.Fprintf(w, html) // отправляем данные на клиентскую сторону
}

func main() {
	http.HandleFunc("/", HomeRouterHandler)  // установим роутеры
	http.HandleFunc("/rss", RSSRouterHandler)
	http.HandleFunc("/about", AboutRouterHandler)

	err := http.ListenAndServe(":9000", nil) // задаем слушать порт
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}	
}