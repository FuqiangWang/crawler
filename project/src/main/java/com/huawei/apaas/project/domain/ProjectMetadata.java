package com.huawei.apaas.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huawei.apaas.project.domain.enumeration.ProjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * entities for Project mirco service
 */
@Schema(description = "entities for Project mirco service")
@Entity
@Table(name = "project_metadata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ProjectType type;

    @NotNull
    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "lang_version")
    private String langVersion;

    @Column(name = "build_tool")
    private String buildTool;

    @Column(name = "build_tool_version")
    private String buildToolVersion;

    @NotNull
    @Column(name = "banner", nullable = false)
    private String banner;

    @Lob
    @Column(name = "favicon", nullable = false)
    private String favicon;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "rent_id", nullable = false)
    private String rentId;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @JsonIgnoreProperties(value = { "projectMetadata", "arcTemplate" }, allowSetters = true)
    @OneToOne(mappedBy = "projectMetadata")
    private ArcMetadata arcMetadata;

    @JsonIgnoreProperties(value = { "projectMetadata" }, allowSetters = true)
    @OneToOne(mappedBy = "projectMetadata")
    private CicdMetadata cicdMetadata;

    @JsonIgnoreProperties(value = { "projectMetadata" }, allowSetters = true)
    @OneToOne(mappedBy = "projectMetadata")
    private OpsMetadata opsMetadata;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectMetadata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProjectMetadata name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectType getType() {
        return this.type;
    }

    public ProjectMetadata type(ProjectType type) {
        this.setType(type);
        return this;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public String getLanguage() {
        return this.language;
    }

    public ProjectMetadata language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLangVersion() {
        return this.langVersion;
    }

    public ProjectMetadata langVersion(String langVersion) {
        this.setLangVersion(langVersion);
        return this;
    }

    public void setLangVersion(String langVersion) {
        this.langVersion = langVersion;
    }

    public String getBuildTool() {
        return this.buildTool;
    }

    public ProjectMetadata buildTool(String buildTool) {
        this.setBuildTool(buildTool);
        return this;
    }

    public void setBuildTool(String buildTool) {
        this.buildTool = buildTool;
    }

    public String getBuildToolVersion() {
        return this.buildToolVersion;
    }

    public ProjectMetadata buildToolVersion(String buildToolVersion) {
        this.setBuildToolVersion(buildToolVersion);
        return this;
    }

    public void setBuildToolVersion(String buildToolVersion) {
        this.buildToolVersion = buildToolVersion;
    }

    public String getBanner() {
        return this.banner;
    }

    public ProjectMetadata banner(String banner) {
        this.setBanner(banner);
        return this;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFavicon() {
        return this.favicon;
    }

    public ProjectMetadata favicon(String favicon) {
        this.setFavicon(favicon);
        return this;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getVersion() {
        return this.version;
    }

    public ProjectMetadata version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRentId() {
        return this.rentId;
    }

    public ProjectMetadata rentId(String rentId) {
        this.setRentId(rentId);
        return this;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public ProjectMetadata updateTime(String updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public ProjectMetadata createTime(String createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ArcMetadata getArcMetadata() {
        return this.arcMetadata;
    }

    public void setArcMetadata(ArcMetadata arcMetadata) {
        if (this.arcMetadata != null) {
            this.arcMetadata.setProjectMetadata(null);
        }
        if (arcMetadata != null) {
            arcMetadata.setProjectMetadata(this);
        }
        this.arcMetadata = arcMetadata;
    }

    public ProjectMetadata arcMetadata(ArcMetadata arcMetadata) {
        this.setArcMetadata(arcMetadata);
        return this;
    }

    public CicdMetadata getCicdMetadata() {
        return this.cicdMetadata;
    }

    public void setCicdMetadata(CicdMetadata cicdMetadata) {
        if (this.cicdMetadata != null) {
            this.cicdMetadata.setProjectMetadata(null);
        }
        if (cicdMetadata != null) {
            cicdMetadata.setProjectMetadata(this);
        }
        this.cicdMetadata = cicdMetadata;
    }

    public ProjectMetadata cicdMetadata(CicdMetadata cicdMetadata) {
        this.setCicdMetadata(cicdMetadata);
        return this;
    }

    public OpsMetadata getOpsMetadata() {
        return this.opsMetadata;
    }

    public void setOpsMetadata(OpsMetadata opsMetadata) {
        if (this.opsMetadata != null) {
            this.opsMetadata.setProjectMetadata(null);
        }
        if (opsMetadata != null) {
            opsMetadata.setProjectMetadata(this);
        }
        this.opsMetadata = opsMetadata;
    }

    public ProjectMetadata opsMetadata(OpsMetadata opsMetadata) {
        this.setOpsMetadata(opsMetadata);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectMetadata)) {
            return false;
        }
        return id != null && id.equals(((ProjectMetadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectMetadata{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", language='" + getLanguage() + "'" +
            ", langVersion='" + getLangVersion() + "'" +
            ", buildTool='" + getBuildTool() + "'" +
            ", buildToolVersion='" + getBuildToolVersion() + "'" +
            ", banner='" + getBanner() + "'" +
            ", favicon='" + getFavicon() + "'" +
            ", version='" + getVersion() + "'" +
            ", rentId='" + getRentId() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
