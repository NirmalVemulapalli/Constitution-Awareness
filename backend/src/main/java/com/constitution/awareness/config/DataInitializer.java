package com.constitution.awareness.config;

import com.constitution.awareness.entity.Category;
import com.constitution.awareness.entity.Part;

import com.constitution.awareness.repository.CategoryRepository;
import com.constitution.awareness.repository.PartRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(

            PartRepository partRepository,

            CategoryRepository categoryRepository

    ) {

        return args -> {


            /*
             * =====================================
             * INITIALIZE PARTS
             * =====================================
             */

            if (
                    partRepository.count() == 0
            ) {

                partRepository.save(
                        new Part(
                                "Part III",
                                "Fundamental Rights",
                                "Fundamental Rights guaranteed by the Constitution."
                        )
                );


                partRepository.save(
                        new Part(
                                "Part IV",
                                "Directive Principles of State Policy",
                                "Principles intended to guide the State in governance."
                        )
                );
            }


            /*
             * =====================================
             * INITIALIZE CATEGORIES
             * =====================================
             */

            if (
                    categoryRepository.count() == 0
            ) {

                categoryRepository.save(
                        new Category(
                                "Right to Equality",
                                "Constitutional protections relating to equality."
                        )
                );


                categoryRepository.save(
                        new Category(
                                "Right to Freedom",
                                "Constitutional freedoms available to citizens."
                        )
                );


                categoryRepository.save(
                        new Category(
                                "Directive Principles",
                                "Principles for governance and social welfare."
                        )
                );
            }
        };
    }
}