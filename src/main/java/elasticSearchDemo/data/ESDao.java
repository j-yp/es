package elasticSearchDemo.data;

import org.elasticsearch.common.xcontent.ToXContent;

import elasticSearchDemo.pojo.Entity;

public interface ESDao {

	ToXContent get(Entity entity) throws Exception;
	
}
