package dev.vink.k8sclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.nio.file.Path;

public class KubeConfigReader {
    private final ObjectMapper mapper;

    public KubeConfigReader() {
        this.mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public KubeConfig fromPath(Path path) throws IOException {
        return mapper.readValue(path.toFile(), KubeConfig.class);
    }
}
