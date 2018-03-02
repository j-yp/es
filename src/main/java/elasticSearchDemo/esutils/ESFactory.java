package elasticSearchDemo.esutils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import elasticSearchDemo.prop.ESProps;

@Component
public class ESFactory {
	@Autowired
	private ESProps esProps;
	
	private TransportClient client;
	
	public TransportClient getTransportClient() {
		if(client == null) {
			client = new PreBuiltTransportClient(Settings.EMPTY);
			esProps.getEsNodes().forEach(x->{
				try {
					client.addTransportAddress(new TransportAddress(InetAddress.getByName(x.get("host")), Integer.valueOf(x.get("port"))));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			});
		}
		return client;
	}
	
	public void cleanTransportClient() {
		client = null;
	}
	
	public void clientClose() {
		if(client != null) {
			client.close();
		}
	}
	
	
}
