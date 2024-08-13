package com.example.leave_manager.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    Boolean success;
    Integer statusCode;
    String message;
    int currentPage;
    long totalItems;
    long size;
    int totalPages;
    private T data;
}
