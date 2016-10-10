package com.example;

/**
 * Created by Administrator on 2016-10-05.
 */
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;


@RequestMapping(method = RequestMethod.POST, path = "project/{projectId}/addposts")
public String InsertPosts(HttpServletRequest request, @RequestParam String title, @PathVariable long projectId, @RequestParam long pledged, @RequestParam String message ) {
    Project project = projectRepository.postComment(title, projectId, pledged, message);
    String redirectUrl = request.getScheme() + "://localhost:8080/project/{projectId}";
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

    @GetMapping("/newproject")
    public String newProject(Model model, HttpSession session){
        String userName= (String) session.getAttribute("user");
        List<Project> projects=projectRepository.getUsersProjects(userName);
        model.addAttribute("project", new Project());
        model.addAttribute("user",userName);
        model.addAttribute("projects",projects);
        return "newProject";
    }
    @RequestMapping(method=RequestMethod.POST, path="/newproject")
    public String addNewProject(@ModelAttribute Project project, HttpSession session) {
        String userName= (String) session.getAttribute("user");
        if (userName != null) {
                    project.setUser_ID(5); //Hårdkodat tills vi får inloggad User
            projectRepository.newProject(project);
            return "redirect:/newproject";

        } else {
            return "redirect:/logIn";
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/logIn")
    public ModelAndView logIn() {
        return new ModelAndView("/logIn");
    } // Logga in sidan.

    @RequestMapping(method = RequestMethod.POST, path = "/logIn")
    public String submit(HttpSession session, @RequestParam String fname, @RequestParam String lname) {
        if (projectRepository.getUser(fname, lname )) {
            session.setAttribute("user", fname);
            return "redirect:/newproject";
        }
        return "/logIn";
        //inloggning med lösenord
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public ModelAndView createUser() {
        return new ModelAndView("/createUser");
    }
    // Skapa användare.

    @RequestMapping(method = RequestMethod.POST, path = "/userCreated")
    public ModelAndView createdUser(HttpSession session, @RequestParam String spara1, @RequestParam String spara2) {
        projectRepository.postUser(spara1, spara2);

        return new ModelAndView("/LogIn");
    } //11 Går tillbaka till Login efter man skapat användare. Sparar via databasen användarnamn och lösenord.

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res) {
        session.invalidate();
        Cookie cookie = new Cookie("jsessionid", "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return "logIn";
    }

}

