package com.project.quizo.service.ServiceImpl;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collection;

public class Test {
    public static void main(String[] args) throws IOException {
//        String filePath = new ClassPathResource("files/test.txt").getFile().getAbsolutePath();
//        SentenceIterator iter = new BasicLineIterator(filePath);
//        TokenizerFactory t = new DefaultTokenizerFactory();
//        t.setTokenPreProcessor(new CommonPreprocessor());
//        Word2Vec vec = new Word2Vec.Builder()
//                .minWordFrequency(2)
//                .layerSize(100)
//                .seed(42)
//                .windowSize(5)
//                .iterate(iter)
//                .tokenizerFactory(t)
//                .build();
//        vec.fit();
        Word2Vec vec = WordVectorSerializer.readWord2VecModel(new ClassPathResource("files/deps.words.zip").getFile());
        //Word2Vec vec = WordVectorSerializer.readWord2VecModel("src/main/resources/files/trainedData.txt");
        //WordVectorSerializer.writeWord2VecModel(vec, new ClassPathResource("files/trainedData.txt").getFile().getAbsolutePath());
        try {
            Collection<String> lst = vec.wordsNearestSum("Japan", 10);
        } catch (NullPointerException exe) {
            System.out.println("Word not found");
        }
        int a = 1;
    }
}
