package dev.thihup.valhalla.mandelbrot.example;

import javax.swing.*;
import java.awt.*;

public final class Main extends JPanel {

    private Main() {
        super(new BorderLayout());

        UpdatableImageComponent imComp = new UpdatableImageComponent();
        add(imComp, BorderLayout.CENTER);
        JLabel jl = new JLabel(String.format("fps: %.1f", 0.0),  JLabel.CENTER);
        jl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 96));
        jl.setForeground(Color.GREEN);

        add(jl, BorderLayout.SOUTH);
        setOpaque(false);
        setPreferredSize(new Dimension(500, 613));
        new UpdateThread(imComp, jl).start();
    }


    private static Type type;
    static int midFps = 1;
    static JFrame frame;

    public static void main(String[] args) {
        type = args.length == 1 ? Type.valueOf(args[0].toUpperCase()) : Type.IDENTITY;
        EventQueue.invokeLater(Main::createAndShowGui);
    }

    private static void createAndShowGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
        }
        frame = new JFrame(type.friendlyName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Main());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class UpdatableImageComponent extends JComponent {
        Image image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(image!=null) {
                g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
            }
        }

        public void setImage(Image im) {
            image = im;
            SwingUtilities.invokeLater(this::repaint);
        }
    };

    static class UpdateThread extends Thread {
        final UpdatableImageComponent component;
        final JLabel label;

        UpdateThread(UpdatableImageComponent component, JLabel jl) {
            this.component = component;
            label = jl;
        }

        @Override
        public void run() {
            ImageMaker imaker = new ImageMaker(512, type);
            component.setImage(imaker.getImage());
            long startTime = System.nanoTime();
            long cnt = 1;
            while (true) {
                component.setImage(imaker.getImage());
                cnt++;
                long time = System.nanoTime() - startTime;
                double fps = 1000000000.0*cnt/time;
                label.setText(String.format("fps: %.1f", fps));
                midFps = (int) ((midFps + fps) / 2);
                frame.setTitle(type.friendlyName + " - " + midFps + " fps");
            }
        }


    }
}
