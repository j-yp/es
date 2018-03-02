package elasticSearchDemo.prop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="ES")
public class ESProps {
	private List<Map<String, String>> esNodes = new ArrayList<>();

	public List<Map<String, String>> getEsNodes() {
		return esNodes;
	}

	public void setEsNodes(List<Map<String, String>> esNodes) {
		this.esNodes = esNodes;
	}
	
}
