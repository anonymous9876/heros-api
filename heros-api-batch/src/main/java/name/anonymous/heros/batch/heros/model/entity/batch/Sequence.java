package name.anonymous.heros.batch.heros.model.entity.batch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Sequence implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2693304511757829025L;

    @Id
    private String name;
    private Long seq;
    private Date lastChange;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getLastChange() {
        return lastChange;
    }
    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }
    public Long getSeq() {
        return seq;
    }
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
}
