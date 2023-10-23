package javaBasicsTest.basicsTest.src.task3;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitivesCounter {
    public static Map<String, Map<String, Integer>> countPrimitives(File... javaFiles) {
        Map<String, Map<String, Integer>> results = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(javaFiles.length);

        for (File file : javaFiles) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                executorService.submit(() -> {
                    Map<String, Integer> primitiveCounts = new HashMap<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        boolean inCommentBlock = false;
                        boolean inString = false;

                        while ((line = reader.readLine()) != null) {
                            if (inCommentBlock) {
                                int endIndex = line.indexOf("*/");
                                if (endIndex != -1) {
                                    inCommentBlock = false;
                                    line = line.substring(endIndex + 2);
                                } else {
                                    continue;
                                }
                            }
                            line = line.replaceAll("//.*", "");
                            if (line.contains("/*")) {
                                inCommentBlock = true;
                                line = line.substring(0, line.indexOf("/*"));
                            }
                            if (!inString) {
                                line = removeStrings(line);
                            }
                            while (line.contains("\"")) {
                                int startIndex = line.indexOf("\"");
                                int endIndex = line.indexOf("\"", startIndex + 1);
                                if (startIndex != -1 && endIndex != -1) {
                                    String stringContent = line.substring(startIndex, endIndex + 1);
                                    line = line.replace(stringContent, "");
                                } else {
                                    inString = true;
                                    break;
                                }
                            }
                            if (inString) {
                                continue;
                            }
                            String[] words = line.split("\\s+");
                            for (String word : words) {
                                if (isPrimitiveType(word)) {
                                    primitiveCounts.merge(word, 1, Integer::sum);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                            results.put(file.getName(), primitiveCounts);
                });
            } else {
                System.out.println("File skipped: " + file.getName() + " (is not java file!");
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    private static String removeStrings(String line) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "");
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private static boolean isPrimitiveType(String word) {
        List<String> primitiveTypes = Arrays.asList(
                "boolean", "byte", "char", "short", "int", "long", "float", "double"
        );

        return primitiveTypes.contains(word);
    }
}