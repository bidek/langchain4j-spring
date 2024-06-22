import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import dev.langchain4j.store.embedding.milvus.spring.MilvusEmbeddingStoreAutoConfiguration;
import dev.langchain4j.store.embedding.spring.EmbeddingStoreAutoConfigurationIT;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.milvus.MilvusContainer;

class MilvusEmbeddingStoreAutoConfigurationIT extends EmbeddingStoreAutoConfigurationIT {

    static MilvusContainer milvus = new MilvusContainer("milvusdb/milvus:v2.3.16");

    @BeforeAll
    static void beforeAll() {
        milvus.start();
    }

    @AfterAll
    static void afterAll() {
        milvus.stop();
    }

    @Override
    protected Class<?> autoConfigurationClass() {
        return MilvusEmbeddingStoreAutoConfiguration.class;
    }

    @Override
    protected Class<? extends EmbeddingStore<TextSegment>> embeddingStoreClass() {
        return MilvusEmbeddingStore.class;
    }

    @Override
    protected String[] properties() {
        return new String[]{
                "langchain4j.milvus.host=" + milvus.getHost(),
                "langchain4j.milvus.port=" + milvus.getMappedPort(19530),
                "langchain4j.milvus.retrieveEmbeddingsOnSearch=true"
        };
    }
}
