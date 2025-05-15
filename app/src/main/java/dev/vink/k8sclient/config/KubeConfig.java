package dev.vink.k8sclient.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KubeConfig {
    private String apiVersion;
    private String kind;
    private List<Cluster> clusters;
    private List<Context> contexts;
    private List<User> users;
    private String currentContext;
    private Preferences preferences;

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @JsonProperty("current-context")
    public String getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(String currentContext) {
        this.currentContext = currentContext;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public static class Cluster {
        private String name;
        private ClusterData cluster;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ClusterData getCluster() {
            return cluster;
        }

        public void setCluster(ClusterData cluster) {
            this.cluster = cluster;
        }
    }

    public static class ClusterData {
        private String server;

        @JsonProperty("certificate-authority-data")
        private String certificateAuthorityData;

        @JsonProperty("insecure-skip-tls-verify")
        private Boolean insecureSkipTlsVerify;

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getCertificateAuthorityData() {
            return certificateAuthorityData;
        }

        public void setCertificateAuthorityData(String certificateAuthorityData) {
            this.certificateAuthorityData = certificateAuthorityData;
        }

        public Boolean getInsecureSkipTlsVerify() {
            return insecureSkipTlsVerify;
        }

        public void setInsecureSkipTlsVerify(Boolean insecureSkipTlsVerify) {
            this.insecureSkipTlsVerify = insecureSkipTlsVerify;
        }
    }

    public static class Context {
        private String name;
        private ContextData context;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ContextData getContext() {
            return context;
        }

        public void setContext(ContextData context) {
            this.context = context;
        }
    }

    public static class ContextData {
        private String cluster;
        private String namespace;
        private String user;

        public String getCluster() {
            return cluster;
        }

        public void setCluster(String cluster) {
            this.cluster = cluster;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    public static class User {
        private String name;
        private UserData user;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UserData getUser() {
            return user;
        }

        public void setUser(UserData user) {
            this.user = user;
        }
    }

    public static class UserData {
        private String clientCertificateData;
        private String clientKeyData;
        private String token;
        private String username;
        private String password;

        @JsonProperty("client-certificate-data")
        public String getClientCertificateData() {
            return clientCertificateData;
        }

        public void setClientCertificateData(String clientCertificateData) {
            this.clientCertificateData = clientCertificateData;
        }

        @JsonProperty("client-key-data")
        public String getClientKeyData() {
            return clientKeyData;
        }

        public void setClientKeyData(String clientKeyData) {
            this.clientKeyData = clientKeyData;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Preferences {
        private Map<String, Object> colors;

        public Map<String, Object> getColors() {
            return colors;
        }

        public void setColors(Map<String, Object> colors) {
            this.colors = colors;
        }
    }
}
