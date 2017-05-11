package com.ppb;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * mongodb
 *
 * @author daizy
 * @Ccreate 2017-05-11 16:10
 */
@RestController
public class MongodbController {

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping("/addUser")
    public User addUser(User user) {
        mongoTemplate.save(user);
        return user;
    }

    @RequestMapping("/delUser")
    public WriteResult delUser(User user){
        return mongoTemplate.remove(query(where("name").is(user.getName())),"user");
    }

}
