package org.openjdk.valhalla;

import org.openjdk.valhalla.primitive.PrimitiveFrameMaker;
import org.openjdk.valhalla.reference.ReferenceFrameMaker;
import org.openjdk.valhalla.value.ValueFrameMaker;

public enum Type {
    REFERENCE("Reference"),
    VALUE("Value"),
    PRIMITIVE("Primitive");

    private Type(String friendlyName) {
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
