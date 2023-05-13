#ifndef DECLARATION_H
#define DECLARATION_H

#include <vector>

using namespace std;

struct Point {
    int x;
    int y;
};

class Polyline {
public:
    Polyline();
    Polyline(const vector<Point>& points);
    int size() const;
    Point& operator[](int index);
    const Point& operator[](int index) const;
    Polyline operator+(const Polyline& other) const;
    Polyline subPolyline(int i, int j) const;
    
private:
    vector<Point> m_points;
};

#endif