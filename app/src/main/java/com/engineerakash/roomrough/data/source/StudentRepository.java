package com.engineerakash.roomrough.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.engineerakash.roomrough.data.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load Student from different sources into cache.
 * <p>
 * First it will search the student in local database, if local database does not exit or
 * is empty, then it will fetch data from remote data source.
 */
public class StudentRepository implements StudentDataSource {

    private static StudentRepository INSTANCE = null;

    private final StudentDataSource mStudentRemoteDataSource;

    private final StudentDataSource mStudentLocalDataSource;

    Map<String, Student> mCachedStudents;

    /**
     * Marks the cache is invalid, to force an update the next time data is required.
     */
    boolean mCacheIsDirty = false;

    // Prevent Direct Instantiation
    private StudentRepository(StudentDataSource studentRemoteDataSource,
                              StudentDataSource studentLocalhostDataSource) {
        mStudentRemoteDataSource = checkNotNull(studentRemoteDataSource);
        mStudentLocalDataSource = checkNotNull(studentLocalhostDataSource);
    }

    /**
     * Creating the single instance of this class, creating if necessary.
     *
     * @param studentRemoteDataSource    the backend data source
     * @param studentLocalhostDataSource the device data source
     * @return the {@link StudentRepository} instance
     */
    public static StudentRepository getInstance(StudentDataSource studentRemoteDataSource,
                                                StudentDataSource studentLocalhostDataSource) {
        if (INSTANCE == null)
            INSTANCE = new StudentRepository(studentRemoteDataSource, studentLocalhostDataSource);
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(StudentDataSource, StudentDataSource)} to create a new
     * instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Get Students from cache, local data source (SqLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadStudentsCallback#onDataNotAvailable()} is fired if all data sources fail
     * to get data.
     */
    @Override
    public void getStudents(@NonNull final LoadStudentsCallback loadStudentsCallback) {
        checkNotNull(loadStudentsCallback);

        // Respond immediately with cache if available & not dirty
        if (mCachedStudents != null && !mCacheIsDirty) {
            loadStudentsCallback.onStudentsLoaded(new ArrayList<>(mCachedStudents.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If cache is dirty we need to fetch data from network
            getStudentsFromRemoteDataSource(loadStudentsCallback);
        } else {
            // Query the local storage if available. If not, query the network
            mStudentLocalDataSource.getStudents(new LoadStudentsCallback() {
                @Override
                public void onStudentsLoaded(List<Student> students) {
                    refreshCache(students);
                    loadStudentsCallback.onStudentsLoaded(students);
                }

                @Override
                public void onDataNotAvailable() {
                    getStudentsFromRemoteDataSource(loadStudentsCallback);
                }
            });
        }
    }

    /**
     * Get Students from local data source (SQLite) unless the table is new or empty. In that case
     * it uses the network data source.
     * <p>
     * Note: {@link GetStudentCallback#onDataNotAvailable()} is fired if both the data sources fails
     * to get data.
     */
    @Override
    public void getStudent(@NonNull final String studentId, @NonNull final GetStudentCallback getStudentCallback) {
        checkNotNull(studentId);
        checkNotNull(getStudentCallback);

        Student cachedStudent = getStudentWithId(studentId);

        // Respond immediately if cache is available and not null
        if (cachedStudent != null) {
            getStudentCallback.onStudentLoaded(cachedStudent);
            return;
        }

        // Load from server/persisted data if needed.
        // Is Student is in the local data storage? If not, query the network.
        mStudentLocalDataSource.getStudent(studentId, new GetStudentCallback() {
            @Override
            public void onStudentLoaded(Student student) {
                // Do in memory cache update to keep the app UI up to data
                if (mCachedStudents == null)
                    mCachedStudents = new LinkedHashMap<>();

                mCachedStudents.put(student.getId(), student);
                getStudentCallback.onStudentLoaded(student);
            }

            @Override
            public void onDataNotAvailable() {
                mStudentRemoteDataSource.getStudent(studentId, new GetStudentCallback() {
                    @Override
                    public void onStudentLoaded(Student student) {
                        // Do in memory cache update to keep the app UI up to data
                        if (mCachedStudents == null)
                            mCachedStudents = new LinkedHashMap<>();

                        mCachedStudents.put(student.getId(), student);
                        getStudentCallback.onStudentLoaded(student);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        getStudentCallback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void saveStudent(@NonNull Student student, @NonNull SaveStudentCallback saveStudentCallback) {

    }

    @Override
    public void deleteAllStudent(@NonNull DeleteAllStudentCallback deleteAllStudentCallback) {

    }

    @Override
    public void deleteStudent(@NonNull String studentId, @NonNull DeleteStudentCallback deleteStudentCallback) {

    }

    @Override
    public void updateStudentDetails(@NonNull Student student, @NonNull UpdateStudentCallback updateStudentCallback) {

    }

    @Override
    public void refreshStudents() {

    }

    private void getStudentsFromRemoteDataSource(LoadStudentsCallback loadStudentsCallback) {

    }

    private void refreshCache(List<Student> students) {

    }

    private void refreshLocalDataSource(List<Student> students) {

    }

    @Nullable
    private Student getStudentWithId(String studentId) {
        return null;
    }
}
