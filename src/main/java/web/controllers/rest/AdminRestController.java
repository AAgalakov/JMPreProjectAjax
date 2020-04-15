package web.controllers.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.service.UserService;

@RestController
@RequestMapping("/rest")
public class AdminRestController {

    private UserService userService;

    public AdminRestController (UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersRest() {
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<?> getUser(@PathVariable("user-id") Long id) {
        return new ResponseEntity<>(new UserDto(userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping("/userAdd")
    public ResponseEntity<?> addUserRest(@RequestBody UserDto userDto) {
        if (userService.addUser(userDto)){
            return ResponseEntity.ok(new UserDto(userService.getUserByName(userDto.getName())));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUserRest(@PathVariable("user-id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        if (userService.updateUser(userDto)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
