package com.taskforge.taskforge_projectservice.controller;

import com.taskforge.taskforge_projectservice.dto.MemberRequest;
import com.taskforge.taskforge_projectservice.dto.ProjectRequest;
import com.taskforge.taskforge_projectservice.dto.ProjectResponse;
import com.taskforge.taskforge_projectservice.entity.Project;
import com.taskforge.taskforge_projectservice.security.JwtUtil;
import com.taskforge.taskforge_projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProjectResponse>> getMyProjects(
            @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserId(authHeader);
        List<ProjectResponse> projects = projectService.getProjectsForUser(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUser(@PathVariable("userId") Long userId) {
        List<ProjectResponse> projects = projectService.getProjectsForUser(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toResponse(projectService.getProjectById(id)));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserId(authHeader);
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setPriority(request.getPriority());
        project.setOwnerId(userId);
        return ResponseEntity.ok(toResponse(projectService.createProject(project)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProjectRequest request) {
        Project updatedProject = new Project();
        updatedProject.setName(request.getName());
        updatedProject.setDescription(request.getDescription());
        updatedProject.setPriority(request.getPriority());
        return ResponseEntity.ok(toResponse(projectService.updateProject(id, updatedProject)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<ProjectResponse> addMember(
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(toResponse(projectService.addMember(id, request.getUserId())));
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<ProjectResponse> removeMember(
            @PathVariable("id") Long id,
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(toResponse(projectService.removeMember(id, userId)));
    }

    private Long extractUserId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwnerId(),
                project.getStatus(),
                project.getPriority(),
                project.getCreatedAt(),
                project.getMemberIds()
        );
    }
}