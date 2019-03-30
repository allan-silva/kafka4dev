import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BrokerTest {

    private List<NewTopic> newTopics;

    private AdminClient adminClient;

    @Before
    public void setup() {
        newTopics = getNewTopics();
        adminClient = AdminClient.create(getProperties());
    }

    @Test
    public void info() throws Exception {
        final DescribeClusterResult describeClusterResult = adminClient.describeCluster();
        final KafkaFuture<Collection<Node>> nodes = describeClusterResult.nodes();
        assertEquals(1, nodes.get().size());
        final Node node = nodes.get().stream().findFirst().get();
        assertEquals("42", node.idString());
    }

    @Test
    public void createAndDeleteTopics() throws Exception {
        final CreateTopicsResult createTopicsResult = adminClient.createTopics(newTopics);
        createTopicsResult.all().get();

        final List<String> topics = getTopics();
        assertEquals(3, topics.size());
        assertTrue(topics.stream().allMatch(
                topicName -> getNewTopics().stream().anyMatch(
                        newTopic -> newTopic.name().equals(topicName))
        ));

        final DeleteTopicsResult deleteTopicsResult =
                adminClient.deleteTopics(
                        newTopics.stream().map(NewTopic::name).collect(Collectors.toList()));
        deleteTopicsResult.all().get();

        assertEquals(0, getTopics().size());
    }

    private List<String> getTopics() throws Exception {
        final ListTopicsResult listTopicsResult =
                adminClient.listTopics();
        return new ArrayList<>(listTopicsResult.names().get());
    }

    private List<NewTopic> getNewTopics() {
        return asList(
                new NewTopic("topic-1", 3, (short) 1),
                new NewTopic("topic-2", 3, (short) 1),
                new NewTopic("topic-3", 3, (short) 1)
        );
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return properties;
    }
}
