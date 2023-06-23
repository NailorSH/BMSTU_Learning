// "https://habr.com/ru/post/138173/"

package main

import "fmt"

func encode(utf32 []rune) []byte {
    var utf8 []byte
    for _, c := range utf32 {
        if c <= 0x7F { // однобайтовое представление
            utf8 = append(utf8, byte(c))
        } else if c <= 0x7FF { // двухбайтовое представление
            utf8 = append(utf8, byte((c>>6)|0xC0))
            utf8 = append(utf8, byte((c&0x3F)|0x80))
        } else if c <= 0xFFFF { // трехбайтовое представление
            utf8 = append(utf8, byte((c>>12)|0xE0))
            utf8 = append(utf8, byte(((c>>6)&0x3F)|0x80))
            utf8 = append(utf8, byte((c&0x3F)|0x80))
        } else { // четырехбайтовое представление
            utf8 = append(utf8, byte((c>>18)|0xF0))
            utf8 = append(utf8, byte(((c>>12)&0x3F)|0x80))
            utf8 = append(utf8, byte(((c>>6)&0x3F)|0x80))
            utf8 = append(utf8, byte((c&0x3F)|0x80))
        }
    }
    return utf8
}

func decode(utf8 []byte) []rune {
    var utf32 []rune
    for i := 0; i < len(utf8); {
        switch {
        case utf8[i] <= 0x7F: // однобайтовый символ
            utf32 = append(utf32, rune(utf8[i]))
            i += 1
        case utf8[i]&0xE0 == 0xC0: // двухбайтовый символ
            if i+1 >= len(utf8) {
                return utf32
            }
            utf32 = append(utf32, rune((uint(utf8[i]&0x1F)<<6)|(uint(utf8[i+1]&0x3F))))
            i += 2
        case utf8[i]&0xF0 == 0xE0: // трехбайтовый символ
            if i+2 >= len(utf8) {
                return utf32
            }
            utf32 = append(utf32, rune((uint(utf8[i]&0x0F)<<12)|(uint(utf8[i+1]&0x3F)<<6)|(uint(utf8[i+2]&0x3F))))
            i += 3
        case utf8[i]&0xF8 == 0xF0: // четырехбайтовый символ
            if i+3 >= len(utf8) {
                return utf32
            }
            utf32 = append(utf32, rune((uint(utf8[i]&0x07)<<18)|(uint(utf8[i+1]&0x3F)<<12)|(uint(utf8[i+2]&0x3F)<<6)|(uint(utf8[i+3]&0x3F))))
            i += 4
        default: // недопустимый символ
            return utf32
        }
    }
    return utf32
}

func main() {
    textUTF32 := []rune("Привет, мир!") // текст в UTF-32
    encodedText := encode(textUTF32)    // кодируем в UTF-8
    decodedText := decode(encodedText)  // декодируем обратно в UTF-32

    // проверяем, что декодированный текст совпадает с оригинальным
    fmt.Println(textUTF32)
    fmt.Println(string(textUTF32))
    fmt.Println(encodedText)
    fmt.Println(string(encodedText))
    fmt.Println(decodedText)
    fmt.Println(string(decodedText))
    

    // if string(decodedText) == string(textUTF32) {
    //     fmt.Println("Encoding and decoding works fine!")
    // } else {
    //     fmt.Println("Something went wrong :(")
    // }
}