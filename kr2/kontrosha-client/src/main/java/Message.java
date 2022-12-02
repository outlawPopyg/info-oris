import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String message;
    private int key;
}
