package com.example.leave_manager.services;

import com.example.leave_manager.dtos.LeaveRequestDto;
import com.example.leave_manager.enums.Status;
import com.example.leave_manager.exceptions.ResourceNotFoundException;
import com.example.leave_manager.models.LeaveRequest;
import com.example.leave_manager.repositories.LeaveRepository;
import com.example.leave_manager.response.ApiResponse;
import com.example.leave_manager.response.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class LeaveService {
    private final LeaveRepository leaveRepository;
    public GenericResponse<List<LeaveRequestDto>> findAll(Pageable pageable, Specification<LeaveRequest> spec) {
        Pageable updatedPageable = PageRequest.of(pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0,
                pageable.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Page<LeaveRequest> leavePage = leaveRepository.findAll(spec, updatedPageable);
        List<LeaveRequestDto> leaveResponse = leavePage.getContent().stream().map(leaveRequest -> LeaveRequestDto.builder()
                .status(leaveRequest.getStatus().name())
                .leaveType(leaveRequest.getLeaveType())
                .active(leaveRequest.getActive())
                .reason(leaveRequest.getReason())
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .dateCreated(leaveRequest.getDateCreated())
                .dateModified(leaveRequest.getDateModified())
                .build()
        ).collect(Collectors.toList());

        return GenericResponse.<List<LeaveRequestDto>>builder().currentPage(pageable.getPageNumber()).size(pageable.getPageSize())
                .success(Boolean.TRUE).message("Fetched leave request successfully").totalItems(leavePage.getTotalElements())
                .totalPages(leavePage.getTotalPages()).data(leaveResponse).build();

    }

    public ApiResponse<LeaveRequest> createLeave(LeaveRequestDto leaveRequestDto) {
        LeaveRequest leave = LeaveRequest.builder()
                .leaveType(leaveRequestDto.getLeaveType())
                .startDate(leaveRequestDto.getStartDate())
                .endDate(leaveRequestDto.getEndDate())
                .status(Status.PENDING)
                .active(1).build();
        leaveRepository.save(leave);
        return new ApiResponse<>(true, "Leave request created successfully", leave,HttpStatus.OK.value());


    }
    public ApiResponse<LeaveRequest> approveLeave(Long id) {
        return updateLeaveStatus(id, Status.APPROVED, "Leave request approved successfully");
    }

    public ApiResponse<LeaveRequest> rejectLeave(Long id) {
        return updateLeaveStatus(id, Status.REJECTED, "Leave request rejected successfully");
    }
    public ApiResponse<LeaveRequest> updateLeaveStatus(Long id,Status status,String message) {
        LeaveRequest leave = leaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave not found"));
        leave.setStatus(status);
        leave.setDateModified(LocalDateTime.now());
        leaveRepository.save(leave);
        return new ApiResponse<>(true, message, leave,HttpStatus.OK.value());
    }

}
