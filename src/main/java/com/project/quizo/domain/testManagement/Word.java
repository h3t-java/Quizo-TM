package com.project.quizo.domain.testManagement;

import java.util.Objects;

public class Word {
    private String word;

    private String sentence;

    public Word() {
    }

    public Word(String word, String sentence) {
        this.word = word;
        this.sentence = sentence;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) &&
                Objects.equals(sentence, word1.sentence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, sentence);
    }
}
