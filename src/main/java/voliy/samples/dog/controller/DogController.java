package voliy.samples.dog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class DogController {
    private final DogService dogService;

    DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processValidationError(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getBindingResult().getFieldErrors(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dog")
    public Collection<Dog> dogs() {
        return dogService.dogs();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dog/{id}")
    public Dog dog(@PathVariable int id) {
        return dogService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/dog")
    public Dog addDog(@Valid @RequestBody Dog dog) {
        return dogService.add(dog);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/dog")
    public void updateDog(@Valid @RequestBody Dog dog) {
        dogService.update(dog);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/dog/{id}")
    public void deleteDog(@PathVariable int id) {
        dogService.delete(id);
    }
}
