package linear.systems;

import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SquareRootMethod {
    public static final Matrix matrix = new Basic2DMatrix(new double[][]{
            {1, 2, 3},
            {2, 5, 5},
            {3, 5, 6}});
    protected static final List<Double> b = Arrays.asList(1.0, 2.0, 3.0);

    public static void main(String[] args) {
        findSolutions();
    }

    private static void findSolutions(){
        Matrix sMatrix = new Basic2DMatrix(new double[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}});
        Matrix dMatrix = new Basic2DMatrix(new double[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}});

        findSandDMatrixes(sMatrix, dMatrix);

        Matrix firstEquationMatrix = sMatrix.transpose().multiply(dMatrix);
        List<Double> yResult = new ArrayList<>(Collections.nCopies(matrix.columns(), 0.0));
        solveYEquation(firstEquationMatrix, yResult);

        List<Double> result = new ArrayList<>(Collections.nCopies(matrix.columns(), 0.0));
        for (int i = 0; i< matrix.columns(); i++){
            double tempSum = 0;
            for (int j = 0; j < matrix.columns(); j++){
                tempSum += sMatrix.get(i,j) * result.get(j);
            }
            result.set(i, (yResult.get(i) - tempSum) / sMatrix.get(i,i));
        }
        for (int i = 0; i < result.size(); i++) {
            System.out.println("x"+(i+1) + " = " + result.get(i));
        }

        double determinant = 1;
        for(int i = 0; i < matrix.columns(); i++){
            determinant *= dMatrix.get(i,i) * Math.pow(sMatrix.get(i,i),2);
        }
        System.out.println("The determinant of matrix A= " + determinant);
    }

    private static void findSandDMatrixes(Matrix sMatrix, Matrix dMatrix){
        sMatrix.set(0, 0, Math.signum(matrix.get(0, 0)));
        dMatrix.set(0, 0, Math.sqrt(Math.abs(matrix.get(0, 0))));
        for (int i = 0; i < matrix.rows(); i++){
            for (int j = 0; j < matrix.columns(); j++){
                double tempSum = 0;
                if(i == 0 && i==j){
                    continue;
                }
                if(i == j){
                    for(int p = 0; p < matrix.columns(); p++){
                        tempSum += Math.pow(sMatrix.get(p, i), 2) * dMatrix.get(p,p);
                    }
                    double value = matrix.get(i,i) - tempSum;
                    dMatrix.set(i,i, Math.signum(value));
                    sMatrix.set(i,i, Math.sqrt(Math.abs(value)));
                } else{
                    double denominator = dMatrix.get(i,i) * sMatrix.get(i,i);
                    for(int p = 0; p < matrix.columns(); p++){
                        tempSum += sMatrix.get(p,i)* dMatrix.get(p,p) * sMatrix.get(p, j);
                    }
                    if(denominator == 0){
                        denominator = 1;
                    }
                    sMatrix.set(i,j, (matrix.get(i,j) - tempSum)/denominator);
                }
            }
        }
    }

    private static void solveYEquation( Matrix firstEquationMatrix, List<Double> yResult){
        for (int i = 0; i < matrix.columns(); i++) {
            double tempSum = 0;
            for (int j = 0; j < matrix.columns(); j++) {
                tempSum += firstEquationMatrix.get(i, j) * yResult.get(j);
            }
            tempSum = b.get(i) - tempSum;
            yResult.set(i, (tempSum/firstEquationMatrix.get(i,i)));
        }
    }
}
