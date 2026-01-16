package ru.sstu.nlp;

import java.util.*;

public class Task3 {
    static void main() {
        ViterbiAlgorithm viterbi = new ViterbiAlgorithm();

        String[] sentence1 = {"I", "love", "green", "tea"};
        System.out.println("=".repeat(50));
        viterbi.printProbabilities(sentence1);

        List<String> result1 = viterbi.viterbi(sentence1);
        System.out.println("\nНаиболее вероятная последовательность тегов:");
        System.out.println("Слова:    " + String.join("   ", sentence1));
        System.out.println("Теги:     " + String.join("   ", result1));
    }

    static class ViterbiAlgorithm {

        // Состояния (теги частей речи)
        private String[] states;

        // Начальные вероятности
        private Map<String, Double> startProbability;

        // Матрица переходов (тег -> тег)
        private Map<String, Map<String, Double>> transitionProbability;

        // Матрица эмиссий (тег -> слово)
        private Map<String, Map<String, Double>> emissionProbability;

        public ViterbiAlgorithm() {
            initializeData();
        }

        private void initializeData() {
            // Теги частей речи
            states = new String[]{"NN", "VB", "JJ", "IN"};

            // Начальные вероятности P(tag|START)
            startProbability = new HashMap<>();
            startProbability.put("NN", 0.3);  // существительное
            startProbability.put("VB", 0.2);  // глагол
            startProbability.put("JJ", 0.2);  // прилагательное
            startProbability.put("IN", 0.3);  // предлог

            // Вероятности переходов P(tag_i|tag_{i-1})
            transitionProbability = new HashMap<>();

            // из сущ
            Map<String, Double> nnTrans = new HashMap<>();
            nnTrans.put("NN", 0.2);
            nnTrans.put("VB", 0.3);
            nnTrans.put("JJ", 0.3);
            nnTrans.put("IN", 0.2);
            transitionProbability.put("NN", nnTrans);

            // из глагола
            Map<String, Double> vbTrans = new HashMap<>();
            vbTrans.put("NN", 0.4);
            vbTrans.put("VB", 0.1);
            vbTrans.put("JJ", 0.2);
            vbTrans.put("IN", 0.3);
            transitionProbability.put("VB", vbTrans);

            // из прилагательного
            Map<String, Double> jjTrans = new HashMap<>();
            jjTrans.put("NN", 0.5);
            jjTrans.put("VB", 0.1);
            jjTrans.put("JJ", 0.2);
            jjTrans.put("IN", 0.2);
            transitionProbability.put("JJ", jjTrans);

            // из предлога
            Map<String, Double> inTrans = new HashMap<>();
            inTrans.put("NN", 0.6);
            inTrans.put("VB", 0.2);
            inTrans.put("JJ", 0.1);
            inTrans.put("IN", 0.1);
            transitionProbability.put("IN", inTrans);

            // Вероятности принадлежности слова к части речи
            emissionProbability = new HashMap<>();

            Map<String, Double> nnEmit = new HashMap<>();
            nnEmit.put("I", 0.1);
            nnEmit.put("love", 0.01);
            nnEmit.put("green", 0.1);
            nnEmit.put("tea", 0.5);
            emissionProbability.put("NN", nnEmit);

            Map<String, Double> vbEmit = new HashMap<>();
            vbEmit.put("I", 0.01);
            vbEmit.put("love", 0.7);
            vbEmit.put("green", 0.01);
            vbEmit.put("tea", 0.01);
            emissionProbability.put("VB", vbEmit);

            Map<String, Double> jjEmit = new HashMap<>();
            jjEmit.put("I", 0.01);
            jjEmit.put("love", 0.01);
            jjEmit.put("green", 0.8);
            jjEmit.put("tea", 0.1);
            emissionProbability.put("JJ", jjEmit);

            Map<String, Double> inEmit = new HashMap<>();
            inEmit.put("I", 0.5);
            inEmit.put("love", 0.01);
            inEmit.put("green", 0.01);
            inEmit.put("tea", 0.01);
            emissionProbability.put("IN", inEmit);
        }

        public List<String> viterbi(String[] observations) {
            int T = observations.length; // длина последовательности
            int N = states.length;       // количество состояний

            // V[t][j] - максимальная вероятность достичь состояния j в момент t
            double[][] V = new double[T][N];

            // backpointer[t][j] - наиболее вероятное предыдущее состояние
            int[][] backpointer = new int[T][N];

            // Инициализация (первый шаг)
            for (int j = 0; j < N; j++) {
                String state = states[j];
                double startProb = startProbability.get(state);
                double emitProb = emissionProbability.get(state).getOrDefault(observations[0], 1e-10);
                V[0][j] = startProb * emitProb;
                backpointer[0][j] = 0;
            }

            // Прямой проход
            for (int t = 1; t < T; t++) {
                for (int j = 0; j < N; j++) {
                    double maxProb = -1.0;
                    int maxIndex = 0;

                    for (int i = 0; i < N; i++) {
                        double transProb = transitionProbability.get(states[i]).get(states[j]);
                        double prob = V[t - 1][i] * transProb;

                        if (prob > maxProb) {
                            maxProb = prob;
                            maxIndex = i;
                        }
                    }

                    double emitProb = emissionProbability.get(states[j]).getOrDefault(observations[t], 1e-10);
                    V[t][j] = maxProb * emitProb;
                    backpointer[t][j] = maxIndex;
                }
            }

            // Находим наилучший конечный тег
            double bestProb = -1.0;
            int bestLastIndex = 0;

            for (int j = 0; j < N; j++) {
                if (V[T - 1][j] > bestProb) {
                    bestProb = V[T - 1][j];
                    bestLastIndex = j;
                }
            }

            // Восстанавливаем последовательность тегов
            List<String> bestPath = new ArrayList<>();
            bestPath.add(states[bestLastIndex]);

            for (int t = T - 1; t > 0; t--) {
                bestLastIndex = backpointer[t][bestLastIndex];
                bestPath.addFirst(states[bestLastIndex]);
            }

            return bestPath;
        }


        public void printProbabilities(String[] observations) {
            System.out.println("Входное предложение: " + String.join(" ", observations));
            System.out.println("\nВероятности эмиссий (P(word|tag)):");

            for (String tag : states) {
                System.out.print(tag + ": ");
                for (String word : observations) {
                    double prob = emissionProbability.get(tag).getOrDefault(word, 0.0);
                    System.out.printf("%s=%.4f ", word, prob);
                }
                System.out.println();
            }
        }
    }
}