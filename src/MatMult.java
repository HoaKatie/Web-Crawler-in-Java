public class MatMult {

    public static double[][] matrixMultiplication(double[][] a, double[][] b) {
        int rowa = a.length;
        int rowb = b.length;
        int cola = a[0].length;
        int colb = b[0].length;

        if (cola != rowb) {
            return null;
        }

        double[][] newMatrix = new double[rowa][colb];
        for (int x=0; x<rowa; x++) {
            for (int y=0; y<colb; y++) {
                double product = 0.0;
                for (int z=0; z<rowb; z++) {
                    product += a[x][z] * b[z][y];
                }
                newMatrix[x][y] = product;
            }
        }
        return newMatrix;
    }

    public static double euclideanDistance(double[][] a, double[][] b) { // a AND b ARE SINGLE ROW MATRICES (VECTORS)
        int length = a[0].length; // same as b[0].length
        double total = 0.0;
        for (int i=0; i<length; i++) {
            total += Math.pow((a[0][i] - b[0][i]), 2);
        }
        return Math.pow(total, 0.5);
    }
}
