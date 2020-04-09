package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Out {
    public static int FRAME_WIDTH = 1000;
    public static int FRAME_HEIGHT = 1000;

    JPanel panel4 = new JPanel();
    GridLayout g4 = new GridLayout(3, 1);
    JPanel panel3 = new JPanel();
    GridLayout g3 = new GridLayout(2, 2);

    JLabel label1 = new JLabel("M(x)");
    JTextField field1 = new JTextField(10);
    JLabel label2 = new JLabel("D(x)");
    JTextField field2 = new JTextField(10);

    XYSeriesCollection data = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Кривая");

    XYSeriesCollection data2 = new XYSeriesCollection();
    XYSeries series2 = new XYSeries("Фурье");


    public Out() {
        JFrame frame = new JFrame();
        frame.setTitle("Lab2.1");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        panel4.setLayout(g4);
        panel3.setLayout(g3);

        series1.clear();

        int n = 10;
        int omegaMax =  1500;
        int omega = omegaMax / n;
        int N = 256;
        double[] yArray;
        yArray = new double[N+1];
        double Mean = 0;
        for (int x = 1; x <= N; x++) {
            double y = 0;
            for (int i = 1; i <= n; i++) {
                double A = Math.random();
                double phi = Math.random();
                y += A * Math.sin(omega * i * x + phi);
                omega+=omegaMax/n;
            }
            yArray[x] = y;
            Mean+=y;
            series1.add(x, y);
        }

        for (int p = 1; p <= N; p++) {
            double REbuf =0;
            double lmbuf =0;
            for (int k = 1; k <= n; k++) {
                REbuf+=  yArray[k]*Math.cos(2*Math.PI/N*p*k);
                lmbuf+=  yArray[k]*Math.sin(2*Math.PI/N*p*k);
            }
            series2.add(p, Math.abs(Math.sqrt(Math.pow(REbuf,2)+(Math.pow(lmbuf,2)))));
        }


        Mean /= N;

        double sum = 0;
        for (int x = 1; x <= N; x++) {
            sum += Math.pow((yArray[x]-Mean),2);
        }
        double Dx = sum / (N-1);

        System.out.println("Mean = " + Mean);
        System.out.println("Dx = " + Dx);
        data.addSeries(series1);
        data2.addSeries(series2);

        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "График",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        final ChartPanel chartPanel1 = new ChartPanel(chart1);

        JFreeChart chart2 = ChartFactory.createXYLineChart(
                "График Фурье",
                "X",
                "Y",
                data2,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        final ChartPanel chartPanel2 = new ChartPanel(chart2);

        chartPanel1.setPreferredSize(new java.awt.Dimension(300, 400));

        panel4.add(chartPanel1);
        field1.setText(String.valueOf(Mean));
        field2.setText(String.valueOf(Dx));

        panel3.add(label1);
        panel3.add(field1);
        panel3.add(label2);
        panel3.add(field2);
        panel4.add(panel3);
        panel4.add(chartPanel2);

        frame.add(panel4);
        frame.setVisible(true);
    }
}