package dev.thihup.valhalla.mandelbrot.example.primitive;

import dev.thihup.valhalla.mandelbrot.example.FrameMaker;


public class PrimitiveFrameMaker extends FrameMaker {

    Complex center = new Complex(CENTER_X, CENTER_Y);

    public PrimitiveFrameMaker(int pix_size, int[] rgbPalette) {
        super(pix_size, rgbPalette);
    }

    public void countScene(int x_from, int x_to, int[] data, int[] palette) {
        int i = x_from*pix_size;
        for (int x = x_from; x < x_to; x++) {
            for (int y = 0; y < pix_size; y++) {
                data[i] = palette[count(toComplexCoord(x, y))];
                i++;
            }
        }
    }

    private static int count(Complex c) {
       Complex z = c;
        for (int i = 1; i < MAX_ITERATIONS; i++) {
            if (z.modulus() >= 4.0) return i;
            z = z.square().add(c);
        }
        return 0;
    }

    private Complex toComplexCoord(double x, double y) {
        return new Complex((y * 2.0 / pix_size) - 1.0, (x * 2.0 / pix_size) - 1.0).scalarMult(scale).add(center);
    }



}
