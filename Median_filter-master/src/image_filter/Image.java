package image_filter;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class Image {
    private JPanel main_panel;
    private JButton grayscale_image;
    private JSlider noise_slider;
    private JButton load_image;
    private JLabel original_image;
    private JLabel noise_image;
    private JLabel filter_image;
    private JComboBox size_comboBox;
    private JButton graphic_button;
    private JButton graphic_noise_button;
    public BufferedImage img = null;

    public  Image(){
        grayscale_image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grayscale gs = new Grayscale();
                Icon icon = original_image.getIcon();
                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                icon.paintIcon(null, g, 0,0);
                g.dispose();

                if (grayscale_image.getText() == "Сіре зображення") {
                    bi = gs.Grayscale_image(bi);
                    grayscale_image.setText("Кольорове зображення");
                    ImageIcon ic = new ImageIcon(bi);
                    original_image.setIcon(ic);
                    noise_image.setIcon(ic);
                    filter_image.setIcon(ic);
                    noise_slider.setValue(0);
                    size_comboBox.setSelectedIndex(0);
                } else {
                    grayscale_image.setText("Сіре зображення");
                    ImageIcon ic = new ImageIcon(img);
                    original_image.setIcon(ic);
                    noise_image.setIcon(ic);
                    filter_image.setIcon(ic);
                    noise_slider.setValue(0);
                    size_comboBox.setSelectedIndex(0);
                }


            }
        });
        load_image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reader_Writer rw = new Reader_Writer();
                BufferedImage img1 = rw.Reader(original_image.getWidth(), original_image.getHeight());
                if (img1 != null){
                    img = img1;

                    grayscale_image.setEnabled(true);
                    noise_slider.setEnabled(true);
                    size_comboBox.setEnabled(true);
                    graphic_button.setEnabled(true);
                    graphic_noise_button.setEnabled(true);
                    noise_slider.setValue(0);
                    size_comboBox.setSelectedIndex(0);
                    ImageIcon ic = new ImageIcon(img);
                    original_image.setIcon(ic);
                    noise_image.setIcon(ic);
                    filter_image.setIcon(ic);
                }
                else {
                    grayscale_image.setText("Сіре зображення");
                }

            }
        });


        noise_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Noise ns = new Noise();
                Icon icon = original_image.getIcon();
                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                icon.paintIcon(null, g, 0,0);
                g.dispose();
                bi = ns.Noise_image(bi, noise_slider.getValue());
                ImageIcon ic = new ImageIcon(bi);
                noise_image.setIcon(ic);
            }
        });
        size_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (size_comboBox.getSelectedIndex() != 0) {
                    Median mf = new Median();
                    Icon icon = noise_image.getIcon();
                    BufferedImage bi = new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    Graphics g = bi.createGraphics();
                    icon.paintIcon(null, g, 0, 0);
                    g.dispose();
                    int masksize;
                    switch (size_comboBox.getSelectedIndex()) {
                        case 1:
                            masksize = 3;
                            break;
                        case 2:
                            masksize = 5;
                            break;
                        case 3:
                            masksize = 7;
                            break;
                        default:
                            masksize = 3;
                            break;
                    }
                    bi = mf.Filter(bi, masksize);
                    ImageIcon ic = new ImageIcon(bi);
                    filter_image.setIcon(ic);
                }
            }
        });
        graphic_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signal_ratio sr = new Signal_ratio();
                sr.show_result_median(img);
            }
        });
        graphic_noise_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signal_ratio sr = new Signal_ratio();
                sr.show_result_noise(img);
            }
        });
    }





    public static void main(String[] args) {
        Image im = new Image();
        ((JLabel)im.size_comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);


        JFrame frame = new JFrame("Median filter");
        frame.setContentPane(im.main_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
