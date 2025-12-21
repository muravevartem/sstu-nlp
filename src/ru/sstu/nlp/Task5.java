package ru.sstu.nlp;

public class Task5 {
    static void main() {
        int[][] graph = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
        };

        print("Исходное отношение", graph);

        int[][] transitiveClosure = transitiveClosure(graph);
        print("Транзитивное замыкание", transitiveClosure);

        int[][] reflexiveClosure = reflexiveClosure(graph);
        print("Рефлексивное замыкание", reflexiveClosure);

        int[][] symmetricClosure = symmetricClosure(graph);
        print("Симметричное замыкание", symmetricClosure);
    }

    private static int[][] transitiveClosure(int[][] relation) {
        int n = relation.length;
        int[][] closure = copyArray(relation);

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

    private static int[][] reflexiveClosure(int[][] relation) {
        int n = relation.length;

        int[][] closure = copyArray(relation);
        for (int i = 0; i < n; i++) {
            closure[i][i] = 1;
        }

        return closure;
    }

    private static int[][] symmetricClosure(int[][] relation) {
        int n = relation.length;

        int[][] closure = copyArray(relation);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (relation[i][j] == 1) {
                    closure[j][i] = 1;
                }
            }
        }

        return closure;
    }

    private static void print(String name, int[][] relation) {
        System.out.println(name);
        for (int i = 0; i < relation.length; i++) {
            for (int j = 0; j < relation.length; j++) {
                if (relation[i][j] == 1) {
                    System.out.println("(" + (i + 1) + "; " + (j + 1) + ")");
                }
            }
        }
        System.out.println();
    }

    private static int[][] copyArray(int[][] source) {
        int n = source.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, n);
        }
        return copy;
    }


}
