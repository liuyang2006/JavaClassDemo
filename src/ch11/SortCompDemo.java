package ch11;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class SortCompDemo extends JApplet {
    private DisplayArrayPanel displayArrayPanel1 = new
            DisplayArrayPanel();
    private int[] numbers1 = initializeNumbers();

    private DisplayArrayPanel displayArrayPanel2 = new
            DisplayArrayPanel();
    private int[] numbers2 = numbers1.clone();

    private DisplayArrayPanel displayArrayPanel3 = new
            DisplayArrayPanel();
    private int[] numbers3 = numbers1.clone();

    final int PAUSE_TIME = 250;

    public SortCompDemo() {
        setLayout(new GridLayout(1, 4));

        displayArrayPanel1.setNumbers(numbers1);
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(new JLabel("Selection Sort", JLabel.CENTER),
                BorderLayout.NORTH);
        panel1.add(displayArrayPanel1, BorderLayout.CENTER);
        add(panel1);

        displayArrayPanel2.setNumbers(numbers2);
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(new JLabel("Insertion Sort", JLabel.CENTER),
                BorderLayout.NORTH);
        panel2.add(displayArrayPanel2, BorderLayout.CENTER);
        add(panel2);

        displayArrayPanel3.setNumbers(numbers3);
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(new JLabel("Bubble Sort", JLabel.CENTER),
                BorderLayout.NORTH);
        panel3.add(displayArrayPanel3, BorderLayout.CENTER);
        add(panel3);

        new Thread(new Runnable() {
            public void run() {
                selectionSort(numbers1);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                insertionSort(numbers2);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                bubbleSort(numbers3);
            }
        }).start();
    }

    public void selectionSort(int[] list) {
        for (int i = list.length - 1; i >= 1; i--) {
            // Find the maximum in the list[0..i]
            int currentMax = list[0];
            int currentMaxIndex = 0;

            for (int j = 1; j <= i; j++) {
                if (currentMax < list[j]) {
                    currentMax = list[j];
                    currentMaxIndex = j;
                }
            }

            // Swap list[i] with list[currentMaxIndex] if necessary;
            if (currentMaxIndex != i) {
                list[currentMaxIndex] = list[i];
                list[i] = currentMax;
            }

            try {
                Thread.sleep(PAUSE_TIME);
            } catch (InterruptedException e) {
            }
            displayArrayPanel1.repaint();
        }
    }

    /**
     * The method for sorting the numbers
     */
    public void insertionSort(int[] list) {
        for (int i = 1; i < list.length; i++) {
            /** insert list[i] into a sorted sublist list[0..i-1] so that
             list[0..i] is sorted. */
            int currentElement = list[i];
            int k;
            for (k = i - 1; k >= 0 && list[k] > currentElement; k--) {
                list[k + 1] = list[k];

                try {
                    Thread.sleep(PAUSE_TIME);
                } catch (InterruptedException e) {
                }

                displayArrayPanel2.repaint();
            }

            // Insert the current element into list[k+1]
            list[k + 1] = currentElement;
            try {
                Thread.sleep(PAUSE_TIME);
            } catch (InterruptedException e) {
            }

            displayArrayPanel2.repaint();

        }
    }

    /**
     * The method for sorting the numbers
     */
    public void bubbleSort(int[] list) {
        boolean needNextPass = true;

        for (int k = 1; k < list.length && needNextPass; k++) {
            // Array may be sorted and next pass not needed
            needNextPass = false;
            for (int i = 0; i < list.length - k; i++) {
                if (list[i] > list[i + 1]) {
                    // Swap list[i] with list[i + 1]
                    int temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;

                    needNextPass = true; // Next pass still needed

                    try {
                        Thread.sleep(PAUSE_TIME);
                    } catch (InterruptedException e) {
                    }

                    displayArrayPanel3.repaint(); // setNumbers(numbers);
                }
            }
        }
    }

    public int[] initializeNumbers() {
        int[] numbers = new int[50];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        java.util.List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            list.add(numbers[i]);
        }

        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        Object[] objects = list.toArray();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(objects[i].toString());
        }

        return numbers;
    }

    public static void main(String[] args) {
        SortCompDemo applet = new SortCompDemo();
        JFrame frame = new JFrame();
        //EXIT_ON_CLOSE == 3
        frame.setTitle("SortCompDemo");
        frame.add(applet, BorderLayout.CENTER);
        applet.init();
        applet.start();
        frame.setSize(600, 160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}

class DisplayArrayPanel extends JPanel {
    int[] numbers;

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Find maximum integer
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (max < numbers[i]) {
                max = numbers[i];
            }
        }

        int height = (int) (getSize().height * 0.98);
        int width = getSize().width;
        int unitWidth = (int) (width * 1.0 / numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            g.drawRect(
                    (int) (i * unitWidth),
                    getSize().height - (int) ((numbers[i] * 1.0 / max) * height),
                    (int) (unitWidth), getSize().height);
        }
    }
}
