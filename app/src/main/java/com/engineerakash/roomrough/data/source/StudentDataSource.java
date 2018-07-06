package com.engineerakash.roomrough.data.source;

import android.support.annotation.NonNull;

import com.engineerakash.roomrough.data.Student;

import java.util.List;

/**
 * Main entry point for accessing Student Data
 */
public interface StudentDataSource {

    interface LoadStudentsCallback {
        void onStudentsLoaded(List<Student> students);

        void onDataNotAvailable();

    }

    interface GetStudentCallback {
        void onStudentLoaded(Student student);

        void onDataNotAvailable();
    }

    interface SaveStudentCallback {
        void onStudentSavedSuccessfully();

        void onFailedToSaveStudent();
    }

    interface DeleteAllStudentCallback {
        void onAllStudentDeletedSuccessfully();

        void onFailedToDeleteAllStudent();
    }

    interface DeleteStudentCallback {
        void onStudentDeletedSuccessfully();

        void onFailedToDeleteStudent();
    }

    interface UpdateStudentCallback {
        void onStudentDetailsUpdatedSuccessfully();

        void onFailedToUpdateStudentDetails();
    }

    void getStudents(@NonNull LoadStudentsCallback loadStudentsCallback);

    void getStudent(@NonNull String studentId, @NonNull GetStudentCallback getStudentCallback);

    void saveStudent(@NonNull Student student, @NonNull SaveStudentCallback saveStudentCallback);

    void deleteAllStudent(@NonNull DeleteAllStudentCallback deleteAllStudentCallback);

    void deleteStudent(@NonNull String studentId, @NonNull DeleteStudentCallback deleteStudentCallback);

    void updateStudentDetails(@NonNull Student student, @NonNull UpdateStudentCallback updateStudentCallback);

    void refreshStudents();
}
