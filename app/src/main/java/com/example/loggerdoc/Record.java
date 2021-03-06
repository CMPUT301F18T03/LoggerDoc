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

public class Record implements ElasticID {

    private String title;
    private String comment;
    private LocalDateTime timestamp;

    private RecordGeoLocation recordGeoLocation = null;
    private Bodylocation bodylocation;

    private ArrayList<Integer> blPhotoList;
    private ArrayList<Integer> recordPhotoList;

    private Integer ElasticID;
    private Integer ElasticID_Owner;
    private Integer ElasticID_OwnerProblem;

    public Record(){
    }

    public Record(String title, Integer Ownerproblem) {
        this.title = title;
        this.comment = "";
        this.timestamp = LocalDateTime.now();
        this.recordPhotoList = new ArrayList<>();
        this.blPhotoList = new ArrayList<>();

        ElasticID_OwnerProblem = Ownerproblem;
        ElasticID_Owner = ProblemRecordListController.getProblemList().get(ElasticID_OwnerProblem).getElasticID_Owner();
        ElasticID = this.hashCode();

    }

    public Record(String title,Integer Ownerproblem,Integer Owner) {
        this.title = title;
        this.comment = "";
        this.timestamp = LocalDateTime.now();
        this.recordPhotoList = new ArrayList<>();
        this.blPhotoList = new ArrayList<>();
        ElasticID_OwnerProblem = Ownerproblem;
        ElasticID_Owner = Owner;
        ElasticID = this.hashCode();

    }

    /*public Record(String title, String comment, LocalDateTime timestamp, RecordGeoLocation recordGeoLocation, Bodylocation bodylocation, RecordPhotoList recordPhotoList, Integer elasticID, Integer elasticID_Owner, Integer elasticID_OwnerProblem) {
        this.title = title;
        this.comment = comment;
        this.timestamp = timestamp;
        this.recordGeoLocation = recordGeoLocation;
        this.bodylocation = bodylocation;
        this.recordPhotoList = recordPhotoList;
        ElasticID = elasticID;
        ElasticID_Owner = elasticID_Owner;
        ElasticID_OwnerProblem = elasticID_OwnerProblem;
    }*/


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

    public ArrayList<Integer> getRecordPhotoList(){return this.recordPhotoList;}



    public RecordGeoLocation getRecordGeoLocation(){
        return this.recordGeoLocation;
    }

    public void setRecordPhotoList(ArrayList<Integer> newRecordPhotoList){
        recordPhotoList = newRecordPhotoList;
    }

    public void setBlPhotoList(ArrayList<Integer> newList){
        this.blPhotoList = newList;
    }

    public void setBodylocation(Bodylocation BodLoc){
        this.bodylocation = BodLoc;
    }

    public Bodylocation getBodylocation(){
        return this.bodylocation;
    }
    public ArrayList<Integer> getBlPhotoList(){return this.blPhotoList;}

    @Override
    public Integer getElasticID() {
        return this.ElasticID;
    }
    public Integer getElasticID_Owner(){
        return this.ElasticID_Owner;
    }
    public Integer getElasticID_OwnerProblem(){
        return this.ElasticID_OwnerProblem;
    }

    public void addRecordPhoto(RecordPhoto photo){
        recordPhotoList.add(photo.getElasticID());

    }
    public void addBlPhoto(BodyLocationPhoto photo){
        blPhotoList.add(photo.getElasticID());

    }
    public void removeBlPhoto(BodyLocationPhoto photo){
        blPhotoList.remove(photo.getElasticID());

    }

}
