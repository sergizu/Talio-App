package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Objects;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TDList tdList = (TDList) o;
        return id == tdList.id && Objects.equals(title, tdList.title) && Objects.equals(list, tdList.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, list);
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

    public ArrayList<Card> getList() {
        return list;
    }

    public void setList(ArrayList<Card> list) {
        this.list = list;
    }
}
