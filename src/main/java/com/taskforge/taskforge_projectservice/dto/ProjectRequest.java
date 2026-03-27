package com.taskforge.taskforge_projectservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.taskforge.taskforge_projectservice.entity.Project;

@Data
public class ProjectRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Priority is required")
    private Project.Priority priority;
}