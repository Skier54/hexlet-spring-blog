package hexlet.code.mapper;

import hexlet.code.dto.dtoUser.UserCreateDTO;
import hexlet.code.dto.dtoUser.UserDTO;
import hexlet.code.dto.dtoUser.UserUpdateDTO;
import hexlet.code.model.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserCreateDTO userDTO);

    void toEntityUpdate(UserUpdateDTO userDTO, @MappingTarget User user);
}

//@Component
//public class UserMapper {
//    public UserDTO toDTO(User user) {
//        var dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setFirstName(user.getFirstName());
//        dto.setLastName(user.getLastName());
//        dto.setEmail(user.getEmail());
//        return dto;
//    }
//
//    public User toEntity(UserCreateDTO userDTO) {
//        var user = new User();
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        return user;
//    }
//
//    public User toEntityUpdate(UserUpdateDTO userDTO, User user) {
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setUpdatedAt(LocalDate.from(LocalDateTime.now()));
//        //user.setEmail(userDTO.getEmail());
//        return user;
//    }
//}
