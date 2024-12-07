package com.example.todo.dto.request.projects;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectCreateRequestTest {
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Test
  void 有効なリクエストであること() {
    ProjectCreateRequest request = new ProjectCreateRequest("Project name", "Project summary");

    Set<ConstraintViolation<ProjectCreateRequest>> violations = this.validator.validate(request);
    assertTrue(violations.isEmpty());

    assertEquals("Project name", request.getName());
    assertEquals("Project summary", request.getSummary());
  }

  @Test
  void 名前が空の場合にエラーになること() {
    ProjectCreateRequest request = new ProjectCreateRequest("", "Project summary");

    Set<ConstraintViolation<ProjectCreateRequest>> violations = this.validator.validate(request);
    assertEquals(1, violations.size());

    ConstraintViolation<ProjectCreateRequest> violation = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals("must not be empty", violation.getMessage());
  }

  @Test
  void 概要が空でも有効なリクエストであること() {
    ProjectCreateRequest request = new ProjectCreateRequest("Project name", "");

    Set<ConstraintViolation<ProjectCreateRequest>> violations = this.validator.validate(request);
    assertTrue(violations.isEmpty());

    assertEquals("Project name", request.getName());
    assertEquals("", request.getSummary());
  }
}
