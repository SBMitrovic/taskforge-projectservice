package com.taskforge.taskforge_projectservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.taskforge.taskforge_projectservice.entity.Task;

@Data
public class TaskStatusRequest {

    @NotNull(message = "Status is required")
    private Task.Status status;
}