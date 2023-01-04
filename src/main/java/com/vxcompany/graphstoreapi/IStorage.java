package com.vxcompany.graphstoreapi;

import java.util.List;

public interface IStorage {
    List<Object> GetAll(String endpoint);
    Object Get(String endpoint, String id);
    void Create(String endpoint, String id, Object value);
    void Update(String endpoint, String id, Object value);
    void Delete(String endpoint, String id);
}
