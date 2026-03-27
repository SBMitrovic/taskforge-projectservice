package com.taskforge.taskforge_projectservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.taskforge.taskforge_projectservice.entity.Task;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private Task.Priority priority;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedUserId;
}
