package ru.sstu.nlp;

public class Task5 {
    static void main() {
        int[][] graph = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
        };

        System.out.println("Исходные отношения:");
        print(graph);

        int[][] closure = detectTransitiveClosure(graph);
        System.out.println("Выявленные транзитивные замыкания:");
        print(closure);


    }

    private static int[][] detectTransitiveClosure(int[][] matrix) {
        int n = matrix.length;
        int[][] closure = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, closure[i], 0, n);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (closure[i][k] == 1) {
                    for (int j = 0; j < n; j++) {
                        closure[i][j] = closure[i][j] | closure[k][j];
                    }
                }
            }
        }

        return closure;
    }

    private static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    System.out.println("(" + (i + 1) + "; " + (j + 1) + ")");
                }
            }
        }
    }
}
