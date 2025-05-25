package com.example.aot_data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Objects;

@SpringBootApplication
public class AotDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(AotDataApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(CatRepository repository) {
        return _ -> {
            for (var n : "Tom,Jerry".split(","))
                repository.save(new Cat(null, n));
//            repository.findByName("Tom").forEach(System.out::println);
        };
    }
}

@Controller
@ResponseBody
class CatController {

    private final CatRepository repository;

    CatController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cats")
    Collection<Cat> cats() {
        return repository.findByName("Tom");
    }
}

interface CatRepository extends JpaRepository<Cat, Integer> {

    Collection<Cat> findByName(String name);
}

@Entity
class Cat {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) && Objects.equals(name, cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    public Integer id;

    public String name;

    public Cat() {
    }

    public Cat(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}