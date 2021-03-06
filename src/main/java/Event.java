import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Event {

    private String id;

    private String state;

    private String type;

    private String host;

    private Long timestamp;

}
