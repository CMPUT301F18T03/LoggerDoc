/*
 * Problem.class
 *
 * Version 1.0
 *
 * November 15, 2018
 *
 * Created 2018-10-24 by Nick Hoskins
 *
 *
 */

package com.example.loggerdoc;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Problem implements Serializable,ElasticID {

    private String title;
    private String description;
    private LocalDateTime timestamp;

    private CaregiverCommentList commentList;

    private Integer ElasticID;
    private Integer ElasticID_Owner;

    /**
     * Returns a Problem object that holds a title, description, date, a list of records and a
     * list of caregiver's comment. The title can not be more than 30 characters, and the
     * </p>
     * This method always returns immediately.
     *
     * @param title The title of the problem
     * @param timestamp  timestamp holds the date of the problem.
     * @param description The description of the problem.
     */

    public Problem(String title, LocalDateTime timestamp, String description,Integer ElasticID_Owner) {
        this.ElasticID = this.hashCode();
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.commentList = new CaregiverCommentList();
        this.ElasticID_Owner = ElasticID_Owner;
    }

    public Problem(String title, LocalDateTime timestamp, String description,Integer ElasticID,Integer ElasticID_Owner) {
        this.ElasticID = ElasticID;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.commentList = new CaregiverCommentList();
        this.ElasticID_Owner = ElasticID_Owner;
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
     * Sets the timestamp of the problem by calling formatDateAndTime.
     *
     * @param newTime the object that holds the date of the problem
     */
    public void setTimestamp (LocalDateTime newTime){
        this.timestamp = newTime;
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

    public boolean checkDescriptionLength(String description){

        if (description.length() > 300){
            return false;
        }

        return true;
    }

    @Override
    public Integer getElasticID() {
        return ElasticID;
    }

    public Integer getElasticID_Owner(){
        return ElasticID_Owner;
    }
}
