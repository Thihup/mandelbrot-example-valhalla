package dev.thihup.valhalla.mandelbrot.example.mutable;

import java.util.StringJoiner;

public class Complex {

    double re;
    double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double re() { return re; }

    public double im() { return im; }

    public double modulus() {
        return (re * re) + (im * im);
    }

    public Complex square() {
        double re = (this.re * this.re) - (this.im * this.im);
        double im = 2 * this.re * this.im;

        this.re = re;
        this.im = im;
        return this;
    }

    public Complex add(Complex that) {
        double re = this.re + that.re;
        double im = this.im + that.im;
        this.re = re;
        this.im = im;
        return this;
    }

    public Complex sub(Complex that) {
        double re = this.re - that.re;
        double im = this.im - that.im;

        this.re = re;
        this.im = im;
        return this;
    }

    public Complex mult(Complex that) {
        double re = this.re * that.re - this.im * that.im;
        double im = this.re * that.im + this.im * that.re;

        this.re = re;
        this.im = im;
        return this;
    }

    public Complex scalarMult(double scale) {
        double re = this.re * scale;
        double im = this.im * scale;

        this.re = re;
        this.im = im;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ",
            dev.thihup.valhalla.mandelbrot.example.reference.Complex.class.getSimpleName() + "[",
            "]")
            .add("re=" + re)
            .add("im=" + im)
            .toString();
    }
}
