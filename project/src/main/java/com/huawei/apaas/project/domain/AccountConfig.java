package com.huawei.apaas.project.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AccountConfig.
 */
@Entity
@Table(name = "account_config")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "git_hub_user")
    private String gitHubUser;

    @Column(name = "git_hub_pwd")
    private String gitHubPwd;

    @Column(name = "git_lab_user")
    private String gitLabUser;

    @Column(name = "git_lab_pwd")
    private String gitLabPwd;

    @Column(name = "gitee_user")
    private String giteeUser;

    @Column(name = "gitee_pwd")
    private String giteePwd;

    @Column(name = "docker_hub_user")
    private String dockerHubUser;

    @Column(name = "dock_hub_pwd")
    private String dockHubPwd;

    @NotNull
    @Column(name = "rend_id", nullable = false)
    private String rendId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGitHubUser() {
        return this.gitHubUser;
    }

    public AccountConfig gitHubUser(String gitHubUser) {
        this.setGitHubUser(gitHubUser);
        return this;
    }

    public void setGitHubUser(String gitHubUser) {
        this.gitHubUser = gitHubUser;
    }

    public String getGitHubPwd() {
        return this.gitHubPwd;
    }

    public AccountConfig gitHubPwd(String gitHubPwd) {
        this.setGitHubPwd(gitHubPwd);
        return this;
    }

    public void setGitHubPwd(String gitHubPwd) {
        this.gitHubPwd = gitHubPwd;
    }

    public String getGitLabUser() {
        return this.gitLabUser;
    }

    public AccountConfig gitLabUser(String gitLabUser) {
        this.setGitLabUser(gitLabUser);
        return this;
    }

    public void setGitLabUser(String gitLabUser) {
        this.gitLabUser = gitLabUser;
    }

    public String getGitLabPwd() {
        return this.gitLabPwd;
    }

    public AccountConfig gitLabPwd(String gitLabPwd) {
        this.setGitLabPwd(gitLabPwd);
        return this;
    }

    public void setGitLabPwd(String gitLabPwd) {
        this.gitLabPwd = gitLabPwd;
    }

    public String getGiteeUser() {
        return this.giteeUser;
    }

    public AccountConfig giteeUser(String giteeUser) {
        this.setGiteeUser(giteeUser);
        return this;
    }

    public void setGiteeUser(String giteeUser) {
        this.giteeUser = giteeUser;
    }

    public String getGiteePwd() {
        return this.giteePwd;
    }

    public AccountConfig giteePwd(String giteePwd) {
        this.setGiteePwd(giteePwd);
        return this;
    }

    public void setGiteePwd(String giteePwd) {
        this.giteePwd = giteePwd;
    }

    public String getDockerHubUser() {
        return this.dockerHubUser;
    }

    public AccountConfig dockerHubUser(String dockerHubUser) {
        this.setDockerHubUser(dockerHubUser);
        return this;
    }

    public void setDockerHubUser(String dockerHubUser) {
        this.dockerHubUser = dockerHubUser;
    }

    public String getDockHubPwd() {
        return this.dockHubPwd;
    }

    public AccountConfig dockHubPwd(String dockHubPwd) {
        this.setDockHubPwd(dockHubPwd);
        return this;
    }

    public void setDockHubPwd(String dockHubPwd) {
        this.dockHubPwd = dockHubPwd;
    }

    public String getRendId() {
        return this.rendId;
    }

    public AccountConfig rendId(String rendId) {
        this.setRendId(rendId);
        return this;
    }

    public void setRendId(String rendId) {
        this.rendId = rendId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountConfig)) {
            return false;
        }
        return id != null && id.equals(((AccountConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountConfig{" +
            "id=" + getId() +
            ", gitHubUser='" + getGitHubUser() + "'" +
            ", gitHubPwd='" + getGitHubPwd() + "'" +
            ", gitLabUser='" + getGitLabUser() + "'" +
            ", gitLabPwd='" + getGitLabPwd() + "'" +
            ", giteeUser='" + getGiteeUser() + "'" +
            ", giteePwd='" + getGiteePwd() + "'" +
            ", dockerHubUser='" + getDockerHubUser() + "'" +
            ", dockHubPwd='" + getDockHubPwd() + "'" +
            ", rendId='" + getRendId() + "'" +
            "}";
    }
}
