package com.emoby.mph.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.Content} entity.
 */
public class ContentDTO implements Serializable {
    
    private Long id;

    @Lob
    private byte[] data;

    private String dataContentType;
    @Lob
    private String text;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDTO)) {
            return false;
        }

        return id != null && id.equals(((ContentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
