package guru.springframework.spring6reactiveexamples.repositories;

import guru.springframework.spring6reactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testmonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testGetByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testmapOperation() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>>  listMono = personFlux.collectList();

        listMono.subscribe(list -> {
           list.forEach(person -> {
               System.out.println(person.getFirstName());
           });
        });
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Fiona"))
                .subscribe(person -> System.out.println(person.getLastName()));
    }

    @Test
    void testGetById() {
        Mono<Person> fionaMono = personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Fiona"))
                .next();

        fionaMono.subscribe(person -> System.out.println(person.getFirstName()));
    }
}