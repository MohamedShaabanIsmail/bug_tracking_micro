package com.Ashmo.BugService.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    
    private String username;

    private String comment;
    
    private int userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdDate;

    public Comments(String comment, int userId, Date createdDate) {
        this.comment = comment;
        this.userId = userId;
        this.createdDate = createdDate;
    }

    
}
