package org.example;

import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

public class RotationMethodJacobi {
    public static final Matrix matrix = new Basic2DMatrix(new double[][]{
            {2, -1, 0, 0},
            {-1, 2, -1, 0},
            {0, -1, 2, -1},
            {0, 0, -1, 2}});

    public static final double EPS = 0.1;

    public static void main(String[] args) {
        if (matrix.equals(matrix.transpose())) {
            double summ;
            Matrix A = matrix;
            do {
                Matrix matrixU = new Basic2DMatrix(new double[][]{
                        {1, 0, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}});
                summ = 0;
                double biggest = 0;
                int indexOfI = 0;
                int indexOfJ = 0;
                //find the biggest and index of I and J
                for (int i = 0; i < A.columns(); i++) {
                    for (int j = 0; j < A.columns(); j++) {
                        if (i != j) {
                            if (biggest < Math.abs(A.get(i, j))) {
                                biggest = Math.round(Math.abs(A.get(i, j)) * 100.0) / 100.0;
                                indexOfI = i;
                                indexOfJ = j;
                            }
                        }
                    }
                }

                double phi = 0.5 * Math.atan(2 * A.get(indexOfI, indexOfJ) / (A.get(indexOfI, indexOfI) - A.get(indexOfJ, indexOfJ)));

                matrixU.set(indexOfI, indexOfI, Math.cos(phi));
                matrixU.set(indexOfI, indexOfJ, -Math.sin(phi));
                matrixU.set(indexOfJ, indexOfI, Math.sin(phi));
                matrixU.set(indexOfJ, indexOfJ, Math.cos(phi));

                Matrix AK = matrixU.transpose().multiply(A).multiply(matrixU);
                for (int i = 0; i < AK.columns(); i++) {
                    for (int j = 0; j < AK.columns(); j++) {
                        if (i != j) {
                            summ += Math.pow(AK.get(i, j), 2);
                        }
                    }
                }
                A = AK;
            } while (summ > EPS);
            for (int i = 0; i < A.columns(); i++) {
                for (int j = 0; j < A.columns(); j++) {
                    System.out.print(Math.round(A.get(i, j) * 100.0) / 100.0 + " ");
                }
                System.out.println("\n");
            }
        }
    }
}
