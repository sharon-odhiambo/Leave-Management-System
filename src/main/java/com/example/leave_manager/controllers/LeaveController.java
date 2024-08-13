package com.example.leave_manager.controllers;

import com.example.leave_manager.dtos.LeaveRequestDto;
import com.example.leave_manager.models.LeaveRequest;
import com.example.leave_manager.response.ApiResponse;
import com.example.leave_manager.response.GenericResponse;
import com.example.leave_manager.services.LeaveService;
import com.example.leave_manager.specifications.LeaveFilterSpecifications;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<List<LeaveRequestDto>> fetchAllLeave(
            @RequestParam(name = "active", required = false) Integer active,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "dateCreated", required = false) LocalDate dateCreated,
            @RequestParam(name = "dateModified", required = false) LocalDate dateModified,
            @RequestParam(name = "status", required = false) String status,
            Pageable pageable

            ){
        Specification<LeaveRequest> spec = getLeaveSpecification(active, startDate, endDate, dateCreated,dateModified, status);

        return leaveService.findAll(pageable,spec);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LeaveRequest> requestLeave(@Valid @RequestBody  LeaveRequestDto leaveRequestDto){
        return leaveService.createLeave(leaveRequestDto);

    }

    @PutMapping("/{id}/approve")
    public ApiResponse<LeaveRequest> approveLeave(@PathVariable Long id) {
        return leaveService.approveLeave(id);
    }

    @PutMapping("/{id}/reject")
    public ApiResponse<LeaveRequest> rejectLeave(@PathVariable Long id) {
        return leaveService.rejectLeave(id);
    }

    private Specification<LeaveRequest> getLeaveSpecification(Integer active, LocalDate startDate, LocalDate endDate,
                                                              LocalDate dateCreated, LocalDate dateModified, String status) {
        Specification<LeaveRequest> spec = Specification.where( null);
        if (active != null) {
            spec = spec.and(LeaveFilterSpecifications.hasActive(active));
        }
        if (startDate != null) {
            spec = spec.and(LeaveFilterSpecifications.hasStartDate(startDate));
        }
        if (endDate != null) {
            spec = spec.and(LeaveFilterSpecifications.hasEndDate(endDate));
        }
        if (dateCreated != null) {
            spec = spec.and(LeaveFilterSpecifications.hasDateCreated(dateCreated));
        }
        if (dateModified != null) {
            spec = spec.and(LeaveFilterSpecifications.hasDateModified(dateModified));
        }
        if (status != null) {
            spec = spec.and(LeaveFilterSpecifications.hasStatus(status));
        }
        return spec;


    }
}
