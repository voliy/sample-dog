package voliy.samples.dog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import java.util.Collection;

@Controller
public class DogController {
    private DogService dogService;

    @Autowired
    public void setDogService(DogService dogService) {
        this.dogService = dogService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dogs")
    @ResponseBody
    public Collection<Dog> dogs() {
        return dogService.dogs();
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
