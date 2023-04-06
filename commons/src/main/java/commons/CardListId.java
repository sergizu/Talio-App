package commons;

public class CardListId {
    public long boardId;//might be handy later
    public Card card;
    public long listId;
    public CardListId() {}
    public CardListId(Card card,long listId){
        this.card = card;
        this.listId = listId;
    }

    public CardListId(Card card, long listId, long boardId){
        this.card = card;
        this.listId = listId;
        this.boardId = boardId;
    }
}
