package com.example;

/**
 * Created by Administrator on 2016-10-05.
 */
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;



@RequestMapping(method = RequestMethod.POST, path = "project/{projectId}/addposts")
public String InsertPosts(HttpServletRequest request, @RequestParam String title, @PathVariable long projectId, @RequestParam long pledged, @RequestParam String message ) {

    Project project = projectRepository.postComment(title, projectId, pledged, message);
    String redirectUrl = request.getScheme() + "://localhost:8080/project/{projectId}/";
    return "redirect:" + redirectUrl;

}

    @RequestMapping(method = RequestMethod.GET, path = "/project/{projectId}")
    public ModelAndView listPostsafter(@PathVariable long projectId) {
        Project project = projectRepository.getProject(projectId);
        List<Comment> comments = projectRepository.getEntriesIn(project);
        return new ModelAndView("posts")
                .addObject("project", project)
                .addObject("posts", comments)
                .addObject("author", projectRepository.getAuthorOf(project));

    }

    @RequestMapping(method = RequestMethod.GET, path = "/project/")
    public ModelAndView listProjects() {
        List<Project> projects = projectRepository.getAllProjects();

        return new ModelAndView("projects")
                .addObject("projects", projects);

    }




}

