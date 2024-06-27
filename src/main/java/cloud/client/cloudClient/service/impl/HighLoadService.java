package cloud.client.cloudClient.service.impl;

import cloud.client.cloudClient.model.MyRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class HighLoadService {

    @Cacheable(cacheNames = {"recordsCache"}, key = "#recordId")
    public MyRecord getOrCreateRecord(int recordId) {
        try {
            Thread.sleep(3000);
            return new MyRecord(recordId, LocalTime.now());
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @CachePut(cacheNames = {"recordsCache"}, key = "#recordId")
    public MyRecord createOrUpdate(int recordId){
        return new MyRecord(recordId, LocalTime.now());
    }

    @CacheEvict(cacheNames = {"recordsCache"}, key = "#recordId")
    public void deleteRecord(int recordId){

    }
}
