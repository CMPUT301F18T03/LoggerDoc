/*
 * Problem.class
 *
 * Version 1.0
 *
 * November 15, 2018
 *
 * Created 2018-10-24 by Nick Hoskins
 *
 * Problem represents a patient's problem. A problem has a title, a timestamp, and a description.
 * A problem also has a number of Records associated with it.
 */

package com.example.loggerdoc;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Problem implements Serializable {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private RecordList recordList;
    private CaregiverCommentList commentList;

    /**
     * Returns a Problem object that holds a title, description, date, a list of records and a
     * list of caregiver's comment. The title can not be more than 30 characters, and the
     * </p>
     * This method always returns immediately.
     *
     * @param title The title of the problem
     * @param datePicker The datePicker holds the date of the problem.
     * @param description The description of the problem.
     */

    public Problem(String title, DatePickerFragment datePicker, String description) {
        this.title = title;
        this.description = description;
        formatDateAndTime(datePicker);
        this.recordList = new RecordList();
        this.commentList = new CaregiverCommentList();
    }

    /**
     * Returns the title of the problem.
     *
     * @return the title of the problem
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the problem.
     *
     * @param title The new title of the problem.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the problem.
     *
     * @return the description of the problem
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the problem.
     *
     * @param description the new description of the problem.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the timestamp of the problem. This method creates a LocalDateTime object that stores the
     * date of the problem. The day, month and year is taken from the datePickerFragment.
     *
     * @param datePickerFragment the object that holds the date of the problem
     */
    public void formatDateAndTime(DatePickerFragment datePickerFragment){

        timestamp = LocalDateTime.now();

        if(datePickerFragment.getSet()){
            timestamp = timestamp.withDayOfMonth(datePickerFragment.getDay())
                    .withMonth(datePickerFragment.getMonth()).withYear(datePickerFragment.getYear());
        }
    }

    /**
     * Sets the timestamp of the problem by calling formatDateAndTime.
     *
     * @param datePickerFragment the object that holds the date of the problem
     */
    public void setTimestamp (DatePickerFragment datePickerFragment){
        formatDateAndTime(datePickerFragment);
    }

    /**
     * Returns the timestamp of the problem.
     *
     * @return the timestamp (LocalDateTime object)
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the record list of the problem.
     *
     * @return the recordList.
     */
    public RecordList getRecordList() {
        return recordList;
    }

    /**
     * Adds a record to the recordList.
     *
     * @param record The record that needs to be added.
     */
    public void addRecord(Record record) {
        this.recordList.add(record);
    }

    /**
     * Returns the caregiver's comment list of the problem.
     *
     * @return the comment list
     */
    public CaregiverCommentList getCommentList(){
        return this.commentList;
    }

    /**
     * Add a comment to the caregiver's comment list.
     *
     * @param comment
     */
    public void addComment(CaregiverComment comment){
        this.commentList.addComment(comment);
    }

    /**
     * Check if the title of the problem is more than 30 characters. It will return true if it is
     * and false otherwise.
     *
     * @param title The title of the problem.
     * @return
     */
    public boolean checkTitleLength (String title){

        if (title.length() > 30){
            return false;
        }

        return true;
    }

    /**
     * Check if the description of the problem is more than 300 characters. It will return true if
     * it is and false otherwise.
     *
     * @param description The description of the problem.
     * @return
     */

    public boolean checkDescriptionLength (String description){

        if (description.length() > 300){
            return false;
        }
        
        return true;
    }
}
