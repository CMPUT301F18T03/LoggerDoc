/* Created 2018-10-24 by Nick Hoskins
 *
 * Problem represents a patient's problem. A problem has a title, a timestamp, and a description.
 * A problem also has a number of Records associated with it.
 */

package com.example.loggerdoc;

import android.widget.DatePicker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Problem implements Serializable {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private RecordList recordList;

    public Problem(String title, DatePickerFragment datePicker, String description) {
        this.title = title;
        this.description = description;
        this.timestamp = formatDateAndTime(datePicker);

        this.recordList = new RecordList();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime formatDateAndTime(DatePickerFragment datePickerFragment){
        LocalDateTime timestamp = LocalDateTime.now();

        if(datePickerFragment.getSet()){
            timestamp = timestamp.withDayOfMonth(datePickerFragment.getDay())
                    .withMonth(datePickerFragment.getMonth()).withYear(datePickerFragment.getYear());
        }
        return timestamp;
    }

    //convert an object of type Date into a String Object
    public String getDateToString(LocalDateTime time){
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA);
        return(sdf.format(time));
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
