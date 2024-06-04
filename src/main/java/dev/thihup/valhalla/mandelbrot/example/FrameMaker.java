package dev.thihup.valhalla.mandelbrot.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.stream.*;

public abstract class FrameMaker {

    protected static final int MAX_ITERATIONS = 200;
    protected static final double CENTER_X = -0.77569384;
    protected static final double CENTER_Y =  0.13646767;

    protected final int pixSize;
    protected final int fraction;
    protected final int[] palette;
    protected double scale = 2.0;


    static final int CPUS = 64;
    protected FrameMaker(int pixSize, int[] rgbPalette) {
        this.pixSize = pixSize;
        this.fraction = pixSize/CPUS;
        this.palette = rgbPalette;
    }

    public abstract void countScene(int xFrom, int xTo, int[] data, int[] palette);

    public void countScene(int[] data) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < CPUS; i++) {
                int finalI = i;
                scope.fork(new PartialRunner(this, finalI, data));
            }
            scope.join().throwIfFailed();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //scale *=0.95;

    }

    record PartialRunner(FrameMaker maker, int part, int[] data) implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            maker.countScene(part * maker.fraction, Math.min((part + 1) * maker.fraction, maker.pixSize), data, maker.palette);
            return null;
        }
    }
}
