cmake_minimum_required(VERSION 3.25)
project(PtrQueue)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(PtrQueue
        main.cpp
        PtrQueue.cpp)
