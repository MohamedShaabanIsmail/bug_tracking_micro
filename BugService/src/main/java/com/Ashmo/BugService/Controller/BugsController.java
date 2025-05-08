package com.Ashmo.BugService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Ashmo.BugService.DTO.BugWithComment;
import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Service.BugsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/bugs")
public class BugsController {

    @Autowired
    private BugsService bugsService;

    @PutMapping("/update")
    public ResponseEntity<Bugs> UpdateBug(@RequestBody Bugs bug) {
        Bugs bugUpdated = bugsService.updateBug(bug);
        if (bugUpdated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bugUpdated);
    }

    @PostMapping("/create")
    public ResponseEntity<Bugs> CreateNewBug(@RequestBody Bugs bug) {
        Bugs bugCreated = bugsService.createBug(bug);
        if (bugCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bugCreated);
    }
    
    @GetMapping("/by_priority/{priority}")
    public ResponseEntity<List<Bugs>> getBugsByPriority(@PathVariable String priority) {
        List<Bugs> bugs = bugsService.getBugsByPriority(priority);
        if(bugs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/by_status/{status}")
    public ResponseEntity<List<Bugs>> getBugsByStatus(@PathVariable String status) {
        List<Bugs> bugs = bugsService.getBugsByStatus(status);
        if(bugs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(bugs);
    }
    

    @GetMapping("/developer_id/{id}")
    public ResponseEntity<List<Bugs>> getBugByDevId(@PathVariable int id) {
        List<Bugs> bugs = bugsService.getBugByDeveloperId(id);
        if (bugs == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/tester_id/{id}")
    public ResponseEntity<List<Bugs>> getBugByTesId(@PathVariable int id) {
        List<Bugs> bugs = bugsService.getBugByTesterId(id);
        if (bugs == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/bug_id/{id}")
    public ResponseEntity<BugWithComment> getBugById(@PathVariable int id) {
        BugWithComment bug = bugsService.getBugById(id);
        if (bug.getBug() == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bug);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bugs>> getAllBugs() {
        List<Bugs> bugs = bugsService.getAllBugs();
        if (bugs.isEmpty())
            return ResponseEntity.noContent().build();
        else 
            return ResponseEntity.ok(bugs);
    }
    
}
