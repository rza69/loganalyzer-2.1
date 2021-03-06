import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Log {

    private String id;

    private Long duration;

    private String type;

    private String host;

    private boolean alert;
}
