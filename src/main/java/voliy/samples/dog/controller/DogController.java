package voliy.samples.dog.controller;

import org.springframework.web.bind.annotation.*;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.proxyexample.cglib.PersonCglib;
import voliy.samples.dog.proxyexample.jdk.Person;
import voliy.samples.dog.service.DogService;

import java.util.Collection;

@RestController
public class DogController {
    private final DogService dogService;

    DogController(DogService dogService) {
        this.dogService = dogService;
    }

    private Person jdkPerson;
    public void setJdkPerson(Person person) {
        this.jdkPerson = person;
    }

    private PersonCglib cglibPerson;
    public void setCglibPerson(PersonCglib cglibPerson) {
        this.cglibPerson = cglibPerson;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/proxy")
    public void proxy() {
        jdkPerson.test();
        cglibPerson.test();
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
    public Dog addDog(@RequestBody Dog dog) {
        return dogService.add(dog);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/dog")
    public void updateDog(@RequestBody Dog dog) {
        dogService.update(dog);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/dog/{id}")
    public void deleteDog(@PathVariable int id) {
        dogService.delete(id);
    }
}
