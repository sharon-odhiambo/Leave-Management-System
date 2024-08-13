package com.example.leave_manager.dtos;

import com.example.leave_manager.models.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {
    private LeaveType leaveType;
    private String reason;
    @NotNull(message = "email is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateModified;
    private int active;
}
