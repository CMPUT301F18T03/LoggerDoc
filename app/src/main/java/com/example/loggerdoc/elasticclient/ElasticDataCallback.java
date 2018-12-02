package com.example.loggerdoc.elasticclient;

import java.util.ArrayList;

/*
 * this interface is used for anything that needs to provide elastic data callBack functionality
 * when finished with ansync tasks
 */

public interface ElasticDataCallback<T extends ArrayList>{
     void dataCallBack(T data);
}
