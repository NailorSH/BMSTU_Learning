assume CS:code, DS:data

data segment
a dw 1
b dw 2
c dw 3
d dw 8
data ends

code segment
start:
mov ax, data
mov ds, ax

; a * b * c + d/8 - 3

mov ax, a
mul b
mul c
mov bx, d
mov cl, 3
shr bx, cl
add ax, bx
sub ax, 3

mov ah, 4Ch
int 21h

code ends
end start