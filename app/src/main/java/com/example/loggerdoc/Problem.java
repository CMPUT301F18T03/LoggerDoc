/* Created 2018-10-24 by Nick Hoskins
 *
 * Problem represents a patient's problem. A problem has a title, a timestamp, and a description.
 * A problem also has a number of Records associated with it.
 */

package com.example.loggerdoc;

import android.util.Log;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Problem implements Serializable {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private RecordList recordList;

    public Problem(String title, DatePickerFragment datePicker, String description) {
        this.title = title;
        this.description = description;
        formatDateAndTime(datePicker);
        this.recordList = new RecordList();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void formatDateAndTime(DatePickerFragment datePickerFragment){

        timestamp = LocalDateTime.now();

        if(datePickerFragment.getSet()){
            timestamp = timestamp.withDayOfMonth(datePickerFragment.getDay())
                    .withMonth(datePickerFragment.getMonth()).withYear(datePickerFragment.getYear());
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public RecordList getRecordList() {
        return recordList;
    }

    public void addRecord(Record record) {
        this.recordList.add(record);
    }

    public boolean checkTitleLength (String title){

        if (title.length() > 30){
            return false;
        }

        return true;
    }

    public boolean checkDescriptionLength (String description){

        if (description.length() > 300){
            return false;
        }
        return true;
    }
}
