package com.example.todo.dto.service.projects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.example.todo.entity.Project;
import com.example.todo.repository.ProjectRepository;
import com.example.todo.service.projects.ProjectCreateService;
import com.example.todo.dto.request.projects.ProjectCreateRequest;

public class ProjectCreateServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private ProjectCreateService projectCreateService;

  @BeforeEach
  void SetUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void プロジェクトが作成されること() {
      Project mockProject = new Project();
      mockProject.setId(1);
      mockProject.setName("Test Project");
      mockProject.setSummary("Test Summary");

      Mockito.when(this.projectRepository.save(Mockito.any(Project.class))).thenReturn(mockProject);

      ProjectCreateRequest request = new ProjectCreateRequest("Test Project", "Test Summary");

      Project createdProject = this.projectCreateService.invoke(request);

      assertEquals(1, createdProject.getId());
      assertEquals("Test Project", createdProject.getName());
      assertEquals("Test Summary", createdProject.getSummary());

      Mockito.verify(this.projectRepository, Mockito.times(1)).save(Mockito.any(Project.class));
  }
}