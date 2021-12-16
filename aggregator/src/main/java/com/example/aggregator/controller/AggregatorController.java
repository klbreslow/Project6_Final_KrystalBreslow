package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatorController {

    private static final Logger logger = LoggerFactory.getLogger(AggregatorController.class.getName());

    private AggregatorService service;

    public AggregatorController(AggregatorService service) {
        this.service = service;
    }

    @GetMapping("/getDefinitionFor/{word}")
    public Entry getDefinitionFor(@PathVariable String word) {

        return service.getDefinitionFor(word);
    }

    @GetMapping("/getWordsStartingWith/{chars}")
    public List<Entry> getWordsStartingWith(@PathVariable String chars) {

        return service.getWordsStartingWith(chars);
    }

    @GetMapping("/getWordsEndingWith/{chars}")
    public List<Entry> getWordsEndingWith(@PathVariable String chars) {

        return service.getWordsEndingWith(chars);
    }

    @GetMapping("/getWordsThatContainSuccessiveLettersAndStartsWith/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(@PathVariable String chars) {

        StopWatch sw = new StopWatch();

        sw.start();
        List<Entry> listOfWords = service.getWordsThatContainSuccessiveLettersAndStartsWith(chars);
        sw.stop();

        long nanoSeconds = sw.getLastTaskTimeNanos();
        String message = new StringBuilder().append("Retrieved Entries for words starting with [")
                .append(chars)
                .append("] and that contain successive double letters in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();
        logger.info(message);

        return listOfWords;
    }

    @GetMapping("/getAllPalindromes")
    public List<Entry> getAllPalindromes() {

        return service.getAllPalindromes();
    }
}
