package com.huawei.apaas.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ArcTemplate.
 */
@Entity
@Table(name = "arc_template")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArcTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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
    private Integer serverPort;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArcTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ArcTemplate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public ArcTemplate packageName(String packageName) {
        this.setPackageName(packageName);
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getServiceDiscoveryType() {
        return this.serviceDiscoveryType;
    }

    public ArcTemplate serviceDiscoveryType(String serviceDiscoveryType) {
        this.setServiceDiscoveryType(serviceDiscoveryType);
        return this;
    }

    public void setServiceDiscoveryType(String serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
    }

    public String getDevDatabaseType() {
        return this.devDatabaseType;
    }

    public ArcTemplate devDatabaseType(String devDatabaseType) {
        this.setDevDatabaseType(devDatabaseType);
        return this;
    }

    public void setDevDatabaseType(String devDatabaseType) {
        this.devDatabaseType = devDatabaseType;
    }

    public String getProdDatabaseType() {
        return this.prodDatabaseType;
    }

    public ArcTemplate prodDatabaseType(String prodDatabaseType) {
        this.setProdDatabaseType(prodDatabaseType);
        return this;
    }

    public void setProdDatabaseType(String prodDatabaseType) {
        this.prodDatabaseType = prodDatabaseType;
    }

    public String getCacheProvider() {
        return this.cacheProvider;
    }

    public ArcTemplate cacheProvider(String cacheProvider) {
        this.setCacheProvider(cacheProvider);
        return this;
    }

    public void setCacheProvider(String cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public String getMessageBroker() {
        return this.messageBroker;
    }

    public ArcTemplate messageBroker(String messageBroker) {
        this.setMessageBroker(messageBroker);
        return this;
    }

    public void setMessageBroker(String messageBroker) {
        this.messageBroker = messageBroker;
    }

    public Integer getServerPort() {
        return this.serverPort;
    }

    public ArcTemplate serverPort(Integer serverPort) {
        this.setServerPort(serverPort);
        return this;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getFrontMessage() {
        return this.frontMessage;
    }

    public ArcTemplate frontMessage(String frontMessage) {
        this.setFrontMessage(frontMessage);
        return this;
    }

    public void setFrontMessage(String frontMessage) {
        this.frontMessage = frontMessage;
    }

    public String getClientFramework() {
        return this.clientFramework;
    }

    public ArcTemplate clientFramework(String clientFramework) {
        this.setClientFramework(clientFramework);
        return this;
    }

    public void setClientFramework(String clientFramework) {
        this.clientFramework = clientFramework;
    }

    public String getLanguages() {
        return this.languages;
    }

    public ArcTemplate languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTestFramework() {
        return this.testFramework;
    }

    public ArcTemplate testFramework(String testFramework) {
        this.setTestFramework(testFramework);
        return this;
    }

    public void setTestFramework(String testFramework) {
        this.testFramework = testFramework;
    }

    public String getRentId() {
        return this.rentId;
    }

    public ArcTemplate rentId(String rentId) {
        this.setRentId(rentId);
        return this;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public ArcTemplate updateTime(String updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public ArcTemplate createTime(String createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArcTemplate)) {
            return false;
        }
        return id != null && id.equals(((ArcTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArcTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
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
