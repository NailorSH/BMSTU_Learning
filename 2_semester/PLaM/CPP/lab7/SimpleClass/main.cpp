/*
Ломаная линия на плоскости с операциями:
1. получение количества точек;
2. получение ссылки на i-тую точку;
3. конкатенация двух ломаных (формируется
новая ломаная);
4. выделение части ломаной с i-той по j-тую
точки (формируется новая ломаная).
Точки на плоскости должны быть представлены
структурами с целочисленными полями x и y. 
*/

#include <iostream>
#include "declaration.h"

using namespace std;

int main() {
    vector<Point> points1 = {{1, 2}, {3, 4}, {5, 6}};
    vector<Point> points2 = {{7, 8}, {9, 10}};
    
    Polyline p1(points1);
    Polyline p2(points2);
    
    cout << "p1.size() = " << p1.size() << std::endl;
    cout << "p1[1].x = " << p1[1].x << ", p1[1].y = " << p1[1].y << std::endl;
    
    Polyline p3 = p1 + p2;
    cout << "p3.size() = " << p3.size() << std::endl;
    
    Polyline p4 = p1.subPolyline(1, 2);
    cout << "p4.size() = " << p4.size() << std::endl;
    
    return 0;
}