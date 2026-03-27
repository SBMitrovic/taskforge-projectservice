package com.taskforge.taskforge_projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.taskforge.taskforge_projectservice.entity.Task;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Task.Status status;
    private Task.Priority priority;
    private Long projectId;
    private Long assignedUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}