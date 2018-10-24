/* Created 2018-10-24 by Nick Hoskins
 *
 * Record represents a single record of a patient's problem. A record has a title. A record
 * may optionally have a single comment, a single body location, a single geo-location, or multiple
 * photos associated with it. A record also has a timestamp.
 */

package com.example.loggerdoc;

public class Record {

    private String title;
    private String comment;

    // TODO: The following classes are not yet defined.
    // private RecordGeoLocation recordGeoLocation;
    // private RecordBodyLocation recordBodyLocation;
    // private RecordPhotoList recordPhotoList;

    // TODO: What type should we use to represent timestamps?
    // private Calendar timestamp;

    public Record() {
        this.title = "";
        this.comment = "";
    }

    public Record(String title) {
        this.title = title;
        this.comment = "";
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

}
