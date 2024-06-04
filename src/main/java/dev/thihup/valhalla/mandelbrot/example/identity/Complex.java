package dev.thihup.valhalla.mandelbrot.example.identity;

import java.util.StringJoiner;

public record Complex(double re, double im) {

    public double modulus() {
        return (re * re) + (im * im);
    }

    public Complex square() {
        return new Complex((re * re) - (im * im), 2 * re * im);
    }

    public Complex add(Complex that) {
        return new Complex(re + that.re, im + that.im);
    }

    public Complex sub(Complex that) {
        return new Complex(re - that.re, im - that.im);
    }

    public Complex mult(Complex that) {
        return new Complex(re * that.re - im * that.im,
                re * that.im + im * that.re);
    }

    public Complex scalarMult(double scale) {
        return new Complex(re * scale, im * scale);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Complex.class.getSimpleName() + "[", "]")
            .add("re=" + re)
            .add("im=" + im)
            .toString();
    }
}
