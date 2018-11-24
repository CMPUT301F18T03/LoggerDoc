/* Created 2018-10-24 by Nick Hoskins
 *
 * Record represents a single record of a patient's problem. A record has a title. A record
 * may optionally have a single comment, a single body location, a single geo-location, or multiple
 * photos associated with it. A record also has a timestamp.
 */

package com.example.loggerdoc;

import com.example.loggerdoc.elasticclient.ElasticID;

import java.io.Serializable;
import java.security.acl.Owner;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Record implements Serializable,ElasticID {

    private String title;
    private String comment;
    private LocalDateTime timestamp;

    private RecordGeoLocation recordGeoLocation;
    // private RecordBodyLocation recordBodyLocation;
    private RecordPhotoList recordPhotoList;

    private Integer ElasticID;
    private Integer ElasticID_OwnerProblem;

    public Record() {
        this("",null);
    }


    public Record(String title,Integer Ownerproblem) {
        this.title = title;
        this.comment = "";
        this.timestamp = LocalDateTime.now();
        this.recordPhotoList = new RecordPhotoList();
        ElasticID_OwnerProblem = Ownerproblem;
        ElasticID = this.hashCode();

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

    public RecordPhotoList getRecordPhotoList(){return this.recordPhotoList;}

    public void setList(RecordPhotoList photos){this.recordPhotoList = photos;}

    public void addRecordPhoto(RecordPhoto photo){this.recordPhotoList.addPhoto(photo);}

    public RecordGeoLocation getRecordGeoLocation(){
        return this.recordGeoLocation;
    }

    @Override
    public Integer getElasticID() {
        return this.ElasticID;
    }
}
