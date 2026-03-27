package com.taskforge.taskforge_projectservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberRequest {

    @NotNull(message = "User ID is required")
    private Long userId;
}