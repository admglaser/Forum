package hu.bme.aut.onlab.dto.in.topic;


public class CreatePostRequestDto {

    private int topic;
    private String quote;
    private String text;
    private int quotePostNumber;

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuotePostNumber() {
        return quotePostNumber;
    }

    public void setQuotePostNumber(int quotePostNumber) {
        this.quotePostNumber = quotePostNumber;
    }
}
