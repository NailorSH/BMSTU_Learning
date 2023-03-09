#!/bin/bash

# синтаксис: ./lab7 script_path interval
SECS_IN_MIN=2 # по ТЗ интервал в минутах, но для показа можно изменить

function critical_error(){
    if [ $# -gt 0 ]; then
        echo $1 1>&2
    fi
    exit 1
}

function is_pos_number(){
    if [ $1 -eq $1 ] 2> /dev/null && [ $1 -gt 0 ] 
    then
        return 0
    else
        return 1
    fi
} 

function args_test(){
    if [ $# -gt 0 ]; then
        SCRIPT_PATH=$1
        INTERVAL=$2
        shift
        shift
        if [ $# -gt 0 ]; then           
            echo "Неизвестные аргументы: ${@}" 1>&2
            exit 1
        fi
        if ! [ -f $SCRIPT_PATH ]; then
			critical_error "Не удается найти указанный файл $SCRIPT_PATH"
		fi
        if ! [ -z $INTERVAL ] && ! is_pos_number $INTERVAL; then
            critical_error "Аргумент interval должен быть целым положительным числом"
        fi
       
    fi

    if [ -z $SCRIPT_PATH ]; then
		critical_error "Требуется указать аргумент script_path" 
	fi

    if [ -z $INTERVAL ]; then
		critical_error "Требуется указать аргумент interval" 
	fi

	
}

function main() {
    COUNTER=0
    PID=-1
    CURR_START_DT=$(date +"%Y-%m-%d_%H:%M:%S")
    LOGS_LOC="output_$CURR_START_DT.log"
    ERRORS_LOC="errors_$CURR_START_DT.log"
    while [ 1 -eq 1 ]; do
        COUNTER=$[$COUNTER+1]
        if ! ps -p $PID > /dev/null 2>&1; then
            bash $SCRIPT_PATH 1>>$LOGS_LOC 2>>$ERRORS_LOC &
            PID=$!
            echo "Функция выполнена (# $COUNTER)"
        else
            critical_error "Попытка $COUNTER - не успеваю закончить выполнение функции.
            Возможно, интервал слишком мал."
        fi
        sleep $[$INTERVAL*$SECS_IN_MIN]
    done
}

args_test $@
main