package dev.thihup.valhalla.mandelbrot.example.value;

import dev.thihup.valhalla.mandelbrot.example.FrameMaker;


public class ValueFrameMaker extends FrameMaker {

    Complex center = new Complex(CENTER_X, CENTER_Y);

    public ValueFrameMaker(int pixSize, int[] rgbPalette) {
        super(pixSize, rgbPalette);
    }

    public void countScene(int xFrom, int xTo, int[] data, int[] palette) {
        int i = xFrom * pixSize;
        for (int x = xFrom; x < xTo; x++) {
            for (int y = 0; y < pixSize; y++) {
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
        return new Complex((y * 2.0 / pixSize) - 1.0, (x * 2.0 / pixSize) - 1.0).scalarMult(scale).add(center);
    }
}
