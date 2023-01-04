package com.vxcompany.graphstoreapi;

import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemory implements IStorage{

    private final Hashtable<String, Object> Cache = new Hashtable<>();

    @Override
    public List<Object> GetAll(String endpoint) {
        return Cache.entrySet()
                .stream()
                .filter(k -> k.getKey().startsWith(endpoint))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Object Get(String endpoint, String id) {

        String key = CreateKey(endpoint, id);

        if (!Cache.containsKey(key)){
            throw new KeyNotFoundException("Specified key doesn't exist");
        }

        return Cache.entrySet()
                .stream()
                .filter(k -> k.getKey().equals(key))
                .findFirst()
                .get().getValue();
    }

    @Override
    public void Create(String endpoint, String id, Object value) {
        Cache.put(CreateKey(endpoint, id), value);
    }

    @Override
    public void Update(String endpoint, String id, Object value) {
        String key = CreateKey(endpoint, id);

        if (!Cache.containsKey(key)){
            throw new KeyNotFoundException("Specified key doesn't exist");
        }

        Cache.put(CreateKey(endpoint, id), value);
    }

    @Override
    public void Delete(String endpoint, String id) {
        String key = CreateKey(endpoint, id);

        if (!Cache.containsKey(key)){
            throw new KeyNotFoundException("Specified key doesn't exist");
        }

        Cache.remove(CreateKey(endpoint, id));
    }

    private String CreateKey(String endpoint, String id)
    {
        return endpoint + "_" + id;
    }
}
