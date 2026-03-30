package com.taskforge.taskforge_projectservice.controller;

import com.taskforge.taskforge_projectservice.dto.TaskRequest;
import com.taskforge.taskforge_projectservice.dto.TaskResponse;
import com.taskforge.taskforge_projectservice.dto.TaskStatusRequest;
import com.taskforge.taskforge_projectservice.entity.Project;
import com.taskforge.taskforge_projectservice.entity.Task;
import com.taskforge.taskforge_projectservice.security.JwtUtil;
import com.taskforge.taskforge_projectservice.service.ProjectService;
import com.taskforge.taskforge_projectservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@PathVariable("projectId") Long projectId) {
        List<TaskResponse> tasks = taskService.getAllTasksByProject(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks(
            @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserId(authHeader);
        List<TaskResponse> tasks = taskService.getTasksByAssignedUser(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toResponse(taskService.getTaskById(id)));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        Project project = projectService.getProjectById(request.getProjectId());
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setProject(project);
        task.setAssignedUserId(request.getAssignedUserId());
        return ResponseEntity.ok(toResponse(taskService.createTask(task)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskRequest request) {
        Project project = projectService.getProjectById(request.getProjectId());
        Task updatedTask = new Task();
        updatedTask.setTitle(request.getTitle());
        updatedTask.setDescription(request.getDescription());
        updatedTask.setPriority(request.getPriority());
        updatedTask.setProject(project);
        updatedTask.setAssignedUserId(request.getAssignedUserId());
        return ResponseEntity.ok(toResponse(taskService.updateTask(id, updatedTask)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskStatusRequest request) {
        return ResponseEntity.ok(toResponse(taskService.updateTaskStatus(id, request.getStatus())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getProject().getId(),
                task.getAssignedUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}