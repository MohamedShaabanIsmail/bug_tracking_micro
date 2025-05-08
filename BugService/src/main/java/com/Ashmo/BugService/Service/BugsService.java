package com.Ashmo.BugService.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ashmo.BugService.DTO.BugWithComment;
import com.Ashmo.BugService.Feign.CommentsInterface;
import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Repository.BugsRepo;

@Service
public class BugsService {

    @Autowired
    private BugsRepo bugsRepo;

    @Autowired
    private CommentsInterface commentsInterface;
    
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
        bugWithComment.setComments(commentsInterface.getCommentsByBugId(id));
        bugWithComment.setBug(bugsRepo.findById(id).orElse(null));
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
