package dev.thihup.valhalla.mandelbrot.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class FrameMaker {

    protected static final int MAX_ITERATIONS = 200;
    protected static final double CENTER_X = -0.77569384;
    protected static final double CENTER_Y =  0.13646767;

    protected final int pixSize;
    protected final int fraction;
    protected final int[] palette;
    protected double scale = 2.0;


    static final int CPUS = Runtime.getRuntime().availableProcessors();
    final ExecutorService executor;

    protected FrameMaker(int pixSize, int[] rgbPalette) {
        this.pixSize = pixSize;
        this.fraction = pixSize/CPUS;
        this.palette = rgbPalette;
        executor = Executors.newFixedThreadPool(CPUS);
    }

    public abstract void countScene(int xFrom, int xTo, int[] data, int[] palette);

    public void countScene(int[] data) {
        List<PartialRunner> runners = new ArrayList<>();
        for (int i = 0; i < CPUS; i++) {
            PartialRunner partialRunner = new PartialRunner(i, data);
            runners.add(partialRunner);
        }
        try {
            List<Future<Void>> fus = executor.invokeAll(runners);
            for(Future<Void> f : fus) {
                f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

//        scale *=0.95;

    }

    class PartialRunner implements Callable<Void> {
        final int part;
        final int[] data;

        PartialRunner(int part, int[] data) {
            this.part = part;
            this.data = data;
        }

        @Override
        public Void call() throws Exception {
            countScene(part * fraction, Math.min((part + 1) * fraction, pixSize), data, palette);
            return null;
        }
    }
}
