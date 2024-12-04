package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

public class SwingTabbedApp extends JFrame {

    public SwingTabbedApp() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Zadanie 1: Podstawowa obsługa zdarzeń myszy
        JTextField task1TextField = new JTextField("Kliknij tutaj");
        task1TextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Kliknięto w: (" + e.getX() + ", " + e.getY() + ")");
            }
        });
        tabbedPane.addTab("Zadanie 1", task1TextField);

        // Zadanie 2: Rysowanie z obsługą zdarzeń myszy
        JPanel task2Panel = new Task2Panel();
        tabbedPane.addTab("Zadanie 2", task2Panel);

        // Zadanie 3: Obsługa zdarzeń klawiatury
        JTextField task3TextField = new JTextField();
        task3TextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (keyChar == 'A') {
                    task3TextField.setForeground(Color.RED);
                } else if (keyChar == 'B') {
                    task3TextField.setForeground(Color.BLUE);
                } else if (keyChar == 'C') {
                    task3TextField.setText("");
                }
            }
        });
        tabbedPane.addTab("Zadanie 3", task3TextField);

        // Zadanie 4: Prosta animacja
        JPanel task4Panel = new Task4Panel();
        tabbedPane.addTab("Zadanie 4", task4Panel);

        // Zadanie 5: Obsługa rzadszych zdarzeń myszy
        JPanel task5Panel = new Task5Panel();
        tabbedPane.addTab("Zadanie 5", task5Panel);

        // Zadanie 6: Obsługa rzadszych zdarzeń klawiatury
        JPanel task6Panel = new Task6Panel();
        tabbedPane.addTab("Zadanie 6", task6Panel);

        add(tabbedPane);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SwingTabbedApp();
    }

    // Zadanie 2 Panel
    private static class Task2Panel extends JPanel {
        private Rectangle rectangle;
        private Ellipse2D ellipse;
        private Point lastMouseLocation;

        public Task2Panel() {
            rectangle = new Rectangle(50, 50, 100, 100);
            ellipse = new Ellipse2D.Double(200, 50, 100, 100);

            // Obsługa kliknięcia myszą
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (rectangle.contains(e.getPoint())) {
                        lastMouseLocation = e.getPoint();
                    } else if (ellipse.contains(e.getPoint())) {
                        lastMouseLocation = e.getPoint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    lastMouseLocation = null;
                }
            });

            // Obsługa przeciągania myszą
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (lastMouseLocation != null) {
                        Point newMouseLocation = e.getPoint();
                        int dx = newMouseLocation.x - lastMouseLocation.x;
                        int dy = newMouseLocation.y - lastMouseLocation.y;
                        if (rectangle.contains(lastMouseLocation)) {
                            rectangle.translate(dx, dy);
                        } else if (ellipse.contains(lastMouseLocation)) {
                            ellipse.setFrame(ellipse.getX() + dx, ellipse.getY() + dy, ellipse.getWidth(), ellipse.getHeight());
                        }
                        lastMouseLocation = newMouseLocation;
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.draw(rectangle);
            g2d.setColor(Color.BLUE);
            g2d.draw(ellipse);
        }
    }

    // Zadanie 4 Panel
    private static class Task4Panel extends JPanel {
        private Rectangle rectangle;
        private Timer timer;

        public Task4Panel() {
            rectangle = new Rectangle(50, 50, 100, 100);

            // Obsługa kliknięcia myszą
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (rectangle.contains(e.getPoint())) {
                        timer = new Timer(10, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                rectangle.x += 2;
                                repaint();
                            }
                        });
                        timer.start();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    // Zadanie 5 Panel
    private static class Task5Panel extends JPanel {
        private JLabel statusLabel;
        private Rectangle rect; // Przechowuje rozmiar prostokąta

        public Task5Panel() {
            statusLabel = new JLabel("Info o zdarzeniach myszy");
            setLayout(new BorderLayout());
            add(statusLabel, BorderLayout.SOUTH);

            // Inicjalizacja prostokąta
            rect = new Rectangle(100, 100, 100, 100);

            // Obsługa zdarzeń myszy
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    statusLabel.setText("Kursor na obszarze rysowania");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    statusLabel.setText("Kursor opuscił obszar rysowania");
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    statusLabel.setText("Kliknieto: (" + e.getX() + ", " + e.getY() + ")");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    statusLabel.setText("Przycisk myszy zwolniony");
                }
            });

            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int notches = e.getWheelRotation();
                    if (notches < 0) { // Scroll up
                        rect.width += 10;
                        rect.height += 10;
                    } else { // Scroll down
                        rect.width = Math.max(10, rect.width - 10);
                        rect.height = Math.max(10, rect.height - 10);
                    }
                    repaint(); // Odśwież panel
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            // Rysuj prostokąt na podstawie zmiennej instancji rect
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
    }


    // Zadanie 6 Panel
    private static class Task6Panel extends JPanel {
        private Color currentColor = Color.RED;
        private Shape shape = new Rectangle(100, 100, 100, 100);

        public Task6Panel() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        currentColor = Color.BLUE;
                    } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        currentColor = Color.GREEN;
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Zmień na okrąg
                        shape = new Ellipse2D.Double(100, 100, 100, 100);
                    }
                    repaint();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Przywróć kwadrat
                        shape = new Rectangle(100, 100, 100, 100);
                    } else {
                        currentColor = Color.RED;
                    }
                    repaint();
                }
            });
            setFocusable(true);
            requestFocusInWindow();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(currentColor);
            if (shape instanceof Rectangle) {
                g2d.fill((Rectangle) shape);
            } else if (shape instanceof Ellipse2D) {
                g2d.fill((Ellipse2D) shape);
            }
        }
    }

}
