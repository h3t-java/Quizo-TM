package com.project.quizo.config;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class VectorData {
    @Bean
    public Word2Vec getWord2Vec() throws IOException {
        return WordVectorSerializer.readWord2VecModel(new ClassPathResource("files/deps.words.zip").getFile());
    }
}
