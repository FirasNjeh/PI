package esprit.pi.demo.openai;


import lombok.Getter;

@Getter
public class Choice {
    private String text;
    private Integer index;
    private Integer logprobs;
    private String finish_reason;
}
