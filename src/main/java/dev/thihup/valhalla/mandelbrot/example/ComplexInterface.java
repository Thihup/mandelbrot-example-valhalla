package dev.thihup.valhalla.mandelbrot.example;

public interface ComplexInterface<T extends ComplexInterface<T>> {

    double re();

    double im();

    double modulus();

    T square();

    T add(T that);

    T sub(T that);

    T mult(T that);

    T scalarMult(double scale);

}
