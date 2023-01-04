package com.vxcompany.graphstoreapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class GraphStoreController {

    private final InMemory store;

    public GraphStoreController(InMemory store) {
        this.store = store;
    }

    @RequestMapping(value = "/{endpointaddress}", method = RequestMethod.GET, headers="Accept=*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Object> GetAll(@PathVariable("endpointaddress") String endpointAddress) {
        List<Object> list = store.GetAll(endpointAddress);
        return new JSONArray(list).toList();
    }

    @RequestMapping(value = "/{endpointaddress}/{id}", method = RequestMethod.GET, headers="Accept=*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object Get(@PathVariable("id") String id, @PathVariable("endpointaddress") String endpointAddress) {
       return store.Get(endpointAddress, id).toString();
    }

    @RequestMapping(value = "/{endpointaddress}", method = RequestMethod.POST, headers="Accept=*/*")
    @ResponseBody
    public ResponseEntity Create(@RequestBody String body, @PathVariable("endpointaddress") String endpointAddress) {

        JSONObject jsonObject = new JSONObject(body);

        if (!jsonObject.has("id")){
            jsonObject.put("id",java.util.UUID.randomUUID().toString());
        }

        store.Create(endpointAddress, jsonObject.getString("id"), jsonObject);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{endpointaddress}/{id}", method = RequestMethod.PUT, headers="Accept=*/*")
    @ResponseBody
    public ResponseEntity Update(@RequestBody String body, @PathVariable("endpointaddress") String endpointAddress, @PathVariable("id") String id) {

        JSONObject jsonObject = new JSONObject(body);

        if (!jsonObject.has("id")){
            jsonObject.put("id",id);
        }

        store.Update(endpointAddress, jsonObject.getString("id"), jsonObject);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/{endpointaddress}/{id}", method = RequestMethod.DELETE, headers="Accept=*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity Delete(@PathVariable("endpointaddress") String endpointAddress, @PathVariable("id") String id) {
        store.Delete(endpointAddress, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/**", method = RequestMethod.PATCH, headers="Accept=*/*")
    @ResponseBody
    public ResponseEntity Teapot() {
        return ResponseEntity.ok(HttpStatus.I_AM_A_TEAPOT);
    }
}
