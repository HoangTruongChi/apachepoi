package Service.Impl;

import Service.DataService;

import java.util.ArrayList;
import java.util.List;

public class DataServiceImpl implements DataService {
    @Override
    public List<List<Object>> listAll(List<List<Object>> lists){
        List<List<Object>> listData = new ArrayList<>();
        for (int i = 1; i < lists.size() ; i++) {
            List<Object> objectList = lists.get(i);
            listData.add(objectList);
        }
        return listData;
    }
}
