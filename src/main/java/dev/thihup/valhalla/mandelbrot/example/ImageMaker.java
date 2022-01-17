package dev.thihup.valhalla.mandelbrot.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImageMaker {
    private static final int MIN_COLOR = 50;
    private static final int MAX_COLOR = 255;
    private static final int R_STEPS = 5;
    private static final int G_STEPS = 5;
    private static final int B_STEPS = 5;
    private static final int R_ANGLE = 270;
    private static final int G_ANGLE = 90;
    private static final int B_ANGLE = 0;


    final BufferedImage buffer;
    final int pixSize;
    final Palette palette;
    final int[] rgbPalette;
    final FrameMaker frame;

    public ImageMaker(int size, Type type) {
        this.pixSize = size;
        buffer = new BufferedImage(pixSize, pixSize, BufferedImage.TYPE_INT_RGB);
        palette = new Palette(MAX_COLOR - MIN_COLOR, MIN_COLOR, MAX_COLOR,
                Math.toRadians(R_ANGLE), Math.toRadians(G_ANGLE),
                Math.toRadians(B_ANGLE), R_STEPS, G_STEPS, B_STEPS);
        palette.setSize(FrameMaker.MAX_ITERATIONS);
        rgbPalette = palette.getRgbColors();
        rgbPalette[0] = 0; // I want it painted black
        frame = type.frameMaker(size, rgbPalette);
    }

    public Image getImage() {
        int[] data = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        frame.countScene(data);
        return buffer;
    }


}
