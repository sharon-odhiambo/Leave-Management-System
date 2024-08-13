package com.example.leave_manager.specifications;

import com.example.leave_manager.models.LeaveRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LeaveFilterSpecifications {
    public static Specification<LeaveRequest> hasActive(Integer active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }
    public static Specification<LeaveRequest> hasStartDate(LocalDate startDate) {
        return (root, query, cb) -> cb.equal(root.get("startDate"), startDate);
    }
    public static Specification<LeaveRequest> hasEndDate(LocalDate endDate) {
        return (root, query, cb) -> cb.equal(root.get("endDate"), endDate);
    }
    public static Specification<LeaveRequest> hasDateCreated(LocalDate dateCreated) {
        return (root, query, cb) -> cb.equal(root.get("dateCreated"), dateCreated);
    }public static Specification<LeaveRequest> hasDateModified(LocalDate dateModified) {
        return (root, query, cb) -> cb.equal(root.get("dateModified"), dateModified);
    }public static Specification<LeaveRequest> hasStatus(String status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }




}
