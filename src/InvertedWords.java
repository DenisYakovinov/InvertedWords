import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InvertedWords {
    private static List<Pair> result = new LinkedList<>();
    private static String filePath = "";
    private static StringBuilder allWordsFromFileBuilderStr = new StringBuilder();
    private static Map<String, String> mapOfWordsForResult = new LinkedHashMap<>();

    public static void main(String[] args) {
        try (BufferedReader readerFromKeyboard = new BufferedReader(new InputStreamReader(System.in))) {
            filePath = readerFromKeyboard.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader readerWordsFromFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            while (readerWordsFromFile.ready()) {
                allWordsFromFileBuilderStr.append(readerWordsFromFile.readLine()).append("\n");
            }
        } catch (IOException e) {
            System.out.printf("File Name: %s %s", e.getMessage(), e.getClass().getSimpleName());
        }
        String allWordsFromFile = allWordsFromFileBuilderStr.toString();
        String[] splitWords = allWordsFromFile.split("[ \\n]");

        for (int i = 0; i < splitWords.length; i++) {
            StringBuilder stringBuilder = new StringBuilder(splitWords[i]).reverse();
            String backwardsWord = stringBuilder.toString();
            for (int c = 0; c < splitWords.length; c++) {
                if (i == c) continue;
                if (backwardsWord.equals(splitWords[c])) {
                    if (!mapOfWordsForResult.containsKey(splitWords[c])) {
                        mapOfWordsForResult.put(splitWords[i], splitWords[c]);
                    }
                }
            }
        }
        mapOfWordsForResult.forEach((key, value) -> {
            Pair pair = new Pair();
            pair.first = key;
            pair.second = value;
            result.add(pair);
        });

        System.out.println(result.toString());
    }

    public static class Pair {
        String first;
        String second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (!Objects.equals(first, pair.first)) return false;
            return Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return first == null && second == null ? "" :
                    first == null ? second :
                            second == null ? first :
                                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }
}