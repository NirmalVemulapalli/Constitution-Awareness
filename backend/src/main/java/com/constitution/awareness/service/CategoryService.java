package com.constitution.awareness.service;

import com.constitution.awareness.dto.CategoryRequest;
import com.constitution.awareness.entity.Category;
import com.constitution.awareness.exception.DuplicateResourceException;
import com.constitution.awareness.exception.ResourceNotFoundException;
import com.constitution.awareness.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(
            CategoryRepository repository
    ) {
        this.repository = repository;
    }


    public Category create(
            CategoryRequest request
    ) {

        if (repository.existsByName(request.getName())) {

            throw new DuplicateResourceException(
                    "Category already exists"
            );
        }

        Category category = new Category(
                request.getName(),
                request.getDescription()
        );

        return repository.save(category);
    }


    public List<Category> getAll() {

        return repository.findAll();
    }


    public Category getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );
    }


    public Category update(
            Long id,
            CategoryRequest request
    ) {

        Category category = getById(id);

        repository.findByName(request.getName())
                .ifPresent(existingCategory -> {

                    if (!existingCategory.getId().equals(id)) {

                        throw new DuplicateResourceException(
                                "Category already exists"
                        );
                    }
                });

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return repository.save(category);
    }


    public void delete(Long id) {

        Category category = getById(id);

        repository.delete(category);
    }
}