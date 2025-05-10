package com.Ashmo.BugService.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ashmo.BugService.DTO.BugWithComment;
import com.Ashmo.BugService.Feign.CommentsInterface;
import com.Ashmo.BugService.Feign.UsersInterface;
import com.Ashmo.BugService.Model.BugWrapper;
import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Model.Comments;
import com.Ashmo.BugService.Repository.BugsRepo;

@Service
public class BugsService {

    @Autowired
    private BugsRepo bugsRepo;

    @Autowired
    private CommentsInterface commentsInterface;
    @Autowired
    private UsersInterface usersInterface;
    
    public List<Bugs> getAllBugs() {
        return bugsRepo.findAll();
    }

    public List<Bugs> getBugByDeveloperId(int id) {
        return bugsRepo.findByDeveloperId(id);
    }

    public Bugs createBug(Bugs bug) {
        bug.setCreatedDate(new Date(System.currentTimeMillis()));
        bug.setStatus("new");
        return bugsRepo.save(bug);
    }

    public Bugs updateBug(Bugs bug) {
        return bugsRepo.save(bug);
    }
    
    public BugWithComment getBugById(int id) {
        BugWithComment bugWithComment = new BugWithComment();

        List<Comments> comments = commentsInterface.getCommentsByBugId(id);
        if(comments != null){
            comments.forEach(comment -> {
                comment.setUsername(usersInterface.getUsername(comment.getUserId()));
            });
        }
        bugWithComment.setComments(comments);

        Bugs bug = bugsRepo.findById(id).orElse(null);
        BugWrapper bugWrapper = new BugWrapper();
        bugWrapper.setTitle(bug.getTitle());
        bugWrapper.setDescription(bug.getDescription());
        bugWrapper.setStatus(bug.getStatus());
        bugWrapper.setPriority(bug.getPriority());
        bugWrapper.setCreatedDate(bug.getCreatedDate());
        bugWrapper.setUpdatedDate(bug.getUpdatedDate());
        bugWrapper.setTesterName(usersInterface.getUsername(bug.getTesterId()));
        bugWithComment.setBug(bugWrapper);
        
        return bugWithComment;
    }

    public List<Bugs> getBugsByStatus(String status) {
        return bugsRepo.findByStatus(status);
    }

    public List<Bugs> getBugsByPriority(String priority) {
        return bugsRepo.findByPriority(priority);
    }

    public List<Bugs> getBugByTesterId(int id) {
        return bugsRepo.findByTesterId(id);
    }
    
}
