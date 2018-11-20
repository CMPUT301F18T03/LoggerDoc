/* Created 2018-10-24 by Nick Hoskins
 *
 * Record represents a single record of a patient's problem. A record has a title. A record
 * may optionally have a single comment, a single body location, a single geo-location, or multiple
 * photos associated with it. A record also has a timestamp.
 */

package com.example.loggerdoc;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Record implements Serializable {

    private String title;
    private String comment;
    private LocalDateTime timestamp;

    private RecordGeoLocation recordGeoLocation;
    // private RecordBodyLocation recordBodyLocation;
    private RecordPhotoList recordPhotoList;
    private int test;

    public Record() {
        this("");
    }


    public Record(String title) {
        this.title = title;
        this.comment = "";
        this.timestamp = LocalDateTime.now();
        this.test = 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setRecordGeoLocation (RecordGeoLocation geoLocation){
        this.recordGeoLocation = geoLocation;
    }

    public RecordPhotoList getRecordPhotoList(){return recordPhotoList;}

    public void setList(RecordPhotoList photos){this.recordPhotoList = photos;}

    public void addRecordPhoto(RecordPhoto photo){this.recordPhotoList.addPhoto(photo);}

    public RecordGeoLocation getRecordGeoLocation(){
        return this.recordGeoLocation;
    }
}
