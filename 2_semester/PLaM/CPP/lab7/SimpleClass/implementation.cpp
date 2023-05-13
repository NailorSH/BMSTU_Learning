#include "declaration.h"

using namespace std;

Polyline::Polyline(const vector<Point>& points) : m_points(points) {}

int Polyline::size() const {
    return m_points.size();
}

Point& Polyline::operator[](int index) {
    return m_points[index];
}

const Point& Polyline::operator[](int index) const {
    return m_points[index];
}

Polyline Polyline::operator+(const Polyline& other) const {
    vector<Point> new_points(m_points);
    new_points.insert(new_points.end(), other.m_points.begin(), other.m_points.end());
    return Polyline(new_points);
}

Polyline Polyline::subPolyline(int i, int j) const {
    return Polyline(vector<Point>(m_points.begin() + i, m_points.begin() + j + 1));
}