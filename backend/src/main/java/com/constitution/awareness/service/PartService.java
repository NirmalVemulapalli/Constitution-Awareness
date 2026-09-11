package com.constitution.awareness.service;

import com.constitution.awareness.dto.PartRequest;
import com.constitution.awareness.entity.Part;
import com.constitution.awareness.exception.DuplicateResourceException;
import com.constitution.awareness.exception.ResourceNotFoundException;
import com.constitution.awareness.repository.PartRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PartService {

    private final PartRepository repository;


    public PartService(
            PartRepository repository
    ) {
        this.repository = repository;
    }


    public List<Part> getAll() {

        return repository.findAll();
    }


    public Part getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Part not found"
                        )
                );
    }


    public Part create(
            PartRequest request
    ) {

        if (repository.existsByPartNumber(
                request.getPartNumber()
        )) {

            throw new DuplicateResourceException(
                    "Part number already exists"
            );
        }


        Part part = new Part(

                request.getPartNumber(),

                request.getTitle(),

                request.getDescription()

        );


        return repository.save(part);
    }


    public Part update(
            Long id,
            PartRequest request
    ) {

        Part part = getById(id);


        if (!part.getPartNumber()
                .equals(request.getPartNumber())
                &&
                repository.existsByPartNumber(
                        request.getPartNumber()
                )) {

            throw new DuplicateResourceException(
                    "Part number already exists"
            );
        }


        part.setPartNumber(
                request.getPartNumber()
        );

        part.setTitle(
                request.getTitle()
        );

        part.setDescription(
                request.getDescription()
        );


        return repository.save(part);
    }


    public void delete(Long id) {

        Part part = getById(id);

        repository.delete(part);
    }
}