package cat.itacademy.s04.t01.userapi.models;

import java.util.UUID;

public record User(UUID id, String name, String email) {

}
