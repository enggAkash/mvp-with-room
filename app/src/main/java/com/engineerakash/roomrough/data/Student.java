package com.engineerakash.roomrough.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.common.base.Objects;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @Nullable
    @ColumnInfo(name = "rollno")
    private Integer rollNo;

    @Nullable
    @ColumnInfo(name = "dob")
    private Date dob;

    @Nullable
    @ColumnInfo(name = "mobile")
    private String mobile;

    public Student(@NonNull String name) {
        this(UUID.randomUUID().toString(), name, null, null, null);
    }

    public Student(@NonNull String name, @Nullable Integer rollNo) {
        this(UUID.randomUUID().toString(), name, rollNo, null, null);
    }

    public Student(@NonNull String name, @Nullable Integer rollNo, @Nullable Date dob) {
        this(UUID.randomUUID().toString(), name, rollNo, dob, null);
    }

    public Student(@NonNull String name, @Nullable Date dob) {
        this(UUID.randomUUID().toString(), name, null, dob, null);
    }

    public Student(@NonNull String name, @Nullable Integer rollNo, @Nullable String mobile) {
        this(UUID.randomUUID().toString(), name, rollNo, null, mobile);
    }

    public Student(@NonNull String name, @Nullable Date dob, @Nullable String mobile) {
        this(UUID.randomUUID().toString(), name, null, dob, mobile);
    }

    public Student(@NonNull String name, @Nullable String mobile) {
        this(UUID.randomUUID().toString(), name, null, null, mobile);
    }

    public Student(@NonNull String id, @NonNull String name, @Nullable Integer rollNo,
                   @Nullable Date dob, @Nullable String mobile) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.dob = dob;
        this.mobile = mobile;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public Integer getRollNo() {
        return rollNo;
    }

    @Nullable
    public Date getDob() {
        return dob;
    }

    @Nullable
    public String getMobile() {
        return mobile;
    }

    @Nullable
    public Boolean isEighteenPlus() {
        if (dob == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dob);
        int bornYear = calendar.get(Calendar.YEAR);

        return (Calendar.getInstance().get(Calendar.YEAR) - bornYear) >= 18;
    }

    @Nullable
    public Boolean doesHaveMobile() {
        return !TextUtils.isEmpty(mobile);
    }

    @Nullable
    public Boolean doesRollNoAllotted() {
        return rollNo != null && rollNo > 0;
    }

    @NonNull
    public String getFirstName() {
        String[] n = name.split(" ");
        return n[0];
    }

    @Nullable
    public String getLastName() {
        String[] n = name.split(" ");
        return n[n.length - 1];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return Objects.equal(id, student.id) &&
                Objects.equal(name, student.name) &&
                Objects.equal(rollNo, student.rollNo) &&
                Objects.equal(dob, student.dob) &&
                Objects.equal(mobile, student.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, rollNo, dob, mobile);
    }

    @Override
    public String toString() {
        return name;
    }
}
