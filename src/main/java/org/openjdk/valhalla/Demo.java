package org.openjdk.valhalla;

import javax.swing.*;
import java.awt.*;

public final class Demo extends JPanel {

    private Demo() {
        super(new BorderLayout());

        UpdatebleImageComponent imComp = new UpdatebleImageComponent();
        add(imComp, BorderLayout.CENTER);
        JLabel jl = new JLabel(String.format("fps: %.1f", 0.0),  JLabel.CENTER);
        jl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 96));
        jl.setForeground(Color.GREEN);

        add(jl, BorderLayout.SOUTH);
        setOpaque(false);
        setPreferredSize(new Dimension(500, 613));
        new UpdateThread(imComp, jl).start();
    }


    private static boolean isInline = false;

    public static void main(String[] args) {
        isInline = args.length==1 && "inline".equals(args[0]);
        EventQueue.invokeLater(Demo::createAndShowGui);
    }

    private static void createAndShowGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
        }
        JFrame frame = new JFrame(isInline ? "Inline/Value" : "Reference");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Demo());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class UpdatebleImageComponent extends JComponent {
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }
    };

    static class UpdateThread extends Thread {
        final UpdatebleImageComponent component;
        final JLabel label;

        UpdateThread(UpdatebleImageComponent component, JLabel jl) {
            this.component = component;
            label = jl;
        }


        @Override
        public void run() {
            ImageMaker imaker = new ImageMaker(500, isInline);
            component.setImage(imaker.getImage());
            long startTime = System.nanoTime();
            long cnt = 1;
            while (true) {
                component.setImage(imaker.getImage());
                cnt++;
                long time = System.nanoTime() - startTime;
                double fps = 1000000000.0*cnt/time;
                label.setText(String.format("fps: %.1f", fps));
            }
        }


    }
}
