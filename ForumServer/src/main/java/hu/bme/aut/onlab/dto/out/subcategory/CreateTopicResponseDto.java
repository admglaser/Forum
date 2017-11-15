package hu.bme.aut.onlab.dto.out.subcategory;

import hu.bme.aut.onlab.dto.out.PostResponseDto;

public class CreateTopicResponseDto extends PostResponseDto{

    private int topic;

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }
}
