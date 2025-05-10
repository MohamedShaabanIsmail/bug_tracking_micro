package com.Ashmo.BugService.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugWrapper {

    private String title;
    private String description;
    private String status;
    private String priority;
    private String developerName;
    private String testerName;
    private Date createdDate;
    private Date updatedDate;
    
    public BugWrapper(String title, String description, String status, String priority, Date createdDate,
            Date updatedDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
