package org.openjdk.valhalla;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class FrameMaker {

    protected static final int MAX_ITERATIONS = 200;
    protected static final double CENTER_X = -0.77569384;
    protected static final double CENTER_Y =  0.13646767;

    protected final int pix_size;
    protected final int fraction;
    protected final int[] palette;
    protected double scale = 2.0;


    static final int CPUS = Runtime.getRuntime().availableProcessors();
    final ExecutorService executor;

    public FrameMaker(int pix_size, int[] rgbPalette) {
        this.pix_size = pix_size;
        this.fraction = pix_size/CPUS;
        this.palette = rgbPalette;
        executor = Executors.newFixedThreadPool(CPUS);
    }

    public abstract void countScene(int x_from, int x_to, int[] data, int[] palette);


    public void countScene(int[] data) {

        List<PartialRunner> runners = IntStream.range(0, CPUS).mapToObj(i -> new PartialRunner(i, data)).collect(Collectors.toList());
        try {
            List<Future<Void>> fus = executor.invokeAll(runners);
            for(Future<Void> f : fus) {
                f.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        scale *=0.95;
//        countScene(0, pix_size/2, data, palette);
//        countScene( pix_size/2, pix_size, data, palette);

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
            countScene(part * fraction, Math.min((part + 1) * fraction, pix_size), data, palette);
            return null;
        }
    }
}
