package com.example;

/**
 * Created by Administrator on 2016-10-05.
 */
import java.util.List;

public interface ProjectRepository {
    List<Project> listProjects();
    Project getProject(long projectId);
    User getAuthorOf(Project project);
    List<Comment> getEntriesIn(Project project);
    Project postComment(String title, long projectid, long pledged, String message);
    List<Project> getAllProjects();
    Project newProject(Project project);
    void postUser(String UserName, String Password); //11 sparar användarnamn och lösenord.
    boolean getUser(String UserName, String Password); // Kollar om användarnamn och lösenord stämmer.

    long getUserID(String userName);//

    List<Project> getUsersProjects(String userID);
}
