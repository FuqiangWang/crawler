package com.huawei.apaas.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huawei.apaas.project.domain.enumeration.ArcModelType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ArcMetadata.
 */
@Entity
@Table(name = "arc_metadata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArcMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ArcModelType type;

    @NotNull
    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "service_discovery_type")
    private String serviceDiscoveryType;

    @Column(name = "dev_database_type")
    private String devDatabaseType;

    @Column(name = "prod_database_type")
    private String prodDatabaseType;

    @Column(name = "cache_provider")
    private String cacheProvider;

    @Column(name = "message_broker")
    private String messageBroker;

    @NotNull
    @Column(name = "server_port", nullable = false)
    private Long serverPort;

    @Column(name = "front_message")
    private String frontMessage;

    @Column(name = "client_framework")
    private String clientFramework;

    @Column(name = "languages")
    private String languages;

    @Column(name = "test_framework")
    private String testFramework;

    @NotNull
    @Column(name = "rent_id", nullable = false)
    private String rentId;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @JsonIgnoreProperties(value = { "arcMetadata", "cicdMetadata", "opsMetadata" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProjectMetadata projectMetadata;

    @OneToOne
    @JoinColumn(unique = true)
    private ArcTemplate arcTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArcMetadata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArcModelType getType() {
        return this.type;
    }

    public ArcMetadata type(ArcModelType type) {
        this.setType(type);
        return this;
    }

    public void setType(ArcModelType type) {
        this.type = type;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public ArcMetadata packageName(String packageName) {
        this.setPackageName(packageName);
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getServiceDiscoveryType() {
        return this.serviceDiscoveryType;
    }

    public ArcMetadata serviceDiscoveryType(String serviceDiscoveryType) {
        this.setServiceDiscoveryType(serviceDiscoveryType);
        return this;
    }

    public void setServiceDiscoveryType(String serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
    }

    public String getDevDatabaseType() {
        return this.devDatabaseType;
    }

    public ArcMetadata devDatabaseType(String devDatabaseType) {
        this.setDevDatabaseType(devDatabaseType);
        return this;
    }

    public void setDevDatabaseType(String devDatabaseType) {
        this.devDatabaseType = devDatabaseType;
    }

    public String getProdDatabaseType() {
        return this.prodDatabaseType;
    }

    public ArcMetadata prodDatabaseType(String prodDatabaseType) {
        this.setProdDatabaseType(prodDatabaseType);
        return this;
    }

    public void setProdDatabaseType(String prodDatabaseType) {
        this.prodDatabaseType = prodDatabaseType;
    }

    public String getCacheProvider() {
        return this.cacheProvider;
    }

    public ArcMetadata cacheProvider(String cacheProvider) {
        this.setCacheProvider(cacheProvider);
        return this;
    }

    public void setCacheProvider(String cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public String getMessageBroker() {
        return this.messageBroker;
    }

    public ArcMetadata messageBroker(String messageBroker) {
        this.setMessageBroker(messageBroker);
        return this;
    }

    public void setMessageBroker(String messageBroker) {
        this.messageBroker = messageBroker;
    }

    public Long getServerPort() {
        return this.serverPort;
    }

    public ArcMetadata serverPort(Long serverPort) {
        this.setServerPort(serverPort);
        return this;
    }

    public void setServerPort(Long serverPort) {
        this.serverPort = serverPort;
    }

    public String getFrontMessage() {
        return this.frontMessage;
    }

    public ArcMetadata frontMessage(String frontMessage) {
        this.setFrontMessage(frontMessage);
        return this;
    }

    public void setFrontMessage(String frontMessage) {
        this.frontMessage = frontMessage;
    }

    public String getClientFramework() {
        return this.clientFramework;
    }

    public ArcMetadata clientFramework(String clientFramework) {
        this.setClientFramework(clientFramework);
        return this;
    }

    public void setClientFramework(String clientFramework) {
        this.clientFramework = clientFramework;
    }

    public String getLanguages() {
        return this.languages;
    }

    public ArcMetadata languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTestFramework() {
        return this.testFramework;
    }

    public ArcMetadata testFramework(String testFramework) {
        this.setTestFramework(testFramework);
        return this;
    }

    public void setTestFramework(String testFramework) {
        this.testFramework = testFramework;
    }

    public String getRentId() {
        return this.rentId;
    }

    public ArcMetadata rentId(String rentId) {
        this.setRentId(rentId);
        return this;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public ArcMetadata updateTime(String updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public ArcMetadata createTime(String createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ProjectMetadata getProjectMetadata() {
        return this.projectMetadata;
    }

    public void setProjectMetadata(ProjectMetadata projectMetadata) {
        this.projectMetadata = projectMetadata;
    }

    public ArcMetadata projectMetadata(ProjectMetadata projectMetadata) {
        this.setProjectMetadata(projectMetadata);
        return this;
    }

    public ArcTemplate getArcTemplate() {
        return this.arcTemplate;
    }

    public void setArcTemplate(ArcTemplate arcTemplate) {
        this.arcTemplate = arcTemplate;
    }

    public ArcMetadata arcTemplate(ArcTemplate arcTemplate) {
        this.setArcTemplate(arcTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArcMetadata)) {
            return false;
        }
        return id != null && id.equals(((ArcMetadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArcMetadata{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", packageName='" + getPackageName() + "'" +
            ", serviceDiscoveryType='" + getServiceDiscoveryType() + "'" +
            ", devDatabaseType='" + getDevDatabaseType() + "'" +
            ", prodDatabaseType='" + getProdDatabaseType() + "'" +
            ", cacheProvider='" + getCacheProvider() + "'" +
            ", messageBroker='" + getMessageBroker() + "'" +
            ", serverPort=" + getServerPort() +
            ", frontMessage='" + getFrontMessage() + "'" +
            ", clientFramework='" + getClientFramework() + "'" +
            ", languages='" + getLanguages() + "'" +
            ", testFramework='" + getTestFramework() + "'" +
            ", rentId='" + getRentId() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
