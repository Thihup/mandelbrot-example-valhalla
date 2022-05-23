package dev.thihup.valhalla.mandelbrot.example;

import dev.thihup.valhalla.mandelbrot.example.identity.IdentityFrameMaker;
import dev.thihup.valhalla.mandelbrot.example.mutable.MutableReferenceFrameMaker;
import dev.thihup.valhalla.mandelbrot.example.primitive.PrimitiveFrameMaker;
import dev.thihup.valhalla.mandelbrot.example.value.ValueFrameMaker;

public enum Type {
    IDENTITY("Identity"),
    MUTABLE("Mutable Reference"),
    VALUE("Value"),
    PRIMITIVE("Primitive");

    Type(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    final String friendlyName;
    final FrameMaker frameMaker(int pixSize, int[] rgbPalette) {
        return switch(this) {
            case IDENTITY -> new IdentityFrameMaker(pixSize, rgbPalette);
            case MUTABLE -> new MutableReferenceFrameMaker(pixSize, rgbPalette);
            case VALUE -> new ValueFrameMaker(pixSize, rgbPalette);
            case PRIMITIVE -> new PrimitiveFrameMaker(pixSize, rgbPalette);
        };
    }
}
