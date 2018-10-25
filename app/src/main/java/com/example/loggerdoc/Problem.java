/* Created 2018-10-24 by Nick Hoskins
 *
 * Problem represents a patient's problem. A problem has a title, a timestamp, and a description.
 * A problem also has a number of Records associated with it.
 */

package com.example.loggerdoc;

import java.time.LocalDateTime;

public class Problem {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private RecordList recordList;

    public Problem() {
        this("");
    }

    public Problem(String title) {
        this.title = title;
        this.description = "";
        this.timestamp = LocalDateTime.now();

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public RecordList getRecordList() {
        return recordList;
    }

    public void addRecord(Record record) {
        this.recordList.add(record);
    }
}
