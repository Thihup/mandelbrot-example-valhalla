package dev.thihup.valhalla.mandelbrot.example.primitive;

import jdk.internal.vm.annotation.*;

@LooselyConsistentValue
@ImplicitlyConstructible
public value class Complex {

    final double re;
    final double im;

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
}
