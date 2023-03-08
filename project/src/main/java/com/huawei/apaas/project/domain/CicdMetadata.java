package com.huawei.apaas.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huawei.apaas.project.domain.enumeration.CdType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CicdMetadata.
 */
@Entity
@Table(name = "cicd_metadata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CicdMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "repository_type", nullable = false)
    private String repositoryType;

    @NotNull
    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    @NotNull
    @Column(name = "ci_name", nullable = false)
    private String ciName;

    @NotNull
    @Column(name = "ci_url", nullable = false)
    private String ciUrl;

    @NotNull
    @Column(name = "build_pkg", nullable = false)
    private String buildPkg;

    @Column(name = "mirror_repository")
    private String mirrorRepository;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cd_type", nullable = false)
    private CdType cdType;

    @Column(name = "auto_ip")
    private String autoIp;

    @Column(name = "auto_port")
    private Integer autoPort;

    @Column(name = "auto_user")
    private String autoUser;

    @Column(name = "auto_pwd")
    private String autoPwd;

    @Lob
    @Column(name = "auto_key")
    private String autoKey;

    @Column(name = "deploy")
    private String deploy;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CicdMetadata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepositoryType() {
        return this.repositoryType;
    }

    public CicdMetadata repositoryType(String repositoryType) {
        this.setRepositoryType(repositoryType);
        return this;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public String getRepositoryName() {
        return this.repositoryName;
    }

    public CicdMetadata repositoryName(String repositoryName) {
        this.setRepositoryName(repositoryName);
        return this;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getCiName() {
        return this.ciName;
    }

    public CicdMetadata ciName(String ciName) {
        this.setCiName(ciName);
        return this;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public String getCiUrl() {
        return this.ciUrl;
    }

    public CicdMetadata ciUrl(String ciUrl) {
        this.setCiUrl(ciUrl);
        return this;
    }

    public void setCiUrl(String ciUrl) {
        this.ciUrl = ciUrl;
    }

    public String getBuildPkg() {
        return this.buildPkg;
    }

    public CicdMetadata buildPkg(String buildPkg) {
        this.setBuildPkg(buildPkg);
        return this;
    }

    public void setBuildPkg(String buildPkg) {
        this.buildPkg = buildPkg;
    }

    public String getMirrorRepository() {
        return this.mirrorRepository;
    }

    public CicdMetadata mirrorRepository(String mirrorRepository) {
        this.setMirrorRepository(mirrorRepository);
        return this;
    }

    public void setMirrorRepository(String mirrorRepository) {
        this.mirrorRepository = mirrorRepository;
    }

    public CdType getCdType() {
        return this.cdType;
    }

    public CicdMetadata cdType(CdType cdType) {
        this.setCdType(cdType);
        return this;
    }

    public void setCdType(CdType cdType) {
        this.cdType = cdType;
    }

    public String getAutoIp() {
        return this.autoIp;
    }

    public CicdMetadata autoIp(String autoIp) {
        this.setAutoIp(autoIp);
        return this;
    }

    public void setAutoIp(String autoIp) {
        this.autoIp = autoIp;
    }

    public Integer getAutoPort() {
        return this.autoPort;
    }

    public CicdMetadata autoPort(Integer autoPort) {
        this.setAutoPort(autoPort);
        return this;
    }

    public void setAutoPort(Integer autoPort) {
        this.autoPort = autoPort;
    }

    public String getAutoUser() {
        return this.autoUser;
    }

    public CicdMetadata autoUser(String autoUser) {
        this.setAutoUser(autoUser);
        return this;
    }

    public void setAutoUser(String autoUser) {
        this.autoUser = autoUser;
    }

    public String getAutoPwd() {
        return this.autoPwd;
    }

    public CicdMetadata autoPwd(String autoPwd) {
        this.setAutoPwd(autoPwd);
        return this;
    }

    public void setAutoPwd(String autoPwd) {
        this.autoPwd = autoPwd;
    }

    public String getAutoKey() {
        return this.autoKey;
    }

    public CicdMetadata autoKey(String autoKey) {
        this.setAutoKey(autoKey);
        return this;
    }

    public void setAutoKey(String autoKey) {
        this.autoKey = autoKey;
    }

    public String getDeploy() {
        return this.deploy;
    }

    public CicdMetadata deploy(String deploy) {
        this.setDeploy(deploy);
        return this;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public String getRentId() {
        return this.rentId;
    }

    public CicdMetadata rentId(String rentId) {
        this.setRentId(rentId);
        return this;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public CicdMetadata updateTime(String updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public CicdMetadata createTime(String createTime) {
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

    public CicdMetadata projectMetadata(ProjectMetadata projectMetadata) {
        this.setProjectMetadata(projectMetadata);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CicdMetadata)) {
            return false;
        }
        return id != null && id.equals(((CicdMetadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CicdMetadata{" +
            "id=" + getId() +
            ", repositoryType='" + getRepositoryType() + "'" +
            ", repositoryName='" + getRepositoryName() + "'" +
            ", ciName='" + getCiName() + "'" +
            ", ciUrl='" + getCiUrl() + "'" +
            ", buildPkg='" + getBuildPkg() + "'" +
            ", mirrorRepository='" + getMirrorRepository() + "'" +
            ", cdType='" + getCdType() + "'" +
            ", autoIp='" + getAutoIp() + "'" +
            ", autoPort=" + getAutoPort() +
            ", autoUser='" + getAutoUser() + "'" +
            ", autoPwd='" + getAutoPwd() + "'" +
            ", autoKey='" + getAutoKey() + "'" +
            ", deploy='" + getDeploy() + "'" +
            ", rentId='" + getRentId() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
