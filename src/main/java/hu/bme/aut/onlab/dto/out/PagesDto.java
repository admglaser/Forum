package hu.bme.aut.onlab.dto.out;

public class PagesDto {

    private String text;
    private String link;
    private boolean active;

    public PagesDto(String text, String link, boolean active) {
        this.link = link;
        this.active = active;
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
