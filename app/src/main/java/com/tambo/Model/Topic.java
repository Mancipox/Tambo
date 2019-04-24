package com.tambo.Model;

import java.io.Serializable;

public class Topic implements Serializable {

    private Integer topicId;
    private String description;

    public Topic(){

    }

    public Topic(Integer topicId) {
        this.topicId = topicId;
    }

    public Topic(Integer topicId, String description) {
        this.topicId = topicId;
        this.description = description;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (topicId != null ? topicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Topic)) {
            return false;
        }
        Topic other = (Topic) object;
        if ((this.topicId == null && other.topicId != null) || (this.topicId != null && !this.topicId.equals(other.topicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tamboserver.exceptions.Topic[ topicId=" + topicId + " ]";
    }
}
