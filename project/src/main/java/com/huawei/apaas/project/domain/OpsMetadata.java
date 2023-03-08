package com.huawei.apaas.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OpsMetadata.
 */
@Entity
@Table(name = "ops_metadata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OpsMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ops_system", nullable = false)
    private String opsSystem;

    @Column(name = "rent_id")
    private String rentId;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private String createTime;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private String updateTime;

    @JsonIgnoreProperties(value = { "arcMetadata", "cicdMetadata", "opsMetadata" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProjectMetadata projectMetadata;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OpsMetadata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpsSystem() {
        return this.opsSystem;
    }

    public OpsMetadata opsSystem(String opsSystem) {
        this.setOpsSystem(opsSystem);
        return this;
    }

    public void setOpsSystem(String opsSystem) {
        this.opsSystem = opsSystem;
    }

    public String getRentId() {
        return this.rentId;
    }

    public OpsMetadata rentId(String rentId) {
        this.setRentId(rentId);
        return this;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public OpsMetadata createTime(String createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public OpsMetadata updateTime(String updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ProjectMetadata getProjectMetadata() {
        return this.projectMetadata;
    }

    public void setProjectMetadata(ProjectMetadata projectMetadata) {
        this.projectMetadata = projectMetadata;
    }

    public OpsMetadata projectMetadata(ProjectMetadata projectMetadata) {
        this.setProjectMetadata(projectMetadata);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpsMetadata)) {
            return false;
        }
        return id != null && id.equals(((OpsMetadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpsMetadata{" +
            "id=" + getId() +
            ", opsSystem='" + getOpsSystem() + "'" +
            ", rentId='" + getRentId() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
