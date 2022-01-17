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
    final FrameMaker frameMaker(int pix_size, int[] rgbPalette) {
        return switch(this) {
            case REFERENCE -> new ReferenceFrameMaker(pix_size, rgbPalette);
            case VALUE -> new ValueFrameMaker(pix_size, rgbPalette);
            case PRIMITIVE -> new PrimitiveFrameMaker(pix_size, rgbPalette);
        };
    }
}
