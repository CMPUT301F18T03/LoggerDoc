/* Created 2018-10-24 by Nick Hoskins
 *
 * Problem represents a patient's problem. A problem has a title, a timestamp, and a description.
 * A problem also has a number of Records associated with it.
 */

package com.example.loggerdoc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Problem {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private RecordList recordList;

    public Problem(String title, String timestamp, String description) {
        this.title = title;
        this.description = description;
        this.timestamp = formatTimestamp(timestamp);

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

    public void setTimestamp (String newTime){
        this.timestamp = formatTimestamp(newTime);
    }

    public LocalDateTime formatTimestamp(String newTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(newTime, formatter);
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
