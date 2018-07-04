package com.engineerakash.roomrough.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.sql.Date;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @Nullable
    @ColumnInfo(name = "dob")
    private Date dob;

    @Nullable
    @ColumnInfo(name = "mobile")
    private String mobile;

    public Student(String name) {

    }

    public Student(String name, Date dob){

    }

    public Student(String name, String mobile){

    }

    public Student(@NonNull int id, @NonNull String name, Date dob, String mobile) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.mobile = mobile;
    }
}
