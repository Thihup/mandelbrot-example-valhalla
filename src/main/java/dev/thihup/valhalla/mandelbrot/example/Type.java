package dev.thihup.valhalla.mandelbrot.example;

import dev.thihup.valhalla.mandelbrot.example.primitive.PrimitiveFrameMaker;
import dev.thihup.valhalla.mandelbrot.example.reference.ReferenceFrameMaker;
import dev.thihup.valhalla.mandelbrot.example.value.ValueFrameMaker;

public enum Type {
    REFERENCE("Reference"),
    VALUE("Value"),
    PRIMITIVE("Primitive");

    Type(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    final String friendlyName;
    final FrameMaker frameMaker(int pixSize, int[] rgbPalette) {
        return switch(this) {
            case REFERENCE -> new ReferenceFrameMaker(pixSize, rgbPalette);
            case VALUE -> new ValueFrameMaker(pixSize, rgbPalette);
            case PRIMITIVE -> new PrimitiveFrameMaker(pixSize, rgbPalette);
        };
    }
}
