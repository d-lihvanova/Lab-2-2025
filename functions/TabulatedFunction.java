package functions;

public class TabulatedFunction {
    // массив для хранения точек функции
    private FunctionPoint[] points;
    
    // реальное количество точек в массиве
    private int pointsCount;
    
    //создаем функцию с равными интервалами
    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        // создаем массив с небольшим запасом
        this.points = new FunctionPoint[pointsCount + 5];
        this.pointsCount = pointsCount;
            
        // вычисляем расстояние между точками
        double step = (rightX - leftX) / (pointsCount - 1);
            
        // создаем точки
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;  // координата X
            points[i] = new FunctionPoint(x, 0.0);  // Y пока = 0
        }
    }
    
    //создаем функцию с заданными значениями Y
    public TabulatedFunction(double leftX, double rightX, double[] values) {
        // вызываем первый конструктор
        this(leftX, rightX, values.length);
        
        // устанавливаем значения Y из массива values
        for (int i = 0; i < values.length; i++) {
            points[i].setY(values[i]);
        }
    }
    
    public double getLeftDomainBorder() {
        // левая граница - это x самой первой точки
        return points[0].getX();
    }
    
    public double getRightDomainBorder() {
        // правая граница - это x самой последней точки
        return points[pointsCount - 1].getX();
    }
    
    // Возвращает значение функции в точке x
    public double getFunctionValue(double x) {
        // проверяем, лежит ли точка в области определения
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            // если точка вне области определения
            return Double.NaN;
        }
        
        // машинная точность для сравнения чисел с плавающей точкой
        final double EPSILON = 1e-10;
        
        // ищем интервал, в который попадает точка x
        for (int i = 0; i < pointsCount - 1; i++) {
            double x1 = points[i].getX();  // начало интервала
            double x2 = points[i + 1].getX();  // конец интервала
            
            // проверяем, совпадает ли x с x1
            if (Math.abs(x - x1) < EPSILON) {
                return points[i].getY(); // x точно равен x1
            }
            
            // проверяем, совпадает ли x с x2
            if (Math.abs(x - x2) < EPSILON) {
                return points[i + 1].getY(); // x точно равен x2
            }
            
            // если x находится между x1 и x2
            if (x > x1 && x < x2) {
                // берем значения функции на концах интервала
                double y1 = points[i].getY();
                double y2 = points[i + 1].getY();
                
                // линейная интерполяция: считаем, что на отрезке функция - прямая линия
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }
        
        return Double.NaN;
    }
    
    // возвращает количество точек в функции
    public int getPointsCount() {
        return pointsCount;
    }
    
    // возвращает копию точки по индексу
    public FunctionPoint getPoint(int index) {
        // Проверяем, что индекс в пределах массива
        if (index < 0 || index >= pointsCount) {
            return null;
        }
        return new FunctionPoint(points[index]);
    }
    
    // заменяет точку по индексу
    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= pointsCount) {
            return; 
        }
        
        // проверяем, что новая координата X не нарушает порядок точек
        if (index > 0 && point.getX() <= points[index - 1].getX()) {
            return; // точка должна быть правее предыдущей
        }
        if (index < pointsCount - 1 && point.getX() >= points[index + 1].getX()) {
            return; // точка должна быть левее следующей
        }
        
        // заменяем на копию
        points[index] = new FunctionPoint(point);
    }
    
    // возвращает координату X точки по индексу
    public double getPointX(int index) {
        return points[index].getX();
    }
    
    // изменяет координату X точки по индексу
    public void setPointX(int index, double x) {
        // проверяем корректность нового X
        if (index > 0 && x <= points[index - 1].getX()) {
            return;
        }
        if (index < pointsCount - 1 && x >= points[index + 1].getX()) {
            return;
        }
        
        points[index].setX(x);
    }
    
    // возвращает координату Y точки по индексу
    public double getPointY(int index) {
        return points[index].getY();
    }
    
    // изменяет координату Y точки по индексу
    public void setPointY(int index, double y) {
        points[index].setY(y); // Y можно менять без ограничений
    }
    
    // удаляет точку по индексу
    public void deletePoint(int index) {
        // нельзя удалить точку, если останется меньше 2 точек
        if (pointsCount <= 2) {
            return; // минимальное количество точек для функции
        }
        
        if (index < 0 || index >= pointsCount) {
            return; 
        }
        
        // сдвигаем точки после удаляемой влево
        for (int i = index; i < pointsCount - 1; i++) {
            points[i] = points[i + 1];
        }
        
        // очищаем последнюю ячейку
        points[pointsCount - 1] = null;
        pointsCount--;
    }
    
    // добавляет новую точку в функцию
    public void addPoint(FunctionPoint point) {
        // машинная точность для сравнения чисел с плавающей точкой
        final double EPSILON = 1e-10;
        
        // проверяем, нет ли уже точки с таким X
        for (int i = 0; i < pointsCount; i++) {
            if (Math.abs(points[i].getX() - point.getX()) < EPSILON) {
                // Точка с таким X уже существует - обновляем Y
                points[i].setY(point.getY());
                return;
            }
        }
        
        // если массив заполнен - увеличиваем его
        if (pointsCount == points.length) {
            // создаем новый массив большего размера
            FunctionPoint[] newArray = new FunctionPoint[(int)(points.length * 1.5) + 1];
            // копируем старые точки
            for (int i = 0; i < pointsCount; i++) {
                newArray[i] = points[i];
            }
            points = newArray;
        }
        
        // ищем место для вставки 
        int insertIndex = 0;
        while (insertIndex < pointsCount && points[insertIndex].getX() < point.getX()) {
            insertIndex++;
        }
        
        // сдвигаем точки вправо, чтобы освободить место
        for (int i = pointsCount; i > insertIndex; i--) {
            points[i] = points[i - 1];
        }
        
        // вставляем новую точку 
        points[insertIndex] = new FunctionPoint(point);
        pointsCount++;
    }
}