package com.taskforge.taskforge_projectservice.repository;

import com.taskforge.taskforge_projectservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // All tasks for project
    List<Task> findByProjectId(Long projectId);

    // All tasks assigned to user
    List<Task> findByAssignedUserId(Long assignedUserId);

    // Taskovi po statusu za projekat
    List<Task> findByProjectIdAndStatus(Long projectId, Task.Status status);

    // Taskovi po prioritetu za projekat
    List<Task> findByProjectIdAndPriority(Long projectId, Task.Priority priority);

    // Svi taskovi dodijeljeni useru na specifičnom projektu
    @Query("SELECT t FROM Task t WHERE t.assignedUserId = :userId AND t.project.id = :projectId")
    List<Task> findByUserIdAndProjectId(@Param("userId") Long userId,
                                        @Param("projectId") Long projectId);

    // Broj taskova po statusu za projekat
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.id = :projectId AND t.status = :status")
    Long countByProjectIdAndStatus(@Param("projectId") Long projectId,
                                   @Param("status") Task.Status status);
}