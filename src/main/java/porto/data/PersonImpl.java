package porto.data;

import porto.data.api.Person;

public record PersonImpl(
    String CUI, 
    String name, 
    String username
) implements Person {

}
