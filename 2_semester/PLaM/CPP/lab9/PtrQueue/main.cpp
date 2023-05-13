#include "PtrQueue.cpp"

#include <iostream>
using namespace std;

struct Point {
    int x;
    int y;
};

int main() {
    PtrQueue<Point> queue1(3); // создаём очереди для хранения указателей на Point
    PtrQueue<Point> queue2(3);
    
    Point point1 = {1, 2};
    Point point2 = {7, 8};
    
    Point point3 = {5, 6};
    Point point4 = {9, 0};
    
    // добавление указателей в очередь с помощью оператора <<
    queue1 << &point1;
    queue1 << &point2;
    
    queue2 << &point3;
    queue2 << &point4;
    
    
    // получение указателей из очереди с помощью оператора !
    Point *p1 = *queue1;
    Point *p2 = !queue1;
    Point *p3 = *queue2;
    Point *p4 = !queue1;
    
    
    cout << "p1->x = " << p1->x << endl;
    cout << "queue2->x = " << queue2->x << endl;
    cout << "p2->x = " << p2->x << endl;
    cout << "p3->x = " << p3->x << endl;
    cout << "p4->x = " << p4->x << endl;
    
    return 0;
}
