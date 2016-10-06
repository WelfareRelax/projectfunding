package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */

@Component
public class SqlServerProjectRepository implements ProjectRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Project> listProjects() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title FROM [dbo].[Project]")) {
            List<Project> blogs = new ArrayList<>();
            while (rs.next()) blogs.add(rsProject(rs));
            return blogs;
        } catch (SQLException e) {
            throw new ProjectRepositoryException(e);
        }
    }

    @Override
    public Project getProject(long projectId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM [dbo].[Project] WHERE id = ?")) {
            ps.setLong(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new ProjectRepositoryException("No project with ID " + projectId);
                else return rsProject(rs);
            }
        } catch (SQLException e) {
            throw new ProjectRepositoryException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Project] ORDER BY id DESC")) {
            List<Project> projects = new ArrayList<>();
            while (rs.next())
                projects.add(rsProject(rs));
            return projects;
        } catch (SQLException e) {
            System.out.println("asdasd");
            throw new ProjectRepositoryException(e);
        }
    }

    public Project postComment(String title, long projectid, long pledged, String message) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO [dbo].[Comments] (title, ProjectID, pledged, message) VALUES (?,?,?,?)")) {

            ps.setString(1, title);
            ps.setLong(2, projectid);
            ps.setLong(3, pledged);
            ps.setString(4, message);

            int rs = ps.executeUpdate();
            if (rs == 0) {

                System.out.println("error is 0");
            }

            return getProject(projectid);



                /*else return rsBlog(rs);*/

        } catch (SQLException e) {
            throw new ProjectRepositoryException(e);
        }
    }


    @Override
    public User getAuthorOf(Project project) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT u.[UserID], u.[UserName], u.[FirstName], u.[LastName] " +
                     "FROM [dbo].[Users] u JOIN [dbo].[Project] b ON b.User_Id = u.UserID " +
                     "WHERE b.id = ?")) {
            ps.setLong(1, project.id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new ProjectRepositoryException("No project with ID " + project.id);
                else return new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            throw new ProjectRepositoryException(e);
        }
    }

    @Override
    public List<Comment> getEntriesIn(Project project) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * " +

                     "FROM [dbo].[Comments] p WHERE P.ProjectID = ?  ORDER BY p.Date DESC")) {
            ps.setLong(1, project.id);

            try (ResultSet rs = ps.executeQuery()) {
                List<Comment> posts = new ArrayList<>();
                while (rs.next()) posts.add(rsPost(rs));
                return posts;
            }
        } catch (SQLException e) {
            throw new ProjectRepositoryException(e);
        }
    }

    private Comment rsPost(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getLong("Id"),
                rs.getLong("ProjectID"),
                rs.getLong("Pledged"),
                rs.getString("Title"),
                rs.getString("Message"),
                rs.getString("Name"),
                rs.getString("Body"),
                rs.getTimestamp("Date").toLocalDateTime());

    }

    private Project rsProject(ResultSet rs) throws SQLException {
        return new Project(rs.getLong("id"), rs.getString("title"), rs.getLong("User_ID"), rs.getString("Body"),
                rs.getLong("Requested"), rs.getLong("Total_fund") );
    }
    public Project newProject(Project project) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO [dbo].[Project] (Title, User_ID, Body, Requested, Total_fund) VALUES (?,?,?,?,0)")) {

            ps.setString(1, project.title);
            ps.setLong(2, project.User_ID);
            ps.setString(3, project.Body);
            ps.setLong(4, project.Requested);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ProjectRepositoryException(e + "Trouble in newProject() in SQL-repo. Could probably not execute SQLupdate");
        }
        return project;
    }
}

