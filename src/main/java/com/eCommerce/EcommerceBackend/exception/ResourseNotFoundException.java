package com.eCommerce.EcommerceBackend.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ResourseNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String filedName;
    Long fieldId;

    public ResourseNotFoundException( String resourceName,String field, String filedName) {
        super(String.format("%s not found with %s: %s ", resourceName,field, filedName));
        this.field = field;
        this.resourceName = resourceName;
        this.filedName = filedName;
    }


    public ResourseNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d ", resourceName,field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

}


