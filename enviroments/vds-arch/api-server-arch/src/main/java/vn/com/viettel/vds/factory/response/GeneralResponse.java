package vn.com.viettel.vds.factory.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Format of response returned to client
 *
 * @author thanhnd44
 * @param <T>
 */

@Getter
@Setter
@NoArgsConstructor
public class GeneralResponse<T>  {
    @JsonProperty("status")
    private ResponseStatus status;

    @JsonProperty("data")
    private T data;

    public GeneralResponse(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" + "status=" + status +
                ", data=" + data.toString() +
                '}';
    }
}