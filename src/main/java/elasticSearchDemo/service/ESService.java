package elasticSearchDemo.service;

import org.elasticsearch.common.xcontent.ToXContent;

import elasticSearchDemo.pojo.Entity;

public interface ESService {

	ToXContent get(Entity entity) throws Exception;

	
}
