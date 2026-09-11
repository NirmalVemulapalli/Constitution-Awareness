package com.constitution.awareness.service;

import com.constitution.awareness.dto.LearningModuleRequest;
import com.constitution.awareness.dto.LearningModuleResponse;
import com.constitution.awareness.dto.LessonRequest;
import com.constitution.awareness.dto.LessonResponse;

import com.constitution.awareness.entity.LearningModule;
import com.constitution.awareness.entity.Lesson;

import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.LearningModuleRepository;
import com.constitution.awareness.repository.LessonRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LearningService {


    private final LearningModuleRepository learningModuleRepository;

    private final LessonRepository lessonRepository;


    public LearningService(

            LearningModuleRepository learningModuleRepository,

            LessonRepository lessonRepository

    ) {

        this.learningModuleRepository =
                learningModuleRepository;

        this.lessonRepository =
                lessonRepository;
    }


    /*
     * =====================================
     * ADMIN - CREATE MODULE
     * =====================================
     */

    public LearningModuleResponse createModule(

            LearningModuleRequest request

    ) {


        LearningModule module =
                new LearningModule();


        module.setTitle(
                request.getTitle()
        );


        module.setDescription(
                request.getDescription()
        );


        module.setThumbnailUrl(
                request.getThumbnailUrl()
        );


        module.setDisplayOrder(

                request.getDisplayOrder() != null

                        ?

                        request.getDisplayOrder()

                        :

                        0
        );


        module.setPublished(
                request.isPublished()
        );


        LearningModule savedModule =

                learningModuleRepository.save(
                        module
                );


        return mapModuleToResponse(
                savedModule
        );
    }


    /*
     * =====================================
     * ADMIN - UPDATE MODULE
     * =====================================
     */

    public LearningModuleResponse updateModule(

            Long moduleId,

            LearningModuleRequest request

    ) {


        LearningModule module =

                getModuleById(
                        moduleId
                );


        module.setTitle(
                request.getTitle()
        );


        module.setDescription(
                request.getDescription()
        );


        module.setThumbnailUrl(
                request.getThumbnailUrl()
        );


        if (
                request.getDisplayOrder() != null
        ) {

            module.setDisplayOrder(
                    request.getDisplayOrder()
            );
        }


        module.setPublished(
                request.isPublished()
        );


        LearningModule updatedModule =

                learningModuleRepository.save(
                        module
                );


        return mapModuleToResponse(
                updatedModule
        );
    }


    /*
     * =====================================
     * ADMIN - DELETE MODULE
     * =====================================
     */

    public void deleteModule(

            Long moduleId

    ) {


        LearningModule module =

                getModuleById(
                        moduleId
                );


        /*
         * Delete lessons belonging
         * to this module first.
         */

        List<Lesson> lessons =

                lessonRepository
                        .findByModuleOrderByDisplayOrderAsc(
                                module
                        );


        if (
                !lessons.isEmpty()
        ) {

            lessonRepository.deleteAll(
                    lessons
            );
        }


        learningModuleRepository.delete(
                module
        );
    }


    /*
     * =====================================
     * ADMIN - GET ALL MODULES
     * =====================================
     */

    public List<LearningModuleResponse> getAllModulesForAdmin() {


        return learningModuleRepository
                .findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(
                        this::mapModuleToResponse
                )
                .toList();
    }


    /*
     * =====================================
     * USER - GET PUBLISHED MODULES
     * =====================================
     */

    public List<LearningModuleResponse> getPublishedModules() {


        return learningModuleRepository
                .findByPublishedTrueOrderByDisplayOrderAsc()
                .stream()
                .map(
                        this::mapModuleToResponse
                )
                .toList();
    }


    /*
     * =====================================
     * GET MODULE BY ID
     * =====================================
     */

    public LearningModuleResponse getModule(

            Long moduleId

    ) {


        LearningModule module =

                getModuleById(
                        moduleId
                );


        return mapModuleToResponse(
                module
        );
    }


    /*
     * =====================================
     * ADMIN - CREATE LESSON
     * =====================================
     */

    public LessonResponse createLesson(

            LessonRequest request

    ) {


        LearningModule module =

                getModuleById(
                        request.getModuleId()
                );


        Lesson lesson =
                new Lesson();


        lesson.setModule(
                module
        );


        lesson.setTitle(
                request.getTitle()
        );


        lesson.setDescription(
                request.getDescription()
        );


        lesson.setContent(
                request.getContent()
        );


        lesson.setDifficultyLevel(
                request.getDifficultyLevel()
        );


        lesson.setEstimatedMinutes(
                request.getEstimatedMinutes()
        );


        lesson.setDisplayOrder(

                request.getDisplayOrder() != null

                        ?

                        request.getDisplayOrder()

                        :

                        0
        );


        lesson.setPublished(
                request.isPublished()
        );


        Lesson savedLesson =

                lessonRepository.save(
                        lesson
                );


        return mapLessonToResponse(
                savedLesson
        );
    }


    /*
     * =====================================
     * ADMIN - UPDATE LESSON
     * =====================================
     */

    public LessonResponse updateLesson(

            Long lessonId,

            LessonRequest request

    ) {


        Lesson lesson =

                getLessonById(
                        lessonId
                );


        /*
         * Change module if necessary
         */

        if (

                request.getModuleId() != null

                        &&

                (
                        lesson.getModule() == null

                                ||

                        !lesson.getModule()
                                .getId()
                                .equals(
                                        request.getModuleId()
                                )
                )

        ) {

            LearningModule module =

                    getModuleById(
                            request.getModuleId()
                    );


            lesson.setModule(
                    module
            );
        }


        lesson.setTitle(
                request.getTitle()
        );


        lesson.setDescription(
                request.getDescription()
        );


        lesson.setContent(
                request.getContent()
        );


        lesson.setDifficultyLevel(
                request.getDifficultyLevel()
        );


        lesson.setEstimatedMinutes(
                request.getEstimatedMinutes()
        );


        if (
                request.getDisplayOrder() != null
        ) {

            lesson.setDisplayOrder(
                    request.getDisplayOrder()
            );
        }


        lesson.setPublished(
                request.isPublished()
        );


        Lesson updatedLesson =

                lessonRepository.save(
                        lesson
                );


        return mapLessonToResponse(
                updatedLesson
        );
    }


    /*
     * =====================================
     * ADMIN - DELETE LESSON
     * =====================================
     */

    public void deleteLesson(

            Long lessonId

    ) {


        Lesson lesson =

                getLessonById(
                        lessonId
                );


        lessonRepository.delete(
                lesson
        );
    }


    /*
     * =====================================
     * ADMIN - GET LESSONS BY MODULE
     * =====================================
     */

    public List<LessonResponse> getLessonsByModuleForAdmin(

            Long moduleId

    ) {


        LearningModule module =

                getModuleById(
                        moduleId
                );


        return lessonRepository
                .findByModuleOrderByDisplayOrderAsc(
                        module
                )
                .stream()
                .map(
                        this::mapLessonToResponse
                )
                .toList();
    }


    /*
     * =====================================
     * USER - GET PUBLISHED LESSONS
     * =====================================
     */

    public List<LessonResponse> getPublishedLessonsByModule(

            Long moduleId

    ) {


        LearningModule module =

                learningModuleRepository
                        .findById(
                                moduleId
                        )
                        .filter(
                                LearningModule::isPublished
                        )
                        .orElseThrow(

                                () ->
                                        new ResourceNotFoundException(
                                                "Published learning module not found"
                                        )

                        );


        return lessonRepository
                .findByModuleAndPublishedTrueOrderByDisplayOrderAsc(
                        module
                )
                .stream()
                .map(
                        this::mapLessonToResponse
                )
                .toList();
    }


    /*
     * =====================================
     * USER - GET PUBLISHED LESSON
     * =====================================
     */

    public LessonResponse getPublishedLesson(

            Long lessonId

    ) {


        Lesson lesson =

                lessonRepository
                        .findByIdAndPublishedTrue(
                                lessonId
                        )
                        .orElseThrow(

                                () ->
                                        new ResourceNotFoundException(
                                                "Published lesson not found"
                                        )

                        );


        /*
         * Extra safety:
         *
         * Don't expose lessons belonging
         * to unpublished modules.
         */

        if (

                lesson.getModule() == null

                        ||

                !lesson.getModule()
                        .isPublished()

        ) {

            throw new ResourceNotFoundException(
                    "Published lesson not found"
            );
        }


        return mapLessonToResponse(
                lesson
        );
    }


    /*
     * =====================================
     * PRIVATE - GET MODULE ENTITY
     * =====================================
     */

    private LearningModule getModuleById(

            Long moduleId

    ) {


        return learningModuleRepository
                .findById(
                        moduleId
                )
                .orElseThrow(

                        () ->
                                new ResourceNotFoundException(
                                        "Learning module not found"
                                )

                );
    }


    /*
     * =====================================
     * PRIVATE - GET LESSON ENTITY
     * =====================================
     */

    private Lesson getLessonById(

            Long lessonId

    ) {


        return lessonRepository
                .findById(
                        lessonId
                )
                .orElseThrow(

                        () ->
                                new ResourceNotFoundException(
                                        "Lesson not found"
                                )

                );
    }


    /*
     * =====================================
     * MAP MODULE TO RESPONSE
     * =====================================
     */

    private LearningModuleResponse mapModuleToResponse(

            LearningModule module

    ) {


        return new LearningModuleResponse(

                module.getId(),

                module.getTitle(),

                module.getDescription(),

                module.getThumbnailUrl(),

                module.getDisplayOrder(),

                module.isPublished()

        );
    }


    /*
     * =====================================
     * MAP LESSON TO RESPONSE
     * =====================================
     */

    private LessonResponse mapLessonToResponse(

            Lesson lesson

    ) {


        LearningModule module =
                lesson.getModule();


        return new LessonResponse(

                lesson.getId(),

                module != null

                        ?

                        module.getId()

                        :

                        null,

                module != null

                        ?

                        module.getTitle()

                        :

                        null,

                lesson.getTitle(),

                lesson.getDescription(),

                lesson.getContent(),

                lesson.getDifficultyLevel(),

                lesson.getEstimatedMinutes(),

                lesson.getDisplayOrder(),

                lesson.isPublished()

        );
    }
}