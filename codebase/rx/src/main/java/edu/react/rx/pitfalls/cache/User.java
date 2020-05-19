package edu.react.rx.pitfalls.cache;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    Long id;

    String firstName;

    String lastName;

    Integer age;

    String divisionNumber;


}
