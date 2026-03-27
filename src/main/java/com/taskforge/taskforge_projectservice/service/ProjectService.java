package com.taskforge.taskforge_projectservice.service;

import com.taskforge.taskforge_projectservice.entity.Project;
import com.taskforge.taskforge_projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsForUser(Long userId) {
        return projectRepository.findAllProjectsForUser(userId);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existing = getProjectById(id);
        existing.setName(updatedProject.getName());
        existing.setDescription(updatedProject.getDescription());
        existing.setStatus(updatedProject.getStatus());
        existing.setPriority(updatedProject.getPriority());
        return projectRepository.save(existing);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project addMember(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        if (!project.getMemberIds().contains(userId)) {
            project.getMemberIds().add(userId);
            projectRepository.save(project);
        }
        return project;
    }

    public Project removeMember(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        project.getMemberIds().remove(userId);
        return projectRepository.save(project);
    }
}