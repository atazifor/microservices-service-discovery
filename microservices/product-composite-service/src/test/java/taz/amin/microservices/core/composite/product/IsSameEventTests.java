package taz.amin.microservices.core.composite.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import taz.amin.api.core.product.Product;
import taz.amin.api.event.Event;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static taz.amin.api.event.Event.Type.CREATE;
import static taz.amin.api.event.Event.Type.DELETE;
import static taz.amin.microservices.core.composite.product.IsSameEvent.sameEventExceptCreatedAt;

public class IsSameEventTests {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testEventObjectCompare() throws JsonProcessingException {

        // Event #1 and #2 are the same event, but occurs as different times
        // Event #3 and #4 are different events
        Event<Integer, Product> event1 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
        Event<Integer, Product> event2 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
        Event<Integer, Product> event3 = new Event<>(DELETE, 1, null);
        Event<Integer, Product> event4 = new Event<>(CREATE, 1, new Product(2, "name", 1, null));

        String event1Json = mapper.writeValueAsString(event1);

        assertThat(event1Json, is(sameEventExceptCreatedAt(event2)));
        assertThat(event1Json, not(sameEventExceptCreatedAt(event3)));
        assertThat(event1Json, not(sameEventExceptCreatedAt(event4)));
    }
}
