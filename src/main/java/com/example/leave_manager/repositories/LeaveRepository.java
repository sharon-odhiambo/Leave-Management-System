package com.example.leave_manager.repositories;

import com.example.leave_manager.models.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest,Long> {
    Page<LeaveRequest> findAll(Specification<LeaveRequest> spec, Pageable updatedPageable);

    LeaveRequest findByEmail(String email);
}
