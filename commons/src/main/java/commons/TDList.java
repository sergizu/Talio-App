package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class TDList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private ArrayList<Card> list; //Maybe an arrayList isn't the best data structure

    private TDList() {

    }

    public TDList(String title) {
        this.title = title;
        list = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);  // I suggest we could use EqualsBuilder - reliable and shorter implementation
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, list);
    }

    public String toString(){
       return ToStringBuilder.reflectionToString(this,MULTI_LINE_STYLE);} //added toString

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Card> getList() {
        return list;
    }

    public void setList(ArrayList<Card> list) {
        this.list = list;
    }
}
