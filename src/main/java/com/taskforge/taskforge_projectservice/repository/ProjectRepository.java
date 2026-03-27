package com.taskforge.taskforge_projectservice.repository;

import com.taskforge.taskforge_projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // All projects where user is owner
    List<Project> findByOwnerId(Long ownerId);

    // All projects where user is member
    @Query("SELECT p FROM Project p WHERE :userId MEMBER OF p.memberIds")
    List<Project> findByMemberId(@Param("userId") Long userId);

    // All projects where user is owner or member
    @Query("SELECT DISTINCT p FROM Project p WHERE p.ownerId = :userId OR :userId MEMBER OF p.memberIds")
    List<Project> findAllProjectsForUser(@Param("userId") Long userId);

    // Projects by status
    List<Project> findByStatus(Project.Status status);

    // Projects sorted by priority 
    List<Project> findByPriority(Project.Priority priority);
}