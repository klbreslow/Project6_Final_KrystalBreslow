package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AggregatorService {

    private AggregatorRestClient restClient;

    public AggregatorService(AggregatorRestClient restClient) {
        this.restClient = restClient;
    }

    public Entry getDefinitionFor(String word) {

        return restClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsStartingWith(String chars) {

        return restClient.getWordsStartingWith(chars);
    }

    public List<Entry> getWordsEndingWith(String chars) {

        return restClient.getWordsEndingWith(chars);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = restClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = restClient.getWordsThatContainConsecutiveLetters();

        // combine lists with common words
        List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);

        return common;
    }

    public List<Entry> getAllPalindromes() {

        final List<Entry> candidates = new ArrayList<>();

//        //<<<<------- Extra Credit ------->>>>
//        final List<Entry> results = new ArrayList<>();
//        // Populate a list with all words within the dictionary between a-z
//        IntStream.range('a', '{')
//                .mapToObj(i -> Character.toString(i))
//                .forEach(c -> {
//
//                    //get words starting and ending with character
//                    List<Entry> words = restClient.getWordsStartingWith(c);
//
//                    //store list with existing entries
//                    candidates.addAll(words);
//                });
//
//        // For loop grabs all words and iterates through them
//        for (int i = 0; i < candidates.size() - 1; i++) {
//            //Initial new character array
//            char[] charArray = new char[candidates.get(i).getWord().length()];
//
//            //For loop grabs all letters within the word and iterates through them in reverse
//            for (int j = candidates.get(i).getWord().length() - 1; j >= 0; j--) {
//                //Unique case where all single words are palindromes
//                if (candidates.get(i).getWord().length() == 1) {
//                    results.add(candidates.get(i));
//                }
//                if (candidates.get(i).getWord().length() > 1) {
//                    // Reverses character positions creating a new word that is the reverse of the original
//                    charArray[charArray.length - 1 - j] = candidates.get(i).getWord().charAt(j);
//                }
//            }
//            String revWord = new String(charArray);
//            String normWord = candidates.get(i).getWord();
//
//            // Compares the original to the reversed word, if equal populate new list
//            if (revWord.equals(normWord)) {
//                results.add(candidates.get(i));
//            }
//        }
//
//        // Returns the results
//        return results;

        // Iterate from a to z
        IntStream.range('a', '{')
                .mapToObj(i -> Character.toString(i))
                .forEach(c -> {

                    // get words starting and ending with character
                    List<Entry> startsWith = restClient.getWordsStartingWith(c);
                    List<Entry> endsWith = restClient.getWordsEndingWith(c);

                    // keep entries that exist in both lists
                    List<Entry> startsAndEndsWith = new ArrayList<>(startsWith);
                    startsAndEndsWith.retainAll(endsWith);

                    // store list with existing entries
                    candidates.addAll(startsAndEndsWith);
                });

        // test each entry for palindrome, sort and return
        return candidates.stream()
                .filter(entry -> {
                    String word = entry.getWord();
                    String reverse = new StringBuilder(word).reverse()
                            .toString();
                    return word.equals(reverse);
                })
                .sorted()
                .collect(Collectors.toList());
    }
}
