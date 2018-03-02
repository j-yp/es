package elasticSearchDemo.service.impl;

import org.elasticsearch.common.xcontent.ToXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elasticSearchDemo.data.ESDao;
import elasticSearchDemo.pojo.Entity;
import elasticSearchDemo.service.ESService;
@Service
public class ESServiceImpl implements ESService{
	
	@Autowired
	private ESDao esDao;
	
	@Override
	public ToXContent get(Entity entity) throws Exception {
		return esDao.get(entity);
	}
}
