package com.taskforge.taskforge_projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.taskforge.taskforge_projectservice.entity.Project;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private Project.Status status;
    private Project.Priority priority;
    private LocalDateTime createdAt;
    private List<Long> memberIds;
}