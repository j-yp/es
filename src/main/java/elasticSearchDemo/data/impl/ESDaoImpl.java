package elasticSearchDemo.data.impl;

import org.elasticsearch.common.xcontent.ToXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import elasticSearchDemo.data.ESDao;
import elasticSearchDemo.esutils.Client;
import elasticSearchDemo.esutils.ESFactory;
import elasticSearchDemo.pojo.Entity;

@Repository
public class ESDaoImpl implements ESDao {
	@Autowired
	private ESFactory esFacotry;
	
	@Autowired
	private Client client;
	
	public ESFactory getEsFacotry() {
		return esFacotry;
	}

	public void setEsFacotry(ESFactory esFacotry) {
		this.esFacotry = esFacotry;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	public ToXContent get(Entity entity) throws Exception {
		return client.get(entity);
	}

	
	
}
