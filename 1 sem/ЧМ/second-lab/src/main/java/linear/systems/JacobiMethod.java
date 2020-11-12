package linear.systems;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.inversion.MatrixInverter;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JacobiMethod{
    public static final Matrix matrix = new Basic2DMatrix(new double[][]{
            {3, -1, 1},
            {-1, 2, 0.5},
            {1, 0.5, 3}});
    public static final double EPS = 0.5;
    protected static final List<Double> b = Arrays.asList(1.0, 1.75, 2.5);

    public static void main(String[] args) {
        List<Double> result = findSolutions(matrix);
        for (int i = 0; i < result.size(); i++) {
            System.out.println("x"+(i+1) + " = " + result.get(i));
        }

        MatrixInverter inverter = matrix.withInverter(LinearAlgebra.GAUSS_JORDAN);
        Matrix reversed = inverter.inverse();

        double infinityNormaMatrix = findInfinityNorm(matrix);
        double infinityNormReversed = findInfinityNorm(reversed);

        System.out.println("The number of conditionality is " + infinityNormaMatrix * infinityNormReversed );
    }

    public static List<Double> findSolutions(Matrix matrix) {
        if(convergenceCheck()) {
            List<Double> xVector = new ArrayList<>(matrix.columns());
            List<Double> xKVector = new ArrayList<>(Collections.nCopies(matrix.columns(), 0.0));
            double difference;
            do{
                xVector.clear();
                xVector.addAll(xKVector);
                xKVector.clear();
                for (int i = 0; i < matrix.rows(); i++) {
                    double sum = 0;
                    for (int j = 0; j < matrix.columns(); j++) {
                        if(i!=j){
                            sum += matrix.get(i,j) * xVector.get(j);
                        }
                    }
                    xKVector.add(i, (b.get(i) - sum) / matrix.get(i, i));
                }
                difference = norm(xVector, xKVector);
            } while(difference > EPS);
            return xKVector;
        }
        return new ArrayList<>();
    }

    private static double norm(List<Double> xVector, List<Double> xKVector) {
        List<Double> norma = new ArrayList<>(matrix.columns());
        for(int i = 0; i < matrix.columns(); i++){
            norma.add(0d);
        }
        for (int i = 0; i < xVector.size(); i++){
            norma.set(i, xKVector.get(i) - xVector.get(i));
        }
        return Collections.max(norma);
    }

    private static boolean convergenceCheck() {
        for (int i = 0; i < matrix.rows(); i++) {
            int sumOfRow = 0;
            for (int j = 0; j < matrix.columns(); j++){
                if(i == j){
                    continue;
                }
                sumOfRow += Math.abs(matrix.get(i, j));
            }
            if(Math.abs(matrix.get(i, i)) <= sumOfRow){
                return false;
            }
        }
        return true;
    }

    private static double findInfinityNorm(Matrix matrix){
        double result = 0;
        for(int i = 0; i < matrix.columns(); i++){
            double tempSum = 0;
            for(int j = 0; j < matrix.columns(); j++){
                tempSum += Math.abs(matrix.get(i, j));
            }
            if(result < tempSum){
                result = tempSum;
            }
        }
        return result;
    }
}
