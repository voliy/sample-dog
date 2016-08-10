package voliy.samples.dog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import java.util.Collection;

@Controller
public class DogController {
    private DogService dogService;

    DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dog")
    @ResponseBody
    public Collection<Dog> dogs() {
        return dogService.dogs();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dog/{id}")
    @ResponseBody
    public Dog dog(@PathVariable int id) {
        return dogService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/dog")
    @ResponseBody
    public void addDog(@RequestBody Dog dog) {
        dogService.add(dog);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/dog/{id}")
    @ResponseBody
    public void deleteDog(@PathVariable int id) {
        dogService.delete(id);
    }
}
