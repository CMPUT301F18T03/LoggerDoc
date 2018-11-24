package com.example.loggerdoc.elasticclient;

import java.util.ArrayList;

public interface ElasticDataCallback<T extends ArrayList>{
     void dataCallBack(T data);
}
