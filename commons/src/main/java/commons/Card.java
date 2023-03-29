package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "list_id")
    @JsonBackReference
    public TDList list;

    public ArrayList<Subtask> nestedList;

    public Card() {

    }

    public Card(String title) {
        this.title = title;
        nestedList = new ArrayList<>();
    }

    //Equals method, decided to include both the ID and title(maybe we should rethink this later)
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);
        // I suggest we could use EqualsBuilder - reliable and shorter implementation
    }

    //Hashcode method, decided to include both the ID and title(maybe we should rethink this later)
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this); ///also reliable and less code
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

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

    public TDList getList() {
        return list;
    }

    public void setList(TDList list) {
        this.list = list;
    }

    public void addSubTask(Subtask subtask) {
        nestedList.add(subtask);
    }

    public void removeSubTask(Subtask subtask) {
        nestedList.remove(subtask);
    }

    public ArrayList<Subtask> getNestedList() {
        return nestedList;
    }

    public void setNestedList(ArrayList<Subtask> updatedList) {
        this.nestedList = updatedList;
    }

    public int amountSelected() {
        int i = 0;
        for(Subtask subtask : this.nestedList) {
            if(subtask.checked) {
                i++;
            }
        }
        return i;
    }

    public String cellFactory() {
        String titleString = this.title;
        if(nestedList.isEmpty()) {
            return titleString;
        }
        return this.title + "\n" + amountSelected() + "/" + nestedList.size();
    }
}
