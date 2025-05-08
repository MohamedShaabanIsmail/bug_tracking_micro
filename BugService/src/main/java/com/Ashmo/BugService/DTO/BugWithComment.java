package com.Ashmo.BugService.DTO;

import java.util.List;

import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Model.Comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugWithComment {

    private Bugs bug;

    private List<Comments> comments;

}
