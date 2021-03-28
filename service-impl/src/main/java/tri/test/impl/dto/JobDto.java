package tri.test.impl.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JobDto {
    private String uid;
    private String name;
    private String dataJson;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String data) {
        this.dataJson = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JobDto)) {
            return false;
        }

        JobDto jobDto = (JobDto) o;

        return new EqualsBuilder()
                .append(uid, jobDto.uid)
                .append(name, jobDto.name)
                .append(dataJson, jobDto.dataJson)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uid)
                .append(name)
                .append(dataJson)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uid", uid)
                .append("name", name)
                .append("dataJson", dataJson)
                .toString();
    }
}
