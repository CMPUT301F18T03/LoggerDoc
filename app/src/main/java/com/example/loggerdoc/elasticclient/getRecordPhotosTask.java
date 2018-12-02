package com.example.loggerdoc.elasticclient;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loggerdoc.BodyLocationPhoto;
import com.example.loggerdoc.Record;
import com.example.loggerdoc.RecordPhoto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;

public class getRecordPhotosTask extends AsyncTask<Record, Void, ArrayList<RecordPhoto>> {
    private Context context;
    private ElasticDataCallback<ArrayList<RecordPhoto>> callback;
    public getRecordPhotosTask(Context context,ElasticDataCallback<ArrayList<RecordPhoto>> callback){
        this.context = context;
        this.callback = callback;
    }
    @Override
    protected ArrayList<RecordPhoto> doInBackground(Record... records) {
        Gson gson = new Gson();
        Record tosend = records[0];
        ArrayList<Integer> BodyLocs = tosend.getBlPhotoList();
        ArrayList<Integer> RecordPhotos = tosend.getRecordPhotoList();
        ArrayList<Integer> toFetchBody = new ArrayList<>();
        ArrayList<Integer> toFetchRec = new ArrayList<>();
        httphandler getter = ElasticSearchController.getHttpHandler();

        ArrayList<RecordPhoto> ret = new ArrayList<>();
        File targfile;
        BodyLocationPhoto data;
        String fileread;
        for(Integer bodyloc: BodyLocs){
            targfile = new File(context.getFilesDir().getAbsolutePath()+"/Data/"+bodyloc.toString()+".dat");
            if(targfile.exists()){
                fileread = readin(targfile);
                if(fileread == null){
                    toFetchBody.add(bodyloc);
                    continue;
                }
                data = toBody(gson.fromJson(fileread,photodata.class));
                data.setPhoto(new File(context.getFilesDir().getAbsolutePath()+"/Data/"+bodyloc.toString()+".jpg"));
                ret.add(data);
            }
        }

        RecordPhoto moredata;//Theres just more data
        for(Integer recordphoto: RecordPhotos){
            targfile = new File(context.getFilesDir().getAbsolutePath()+"/Data/"+recordphoto.toString()+".dat");
            if(targfile.exists()){
                fileread = readin(targfile);
                if(fileread == null){
                    toFetchRec.add(recordphoto);
                    continue;
                }
                moredata = toRec(gson.fromJson(fileread,photodata.class));
                moredata.setPhoto(new File(context.getFilesDir().getAbsolutePath()+"/Data/"+recordphoto.toString()+".jpg"));
                ret.add(moredata);
            }
        }

        for(Integer EID: toFetchBody){
            String jsonin = getter.httpGET("/photodata/_doc/" + EID.toString());
            photodata dat = gson.fromJson(jsonin,photodata.class);
            data = toBody(dat);
            ret.add(data);
            save(dat,gson);
        }
        for(Integer EID: toFetchRec){
            String jsonin = getter.httpGET("/photodata/_doc/" + EID.toString());
            photodata dat = gson.fromJson(jsonin,photodata.class);
            moredata = toRec(dat);
            ret.add(moredata);
            save(dat,gson);
        }

        return ret;
    }

    private RecordPhoto toRec(photodata photodata) {
        RecordPhoto x = new RecordPhoto();
        x.setElasticID(photodata.ElasticID);
        x.setElasticID_OwnerRecord(photodata.ElasticID_OwnerRecord);
        return x;
    }

    private BodyLocationPhoto toBody(photodata photodata) {
        BodyLocationPhoto x = new BodyLocationPhoto();
        x.setElasticID(photodata.ElasticID);
        x.setElasticID_OwnerRecord(photodata.ElasticID_OwnerRecord);
        x.setLabel(photodata.lbl);
        return x;
    }

    private String readin(File targfile) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(targfile));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void save(photodata data,Gson gson){
        try {
            OutputStream fos;
            BufferedWriter out;
            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath() + "/Data/" + data.ElasticID.toString() + ".dat"));
            out = new BufferedWriter(new OutputStreamWriter(fos));
            out.write(gson.toJson(data));
            out.flush();
            fos.close();
            fos = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath() + "/Data/" + data.ElasticID.toString() + ".jpg"));
            fos.write(Base64.getDecoder().decode(data.img));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<RecordPhoto> x){
        context = null;
        if(callback != null){
            callback.dataCallBack(x);
        }

    }

    /**
     * A handy box to stuff into elastic search
     */
    class photodata{
        public String img;
        public Integer ElasticID;
        public Integer ElasticID_OwnerRecord;
        public String lbl;
    }
}
