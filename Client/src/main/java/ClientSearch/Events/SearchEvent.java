package ClientSearch.Events;

public class SearchEvent {
    String searchedItem;

    public SearchEvent(String searchedItem) {
        this.searchedItem = searchedItem;
    }

    public String getSearchedItem() {
        return searchedItem;
    }
}
