/*package com.example.fractals;

*//*import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import myPCG_Graph.myGraph;*//*

public class test {

    public static int sizeGraphX = 640, sizeGraphY = 480;
    public static int maxStep = 1000;
    public static float maxNorm = 1.0e6f;
    static int dy1 = 20, dx1 = 20, dx2 = 40;
    static float amin = -0.713f, amax = -0.408f, bmin = 0.49216f, bmax = 0.71429f;

}
    public static void main(String[] args) {
        JFrame frame = new JFrame("ЛИО");							//R создадим окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//R назначим способ закрытия - по крестику

        ImageIcon image = new ImageIcon("FractalImage.jpg");		//R иконка в углу
        frame.setIconImage(image.getImage());

        Container contentPane = frame.getContentPane();				//R вытащим из картинки главный слой
        SpringLayout layout = new SpringLayout();					//R создадим разметку
        contentPane.setLayout(layout);								//R положим разметку на слой

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);

        //R создаем элементы интерфейса
        JLabel label = new JLabel("Введите область изменения C = A + i*B :");

        JLabel l1 = new JLabel("A min"); 	JTextField t1 = new JTextField("-0.713",10);
        JLabel l2 = new JLabel("A max"); 	JTextField t2 = new JTextField("-0.408",10);

        JLabel l3 = new JLabel("B min"); 	JTextField t3 = new JTextField("0.49216",10);
        JLabel l4 = new JLabel("B max"); 	JTextField t4 = new JTextField("0.71429",10);

        JButton b1 = new JButton("Построить");
        JButton b2 = new JButton("Очистить ");
        JButton b3 = new JButton("Button 3");
        JButton b4 = new JButton("Button 4");

        myGraph myPaint = new myGraph();

        //R все надписи - одним шрифтом
        label.setFont(font);
        b1.setFont(font);
        b2.setFont(font);
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
        l4.setFont(font);
        t1.setFont(font);
        t2.setFont(font);
        t3.setFont(font);
        t4.setFont(font);

        //R набросаем элементы на слой
        contentPane.add(label);
        contentPane.add(l1);	contentPane.add(t1);	contentPane.add(myPaint);
        contentPane.add(l2);	contentPane.add(t2);

        contentPane.add(l3);	contentPane.add(t3);
        contentPane.add(l4);	contentPane.add(t4);

        contentPane.add(b1);
        //contentPane.add(b2);

        //R разпределим по местам!
        layout.putConstraint(	SpringLayout.WEST , label, dx1, 	//R западная граница label на расстоянии 10
                SpringLayout.WEST , contentPane);					//R от западной границы слоя
        layout.putConstraint(	SpringLayout.NORTH , label, dy1, 	//R северная граница label на расстоянии 10
                SpringLayout.NORTH , contentPane);					//R от северной границы слоя

        //R первая линия
        layout.putConstraint(	SpringLayout.WEST , l1, dx1, 		//R западная граница l1 на расстоянии 10
                SpringLayout.WEST , contentPane);					//R от западной границы слоя
        layout.putConstraint(	SpringLayout.NORTH , l1, dy1, 		//R северная граница l1 на расстоянии 20
                SpringLayout.SOUTH , label);						//R от южной границы label

        layout.putConstraint(	SpringLayout.WEST, t1, dx1, 	 	//R западная граница t1 на расстоянии 10
                SpringLayout.EAST , l1);							//R от восточной границы l1
        layout.putConstraint(	SpringLayout.NORTH, t1, dy1, 		//R северная граница t1 на расстоянии 20
                SpringLayout.SOUTH , label);						//R от южной границы label

        layout.putConstraint(	SpringLayout.WEST, l2, dx2, 		//R и так далее по аналогии
                SpringLayout.EAST , t1);
        layout.putConstraint(	SpringLayout.NORTH, l2, dy1,
                SpringLayout.SOUTH , label);

        layout.putConstraint(	SpringLayout.WEST, t2, dx1,
                SpringLayout.EAST , l2);
        layout.putConstraint(	SpringLayout.NORTH, t2, dy1,
                SpringLayout.SOUTH , label);

        //R вторая линия
        layout.putConstraint(	SpringLayout.WEST, l3, dx1,
                SpringLayout.WEST , contentPane);
        layout.putConstraint(	SpringLayout.NORTH, l3, dy1,
                SpringLayout.SOUTH , l1);

        layout.putConstraint(	SpringLayout.WEST, t3, dx1,
                SpringLayout.EAST , l3);
        layout.putConstraint(	SpringLayout.NORTH, t3, dy1,
                SpringLayout.SOUTH , t1);

        layout.putConstraint(	SpringLayout.WEST, l4, dx2,
                SpringLayout.EAST , t3);
        layout.putConstraint(	SpringLayout.NORTH, l4, dy1,
                SpringLayout.SOUTH , l2);

        layout.putConstraint(	SpringLayout.WEST , t4, dx1,
                SpringLayout.EAST , l4);
        layout.putConstraint(	SpringLayout.NORTH , t4, dy1,
                SpringLayout.SOUTH , l2);

        //R кнопки
        layout.putConstraint(	SpringLayout.WEST , b1, dx1,
                SpringLayout.WEST , contentPane);
        layout.putConstraint(	SpringLayout.NORTH , b1, dy1,
                SpringLayout.SOUTH , l3);

        layout.putConstraint(	SpringLayout.WEST , b2, dx1,
                SpringLayout.WEST , contentPane);
        layout.putConstraint(	SpringLayout.NORTH , b2, dy1,
                SpringLayout.SOUTH , b1);

        //R картинка
        layout.putConstraint(	SpringLayout.WEST , myPaint, dx2,
                SpringLayout.EAST , t2);
        layout.putConstraint(	SpringLayout.NORTH , myPaint, dy1,
                SpringLayout.SOUTH , label);
        layout.putConstraint(	SpringLayout.EAST , myPaint, sizeGraphX,
                SpringLayout.WEST , myPaint);
        layout.putConstraint(	SpringLayout.SOUTH , myPaint, sizeGraphY,
                SpringLayout.NORTH , myPaint);

        frame.setSize(1200, 600);
        frame.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(t1.getText() != "" ) amin = Float.parseFloat(t1.getText());
                    if(t2.getText() != "" ) amax = Float.parseFloat(t2.getText());
                    if(t3.getText() != "" ) bmin = Float.parseFloat(t3.getText());
                    if(t4.getText() != "" ) bmax = Float.parseFloat(t4.getText());

                    myPaint.setGraph(amin, amax, bmin, bmax);
                    frame.repaint();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                    System.out.println("Введено некорректное число!");
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                amin = 0;
                amax = 0;
                bmin = 0;
                bmax = 0;
            }
        });




    }
}*/
