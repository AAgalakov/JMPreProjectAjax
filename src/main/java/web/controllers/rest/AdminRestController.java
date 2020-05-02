package web.controllers.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.service.ServiceAbstr;

@RestController
@RequestMapping("/rest")
public class AdminRestController {

    private final ServiceAbstr<UserDto> serviceAbstr;

    public AdminRestController(ServiceAbstr<UserDto> serviceAbstr) {
        this.serviceAbstr = serviceAbstr;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersRest() {
        return new ResponseEntity<>(serviceAbstr.allEntity(), HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<?> getUser(@PathVariable("user-id") Long id) {
        return new ResponseEntity<>(serviceAbstr.getEntityById(id), HttpStatus.OK);
    }

    @PostMapping("/userAdd")
    public ResponseEntity<?> addUserRest(@RequestBody UserDto userDto) {
        if (serviceAbstr.addEntity(userDto)) {
            return ResponseEntity.ok(serviceAbstr.getEntityByName(userDto.getName()));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @DeleteMapping("/delete/{user-id}")
    public ResponseEntity<?> deleteUserRest(@PathVariable("user-id") Long id) {
        serviceAbstr.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        if (serviceAbstr.updateEntity(userDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
