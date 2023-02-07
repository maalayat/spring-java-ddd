package ec.solmedia.mooc.courses_counter.domain;

import ec.solmedia.mooc.courses.domain.CourseId;
import ec.solmedia.mooc.courses.domain.CourseIdMother;
import java.util.ArrayList;
import java.util.List;

public class CoursesCounterMother {

  public static CoursesCounter random() {
    List<CourseId> existingCourses = new ArrayList<>();
    existingCourses.add(CourseIdMother.random());
    existingCourses.add(CourseIdMother.random());
    existingCourses.add(CourseIdMother.random());

    return new CoursesCounter(
        CoursesCounterIdMother.random(),
        CoursesCounterTotalMother.create(3),
        existingCourses
    );
  }

  public static CoursesCounter withCourseId(CourseId courseId) {
    List<CourseId> existingCourses = new ArrayList<>();
    existingCourses.add(courseId);

    return new CoursesCounter(
        CoursesCounterIdMother.random(),
        CoursesCounterTotalMother.one(),
        existingCourses
    );
  }

  public static CoursesCounter incrementing(CoursesCounter coursesCounter, CourseId courseId) {
    List<CourseId> existingCourses = new ArrayList<>(coursesCounter.existingCourses());
    existingCourses.add(courseId);

    return new CoursesCounter(
        coursesCounter.id(),
        CoursesCounterTotalMother.create(coursesCounter.total().value() + 1),
        existingCourses
    );
  }
}
